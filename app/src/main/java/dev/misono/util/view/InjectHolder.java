package dev.misono.util.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InjectHolder extends RecyclerView.ViewHolder {

    public InjectHolder(ViewGroup recyclerView, int layout) {
        super(LayoutInflater.from(recyclerView.getContext()).inflate(layout, recyclerView, false));
        ViewInjector.inject(this, itemView);
    }

    public InjectHolder(View itemView) {
        super(itemView);
        ViewInjector.inject(this, itemView);
    }
}
