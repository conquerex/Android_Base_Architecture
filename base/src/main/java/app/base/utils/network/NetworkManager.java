package app.base.utils.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

import app.base.utils.log.LogcatLogger;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

public class NetworkManager {
    private final static NetworkManager sInstance = new NetworkManager();
    private BroadcastReceiver monitorReceiver;

    private NetworkManager() {

    }

    public static synchronized NetworkManager getInstance() {
        return sInstance;
    }

    public void initMonitor(Context context) {
        monitorReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogcatLogger.d("NetworkManager", "NetworkManager");
                notifyNetworkState(isOnline(context));

            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        context.registerReceiver(monitorReceiver, intentFilter);
    }

    public void destroyMonitor(Context context) {
        context.unregisterReceiver(monitorReceiver);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }

        if (!NetworkInfo.State.CONNECTED.equals(networkInfo.getState())) {
            return false;
        }

        return true;
    }

    public static boolean isWifiConnected(Context context) {
        if (!isOnline(context)) {
            return false;
        }

        NetworkInfo networkInfo = getActivityNetworkInfo(context);
        if (networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
            return false;
        }

        return true;
    }

    private static NetworkInfo getActivityNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    private final ArrayList<NetworkMonitor> monitors = new ArrayList<>();

    public void addMonitor(NetworkMonitor monitor) {
        if (monitors.contains(monitor)) {
            return;
        }

        monitors.add(monitor);
    }

    public void removeMonitor(NetworkMonitor monitor) {
        if (monitors.contains(monitor)) {
            monitors.remove(monitor);
        }
    }

    private void notifyNetworkState(boolean online) {
        for (NetworkMonitor monitor : monitors) {
            if (monitor == null) {
                continue;
            }

            monitor.onNetworkChanged(online);
        }
    }

    public interface NetworkMonitor {
        void onNetworkChanged(boolean online);
    }
}
