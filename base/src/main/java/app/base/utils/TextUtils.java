package app.base.utils;

import android.text.Layout;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextUtils {
    private static final String REGULAR_EXPRESSION_BIRTHDAY_MMDDYYYY = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";
    private static final String REGULAR_EXPRESSION_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(REGULAR_EXPRESSION_EMAIL);
    private static final Pattern BIRTHDAY_PATTERN = Pattern.compile(REGULAR_EXPRESSION_BIRTHDAY_MMDDYYYY);

    public static boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidBirthDayFromFacebook(String birthday) {
        Matcher matcher = BIRTHDAY_PATTERN.matcher(birthday);
        return matcher.matches();
    }

    public static String changeFormatBirthday(String mmddyyyy) {
        String yyyymmdd = "";
        if (!BIRTHDAY_PATTERN.matcher(mmddyyyy).matches()) {
            return yyyymmdd;
        }

        String month = mmddyyyy.substring(0, 2);
        String day = mmddyyyy.substring(3, 5);
        String year = mmddyyyy.substring(6, 10);
        yyyymmdd = year + month + day;

        return yyyymmdd;
    }

    public static boolean isEllipsized(TextView textView) {
        if (textView == null) {
            return false;
        }

        Layout layout = textView.getLayout();

        if (layout == null) {
            return false;
        }

        int lines = layout.getLineCount();

        if (lines > 0) {
            int ellipsisCount = layout.getEllipsisCount(lines - 1);
            if (ellipsisCount > 0) {
                return true;
            }
        }
        return false;
    }

    public static String getStringFromArray(List array) {
        StringBuffer ret = new StringBuffer();
        if (array == null || array.isEmpty()) {
            return ret.toString();
        }

        for (int index = 0; index < array.size(); index++) {
            Object item = array.get(index);

            if (item == null) {
                continue;
            }

            if (item == null || item.toString().isEmpty()) {
                continue;
            }

            ret.append(item.toString());
            if (index < array.size() - 1) {
                ret.append(", ");
            }
        }

        return ret.toString();
    }
}