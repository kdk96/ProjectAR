buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Deps.androidGradlePlugin)
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
        classpath(kotlin("serialization", version = Versions.kotlin))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        repositories {
            maven("https://dl.bintray.com/haroncode/maven")
            maven("https://dl.bintray.com/kdk96/maven")
        }
    }
}
