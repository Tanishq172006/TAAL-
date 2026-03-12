package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.auth.AuthRepository
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.Icons

@Composable
fun LoginSignupScreen(
    authRepository: AuthRepository,
    onGoogleSignInClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isLoginMode) "Welcome Back" else "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password" ,color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    errorMessage = null

                    val result = if (isLoginMode) {
                        authRepository.signInWithEmail(email, password)
                    } else {
                        authRepository.signUp(email, password)
                    }

                    result.onSuccess {
                        onLoginSuccess()
                    }.onFailure {
                        errorMessage = it.message ?: "Authentication failed"
                    }

                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text(if (isLoginMode) "Login" else "Sign Up")
            }
        }

        TextButton(onClick = { isLoginMode = !isLoginMode }) {
            Text(
                text = if (isLoginMode) "Don't have an account? Sign Up"
                else "Already have an account? Login",
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                onGoogleSignInClick()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Sign in with Google", color = Color.White)
            }
        }
    }
}
class MockAuthRepository : AuthRepository {
    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> = Result.success(Unit)
    override suspend fun signUp(email: String, password: String): Result<Unit> = Result.success(Unit)
    override suspend fun signInWithGoogle(): Result<Unit> = Result.success(Unit)
    override suspend fun firebaseAuthWithGoogle(idToken: String): Result<Unit> = Result.success(Unit)

    override suspend fun signOut() {}

    override fun getCurrentUserId(): String? = "mock_user_123"
}
@Preview
@Composable
fun LoginSignupScreenPreview() {
    MaterialTheme {
        Surface(color = Color(0xFF121212)) {
            LoginSignupScreen(
                authRepository = MockAuthRepository(),
                onGoogleSignInClick = {  },
                onLoginSuccess = {  }
            )
        }
    }
}