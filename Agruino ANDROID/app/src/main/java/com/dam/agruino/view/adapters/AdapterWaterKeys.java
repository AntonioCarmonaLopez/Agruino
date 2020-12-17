package com.dam.agruino.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.agruino.R;
import com.dam.agruino.model.Values;

import java.util.ArrayList;
import java.util.List;

public class AdapterWaterKeys extends RecyclerView.Adapter<AdapterWaterKeys.ValuesVH> {

    private List<String> mKeys;
    private int mItemPos;
    private View.OnClickListener mListener;

    public AdapterWaterKeys() {
        this.mKeys = null;
        mItemPos = -1;
        mListener = null;
    }

    public void setKeys(List<String> keys) {
        mKeys= keys;
    }

    public int getItemPos() {
        return mItemPos;
    }

    public void setItemPos(int itemPos) {
        mItemPos = itemPos;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public String getItem(int pos) {
            if(pos >=0)
                return mKeys.get(pos);
            return "";
    }

    @NonNull
    @Override
    public AdapterWaterKeys.ValuesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_water_keys, parent, false);
        return new ValuesVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWaterKeys.ValuesVH holder, int position) {
        if (mKeys != null) {
            holder.setItem(mKeys.get(position));
            holder.itemView.setBackgroundColor((mItemPos == position)
                    ? holder.itemView.getContext().getResources().getColor(R.color.colorPrimary)
                    : Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        if (mKeys != null)
            return mKeys.size();
        else
            return 0;
    }

    public class ValuesVH extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView tvKey;
        private ImageView imIcon;
        private View mItemView;

        public ValuesVH(@NonNull View itemView) {
            super(itemView);
            tvKey = itemView.findViewById(R.id.tvKey);
            imIcon = itemView.findViewById(R.id.imIcon);
            itemView.setOnClickListener(this);
            mItemView = itemView;
        }

        private void setItem(String key) {
                tvKey.setText(key);
                imIcon.setImageResource(R.drawable.grafica);

        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            notifyItemChanged(mItemPos);
            mItemPos = (mItemPos == pos) ? -1 : pos;
            Log.d("agruino", Integer.toString(mItemPos));
            notifyItemChanged(mItemPos);
            if (mListener != null)
                mListener.onClick(v);
        }
    }

}