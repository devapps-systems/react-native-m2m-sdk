// M2MSdk.m

#import "M2MSdk.h"

@implementation M2MSdk {
    bool _hasListeners;
}

RCT_EXPORT_MODULE()

-(NSArray<NSString *> *)supportedEvents
{
    return @[
        @"onM2mDecisionWithData",
        @"onM2MServiceStarted",
        @"onM2MServiceStopped",
        @"didShowEngagement",
        @"didDismissEngagement",
        @"didReceiveEngagement",
        @"onError",
        @"onWebViewIntegrityError",
        @"engagementNotAvailable",
        @"didGetAvailableOpps",
        @"didReceiveDetection",
        @"didGetScanOps",
        @"didGetProducts"
    ];
}

// Will be called when this module's first listener is added.
-(void)startObserving {
    _hasListeners = YES;
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    _hasListeners = NO;
}

-(void)sendEvent:(NSString*)eventName eventBody:(id)body
{
    if (_hasListeners) {
        [self sendEventWithName:eventName body:body];
    }
}
// //=====================================
// // 1. initWithApplicationUuid
// //=====================================
// RCT_EXPORT_METHOD(initWithApplicationUuid:(NSString*)applicationUuid :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
//     [M2MBeaconMonitor initWithApplicationUuid:applicationUuid andDelegate:self];
// }

//=====================================
// 2. getScanLocationsWithUserId
//=====================================
// RCT_EXPORT_METHOD(getScanLocationsWithUserId:(NSString*)userId :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
//     [M2MBeaconMonitor getScanLocationsWithUserId:userId andDelegate:self];
//     resolve(@{@"status": @"success"});
// }

// //=====================================
// // 3. getProductsForLocation
// //=====================================
// RCT_EXPORT_METHOD(getProductsForLocation:(NSString*)location :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)  {
//     [M2MBeaconMonitor getProductsForLocation:location andDelegate:self];
//     resolve(@{@"status": @"success"});
// }

//=====================================
// 4. setTagKeywords
//=====================================
RCT_EXPORT_METHOD(setTagKeywords:(NSString*)tagsString :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    if(tagsString == nil) {
        resolve(@{@"status": @"error", @"message": @"No tags provided."});
    }

    NSData *tagsData = [tagsString dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *tags = [NSJSONSerialization JSONObjectWithData:tagsData
                                      options:NSJSONReadingMutableContainers 
                                        error:nil];
    
    [M2MBeaconMonitor setTagKeyWords:tags];
    resolve(@{@"status": @"success"});
}

//=====================================
// 5. getTagKeywords
//=====================================
RCT_EXPORT_METHOD(getTagKeywords :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"success", @"tags": [M2MBeaconMonitor getTagKeywords]});
}

//=====================================
// 6. getAvailableOpps
//=====================================
RCT_EXPORT_METHOD(getAvailableOpps :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)  {
    [M2MBeaconMonitor getAvailableOpps:self];
    resolve(@{@"status": @"success"});
}

//=====================================
// 7. checkInToOpp
//=====================================
RCT_EXPORT_METHOD(checkInToOpp:(NSString*)placeId :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)  {
    [M2MBeaconMonitor checkInToOpp:placeId withDelegate:self];
    resolve(@{@"status": @"success"});
}

//=====================================
// 8. setPushToken
//=====================================
RCT_EXPORT_METHOD(setPushToken:(NSString *)pushToken :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSData *pushTokenData = [pushToken dataUsingEncoding:NSUTF8StringEncoding allowLossyConversion:NO];
    [M2MBeaconMonitor setDeviceTokenData:pushTokenData];
    resolve(@{@"status": @"success"});
}

//=====================================
// 9. stopService
//=====================================
RCT_EXPORT_METHOD(stopService :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [M2MBeaconMonitor stopService];
    resolve(@{@"status": @"success"});
}

//=====================================
// 10. startMonitoring
//=====================================
RCT_EXPORT_METHOD(startMonitoring :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [M2MBeaconMonitor startMonitoringWithDelegate:self];
    resolve(@{@"status": @"success"});
}

//=====================================
// 11. requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime
//=====================================
RCT_EXPORT_METHOD(requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime :(BOOL)flag :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)  {
    dispatch_async(dispatch_get_main_queue(), ^{
        [M2MBeaconMonitor requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime:flag];
    });
    resolve(@{@"status": @"success"});
}

//=====================================
// 12. requestWhenInUse
//=====================================
RCT_EXPORT_METHOD(requestWhenInUse :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)  {
    [M2MBeaconMonitor requestWhenInUseWithDelegate:self];
    resolve(@{@"status": @"success"});
}

