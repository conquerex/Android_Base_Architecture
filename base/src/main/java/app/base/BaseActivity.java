package app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import app.base.utils.lifecycle.LifecycleManager;
import app.base.utils.log.LogcatLogger;

public abstract class BaseActivity extends AppCompatActivity {
    private static String mTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = createTag();
        LogcatLogger.d(mTag, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LifecycleManager.onActivityStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LifecycleManager.onActivityStop();
    }

    protected static String getLogTag() {
        return mTag;
    }

    protected abstract String createTag();
}
