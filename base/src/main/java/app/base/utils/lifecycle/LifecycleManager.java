package app.base.utils.lifecycle;

import java.util.ArrayList;

import app.base.utils.log.LogcatLogger;

/**
 * App's Application should have this class's instance as a member
 */
public class LifecycleManager {
    private final static String TAG = "LifecycleManager";
    private final static LifecycleManager sInstance = new LifecycleManager();

    private LifecycleManager() {

    }

    public static LifecycleManager getInstance() {
        return sInstance;
    }

    private static int mNumActiveComponentPrev = 0;
    private static int mNumActiveComponent = 0;

    public static void onActivityStart() {
        mNumActiveComponentPrev = mNumActiveComponent++;
        LogcatLogger.d(TAG, "num of started activities = " + mNumActiveComponent);
        notifyLifecycleEvent();
    }

    public static void onActivityStop() {
        if (mNumActiveComponent >= 0) {
            mNumActiveComponentPrev = mNumActiveComponent--;
        }
        LogcatLogger.d(TAG, "num of stopped activities = " + mNumActiveComponent);
        notifyLifecycleEvent();
    }

    public static boolean isActiveSession() {
        return mNumActiveComponent > 0;
    }


    private final static ArrayList<LifecycleListener> mListeners = new ArrayList<>();

    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        if (lifecycleListener == null) {
            return;
        }

        if (mListeners.contains(lifecycleListener)) {
            return;
        }

        mListeners.add(lifecycleListener);
    }

    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        if (lifecycleListener == null) {
            return;
        }

        if (mListeners.contains(lifecycleListener)) {
            mListeners.remove(lifecycleListener);
        }
    }

    private static void notifyLifecycleEvent() {
        for (LifecycleListener lifecycleListener : mListeners) {
            if (lifecycleListener == null) {
                continue;
            }

            if (mNumActiveComponentPrev == 0 && mNumActiveComponent == 1) {
                lifecycleListener.onStartActiveSession();
                continue;
            }

            if (mNumActiveComponentPrev == 1 && mNumActiveComponent <= 0) {
                lifecycleListener.onStopActiveSession();
            }
        }
    }

    public interface LifecycleListener {
        void onStartActiveSession();

        void onStopActiveSession();
    }
}