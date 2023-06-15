import java.io.FileReader
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.mukesh.template"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.mukesh.template"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        val propertiesFile = readPropertiesFile()
        //Debug App Handling
        getByName("debug") {
            isMinifyEnabled  = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"${"https://dummyjson.com/"}\"")
        }

        //Release App Handling
        getByName("release") {
            isMinifyEnabled  = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"${"https://dummyjson.com/"}\"")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    //This allows the Hilt annotation processors to be isolating so they are only invoked when necessary
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

    //Swipe Refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //Activity and Fragment
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")

    //Sdp Dependency
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    //Mukesh OTP View
    implementation("com.github.rajputmukesh748:MukeshOtpView:1.0.0")

    //Navigation Graph
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    //Coroutines and LifeCycle Libraries
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha06")
    implementation("androidx.datastore:datastore-core:1.0.0-alpha06")

    //Easy Data Store
    implementation("com.github.rajputmukesh748:EasyDataStore:1.0.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.14.2")
}

kapt {
    correctErrorTypes = true
}



/**
 * Get Properties File
 * */
fun readPropertiesFile() : Properties? {
    val properties = Properties()
    val localPropertiesFile = File("config.properties")
    if (!localPropertiesFile.exists()) return null
    properties.load(FileReader(localPropertiesFile))
    return properties
}


/**
 * Get Properties Key
 * */
fun Properties?.getPropertyKey(key: String): String = this?.getProperty(key).toString()