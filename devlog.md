## TAAL
TAAL – which stands for (Tanishq, Ananya, Lavanya, Anshul). A beginner friendly music making software. Avaiable on all android, iOS, windows, MacOS and linux.

---

### What makes us unique from others
- Make music from anywhere.
- Easy to use interface, yeah even if you are not familiar with audio editing terminnologies.
- Different modes – Beginner, Expert, Master.
- Free and Open Source, made with ❤️

## Devlogs #0
*28-02-2026* ~by [Anshul Badhani](https://www.github.com/anshulbadhani)

Among all the brillant ideas discussed by our team. This one stood out the most and liked by everyone. All of us (our team) have experienced this, on random days we get a bit of motivation to make some dope music. But, the learning curve is too steep to even begin with something basic. Lots of terms related to audio editing, many types of file formats. Its all a mess and it feel discouraging us from making something which is our own. To tackle the same problem, we collectively decided to make an easy to use music software where we can make beats edit audio recordings.

Our main focus of this project would be the UX part, specially on phones. We, as a team agreed that in India most of the people use smartphones to create content. Specially, small creators because of various reasons. We believe that making music easier to create for a beginner would democratize content creation by making it more accessible (and we could also test cool ideas quickly :P).

Quickly summarizing and defining a very very rough roadmap and objectives.

#### Our Purpose
As mentioned above.

#### Target Audience
Beginners who just want to create something and test their goofy ideas without diving deep into how to use this software or wth this setting do.

#### Things we would keep in mind
- Ease of use > Number of features.
- Must work on smartphones.
- Documenting the development would be cool lol. So, we can look back at this.
- Complete this a few days before our college exams :(
- 30 minutes per member per day > 12 hour marathons.

#### Tech-Stack (not final though)
- Kotlin with cmp
- C++ or rust for performance critical code (using jni)
We will have more discussion about that along with repo setup tmrw.

Bye :P

## Devlog #1
*01-03-2026* ~by [Anshul Badhani](https://www.github.com/anshulbadhani)
Welcome back to our devlog, today is the first day of FOSSHACK. Here is what advances we made:
- [Lavanya](https://www.github.com/Lavanya24R) made some prototype UI designs.
- [Tanishq](https://github.com/Tanishq172006) and [Ananya](https://github.com/ananyatiwari05) worked on the inital setup for our project. Testing it on both Android and iOS respectively.
- [Anshul](https://www.github.com/anshulbadhani) got familiar with Kotlin.

Now, in today's meet we decided three things: What to do tomorrow, What will be our tech stack and what are the features we would have to implement before implementing our USP.

So, we decided that we will be working on Android studio (PandaOne) with Kotlin and C++ (or maybe Rust) for performace critical parts. For minimal implementation we would have to implement sounds importing/exporting features, tracks creation and editing feature and figure out how to save user's project state before working on other stuff. Our goal would be to make it modular so we can change stuff quickly.

For tomorrow. We are going to do this:
- [Tanishq](https://github.com/Tanishq172006) and [Ananya](https://github.com/ananyatiwari05) will work on menus which won't be changing drastically, like new project screen or beginner/intermediate or advanced user prompt.
- [Lavanya](https://www.github.com/Lavanya24R) will explore more ideas for UI and refine the existing designs.
- [Anshul](https://www.github.com/anshulbadhani) will be figuring out AudioEngine part and what would we require.

After a long chit chat after our meet we will be sleeping a good sleep after a tiring day :D

## Devlog #2  
*02-03-2026* ~by [Lavanya Rastogi](https://www.github.com/Lavanya24R)

Welcome back to our devlog! Day 2 was focused on strengthening our foundation — refining UI, setting up cross-platform support, and researching about the Audio Engine. The progress made by each were: 

- [Tanishq](https://github.com/Tanishq172006) built the beats activity skeleton and implemented its base UI design.
- [Ananya](https://github.com/ananyatiwari05) successfully set up Xcode on macOS for iOS development, added simulators, tested sample code to verify everything works properly, and cloned the project repository to ensure the project runs smoothly on iOS.
- [Anshul](https://www.github.com/anshulbadhani) worked on researching the Audio Engine architecture, focusing on: Smooth task scheduling, efficient audio playback handling and seamless application of effects like reverb, flanger etc.
- [Lavanya](https://www.github.com/Lavanya24R) designed UI layouts for laptop/PC, optimized mobile UI, explored FL Studio for inspiration, and studied other music apps to refine the instruments interface and overall UX.

 We decided that instead of building the Audio Engine components from scratch(not feasible within hackathon constraints) we will be using open-source technologies which will be more scalable and realistic. Therefore, the best primary option would be Media3, with FFmpeg as a fallback if required. For the UI, we will keep the paint and delete features in our app just like FL Studio, decided on what click does what for beats and keep horizontal mode as default for good instrument UI.

### For Tomorrow
- [Tanishq](https://github.com/Tanishq172006) will implement the second canvas screen, refactor and clean up the beats activity code
- [Ananya](https://github.com/ananyatiwari05) will work on the first two activity pages of the app (initial screens flow).
- [Anshul](https://www.github.com/anshulbadhani) will research on how to integrate Audio Engine libraries into our CMP app architecture.
- [Lavanya](https://www.github.com/Lavanya24R) will clone the repository and start implementing UI add-ons and improvements into the project

Day 2 gave us the base UI design with set up on different platforms. 

After a final run of the desktop version of the app we will be able to give up on our coffees and pick up our blankets ;)



## Devlog #3

*03-03-2026* ~by [Tanishq Pandey](https://github.com/Tanishq172006)

Welcome back to our devlog! Today focused on strengthening functionality, fixing cross-platform issues, and making the app more dynamic and stable. The progress made by each were:

- [Ananya](https://github.com/ananyatiwari05) corrected the audio files for iOS, created the first two pages shown when the app starts, and linked all the pages together to complete the initial flow.
- [Anshul](https://github.com/anshulbadhani) set up Android Studio and cloned the repository. The audio which previously played locally is now running through Media3. He also researched a low-latency C++ audio engine and created a demo to explore implementing it into the main app.
- [Lavanya](https://github.com/Lavanya24R) cloned the repository, refined the UI, added features like a metronome, and fixed the toggle UI for smoother interaction.
- [Tanishq](https://github.com/Tanishq172006) created the dialog box to add tiles, made the entire system dynamic, implemented save and create options, added toggle functionality, developed the second main page (canvas — beta version xD) where the main mixing will be done, and fixed desktop compatibility (now working smoothly).
We are now moving from static structure to dynamic functionality across all platforms.

### For Tomorrow

- [Anshul](https://github.com/anshulbadhani) will figure out the C++ low latency audio engine across all platforms and research how to implement a local database in CMP using Room.
- [Ananya](https://github.com/ananyatiwari05) will research how to implement brush-like functionality similar to FL Studio (mainly on Android) and explore implementing a local database in CMP using Room.
- [Lavanya](https://github.com/Lavanya24R) will research implementing a local database in CMP using Room and explore how waves can be implemented.
- [Tanishq](https://github.com/Tanishq172006) will research wave implementation and continue improving the working of the second screen.

Happy Holi to whoever is reading this :)



## Devlog #4
*04-03-2026* ~by [Ananya Tiwari](https://www.github.com/ananyatiwari05)

Happy Holi<3. Today was a mix of colors and code. We did play Holi (priorities balanced 😋), but we also pushed forward on some core architectural and feature research for our app.

- [Tanishq](https://github.com/Tanishq172006) Added the canvas and optimized its behavior across platforms. Implemented tiles system and introduced a long-press feature on piano tiles which opens a dedicated piano UI. Added persistance throughout the OS's.
- [Ananya](https://github.com/ananyatiwari05) Researched the Brush feature inspired by FL Studio’s piano roll workflow. Explored grid generation logic and piano roll grid structuring for better note placement precision.
- [Anshul](https://www.github.com/anshulbadhani) Explored different audio engine options suitable for low-latency music production apps and compared their integration feasibility with Kotlin Multiplatform.
- [Lavanya](https://www.github.com/Lavanya24R) Researched Room database, understanding its setup, dependencies, annotations, and how it can be structured cleanly within our project architecture.

For tomorrow. We plan to:
- [Tanishq](https://github.com/Tanishq172006) Implement long-press audio logic for instrument tiles. Add highlight color inversions. Introduce dropdowns on the second screen to allow instrument selection and tile number assignment.
- [Ananya](https://github.com/ananyatiwari05) Research database strategies- Firebase Authentication for user handling, SQLite for local storage, and deeper study of Room in Kotlin app development.
- [Anshul](https://www.github.com/anshulbadhani) Research and start implementing JUCE (or another suitable low-latency audio engine) and test basic sound output integration within the app.
- [Lavanya](https://www.github.com/Lavanya24R) Begin implementing Room database in the actual app structure.

Colorful and productive day ,, tataaa >.< 

## Devlogs #5
*05-03-2026* ~by [Anshul Badhani](https://www.github.com/anshulbadhani)
> What one programmer can do in one month, two programmers can do in two months.
– Fred Brooks

Thank god, four of us run on separate threads XD.

 After an amazing Holi yesterday. We started the day by discussing more cool ideas for the project. Sharing [reels](https://www.instagram.com/reel/DU-caowEplr/) for inspiration. We were really excited seeing that. Our excitement went through the roof, when [Tanishq](https://github.com/Tanishq172006) shared the UI skeleton of drum beat editor, where we can make the drum beat however we want to and use it. In the meantime [Lavanya](https://www.github.com/Lavanya24R) while battling with storage issues on her machine ;) set up SQLDelight, added it's plugins and dependencies and made the `beats.sq` file for queries. [Anshul](https://www.github.com/anshulbadhani) finally found the perfect tool to make an Audio Engine for our purpose. Made a C wrapper around it and tried to expose it via JNI. And [Ananya](https://github.com/ananyatiwari05) figured about the firebase db for authentication and cloud storage and sqlite for local storage and how to modify it to make it compatible for multiplatform.

That's too much work for a single day by our team. 
#### Here is a quick summary of what we did:
- [Tanishq](https://github.com/Tanishq172006)
    - Added the import button in the dialog box.
    - Made the pipeline for all the mains.
    - Made the drum beat editor where you can make a drum beat according to you and edit it.
    - Made the UI and waveform for the beateditor for the canvas screen.
    - Updated the audioplayer block for all so that it works flexibly.
- [Lavanya](https://www.github.com/Lavanya24R)
    - SQLDelight set up along with it's plugins and dependencies.
    - Made the `beats.sq` file.
    - Linked it with the UI.
    - Wrote some tests.
- [Anshul](https://www.github.com/anshulbadhani)
    - Testing of [mackron/miniaudio](https://github.com/mackron/miniaudio) for our purposes.
    - Setting up of gradle files for NDK and CMake.
    - Wrote a simple C wrapper for using it with Kotlin via JNI.
- [Ananya](https://github.com/ananyatiwari05)
    - Studied about Firebase DB Auth for cloud storage.
    - And SQLite for offline storage as a fallback option for users.
    - How to make that work on multiple platforms with similar consistency.

#### For tomorrow
- [Tanishq](https://github.com/Tanishq172006)
    - Adding of save tile feature. (He already did that as of writing this)
    - Update the piano dialogbox and will make the UI for others.
    - Fix the import thing across platforms.
    - Research on how we can edit audio.
    - Make the canvas screen audio editor working using `ffmpeg` and generate realtime audios and edit it

- [Lavanya](https://www.github.com/Lavanya24R) will make the kotlin file and connect it to the ui and do its testing
- [Ananya](https://github.com/ananyatiwari05) will start implementing the firebase thing to our app and make a profile option for the same with settings and different options
- [Anshul](https://www.github.com/anshulbadhani) will link the audioengine with UI and implement advanced audio editing features.

Here is a video of our application till now:

https://github.com/user-attachments/assets/a14a79d8-fedf-42d6-bb51-016aab62aaf2

Byeee ><<3



## Devlog #6
*07-03-2026* ~by [Lavanya Rastogi](https://www.github.com/Lavanya24R)

Welcome back to our devlog! Day 7 was mainly about UI fixes, some funtionality addition, local database setup and audio engine manifestation. Our individual blood(not literally;)), sweat and tears were shed as follows:

- [Tanishq](https://github.com/Tanishq172006) fixed the issues in existing UI. Added code for each beat tile to have its own beat entity and save the beat in the tile created by us. Created the midi and piano UI. Researched about general song fonts and got an open source technology for generating each piano note. Implemented the technology for jvmMain(Desktop app).
- [Ananya](https://github.com/ananyatiwari05) made a project on firebase, researched and got a repository on how to use it cross platform. Set up email and password signup and login for our app along with Google OAuth authentication. Added a profile icon in the UI and designed the login page. Made the authentication interface in commonMain and implemented it in androidMain and iOSMain.
- [Anshul](https://www.github.com/anshulbadhani) watched a youtube video and set up a submodule which would generate CMake, C and C++ files automatically and help us in making our Audio Engine but later, on further research, got to know that Java Native Interface(JNI) would be better for our Audio Engine. Therefore, read more about JNI networking and learned how to implement it using expect and actual classes.
- [Lavanya](https://www.github.com/Lavanya24R) studied and built the folder structure for SQLDelight and found its most stable version. Made the expect class in the commonMain and actual classes in androidMain, iOSMain etc. to implement it. Wrote queries for the database and studied the code to see the places from where they would be called.

 From UI to music, to database to engines(not literal ones again;)) we worked on all the main topics revolving around our app. (u.u)

### For Tomorrow
- [Tanishq](https://github.com/Tanishq172006) will make dialogue boxes for other instruments as well, look into the issues where our app is crashing, add the feature to highlight a tile if in use and add the metronome feature. Help Anshul in JNI if it posses a frustrative hairpull for him(>u<).
- [Ananya](https://github.com/ananyatiwari05) will implement the firebase database using rest APIs for webMain, finish login page UI, thoroughly test the working of authentication and will research about cloud storage.
- [Anshul](https://www.github.com/anshulbadhani) will continue the linking process of JNI in our app using expect and actual classes.
- [Lavanya](https://www.github.com/Lavanya24R) will design the logo of our app and write code to send and receive data to/from the database. Test it with some sample data. 

Day 7 added more functionalities to our app and made us proceed further towards our goal making us sleep along with snores.zzZZ


## Devlog #7
*07-03-2026* ~by [Anshul Badhani](https://www.github.com/anshulbadhani)
Yoo! (tired). This is 3.30 in the morning rn and we are constantly in the meeting rn. Fixing stupid version issues and merge conflicts. The team just can't wait to sleep rn :cry:. 

Keeping this very quick, here is what we did today:
- [Tanishq](https://github.com/Tanishq172006)
    - Fixed the crashes on desktop.
    - Basically the error was that it was crashing due to the linux innate audio engine.
    - Made it so that it works independently.
    - Added the UI changes for metronome and main screen for music play. Added highlighted tiles on playing the tiles. Added State and others for meteronome and fixed it for android. And Lots of debugging
- [Anshul](https://www.github.com/anshulbadhani)
    - Setup of CMake and C++ modules in actual project
    - Linking of wrappers implemented yesterday with a test compose Application
    - Figuring out a different structure from for the native code compared to yesterday
    - Studying about the [mackron/miniaudio](https://github.com/mackron/miniaudio)
    - Lots of debugging
- [Ananya](https://github.com/ananyatiwari05)
    - Added Firebase Authentication with support for Email–Password login and Google OAuth.
    - The authentication setup is completed for commonMain and Android in the Compose Multiplatform project.
    - Built the Profile button so users can log in or sign up anytime instead of being forced to authenticate at startup.
    - Also implemented the Login/Signup screen and connected it with the Firebase auth flow.
- [Lavanya](https://www.github.com/Lavanya24R)
    - Downgraded the kotlin version to avoid IDE crashes.
    - Created a bridge between .sq and .kt files to use the functions to access queries.
    - Studied the folder structure of SQLDelight and the .kt files generated by it.

For tomorrow:
- [Tanishq](https://github.com/Tanishq172006)
    - Simple fixes and add more UIs for other instruments.
    - Fixing on all platforms.
- [Anshul](https://www.github.com/anshulbadhani)
    - Will study about miniaudio api and its C code.
    - If time allows then I will try to figure out how to extend that API to include 𝓯𝓪𝓷𝓬𝓲𝓮𝓻 effects for advanced version of the software.
- [Ananya](https://github.com/ananyatiwari05)
    - Extend the authentication setup to iOS, macOS, Windows, and Linux to make it fully compatible across all Compose Multiplatform targets.
    - Start working on the dashboard interface and its core functionality.
- [Lavanya](https://www.github.com/Lavanya24R)
    - Will use the bridge between .sq and .kt files to connect the database to the frontend and store new beats in the database.
    - Also find C2 to C9 beats for piano.

Now, we will sleep and wake up day after tomorrow :)
Good night fellers!!



## Devlog #8
*08-03-2026* ~by [Tanishq Pandey](https://github.com/Tanishq172006)

Another day full of debugging, version fixing and platform setup. Gradle decided to be our biggest enemy today, but progress was made and things are slowly getting stable.

Keeping this short, here is what we did today:

- [Anshul](https://www.github.com/anshulbadhani)
    - Read through the API documentation to better understand how to implement it in the audio engine.

- [Lavanya](https://www.github.com/Lavanya24R)
    - Found the piano chords required for the app.
    - Worked a bit on designing the app logo.

- [Ananya](https://github.com/ananyatiwari05)
    - Started setting up Firebase Authentication for iOS.
    - Most of the setup is done, only a small issue remains which will be fixed and pushed tomorrow.

- [Tanishq](https://github.com/Tanishq172006)
    - Fist-fighting with Gradle since morning fixing dependency and version issues.
    - Fixed Android completely and it is now working properly.

For tomorrow:

- [Tanishq](https://github.com/Tanishq172006)
    - Fix the desktop build and make it work across all other platforms.
    - Add UIs for other dialogs.
    - Do some UI polishing and fixes.

- [Lavanya](https://www.github.com/Lavanya24R)
    - Add more tables in the database.
    - Start implementing the UI connected to those tables.

- [Anshul](https://www.github.com/anshulbadhani)
    - Study the `ma_engine` object from [miniaudio](https://github.com/mackron/miniaudio).

- [Ananya](https://github.com/ananyatiwari05)
    - Fix the remaining Firebase authentication issue for desktop.
    - Start creating the REST API for authentication.

    welp college started what else lavanya and ananya came back to hostel so we have now a chance for offline meets 🔥 , but now daily work will be way more fun and welp gradle 😢 kills me atm but i wont give up and we all will fight and comeback stronger 


## Devlog #9
*10-03-2026* ~by [Tanishq Pandey](https://github.com/Tanishq172006)

Today was the first day of college after the Holi break. ANDDD we had our first offline meet today. We decided to meet after our classes, but we bumped into each other before that and were sooo excited to see everyone!

Before the meeting we even went outside the campus and hung out together for a bit. IT WAS FUN!

Now coming to the real stuff.

The biggest achievement of the day: [Tanishq](https://github.com/Tanishq172006), after fighting with Gradle for two days straight, FINALLYYY fixed the Gradle issues.  
(It was a single line of code. Bruh!)

[Lavanya](https://www.github.com/Lavanya24R) made our cool logo while [Ananya](https://github.com/ananyatiwari05) and [Anshul](https://www.github.com/anshulbadhani) were suggesting different design ideas and improvements.

[Ananya](https://github.com/ananyatiwari05) finally fixed the Firebase authentication issue and  
[Anshul](https://www.github.com/anshulbadhani) continued exploring the **miniaudio API**.

After coming back to our respective homes, we had another meet and did some more work.

[Anshul](https://www.github.com/anshulbadhani) implemented a C function that takes the filepath of an audio file and plays the sound from the actual files instead of hardcoding them.

[Lavanya](https://www.github.com/Lavanya24R) and [Tanishq](https://github.com/Tanishq172006) fixed the merge conflicts on the GitHub repositories.

---

### For tomorrow

- [Anshul](https://www.github.com/anshulbadhani)
    - Work further on the audio engine API.

- [Ananya](https://github.com/ananyatiwari05)
    - Implement `AuthRepositoryImpl` for iOS.
    - Fix remaining dependency issues.

- [Lavanya](https://www.github.com/Lavanya24R)
    - Pull the latest changes.
    - Start working on SQLDelight integration.

- [Tanishq](https://github.com/Tanishq172006)
    - Work on making other UIs for other tiles
    - Continue UI improvements and debugging.
    - Start working on DSP for realtime wave formation.
    - fix pipelines for the importer.


Today was a hectic yet fruitful day, we achieved alot today , learnt alot today and moving forward with our heads up high!! 🚀
