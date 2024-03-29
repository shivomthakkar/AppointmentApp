package com.quicsolv.appointmentapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  23 Apr 2018
 ***********************************************************************/

public class Prefs {
    private final static String PREF_FILE = "APPOINTMENT_PREFS";

    public final static String PREF_AUTH_TOKEN = "auth_token";
    public final static String PREF_PID = "pid";
    public final static String PREF_QUESTION_COMPLETED = "ques_completed";
    public final static String PREF_EMAIL = "prefs_email";
    public final static String PREF_PASSWORD = "prefs_password";

    //Profile Prefs
    public final static String PREF_PATIENT_NAME = "patient_name";
    public final static String PREF_PATIENT_EMAIL = "patient_email";
    public final static String PREF_PATIENT_PHONE = "patient_phone";
    public final static String PREF_PATIENT_GENDER = "patient_gender";
    public final static String PREF_PATIENT_DOB = "patient_dob";
    public final static String PREF_PATIENT_PROFILE_IMAGE_URL_ = "patient_profile_image_url";

    public final static String PREF_IS_FROM_REQUEST_APT = "isFromRequestApt";

    public final static String PREF_UPLOADED_FILE_BASE_URL = "uploadedFileBaseUrl";
    public final static String PREF_PROFILE_IMAGE_BASE_URL = "profileImageBaseUrl";
    public final static String PREF_DOCTOR_PROFILE_IMAGE_BASE_URL = "doctorProfileImageBaseUrl";

    public final static String PREF_IS_EMAIL_VERIFICATION_MAIL_ALREADY_SENT = "emailVerificationMailAlreadySent";

    public final static String PREF_WANT_TO_EXIT = "wantToExit";
    public final static String PREF_NOTIFICATION_DATA = "notificationData";
    public static boolean IS_FROM_NOTIFICATION = false;

    /**
     * Set a string shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Set a integer shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void setSharedPreferenceInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Set a Boolean shared preference
     *
     * @param key   - Key to set shared preference
     * @param value - Value for the key
     */
    public static void setSharedPreferenceBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get a string shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static String getSharedPreferenceString(Context context, String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, defValue);
    }

    /**
     * Get a integer shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static int getSharedPreferenceInt(Context context, String key, int defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getInt(key, defValue);
    }

    /**
     * Get a boolean shared preference
     *
     * @param key      - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public static boolean getSharedPreferenceBoolean(Context context, String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getBoolean(key, defValue);
    }
}
