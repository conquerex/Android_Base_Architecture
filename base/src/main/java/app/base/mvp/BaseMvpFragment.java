package app.base.mvp;

import android.os.Bundle;

import app.base.BaseToolbarFragment;

public abstract class BaseMvpFragment<P extends Presenter<U>, U extends Ui> extends BaseToolbarFragment {
    private P mPresenter;

    protected abstract P createPresenter();

    protected abstract U getUi();

    protected BaseMvpFragment() {
        super();
        mPresenter = createPresenter();
    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onUiReady(getUi());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUiDestroy(getUi());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }
}
