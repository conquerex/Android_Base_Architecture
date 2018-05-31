package app.base.mvp;

import android.os.Bundle;

import app.base.BaseDialogFragment;

public abstract class BaseMvpDialogFragment<P extends Presenter<U>, U extends Ui> extends BaseDialogFragment {

    private P mPresenter;

    public BaseMvpDialogFragment() {
        super();
        mPresenter = createPresenter();
    }

    protected abstract U getUi();

    protected abstract P createPresenter();

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
