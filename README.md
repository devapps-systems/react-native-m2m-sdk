# react-native-m2m-sdk

## Getting started

Install the RN Bridge by using the below command

```
npm install https://github.com/devapps-systems/react-native-m2m-sdk --save
cd ios && pod install && cd ..
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

## Setup for Android 

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

2. Inside the project level `build.gradle` file, add below section to the `repositories` under `allprojects`:
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
implementation (group: 'com.inmarket', name: 'm2msdk-google-18.0.0', version: '3.69.582') {
    transitive = true
}
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


## Usage
```javascript
import M2MSdk from 'react-native-m2m-sdk';

// Add the event listeners
React.useEffect(() => {
    M2MSdkModule.addEventListener("onM2mDecisionWithData", _onM2mDecisionWithData);
    M2MSdkModule.addEventListener("onM2MServiceStarted", _onM2MServiceStarted);
    M2MSdkModule.addEventListener("onM2MServiceStopped", _onM2MServiceStopped);
    M2MSdkModule.addEventListener("didShowEngagement", _didShowEngagement);
    M2MSdkModule.addEventListener("didDismissEngagement", _didDismissEngagement);
    M2MSdkModule.addEventListener("didReceiveEngagement", _didReceiveEngagement);
    M2MSdkModule.addEventListener("onError", _onError);
    M2MSdkModule.addEventListener("onWebViewIntegrityError", _onWebViewIntegrityError);
    M2MSdkModule.addEventListener("engagementNotAvailable", _engagementNotAvailable);
    M2MSdkModule.addEventListener("didGetAvailableOpps", _didGetAvailableOpps);
    M2MSdkModule.addEventListener("didReceiveDetection", _didReceiveDetection);
    return () => {
      M2MSdkModule.removeEventListener("onM2mDecisionWithData", _onM2mDecisionWithData);
      M2MSdkModule.removeEventListener("onM2MServiceStarted", _onM2MServiceStarted);
      M2MSdkModule.removeEventListener("onM2MServiceStopped", _onM2MServiceStopped);
      M2MSdkModule.removeEventListener("didShowEngagement", _didShowEngagement);
      M2MSdkModule.removeEventListener("didDismissEngagement", _didDismissEngagement);
      M2MSdkModule.removeEventListener("didReceiveEngagement", _didReceiveEngagement);
      M2MSdkModule.removeEventListener("onError", _onError);
      M2MSdkModule.removeEventListener("onWebViewIntegrityError", _onWebViewIntegrityError);
      M2MSdkModule.removeEventListener("engagementNotAvailable", _engagementNotAvailable);
      M2MSdkModule.removeEventListener("didGetAvailableOpps", _didGetAvailableOpps);
      M2MSdkModule.removeEventListener("didReceiveDetection", _didReceiveDetection);
    };
  }, []);

  const _onM2MServiceStarted = () => {
    
  }

  const _onError = (error) => {
    
  }

  const _didReceiveDetection = (detection) => {
    
  }
  // And so on...
```

**Sample Call:**
```
const requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime = () => {
    M2MSdkModule.requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime(true)
      .then((response) => {
        showAlertMessage(JSON.stringify(respons, null, 4));
    });
}
```
