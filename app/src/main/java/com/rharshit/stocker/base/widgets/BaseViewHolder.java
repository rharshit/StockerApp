package com.rharshit.stocker.base.widgets;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private ViewDataBinding viewDataBinding;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public BaseViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.viewDataBinding = viewDataBinding;
    }

    public View getItemView() {
        return itemView == null ? viewDataBinding.getRoot() : itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public ViewDataBinding getViewDataBinding() {
        return viewDataBinding;
    }

    public void setViewDataBinding(ViewDataBinding viewDataBinding) {
        this.viewDataBinding = viewDataBinding;
    }
}
