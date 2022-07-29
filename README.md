# react-native-m2m-sdk

## Getting started

Install the RN Bridge by using the below command

```
npm install https://github.com/devapps-systems/react-native-m2m-sdk --save
```


## Setup for iOS

1. Open the application’s info.plist and add below keys. Replace existing if required

- `UIViewControllerBasedStatusBarAppearance` with a value of `NO`
- `NSLocationAlwaysUsageDescription` with a string value of the message you would like to be displayed to the user
- `NSLocationWhenInUseUsageDescription` with a string value of the message you would like to be displayed to the user
- `NSLocationAlwaysAndWhenInUseUsageDescription` with a string value of the message you would like to be displayed to the user
- `UIBackgroundModes` - Item 0 = `remote-notification`
- `NSAppTransportSecurity` - Add a key named `NSAllowsArbitraryLoads` with a value of `YES`
- `NSUserTrackingUsageDescription` with a string value of the message you would like to be displayed to the user

2. Open the AppDelegate.m file and add the below import
```
#import <M2MSDK/M2MBeaconMonitor.h>
```

3. Add the below line to `didFinishLaunchingWithOptions` method:

```
[M2MBeaconMonitor initWithApplicationUuid:@"YOUR_APPLICATION_UUID" andDelegate:nil];
```

4. Then add the below method towards the end of the AppDelegate.m file

```
-(void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
  [M2MBeaconMonitor handlePushNotification:userInfo withCompletionHandler:completionHandler];
}
```

## Setup Android 

1. Open the `settings.gradle` file from the project and add the below content at the bottom:
```
dependencyResolutionManagement {
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositoriesMode.set(org.gradle.api.initialization.resolve.RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven {
            credentials{
                username 'm2m_pub'
                password 'Cpts1350!'
            }
            url 'https://artifacts.inmarket.com/artifactory/sun'
        }
    }
}
```

2. Inside the project level `build.gradle` file, add below section to the `respositories` under `allprojects`:
```
maven {
    credentials{
        username 'm2m_pub'
        password 'Cpts1350!'
    }
    url 'https://artifacts.inmarket.com/artifactory/sun'
}
```

3. Add the below lines to the `dependencies` section of the app module's build.gradle file
```
implementation files("../../node_modules/react-native-m2m-sdk/android/aar/m2msdk-google-16.0.0-3.65.570.aar")

implementation 'com.google.android.gms:play-services-base:18.1.0'
implementation 'com.google.android.gms:play-services-ads:21.1.0'

implementation 'com.google.dagger:dagger:2.28.3'
implementation 'com.google.dagger:dagger-android-support:2.11'
implementation 'com.jakewharton.timber:timber:4.7.1'
```

4. Open `MainApplication.java` and add the below import:
```
import com.inmarket.m2m.M2MBeaconMonitor;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.graphics.Color;
```

5. In the `onCreate` method, add the below code:
```
M2MBeaconMonitor.initApplication(this, "YOUR_APPLICATION_UUID");

String id = "m2m_sdk_channel";
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    CharSequence name = "M2M SDK Channel";
    String description = "M2M SDK Notification Channel";
    int importance = NotificationManager.IMPORTANCE_HIGH;
    NotificationChannel mChannel = new NotificationChannel(id, name, importance);
    mChannel.setDescription(description);
    mChannel.enableLights(true);
    mChannel.setLightColor(Color.RED);
    mChannel.enableVibration(true);
    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
    mNotificationManager.createNotificationChannel(mChannel);
}

// mandatory if you target O or beyond
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    M2MBeaconMonitor.setNotificationChannelId(id);
}
```

6. Inside the project level build.gradle file, add the below classpath inside `dependencies`
```
classpath 'com.google.gms:google-services:4.3.13'
```

7. In the app module's build.gradle file add the below plugin at the top:
```
apply plugin: 'com.google.gms.google-services'
```

8. In the app module's AndroidManifest.xml file, add the below lines:
```
 <meta-data
        android:name="com.google.android.gms.version"
        android:value="@integer/google_play_services_version" />
```

9. Create a integers.xml file under `res/values` with the below content:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <integer name="google_play_services_version">12451000</integer>
</resources>
```

## Usage
```javascript
import M2MSdk from 'react-native-m2m-sdk';
```
