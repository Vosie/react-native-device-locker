
package org.vosie.reactnative.devicelocker;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;
import java.util.HashMap;

public class DeviceLockerModule extends ReactContextBaseJavaModule {

  private static final String LOGGER_TAG = "DeviceLocker";
  private static final String PARTIAL_WAKE_LOCK = "PARTIAL_WAKE_LOCK";
  private static final String SCREEN_DIM_WAKE_LOCK = "SCREEN_DIM_WAKE_LOCK";
  private static final String SCREEN_BRIGHT_WAKE_LOCK = "SCREEN_BRIGHT_WAKE_LOCK";
  private static final String FULL_WAKE_LOCK = "FULL_WAKE_LOCK";

  private final ReactApplicationContext reactContext;
  private Map<Integer, PowerManager.WakeLock> lockerMap;

  public DeviceLockerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.lockerMap = new HashMap<Integer, PowerManager.WakeLock>();
  }

  @Override
  public String getName() {
    return "DeviceLocker";
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(PARTIAL_WAKE_LOCK, PowerManager.PARTIAL_WAKE_LOCK);
    constants.put(SCREEN_DIM_WAKE_LOCK, PowerManager.SCREEN_DIM_WAKE_LOCK);
    constants.put(SCREEN_BRIGHT_WAKE_LOCK, PowerManager.SCREEN_BRIGHT_WAKE_LOCK);
    constants.put(FULL_WAKE_LOCK, PowerManager.FULL_WAKE_LOCK);
    return constants;
  }

  @ReactMethod
  public void requestWakeLock(int type, Promise promise) {
    try {
      PowerManager powerManager = (PowerManager) reactContext.getSystemService(
          Context.POWER_SERVICE);
      PowerManager.WakeLock wakeLock = powerManager.newWakeLock(type, LOGGER_TAG);
      wakeLock.acquire();
      int hashCode = wakeLock.hashCode();
      this.lockerMap.put(hashCode, wakeLock);
      promise.resolve(hashCode);
      Log.i(LOGGER_TAG, "request wake lock successful: " + hashCode);
    } catch (Exception ex) {
      Log.e(LOGGER_TAG, "request wake lock failed", ex);
      promise.reject(ex.getMessage());
    }
  }

  @ReactMethod
  public void releaseWakeLock(int id, Promise promise) {
    try {
      if (this.lockerMap.containsKey(id)) {
        this.lockerMap.get(id).release();
        promise.resolve(id);
        Log.i(LOGGER_TAG, "release wake lock successful: " + id);
      } else {
        promise.reject("locker not found with id: " + id);
        Log.e(LOGGER_TAG, "try to release an inexisting wake lock: " + id);
      }
    } catch (Exception ex) {
      Log.e(LOGGER_TAG, "release wake lock failed", ex);
      promise.reject(ex.getMessage());
    }
  }

  @ReactMethod
  public void releaseAll(Promise promise) {
    Exception error = null;
    for (int id : this.lockerMap.keySet()) {
      try {
          this.lockerMap.get(id).release();
          Log.i(LOGGER_TAG, "release wake lock successful: " + id);
      } catch (Exception ex) {
        Log.e(LOGGER_TAG, "release wake lock failed", ex);
        error = ex;
      }
    }

    if (error == null) {
      promise.resolve("ok");
    } else {
      promise.reject(error.getMessage());
    }
  }
}
