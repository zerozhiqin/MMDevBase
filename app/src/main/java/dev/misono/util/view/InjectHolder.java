package dev.misono.util.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class InjectHolder extends RecyclerView.ViewHolder {

    public InjectHolder(ViewGroup recyclerView, int layout) {
        super(LayoutInflater.from(recyclerView.getContext()).inflate(layout, recyclerView, false));
        inject(this, itemView);
    }

    public InjectHolder(View itemView) {
        super(itemView);
        inject(this, itemView);
    }

    public static void inject(Object viewHolder, View itemView) {
        Field[] fields = viewHolder.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            VHInject inject = field.getAnnotation(VHInject.class);
            if (inject != null) {
                int id = inject.value();
                field.setAccessible(true);
                try {
                    field.set(viewHolder, itemView.findViewById(id));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                if (View.class.isAssignableFrom(field.getType())) {
                    try {
                        String packageName = itemView.getContext().getPackageName();
                        Field idField = Class.forName(packageName + ".R$id").getField(field.getName());
                        int id = idField.getInt(null);
                        field.setAccessible(true);
                        try {
                            field.set(viewHolder, itemView.findViewById(id));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