//=====================================
// 13. requestAlways
//=====================================
RCT_EXPORT_METHOD(requestAlways :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)  {
    [M2MBeaconMonitor requestAlwaysWithDelegate:self];
    resolve(@{@"status": @"success"});
}

//=====================================
// 14. getM2MConfig
//=====================================
RCT_EXPORT_METHOD(getM2MConfig :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    M2MConfig* config = [M2MBeaconMonitor getM2MConfig];
    NSLog(@"Config: %@", config);
    resolve(@{@"status": @"success", @"config": [config dictionaryWithValuesForKeys:@[@"isStopped", @"isOptedInForGeofencing", @"isOptedInForPush"]]});
}

//=====================================
// 15. getVersion
//=====================================
RCT_EXPORT_METHOD(getVersion:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"success", @"version":[M2MBeaconMonitor getVersionNum]});
}

//=====================================
// 16. setWaitForReady
//=====================================
RCT_EXPORT_METHOD(setWaitForReady:(BOOL)wait :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    [M2MBeaconMonitor setWaitForReady:wait];
    resolve(@{@"status": @"success"});
}

//=====================================
// 17. readyForEngagement
//=====================================
RCT_EXPORT_METHOD(readyForEngagement :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    if([M2MBeaconMonitor isEngagementReady]) {
        [M2MBeaconMonitor readyForEngagement];
        resolve(@{@"status": @"success"});
    } else {
        resolve(@{@"status": @"error", @"message": @"Is not ready for engagement."});
    }
}

//=====================================
// 18. isEngagementReady
//=====================================
RCT_EXPORT_METHOD(isEngagementReady :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"success", @"isEngagementReady": [M2MBeaconMonitor isEngagementReady] ? @"true" : @"false"});
}

//=====================================
// 19. isOptedInForGeofencing
//=====================================
RCT_EXPORT_METHOD(isOptedInForGeofencing :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    M2MConfig *config = [M2MBeaconMonitor getM2MConfig];
    resolve(@{@"status": @"success", @"isOptedInForGeofencing": config.isOptedInForGeofencing ? @"true" : @"false"});
}

//=====================================
// 20. isOptedInForPush
//=====================================
RCT_EXPORT_METHOD(isOptedInForPush :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    M2MConfig *config = [M2MBeaconMonitor getM2MConfig];
    resolve(@{@"status": @"success", @"isOptedForPush": config.isOptedInForPush ? @"true" : @"false"});
}

//=====================================
// 21. isStopped
//=====================================
RCT_EXPORT_METHOD(isStopped :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    M2MConfig *config = [M2MBeaconMonitor getM2MConfig];
    resolve(@{@"status": @"success", @"isStopped": config.isStopped ? @"true" : @"false"});
}

//===============================================
// ANDROID SPECIFIC METHODS IMPLEMENTATION STARTS
//===============================================

RCT_EXPORT_METHOD(isDemoModeOn :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"error", @"message": @"Not available for iOS."});
}

RCT_EXPORT_METHOD(setPublisherUserId :(NSString *) publisherUserId :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"error", @"message": @"Not available for iOS."});
}

RCT_EXPORT_METHOD(requestLocationPermission :(BOOL)startServiceOnGrant :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"error", @"message": @"Not available for iOS."});
}

RCT_EXPORT_METHOD(requestFineLocationPermission :(BOOL)startServiceOnGrant :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"error", @"message": @"Not available for iOS."});
}

RCT_EXPORT_METHOD(requestBackgroundLocationPermission :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"error", @"message": @"Not available for iOS."});
}

RCT_EXPORT_METHOD(requestForegroundLocationPermission :(BOOL)startServiceOnGrant :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"error", @"message": @"Not available for iOS."});
}

RCT_EXPORT_METHOD(getLocalNotificationEnabled :(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@{@"status": @"error", @"message": @"Not available for iOS."});
}

//===============================================
// ANDROID SPECIFIC METHODS IMPLEMENTATION ENDS
//===============================================

