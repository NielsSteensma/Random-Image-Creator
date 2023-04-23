![image info](./app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

# Random Image Creator

[Google Play Store](https://play.google.com/store/apps/details?id=com.randomimagecreator&hl=en_IN&gl=US)

An Android application that creates a configurable amount of random images.

Useful if you quickly need a big set of images on your Android device for testing purpose.

# GitHub Actions config
__Triggers__
- PR & PR commits triggers ./gradlew build and ./gradlew test. 
- Commit on master triggers ./gradlew publishApk

__Required env variables__
- Set public env variable 'CI' as true. Otherwise google-services.json will be ignored.
- Set secret env variable 'keystore_password', 'keystore_alias' and 'keystore_alias_password' so 
signed apk can be uploaded to Google Play.