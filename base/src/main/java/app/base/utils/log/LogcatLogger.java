package app.base.utils.log;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.honbabin.base.BuildConfig;

public final class LogcatLogger {

    public static void d(String tag, String message) {
        if (!BuildConfig.DEBUG) {
            return;
        }

        Log.d("HONBABINAPP", "[" + tag + "] " + message);
    }

    public static void d(String tag, String[] messages) {
        for (String message : messages) {
            d(tag, message);
        }
    }

    public static void d(String tag, Object object) {
        d(tag, object == null ? null : object.toString());
    }

    public static void d(String tag, Object[] objects) {
        for (Object object : objects) {
            d(tag, object == null ? null : object.toString());
        }
    }

    public static void e(Context context, String tag, String message) {
        Log.d(tag, message);

        if (!BuildConfig.DEBUG) {
            return;
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void e(Context context, String tag, String[] messages) {
        d(tag, messages);

        if (!BuildConfig.DEBUG) {
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        for (String message : messages) {
            if (message == null) {
                continue;
            }
            stringBuffer.append(message);
            stringBuffer.append("\n");
        }

        Toast.makeText(context, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void e(Context context, String tag, Object object) {
        d(tag, object);

        if (!BuildConfig.DEBUG) {
            return;
        }

        Toast.makeText(context, object == null ? null : object.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void e(Context context, String tag, Object[] objects) {
        d(tag, objects);

        if (!BuildConfig.DEBUG) {
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        for (Object object : objects) {
            if (object == null) {
                continue;
            }
            stringBuffer.append(object.toString());
            stringBuffer.append("\n");
        }

        Toast.makeText(context, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
    }
}
