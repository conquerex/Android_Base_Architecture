package app.base.utils.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import app.base.utils.log.LogcatLogger;

public class LocationManager {
    private static final String TAG = "LocationManager";
    private static LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private static LocationRequest mLocationRequest;
    private static LocationManager sInstance;


    private LocationManager(Context context) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public static synchronized LocationManager getInstance(Context context) {
        if (sInstance == null) {
            if (context == null) {
                throw new RuntimeException();
            }
            sInstance = new LocationManager(context);
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    LogcatLogger.d(TAG, "onLocationResult:locationResult = " + locationResult);
                    super.onLocationResult(locationResult);
                    if (locationResult == null) {
                        return;
                    }
                    Location lastLocation = locationResult.getLastLocation();
                    notifyLocationUpdated(lastLocation);
                }

                @Override
                public void onLocationAvailability(LocationAvailability locationAvailability) {
                    super.onLocationAvailability(locationAvailability);
                }
            };

            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                    .setInterval(30000);

        }

        return sInstance;
    }

    public void requestLatestLocation(Context context, OnLocationUpdateListener listener) {
        if (listener == null) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            listener.onNeedToRequestPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        LogcatLogger.d(TAG, "requestLatestLocation:location = " + location);
                        listener.onLocationUpdated(location);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onLocationUpdated(null);
                    }
                });
    }

    public boolean startLocationUpdates(Context context, Looper looper) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            notifyNeedToPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
            return false;
        }

        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                looper);

        return true;
    }

    public void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    public void setOnPermissionGranted() {
        notifyPermissionGranted();
    }

    private final static ArrayList<OnLocationUpdateListener> mOnLocationListener = new ArrayList<>();

    public void addLocationListener(OnLocationUpdateListener listener) {
        if (listener == null) {
            return;
        }
        if (mOnLocationListener.contains(listener)) {
            return;
        }
        mOnLocationListener.add(listener);
    }

    public void removeLocationListener(OnLocationUpdateListener listener) {
        if (listener == null) {
            return;
        }

        if (!mOnLocationListener.contains(listener)) {
            return;
        }

        mOnLocationListener.remove(listener);
    }

    private static void notifyPermissionGranted() {
        for (OnLocationUpdateListener listener : mOnLocationListener) {
            if (listener == null) {
                continue;
            }
            listener.onPermissionGranted();
        }
    }

    private static void notifyNeedToPermission(String[] permissions) {
        for (OnLocationUpdateListener listener : mOnLocationListener) {
            if (listener == null) {
                continue;
            }
            listener.onNeedToRequestPermission(permissions);
        }
    }

    private static void notifyLocationUpdated(Location location) {
        for (OnLocationUpdateListener listener : mOnLocationListener) {
            if (listener == null) {
                continue;
            }
            listener.onLocationUpdated(location);
        }
    }

    public interface OnLocationUpdateListener {
        void onPermissionGranted();

        void onNeedToRequestPermission(String[] permissions);

        void onLocationUpdated(Location location);
    }
}
