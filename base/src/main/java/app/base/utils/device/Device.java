package app.base.utils.device;

import android.os.Build;

/**
 * 앱이 설치된 디바이스 정보.
 * 브랜드, 모델, OS버전, 네트워크 상태, 권한 상태를 알려준다.
 * 권한 요청, 네트워크 세팅 등의 동작을
 */
public class Device {

    private final static String BRAND = Build.BRAND;
    private final static String MODEL = Build.MODEL;
    private final static int OS_VERSION = Build.VERSION.SDK_INT;

    public void isOnline() {

    }
}
