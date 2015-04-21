package dev.misono.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import dev.misono.app.MApp;

public class SPHelper {

    public static class Edit {
        private Editor editor;

        Edit(String spName) {
            SharedPreferences sp = MApp.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
            editor = sp.edit();
        }

        Edit() {
            this("SP_NAME");
        }

        public Edit put(String key, String value) {
            editor.putString(key, value);
            return this;
        }

        public Edit put(String key, boolean value) {
            editor.putBoolean(key, value);
            return this;
        }

        public Edit put(String key, float value) {
            editor.putFloat(key, value);
            return this;
        }

        public Edit put(String key, int value) {
            editor.putInt(key, value);
            return this;
        }

        public Edit put(String key, long value) {
            editor.putLong(key, value);
            return this;
        }

        public void save() {
            editor.commit();
        }

    }

    public static class Read {
        private SharedPreferences sp;

        Read(String spName) {
            sp = MApp.getInstance().getSharedPreferences(spName, Context.MODE_PRIVATE);
        }

        Read() {
            this("SP_NAME");
        }

        public String getString(String key, String defValue) {
            return sp.getString(key, defValue);
        }

        public int getInt(String key, int defValue) {
            return sp.getInt(key, defValue);
        }

        public boolean getBoolean(String key, boolean defValue) {
            return sp.getBoolean(key, defValue);
        }

        public float getFloat(String key, float defValue) {
            return sp.getFloat(key, defValue);
        }

        public long getLong(String key, long defValue) {
            return sp.getLong(key, defValue);
        }
    }

    public static Edit edit() {
        Edit edit = new Edit();
        return edit;
    }

    public static Read read() {
        Read read = new Read();
        return read;
    }

}
