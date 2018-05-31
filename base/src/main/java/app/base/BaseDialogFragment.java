package app.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseDialogFragment extends DialogFragment implements DialogInterface {
    private View mRootView;
    private DialogListener mDialogListener;

    public BaseDialogFragment() {
        super();
        mTag = createTag();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, getTheme());
    }

    protected abstract View createRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    protected View getRootView() {
        return mRootView;
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = createRootView(inflater, container);
        return mRootView;
    }

    @Override
    public void cancel() {

        if (mDialogListener != null) {
            mDialogListener.onCancel();
        }

        dismiss();
    }

    private static String mTag;

    protected static String getLogTag() {
        return mTag;
    }

    protected abstract String createTag();

    public void setDialogListener(DialogListener listener) {
        mDialogListener = listener;
    }

    protected DialogListener getDialogListener() {
        return mDialogListener;
    }

    public interface DialogListener {
        void onPositiveButtonClick();

        void onNegativeButtonClick();

        void onCancel();
    }
}