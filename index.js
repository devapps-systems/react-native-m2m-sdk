import {
    NativeModules,
    NativeEventEmitter
} from 'react-native';

const { M2MSdk } = NativeModules;

const _eventEmitter = new NativeEventEmitter(M2MSdk);

const _eventHandlers = {
    onM2mDecisionWithData: new Map(),
    onM2MServiceStarted: new Map(),
    onM2MServiceStopped: new Map(),
    didShowEngagement: new Map(),
    didDismissEngagement: new Map(),
    didReceiveEngagement: new Map(),
    onError: new Map(),
    onWebViewIntegrityError: new Map(),
    engagementNotAvailable: new Map(),
    didGetAvailableOpps: new Map(),
    didReceiveDetection: new Map(),
}

const M2MSdkModule = {

    /* Common methods */
    setTagKeywords(tags) {
        return M2MSdk.setTagKeywords(tags);
    },

    getTagKeywords() {
        return M2MSdk.getTagKeywords();
    },

    getAvailableOpps() {
        return M2MSdk.getAvailableOpps();
    },

    checkInToOpp(placeID) {
        return M2MSdk.checkInToOpp(placeID);
    },

    setPushToken(pushToken) {
        return M2MSdk.setPushToken(pushToken);
    },

    stopService() {
        return M2MSdk.stopService();
    },

    startMonitoring() {
        return M2MSdk.startMonitoring();
    },

    getM2MConfig() {
        return M2MSdk.getM2MConfig();
    },

    getVersion() {
        return M2MSdk.getVersion();
    },

    setWaitForReady(wait) {
        return M2MSdk.setWaitForReady(wait);
    },

    readyForEngagement() {
        return M2MSdk.readyForEngagement();
    },

    isEngagementReady() {
        return M2MSdk.isEngagementReady();
    },

    isOptedInForGeofencing() {
        return M2MSdk.isOptedInForGeofencing();
    },

    isOptedInForPush() {
        return M2MSdk.isOptedInForPush();
    },

    isStopped() {
        return M2MSdk.isStopped();
    },

    /* iOS specific */

    requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime(flag) {
        return M2MSdk.requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime(flag);
    },

    requestWhenInUse() {
        return M2MSdk.requestWhenInUse();
    },

    requestAlways() {
        return M2MSdk.requestAlways();
    },
    
    /* Android specific */

    setPublisherUserId(publisherID) {
        return M2MSdk.setPublisherUserId(publisherID);
    },

    requestLocationPermission(startServiceOnGrant) {
        return M2MSdk.requestLocationPermission(startServiceOnGrant);
    },

    requestFineLocationPermission(startServiceOnGrant) {
        return M2MSdk.requestFineLocationPermission(startServiceOnGrant);
    },

    requestBackgroundLocationPermission() {
        return M2MSdk.requestBackgroundLocationPermission();
    },

    requestForegroundLocationPermission(startServiceOnGrant) {
        return M2MSdk.requestForegroundLocationPermission(startServiceOnGrant);
    },

    getLocalNotificationEnabled() {
        return M2MSdk.getLocalNotificationEnabled();
    },

    addEventListener(type, handler) {
        if (_eventHandlers[type].has(handler)) {
            return;
        }
        _eventHandlers[type].set(handler, _eventEmitter.addListener(type, func => { handler(func) }));
    },

    removeEventListener(type, handler) {
        if (!_eventHandlers[type].has(handler)) {
            return;
        }
        _eventHandlers[type].get(handler).remove();
        _eventHandlers[type].delete(handler);
    }
}

export default M2MSdkModule;