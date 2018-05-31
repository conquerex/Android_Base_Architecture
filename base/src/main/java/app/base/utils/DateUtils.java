package app.base.utils;

public final class DateUtils {

    public static int maxDayOfMonth(int year, int month) {

        if (month < 1 || month > 12 || year < 0) {
            throw new RuntimeException("please input valid month");
        }

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }

        if (month != 2) {
            return 30;
        }

        boolean isLeapYer = false;

        int modular4 = year % 4;
        int modular100 = year % 100;
        int modular400 = year % 400;
        if ((0 == (modular4) && 0 != modular100)
                || 0 == modular400) {
            isLeapYer = true;
        } else {
            isLeapYer = false;
        }

        if (isLeapYer) {
            return 29;
        } else {
            return 28;
        }
    }
}
