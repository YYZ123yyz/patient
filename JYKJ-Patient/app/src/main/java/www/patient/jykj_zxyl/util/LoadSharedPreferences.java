package www.patient.jykj_zxyl.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import static android.content.Context.MODE_PRIVATE;

public class LoadSharedPreferences {
    private final static String PREFERENCE_NAME = "xxb";
    private final static int MODE = MODE_PRIVATE;
    public final static String MUSIC_CODE = "music_code";


    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, MODE);
    }
}
