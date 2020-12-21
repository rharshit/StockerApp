package com.rharshit.stocker.base.widgets;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rharshit.stocker.base.data.BaseData;

import java.util.List;

public abstract class BaseAdapter<VH extends BaseViewHolder, D extends BaseData>
        extends RecyclerView.Adapter<VH> {

    private Context context;
    private List<D> data;

    public BaseAdapter(Context context, List<D> data) {
        this.context = context;
        this.data = data;
    }

    public void updateList(List<D> updatedData) {
        data.clear();
        data.addAll(updatedData);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
    }

    public D getData(int i) {
        return data.get(i);
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull VH holder, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }
}
