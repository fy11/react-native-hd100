package com.hd;

import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ArrayUtils;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.reader.usbdevice.DeviceLib;
import com.reader.usbdevice.DeviceStatusCallback;

public class RNHDModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    // 可以修饰成员变量和成员方法被其修饰的成员只能在本类中被访问
    private final ReactApplicationContext reactContext;
    private HDCardReader HDCardReader;
    private DeviceLib mdev;

    public RNHDModule(ReactApplicationContext reactContext) {
        super(reactContext);
        // this.reactContext = reactContext;
        reactContext.addLifecycleEventListener(this);
    }

    @ReactMethod
    public void start() {
    
    }

    @Override
    public String getName() {
      return "RNHDIdCard";
    }
