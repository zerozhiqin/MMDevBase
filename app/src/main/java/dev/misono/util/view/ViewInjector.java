package dev.misono.util.view;

import android.app.Activity;
import android.view.View;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class ViewInjector {

    static class InjectInfo {
        public InjectInfo(Field into, int inject) {
            this.into = into;
            this.inject = inject;
        }

        Field into;
        int inject;
    }

    static class ClassInfo {
        String className;
        ArrayList<InjectInfo> fields = new ArrayList<>();

        static HashMap<String, SoftReference<ClassInfo>> infoCache = new HashMap<>();

        static ClassInfo get(Class<?> clazz) {
            ClassInfo classInfo = null;
            SoftReference<ClassInfo> infoSoft = infoCache.get(clazz.getName());
            if (infoSoft == null || infoSoft.get() == null) {
                classInfo = build(clazz);
                infoCache.put(clazz.getName(), new SoftReference<ClassInfo>(classInfo));
            } else {
                classInfo = infoSoft.get();
            }
            return classInfo;
        }

        static ClassInfo build(Class<?> clazz) {
            ClassInfo classInfo = new ClassInfo();

            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                ViewInject inject = field.getAnnotation(ViewInject.class);
                if (inject != null) {
                    int id = inject.value();
                    InjectInfo injectInfo = new InjectInfo(field,id);
                    classInfo.fields.add(injectInfo);
                }
            }

            return classInfo;
        }
    }

    public static void inject(Object object, View itemView) {
        ClassInfo classInfo = ClassInfo.get(object.getClass());
        for (InjectInfo injectInfo : classInfo.fields) {
            injectInfo.into.setAccessible(true);
            try {
                injectInfo.into.set(object, itemView.findViewById(injectInfo.inject));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    public static void inject(Object object, Activity activity) {
        ClassInfo classInfo = ClassInfo.get(object.getClass());
        for (InjectInfo injectInfo : classInfo.fields) {
            injectInfo.into.setAccessible(true);
            try {
                injectInfo.into.set(object, activity.findViewById(injectInfo.inject));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
