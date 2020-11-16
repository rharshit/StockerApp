package com.rharshit.stocker.base.widgets;

import android.view.View;

public interface IncludeFloatingActionButton {
    boolean isFabRequired();

    void onFabClickListener(View v);

    boolean onFabLongClickListener(View v);
}
