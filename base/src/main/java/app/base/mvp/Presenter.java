package app.base.mvp;

import android.os.Bundle;

public abstract class Presenter<U extends Ui> {
    private final String TAG = this.getClass().getSimpleName();
    private U mUi;

    public U getUi() {
        return mUi;
    }

    public void onUiReady(U ui) {
        mUi = ui;
    }

    public final void onUiDestroy(U ui) {
        onUiUnready(ui);
        mUi = null;
    }

    public void onUiUnready(U ui) {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    public abstract String getLogTag();
}
