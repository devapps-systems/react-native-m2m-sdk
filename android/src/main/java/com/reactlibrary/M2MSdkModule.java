// M2MSdkModule.java

package com.reactlibrary;

import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.PermissionListener;
import com.inmarket.m2m.M2MBeaconMonitor;
import com.inmarket.m2m.M2MConfig;
import com.inmarket.m2m.M2MListenerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class M2MSdkModule extends ReactContextBaseJavaModule implements PermissionListener, M2MListenerInterface {
    private static final String TAG = M2MSdkModule.class.getSimpleName();

    private final ReactApplicationContext reactContext;

    public M2MSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "M2MSdk";
    }

    /****************************************
     * Common methods implementation
     *****************************************/

    @ReactMethod
    public void setTagKeywords(JSONObject tags, Callback callback) {
        try {
            HashMap<String, String> tagsMap = toMap(tags);
            M2MBeaconMonitor.setTagKeywords(tagsMap);
            callback.invoke(new JSONObject().put("status", "success").toString());
        } catch (Exception ex) {
            callback.invoke("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}");
        }
    }

    @ReactMethod
    public void getTagKeywords(Callback callback) {
        HashMap tags = M2MBeaconMonitor.getTagKeywords();
        JSONObject jsonTags = new JSONObject();
        try {
            Iterator it = tags.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                jsonTags.put((String) pair.getKey(), pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }

            callback.invoke(new JSONObject().put("status", "success").put("tags", jsonTags.toString()));
        } catch (Exception ex) {
            callback.invoke("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}");
        }
    }

    @ReactMethod
    public void getAvailableOpps() {
        M2MBeaconMonitor.getAvailableOpps();
    }

    @ReactMethod
    public void checkInToOpp(String placeID, Callback callback) {
        try {
            M2MBeaconMonitor.checkInToOpp(placeID);
            callback.invoke(new JSONObject().put("status", "success").toString());
        } catch (Exception ex) {
            callback.invoke("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}");
        }
    }

    @ReactMethod
    public void setPushToken(String registrationID, Callback callback) {
        try {
            M2MBeaconMonitor.setPushToken(reactContext, registrationID);
            callback.invoke(new JSONObject().put("status", "success").toString());
        } catch (Exception ex) {
            callback.invoke("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}");
        }
    }

    @ReactMethod
    public void stopService(Callback callback) {
        try {
            M2MBeaconMonitor.stopService(reactContext);
            callback.invoke("{\"status\", \"success\"}");
        } catch (Exception ex) {
            callback.invoke("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}");
        }
    }

    @ReactMethod
    public void startMonitoring(Callback callback) {
        try {
            if(M2MBeaconMonitor.checkLocationPermission(reactContext)) {
                M2MBeaconMonitor.startM2MService(this);
            } else {
                M2MBeaconMonitor.requestLocationPermission(reactContext.getCurrentActivity(), true);
            }
            callback.invoke("{\"status\", \"success\"}");
        } catch (Exception ex) {
            callback.invoke("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}");
        }
    }

    @ReactMethod
    public void getM2MConfig(Callback callback) {
        M2MConfig config = M2MBeaconMonitor.getConfig();
        callback.invoke("{\"status\", \"success\", \"config\", \"" + ((config != null) ? config.toString() : "null") + "\"}");
    }

    @ReactMethod
    public void getVersion(Callback callback) {
        try {
            callback.invoke(new JSONObject().put("status", "success").put("version", M2MBeaconMonitor.getVersion()).toString());
        } catch (Exception ex) {
            callback.invoke("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}");
        }
    }

    @ReactMethod
    public void setWaitForReady(boolean wait, Callback callback) {
        M2MBeaconMonitor.setWaitForReady(wait);
        callback.invoke("{\"status\", \"success\"}");
    }

    @ReactMethod
    public void readyForEngagement(Callback callback) {
        M2MBeaconMonitor.readyForEngagement();
        callback.invoke("{\"status\", \"success\"}");
    }

    @ReactMethod
    public void isEngagementReady(Callback callback) {
        callback.invoke("{\"status\", \"success\", \"isEngagementReady\", \"" + (M2MBeaconMonitor.isEngagementReady() ? "true" : "false") + "\"}");
    }

    @ReactMethod
    public void isOptedInForGeofencing(Callback callback) {
        callback.invoke("{\"status\", \"success\", \"isOptedInForGeofencing\", \"" + (M2MBeaconMonitor.getConfig().isOptedInForGeofencing() ? "true" : "false") + "\"}");
    }

    @ReactMethod
    public void isOptedInForPush(Callback callback) {
        callback.invoke("{\"status\", \"success\", \"isOptedInForPush\", \"" + (M2MBeaconMonitor.getConfig().isOptedInForPush() ? "true" : "false") + "\"}");
    }

    @ReactMethod
    public void isStopped(Callback callback) {
        callback.invoke("{\"status\", \"success\", \"isStopped\", \"" + (!M2MBeaconMonitor.isServiceStarted() ? "true" : "false") + "\"}");
    }

    /****************************************
     * iOS Specific method implementation
    *****************************************/
    @ReactMethod
    public void requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime(boolean flag, Callback callback) {
        callback.invoke("{\"status\", \"error\", \"message\": \"Not available for Android.\"}");
    }

    @ReactMethod
    public void requestWhenInUse(Callback callback) {
        callback.invoke("{\"status\", \"error\", \"message\": \"Not available for Android.\"}");
    }

    @ReactMethod
    public void requestAlways(Callback callback) {
        callback.invoke("{\"status\", \"error\", \"message\": \"Not available for Android.\"}");
    }

    /****************************************
     * Android Specific method implementation
     *****************************************/
    @ReactMethod
    public void setPublisherUserId(String publisherID, Callback callback) {
        M2MBeaconMonitor.setPublisherUserId(publisherID);
        callback.invoke("{\"status\", \"success\"}");
    }

    @ReactMethod
    public void requestLocationPermission(boolean startServiceOnGrant, Callback callback) {
        M2MBeaconMonitor.requestLocationPermission(reactContext.getCurrentActivity(), startServiceOnGrant);
        callback.invoke("{\"status\", \"success\"}");
    }

    @ReactMethod
    public void requestFineLocationPermission(boolean startServiceOnGrant, Callback callback) {
        M2MBeaconMonitor.requestFineLocationPermission(reactContext.getCurrentActivity(), startServiceOnGrant);
        callback.invoke("{\"status\", \"success\"}");
    }

    @ReactMethod
    public void requestBackgroundLocationPermission(Callback callback) {
        M2MBeaconMonitor.requestBackgroundLocationPermission(reactContext.getCurrentActivity());
        callback.invoke("{\"status\", \"success\"}");
    }

    @ReactMethod
    public void requestForegroundLocationPermission(boolean startServiceOnGrant, Callback callback) {
        M2MBeaconMonitor.requestForegroundLocationPermission(reactContext.getCurrentActivity(), startServiceOnGrant);
        callback.invoke("{\"status\", \"success\"}");
    }

    @ReactMethod
    public void getLocalNotificationEnabled(Callback callback) {
        M2MBeaconMonitor.getLocalNotificationEnabled()
        callback.invoke("{\"status\", \"success\"}");
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        Log.d(TAG, "sendEvent " + eventName + " params " + params);
        if (getReactApplicationContext().hasActiveCatalystInstance()) {
            getReactApplicationContext()
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        } else {
            Log.d(TAG, "Send event failed, Catalyst instance not active");
        }
    }

    // =================================
    // PermissionListener method
    // =================================
    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean result = M2MBeaconMonitor.onRequestPermissionResult(requestCode, permissions, grantResults);
        if (result) {
            M2MBeaconMonitor.startService();
        }
        return result;
    }

    // =================================
    // M2MListenerInterface methods
    // =================================
    @Override
    public void engagementReceived() {
        sendEvent("didReceiveEngagement", null);
    }

    @Override
    public void engagementShowing() {
        sendEvent("didShowEngagement", null);
    }

    @Override
    public void engagementDismissed() {
        sendEvent("didDismissEngagement", null);
    }

    @Override
    public void engagementNotAvailable() {
        sendEvent("engagementNotAvailable", null);
    }

    @Override
    public void onStartM2MService() {
        sendEvent("onM2MServiceStarted", null);
    }

    @Override
    public void onM2MServiceStopped() {
        sendEvent("onM2MServiceStopped", null);
    }

    @Override
    public void onAvailableOpps(JSONObject jsonObject) {
        WritableMap params = Arguments.createMap();

        try {
            if(jsonObject != null) {
                JSONArray keys = jsonObject.names();
                for (int i = 0; i < keys.length (); i++) {
                    String key = keys.getString(i);
                    String value = jsonObject.getString(key);
                    params.putString(key, value);
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        sendEvent("didGetAvailableOpps", params);
    }

    @Override
    public void onDetection(JSONObject jsonObject) {
        WritableMap params = Arguments.createMap();

        try {
            if(jsonObject != null) {
                JSONArray keys = jsonObject.names();
                for (int i = 0; i < keys.length (); i++) {
                    String key = keys.getString(i);
                    String value = jsonObject.getString(key);
                    params.putString(key, value);
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        sendEvent("didReceiveDetection", params);
    }

    @Override
    public void onError(JSONObject jsonObject) {
        WritableMap params = Arguments.createMap();

        try {
            if(jsonObject != null) {
                JSONArray keys = jsonObject.names();
                for (int i = 0; i < keys.length (); i++) {
                    String key = keys.getString(i);
                    String value = jsonObject.getString(key);
                    params.putString(key, value);
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        sendEvent("onError", params);
    }

    @Override
    public void onM2mDecisionWithData(JSONObject jsonObject) {
        WritableMap params = Arguments.createMap();

        try {
            if(jsonObject != null) {
                JSONArray keys = jsonObject.names();
                for (int i = 0; i < keys.length (); i++) {
                    String key = keys.getString(i);
                    String value = jsonObject.getString(key);
                    params.putString(key, value);
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        sendEvent("onM2mDecisionWithData", params);
    }

    private HashMap<String, String> toMap(JSONObject jsonobj)  throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<String> keys = jsonobj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            Object value = jsonobj.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value.toString());
        }   return map;
    }

    public List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }
            else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }   return list;
    }
}
