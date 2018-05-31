package app.base.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import app.base.R;
import app.base.utils.log.LogcatLogger;

public class IconTextView extends RelativeLayout implements Checkable {

    private AppCompatImageView imageView;
    private Drawable icon;
    private Drawable checkedIcon;
    private int iconWidth;
    private int iconHeight;
    private String text;

    private AppCompatTextView textView;
    private float textSize;
    private ColorStateList textColor;

    private int badgeCount;
    private AppCompatTextView textBadge;
    private Drawable badgeBackground;
    private int badgeTextColor;
    private float badgeTextSize;
    private boolean isChecked = false;

    public IconTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IconTextView, defStyle, 0);

        setClickable(false);
        setGravity(Gravity.CENTER);

        icon = a.getDrawable(R.styleable.IconTextView_icon);
        checkedIcon = a.getDrawable((R.styleable.IconTextView_checkedIcon));
        iconWidth = a.getDimensionPixelSize(R.styleable.IconTextView_iconWidth, 0);
        iconHeight = a.getDimensionPixelSize(R.styleable.IconTextView_iconHeight, 0);
        imageView = new AppCompatImageView(getContext());
        imageView.setImageDrawable(icon);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setId(R.id.iconImageViewImageView);
        addView(imageView);
        LayoutParams imageParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.width = iconWidth;
        imageParams.height = iconHeight;
        imageParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.dp_5);
        imageView.setLayoutParams(imageParams);

        text = a.getString(R.styleable.IconTextView_text);
        textSize = a.getDimension(R.styleable.IconTextView_textSize, getResources().getDimensionPixelOffset(R.dimen.dp_11));
        textColor = a.getColorStateList(R.styleable.IconTextView_textColor);
        textView = new AppCompatTextView(getContext());
        textView.setText(text);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setId(R.id.iconImageViewTextView);
        addView(textView);
        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.addRule(BELOW, imageView.getId());
        textView.setLayoutParams(textParams);

        try {
            badgeTextColor = a.getColor(R.styleable.IconTextView_badgeTextColor, ContextCompat.getColor(getContext(), R.color.white));
            badgeTextSize = a.getDimension(R.styleable.IconTextView_badgeTextSize, getResources().getDimensionPixelOffset(R.dimen.dp_10));
            badgeBackground = a.getDrawable(R.styleable.IconTextView_badgeTextBackground);
            badgeCount = a.getInt(R.styleable.IconTextView_badgeCount, 0);

            textBadge = new AppCompatTextView(getContext());
            textBadge.setId(R.id.iconImageViewBadge);
            textBadge.setTextColor(badgeTextColor);
            textBadge.setTextSize(TypedValue.COMPLEX_UNIT_PX, badgeTextSize);
            textBadge.setBackgroundDrawable(badgeBackground);
            textBadge.setPadding(
                    getResources().getDimensionPixelOffset(R.dimen.dp_5),
                    getResources().getDimensionPixelOffset(R.dimen.dp_2),
                    getResources().getDimensionPixelOffset(R.dimen.dp_5),
                    getResources().getDimensionPixelOffset(R.dimen.dp_2)
            );

            addView(textBadge);
            LayoutParams badgeParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            badgeParams.addRule(ALIGN_END, R.id.iconImageViewImageView);
            badgeParams.addRule(ALIGN_TOP, R.id.iconImageViewImageView);
            badgeParams.setMargins(0,
                    getResources().getDimensionPixelOffset(R.dimen.dp_4) * -1,
                    getResources().getDimensionPixelOffset(R.dimen.dp_7) * -1,
                    0);
            textBadge.setLayoutParams(badgeParams);
            setBadgeCount(badgeCount);
        } catch (Exception e) {
            LogcatLogger.d("IconTextView", e.getStackTrace());
        }

        a.recycle();
    }

    public void setBadgeCount(int count) {
        badgeCount = count;
        int shownCount = badgeCount;
        if (textBadge == null) {
            return;
        }

        if (shownCount < 1) {
            textBadge.setVisibility(View.GONE);
            return;
        }

        if (shownCount > 99) {
            shownCount = 99;
        }

        textBadge.setText(String.valueOf(shownCount));
        textBadge.setVisibility(View.VISIBLE);
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    public void setText(String text) {
        textView.setText(text);
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;

        if (imageView == null) {
            return;
        }

        if (checkedIcon == null) {
            return;
        }

        imageView.setImageDrawable(checked ? checkedIcon : icon);
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        isChecked = !isChecked;
    }
}
