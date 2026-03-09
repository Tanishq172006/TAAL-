#define MINIAUDIO_IMPLEMENTATION
#include <jni.h>
#include <android/log.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include "miniaudio.h"

#define LOG_TAG "MiniAudioJNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,  LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Persistent state
static ma_engine      g_engine         = {0};
static AAssetManager* g_assetManager   = nullptr;
static ma_sound       g_currentSound   = {0};
static ma_audio_buffer g_audioBuffer   = {0};
static void*          g_pDecodedData   = nullptr;
static bool           g_engineReady    = false;
static bool           g_soundPlaying   = false;


extern "C"
JNIEXPORT void JNICALL
Java_com_example_myapplication_NativeBridge_playAsset(
        JNIEnv* env,
        jobject thiz,
        jobject assetManagerObj,
        jstring jFilename
) {
    if (jFilename == nullptr) {
        LOGE("Filename is null");
        return;
    }

    const char* filename = env->GetStringUTFChars(jFilename, nullptr);
    if (filename == nullptr) {
        LOGE("Failed to get UTF chars from jstring");
        return;
    }

    // One-time init (engine + asset manager)
    if (!g_engineReady) {
        g_assetManager = AAssetManager_fromJava(env, assetManagerObj);
        if (g_assetManager == nullptr) {
            LOGE("Failed to acquire AAssetManager");
            env->ReleaseStringUTFChars(jFilename, filename);
            return;
        }

        ma_result result = ma_engine_init(nullptr, &g_engine);
        if (result != MA_SUCCESS) {
            LOGE("Engine init failed: %d", result);
            env->ReleaseStringUTFChars(jFilename, filename);
            return;
        }
        g_engineReady = true;
    }

    // Stop & cleanup previous
    if (g_soundPlaying) {
        ma_sound_stop(&g_currentSound);
        ma_sound_uninit(&g_currentSound);
        ma_audio_buffer_uninit(&g_audioBuffer);
        if (g_pDecodedData) {
            ma_free(g_pDecodedData, nullptr);
            g_pDecodedData = nullptr;
        }
        g_soundPlaying = false;
    }

    // Open the requested asset
    AAsset* asset = AAssetManager_open(g_assetManager, filename, AASSET_MODE_BUFFER);
    if (asset == nullptr) {
        LOGE("Cannot open asset: %s", filename);
        env->ReleaseStringUTFChars(jFilename, filename);
        return;
    }

    const void* rawData = AAsset_getBuffer(asset);
    ma_uint64 dataSize  = AAsset_getLength(asset);

    ma_decoder_config decoderConfig = ma_decoder_config_init(
            ma_format_f32,
            ma_engine_get_channels(&g_engine),
            ma_engine_get_sample_rate(&g_engine)
    );

    ma_uint64 framesDecoded = 0;
    ma_result result = ma_decode_memory(rawData, dataSize, &decoderConfig, &framesDecoded, &g_pDecodedData);
    AAsset_close(asset);

    if (result != MA_SUCCESS || g_pDecodedData == nullptr) {
        LOGE("Decode failed for %s: %d", filename, result);
        env->ReleaseStringUTFChars(jFilename, filename);
        return;
    }

    ma_audio_buffer_config bufConfig = ma_audio_buffer_config_init(
            ma_format_f32,
            decoderConfig.channels,
            framesDecoded,
            g_pDecodedData,
            nullptr
    );

    result = ma_audio_buffer_init(&bufConfig, &g_audioBuffer);
    if (result != MA_SUCCESS) {
        LOGE("Buffer init failed: %d", result);
        ma_free(g_pDecodedData, nullptr);
        env->ReleaseStringUTFChars(jFilename, filename);
        return;
    }

    result = ma_sound_init_from_data_source(&g_engine, &g_audioBuffer, 0, nullptr, &g_currentSound);
    if (result != MA_SUCCESS) {
        LOGE("Sound init failed: %d", result);
        ma_audio_buffer_uninit(&g_audioBuffer);
        ma_free(g_pDecodedData, nullptr);
        env->ReleaseStringUTFChars(jFilename, filename);
        return;
    }

    ma_sound_start(&g_currentSound);
    g_soundPlaying = true;
    LOGI("Playback started: %s", filename);

    env->ReleaseStringUTFChars(jFilename, filename);
}