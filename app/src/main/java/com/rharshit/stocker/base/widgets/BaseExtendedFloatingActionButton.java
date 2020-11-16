package com.rharshit.stocker.base.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class BaseExtendedFloatingActionButton extends ExtendedFloatingActionButton {
    public BaseExtendedFloatingActionButton(@NonNull Context context) {
        super(context);
    }

    public BaseExtendedFloatingActionButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseExtendedFloatingActionButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
