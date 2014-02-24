Check out this blog post - http://kgriff.posterous.com/building-a-phonegap-plugin-for-android
From the root directory of this project
Give the path of the android.jar

This command will compile the code.

javac -d . -classpath \android-sdk-windows\platforms\android-8\android.jar;.\lib\gson-2.1.jar;.\lib\httpmime-4.1.3.jar;.\lib\json_simple-1.1.jar;.\lib\phonegap-0.9.3.jar;.\lib\signpost-commonshttp4-1.2.1.2.jar;.\lib\signpost-core-1.2.1.2.jar;.\lib\vzaar-java-api.jar .\src\com\vzaar\phonegap\VzaarPhoneGapPlugin.java

This command will make the jar of the vzaar phonegap plugin

jar -cvf phonegap-vzaar.jar com/vzaar/phonegap/*.class

Zip all the dependent jar (from the class path, other than android.jar) and vzaar.js from the assets/www directory and the distribution is ready.

