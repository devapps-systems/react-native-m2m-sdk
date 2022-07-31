// M2MSdkModule.java

package com.reactlibrary;

import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.PermissionListener;
import com.google.gson.Gson;
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
import java.util.stream.Collectors;

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
    public void setTagKeywords(String tags, Promise promise) {
        try {
            HashMap<String,String> tagsMap = toMap(new JSONObject(tags));

            M2MBeaconMonitor.setTagKeywords(tagsMap);
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void getTagKeywords(Promise promise) {
        HashMap tags = M2MBeaconMonitor.getTagKeywords();
        JSONObject jsonTags = new JSONObject();
        try {
            Iterator it = tags.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                jsonTags.put((String) pair.getKey(), pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
            }

            promise.resolve((new JSONObject().put("status", "success").put("tags", jsonTags)));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void getAvailableOpps() {
        M2MBeaconMonitor.getAvailableOpps();
    }

    @ReactMethod
    public void checkInToOpp(String placeID, Promise promise) {
        M2MBeaconMonitor.checkInToOpp(placeID);
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void setPushToken(String registrationID, Promise promise) {
        M2MBeaconMonitor.setPushToken(reactContext, registrationID);
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void stopService(Promise promise) {
        M2MBeaconMonitor.stopService(reactContext);
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void startMonitoring(Promise promise) {
        if(M2MBeaconMonitor.checkLocationPermission(reactContext)) {
            M2MBeaconMonitor.startM2MService(this);
        } else {
            M2MBeaconMonitor.requestLocationPermission(getCurrentActivity(), true);
        }
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void getM2MConfig(Promise promise) {
        try {
            M2MConfig config = M2MBeaconMonitor.getConfig();
            JSONObject configJSON = new JSONObject();
            configJSON.put("isStopped", !M2MBeaconMonitor.isServiceStarted());
            configJSON.put("isOptedInForGeofencing", config.isOptedInForGeofencing());
            configJSON.put("isOptedInForPush", config.isOptedInForPush());
            promise.resolve(new JSONObject().put("status", "success").put("config", configJSON));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void getVersion(Promise promise) {
        try {
            promise.resolve(new JSONObject().put("status", "success").put("version", M2MBeaconMonitor.getVersion()));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void setWaitForReady(boolean wait, Promise promise) {
        M2MBeaconMonitor.setWaitForReady(wait);
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void readyForEngagement(Promise promise) {
        M2MBeaconMonitor.readyForEngagement();
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void isEngagementReady(Promise promise) {
        try {
            promise.resolve(new JSONObject().put("status", "success").put("isEngagementReady", (M2MBeaconMonitor.isEngagementReady() ? "true" : "false"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void isOptedInForGeofencing(Promise promise) {
        try {
            promise.resolve(new JSONObject().put("status", "success").put("isOptedInForGeofencing", (M2MBeaconMonitor.getConfig().isOptedInForGeofencing() ? "true" : "false"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void isOptedInForPush(Promise promise) {
        try {
            promise.resolve(new JSONObject().put("status", "success").put("isOptedInForPush", (M2MBeaconMonitor.getConfig().isOptedInForPush() ? "true" : "false"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void isStopped(Promise promise) {
        try {
            promise.resolve(new JSONObject().put("status", "success").put("isStopped", (!M2MBeaconMonitor.isServiceStarted() ? "true" : "false"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    /****************************************
     * iOS Specific method implementation
    *****************************************/
    @ReactMethod
    public void requestAppTrackingPermissionAndOpenSettingsIfNotFirstTime(boolean flag, Promise promise) {
        try {
            promise.resolve(new JSONObject().put("status", "error").put("message", "Not available for Android."));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": \"Not available for Android.\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void requestWhenInUse(Promise promise) {
        try {
            promise.resolve(new JSONObject().put("status", "error").put("message", "Not available for Android."));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": \"Not available for Android.\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void requestAlways(Promise promise) {
        try {
            promise.resolve(new JSONObject().put("status", "error").put("message", "Not available for Android."));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": \"Not available for Android.\"}", JSONObject.class));
        }
    }

    /****************************************
     * Android Specific method implementation
     *****************************************/
    @ReactMethod
    public void setPublisherUserId(String publisherID, Promise promise) {
        M2MBeaconMonitor.setPublisherUserId(publisherID);
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void requestLocationPermission(boolean startServiceOnGrant, Promise promise) {
        M2MBeaconMonitor.requestLocationPermission(getCurrentActivity(), startServiceOnGrant);
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void requestFineLocationPermission(boolean startServiceOnGrant, Promise promise) {
        M2MBeaconMonitor.requestFineLocationPermission(getCurrentActivity(), startServiceOnGrant);
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void requestBackgroundLocationPermission(Promise promise) {
        M2MBeaconMonitor.requestBackgroundLocationPermission(getCurrentActivity());
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void requestForegroundLocationPermission(boolean startServiceOnGrant, Promise promise) {
        M2MBeaconMonitor.requestForegroundLocationPermission(getCurrentActivity(), startServiceOnGrant);
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
    }

    @ReactMethod
    public void getLocalNotificationEnabled(Promise promise) {
        M2MBeaconMonitor.getLocalNotificationEnabled();
        try {
            promise.resolve(new JSONObject().put("status", "success"));
        } catch (Exception ex) {
            Gson gson = new Gson();
            promise.resolve(gson.fromJson("{\"status\":\"error\", \"message\": " + ex.getMessage() + "\"}", JSONObject.class));
        }
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
