package app.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseToolbarFragment<T> extends BaseFragment<T>
        implements Toolbar.OnMenuItemClickListener {
    private Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View createRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return null;
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mToolbar = createToolbar(toolbarResId());
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(v -> performToolbarNavigationClick());

            if (actionMenuResId() != 0) {
                mToolbar.inflateMenu(actionMenuResId());
                mToolbar.setOnMenuItemClickListener(this);
            }
        }

        return getRootView();
    }

    @Override
    protected abstract String createLogTag();

    protected Toolbar createToolbar(int resId) {
        if (resId == 0) {
            return null;
        }

        Toolbar toolbar = getRootView().findViewById(resId);
        return toolbar;
    }

    protected int toolbarResId() {
        return 0;
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected void performToolbarNavigationClick() {

    }

    protected int actionMenuResId() {
        return 0;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
