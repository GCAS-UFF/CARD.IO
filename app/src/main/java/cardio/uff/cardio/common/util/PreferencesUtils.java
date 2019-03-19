package cardio.uff.cardio.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {
    public static final String CURRENT_PATIENT_KEY = "currentPatientKey";
    public static final String IS_CURRENT_USER_PROFESSIONAL = "currentUserType";

    private static final String PREF_ID = "CARDIO";
    private static SharedPreferences sharedpreferences;

    public static void initialize (Context context) {
        sharedpreferences = context.getSharedPreferences(PREF_ID, Context.MODE_PRIVATE);
    }

    public static void setBollean(Context context, String chave, boolean on) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(chave, on);
        editor.commit();
    }

    public static boolean getBollean(Context context, String chave) {
        boolean b = sharedpreferences.getBoolean(chave, false);
        return b;
    }

    public static void setInteger(Context context, String chave, int valor) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(chave, valor);
        editor.commit();
    }

    public static int getInteger(Context context, String chave) {
        return sharedpreferences.getInt(chave, 0);
    }

    public static void setString(Context context, String chave, String valor) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(chave, valor);
        editor.commit();
    }

    public static String getString(Context context, String chave) {
        return sharedpreferences.getString(chave, null);  //String.valueOf(pref.getInt(chave, 0));
    }
}
