# react-native-m2m-sdk

## Getting started

`$ npm install https://github.com/devapps-systems/react-native-m2m-sdk --save`


### for iOS

1. Open the application’s info.plist and add below keys. Replace existing if required

- `UIViewControllerBasedStatusBarAppearance` with a value of `NO`
- `NSLocationAlwaysUsageDescription` with a string value of the message you would like to be displayed to the user
- `NSLocationWhenInUseUsageDescription` with a string value of the message you would like to be displayed to the user
- `NSLocationAlwaysAndWhenInUseUsageDescription` with a string value of the message you would like to be displayed to the user
- `UIBackgroundModes` - Item 0 = `remote-notification`
- `NSAppTransportSecurity` - Add a key named `NSAllowsArbitraryLoads` with a value of `YES`

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

### for Android 

## Usage
```javascript
import M2MSdk from 'react-native-m2m-sdk';
```
