// main index.js

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
    didGetScanOps: new Map(),
    didGetProducts: new Map()
}

const M2MSdkModule = {
    getScanLocationsWithUserId(userID) {
        return M2MSdk.getScanLocationsWithUserId(userID);
    },

    getProductsForLocation(location) {
        return M2MSdk.getProductsForLocation(location);
    },

    setTagKeyWords(tags) {
        return M2MSdk.setTagKeyWords(tags);
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

    requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime(flag) {
        return M2MSdk.requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime(flag);
    },

    requestWhenInUse() {
        return M2MSdk.requestWhenInUse();
    },

    requestAlways() {
        return M2MSdk.requestAlways();
    },

    getM2MConfig() {
        return M2MSdk.getM2MConfig();
    },

    getVersionNum() {
        return M2MSdk.getVersionNum();
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