- (NSString*)formatTypeToString:(M2M_ERROR_CODES)formatType {
    NSString *result = nil;

    switch(formatType) {
        case M2M_NO_LOCATION_PERMISSION_ERROR_CODE:
            result = @"M2M_NO_LOCATION_PERMISSION_ERROR_CODE";
            break;
        case M2M_NO_PRECISE_LOCATION_PERMISSION_ERROR_CODE:
            result = @"M2M_NO_PRECISE_LOCATION_PERMISSION_ERROR_CODE";
            break;
        case M2M_INVALID_RESPONSE_ERROR_CODE:
            result = @"M2M_INVALID_RESPONSE_ERROR_CODE";
            break;
        case M2M_NOT_INITIALIZED_ERROR_CODE:
            result = @"M2M_NOT_INITIALIZED_ERROR_CODE";
            break;
        case M2M_NO_STATUS_ERROR_CODE:
            result = @"M2M_NO_STATUS_ERROR_CODE";
            break;
        case M2M_UNKNOWN_ERROR_CODE:
            result = @"M2M_UNKNOWN_ERROR_CODE";
            break;
        case M2M_TIME_OUT_ERROR_CODE:
            result = @"M2M_TIME_OUT_ERROR_CODE";
            break;
        case M2M_PENDING_CHECKIN_OPPS_ERROR_CODE:
            result = @"M2M_PENDING_CHECKIN_OPPS_ERROR_CODE";
            break;
        case M2M_PENDING_CHECKIN_ERROR_CODE:
            result = @"M2M_PENDING_CHECKIN_ERROR_CODE";
            break;
        case M2M_PENDING_SCAN_OPPS_ERROR_CODE:
            result = @"M2M_PENDING_SCAN_OPPS_ERROR_CODE";
            break;
        case M2M_NO_ADS_AVAILABLE_ERROR_CODE:
            result = @"M2M_NO_ADS_AVAILABLE_ERROR_CODE";
            break;
        case M2M_ENGAGEMENT_EXPIRED_ERROR_CODE:
            result = @"M2M_ENGAGEMENT_EXPIRED_ERROR_CODE";
            break;
        case M2M_E2_LOAD_ERROR_CODE:
            result = @"M2M_E2_LOAD_ERROR_CODE";
            break;
        case M2M_INIT_EXPIRED_ERROR_CODE:
            result = @"M2M_INIT_EXPIRED_ERROR_CODE";
            break;
        case M2M_INVALID_APP_UUID:
            result = @"M2M_INVALID_APP_UUID";
            break;
        case M2M_APP_UUID_REQUIRED:
            result = @"M2M_APP_UUID_REQUIRED";
            break;
        case M2M_SCANSENSE_NO_PUB_USERID:
            result = @"M2M_SCANSENSE_NO_PUB_USERID";
            break;
        case M2M_SCANSENSE_NO_LOCATION:
            result = @"M2M_SCANSENSE_NO_LOCATION";
            break;
        case M2M_SCANSENSE_NO_PRODUCT:
            result = @"M2M_SCANSENSE_NO_PRODUCT";
            break;
        case M2M_SCANSENSE_NO_PRECISE_LOCATION:
            result = @"M2M_SCANSENSE_NO_PRECISE_LOCATION";
            break;
        case M2M_SCANSENSE_WRONG_UPC:
            result = @"M2M_SCANSENSE_WRONG_UPC";
            break;
        case M2M_SCANSENSE_REJECTED:
            result = @"M2M_SCANSENSE_REJECTED";
            break;
        default:
            [NSException raise:NSGenericException format:@"Unexpected M2M_ERROR_CODES."];
    }

    return result;
}


#pragma mark - M2MServiceDelegate delegate methods -

-(void)didGetAvailableOpps:(NSDictionary*)opps {
    [self sendEventWithName:@"didGetAvailableOpps" body:opps];
}

-(void)onM2mDecisionWithData:(NSDictionary*)dict {
    [self sendEventWithName:@"onM2mDecisionWithData" body:dict];
}

-(void)onStartM2MService{
    [self sendEventWithName:@"onM2MServiceStarted" body:@{}];
}

-(void)onM2MServiceStopped{
    [self sendEventWithName:@"onM2MServiceStopped" body:@{}];
}

-(void)didShowEngagement{
    [self sendEventWithName:@"didShowEngagement" body:@{}];
}

-(void)didDismissEngagement{
    [self sendEventWithName:@"didDismissEngagement" body:@{}];
}

-(void)didReceiveEngagement{
    [self sendEventWithName:@"didReceiveEngagement" body:@{}];
}

-(void)onErrorWithCode:(M2M_ERROR_CODES)code andMessage:(NSString*)message forRequest:(M2M_REQUEST_TYPE)type{
    [self sendEventWithName:@"onError" body:@{
        @"error_code": [NSString stringWithFormat:@"%d", code],
        @"error_name": [self formatTypeToString:code],
        @"message": message
    }];
}

-(void)onWebViewIntegrityError{
    [self sendEventWithName:@"onWebViewIntegrityError" body:@{}];
}

-(void)engagementNotAvailable{
    [self sendEventWithName:@"engagementNotAvailable" body:@{}];
}

-(void)didReceiveDetection:(NSDictionary*)detection{
    [self sendEventWithName:@"didReceiveDetection" body:@{@"detection" : detection}];
}

-(void)didGetScanOps:(NSMutableArray*)ops{
    [self sendEventWithName:@"didGetScanOps" body:@{@"ops" : ops}];
}

-(void)didGetProducts:(NSMutableArray*)products{
    [self sendEventWithName:@"didGetProducts" body:@{@"products" : products}];
}

@end
