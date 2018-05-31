package app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.base.log.IScreenLogger;
import app.base.utils.log.LogcatLogger;

/**
 * Created by woogis on 2018. 2. 28..
 */

public abstract class BaseFragment<T> extends Fragment {
    private String mLogTag;
    private T mData;
    private boolean mCanAccessUi;
    private View mRootView;
    private boolean enableUserLog = false;
    private IScreenLogger userLogger;
    protected Bundle logBundle;

    public BaseFragment() {
        LogcatLogger.d(mLogTag, "call constructor");
    }

    protected abstract View createRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    protected View getRootView() {
        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLogTag = createLogTag();
        if (mLifeCycleListener != null) {
            mLifeCycleListener.onAttached();
        }

        enableUserLog = enableUserLog();
        if (enableUserLog) {
            logBundle = new Bundle();
            userLogger = createLogger();
        }

        onDataChanged(mData);
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = createRootView(inflater, container);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mLifeCycleListener != null) {
            mLifeCycleListener.onViewCreated();
        }
    }

    @Override
    public void onDestroyView() {
        mCanAccessUi = false;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (mLifeCycleListener != null) {
            mLifeCycleListener.onDetached();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogcatLogger.d(getLogTag(), "onStart");
        if (enableUserLog) {
            userLogger.logging(getActivity(), getLogTag());
        }
    }

    public boolean canAccessUi() {
        return mCanAccessUi;
    }

    protected String getLogTag() {
        return mLogTag;
    }


    public void setData(T data) {
        mData = data;
        if (mCanAccessUi) {
            onDataChanged(mData);
        }
    }

    public T getData() {
        return mData;
    }

    protected void onDataChanged(T data) {

    }

    protected abstract String createLogTag();

    private LifeCycleListener mLifeCycleListener;

    public void setLifeCycleListener(LifeCycleListener lifeCycleListener) {
        mLifeCycleListener = lifeCycleListener;
    }

    public boolean enableUserLog() {
        return false;
    }

    public IScreenLogger createLogger() {
        return null;
    }

    public interface LifeCycleListener {
        void onAttached();

        void onViewCreated();

        void onDetached();
    }
}
