// M2MSdk.h

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
@import M2MSDK;

@interface M2MSdk : RCTEventEmitter <RCTBridgeModule, M2MServiceDelegate>

@end
