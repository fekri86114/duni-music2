# DuniMusic2
A simple application to listen to music. This application is version 2 of DuniMusic application.

I made this app with my own design.

DuniMusic is an educational application to learn how to play music in android, ... .

Dunijet(https://www.dunijet.ir)

# Used
RecyclerView, viewBinding, CircleButton, SparkButton, Glide, AppCompat components, MediaPlayer, ...

# Dependencies
in `build.gradle (:app)`:

  add `viewBinding`:
  
    android {
      ...
      buildFeatures {
        viewBinding true
      }
    }
  
  add `libraries`:
  
    dependencies {
      ...
      // Circle image -->
      implementation 'de.hdodenhof:circleimageview:3.1.0'
      
      // Spark button -->
      implementation 'com.github.varunest:sparkbutton:1.0.6'
      
      // Glide - load image -->
      implementation 'com.github.bumptech.glide:glide:4.14.2'
      annotationProcessor 'com.github.bumptech.glide:compiler:4.14.2'
    }
 
in `build.properties (Projet Properties)`:
  
  ! add it below the `android.useAndroidX=true` !

    android.enableJetifier=true
  
in `settings.gradle (Project Settings)`:

    pluginManagement {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
    
    dependencyResolutionManagement {
      ...
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }




