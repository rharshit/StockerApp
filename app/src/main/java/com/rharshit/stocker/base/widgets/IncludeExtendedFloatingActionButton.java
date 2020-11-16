package com.rharshit.stocker.base.widgets;

import android.view.View;

public interface IncludeExtendedFloatingActionButton {
    boolean isExtendedFabRequired();

    void onExtendedFabClickListener(View v);

    boolean onExtendedFabLongClickListener(View v);
}
