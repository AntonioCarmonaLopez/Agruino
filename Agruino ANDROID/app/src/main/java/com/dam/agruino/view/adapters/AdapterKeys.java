package com.dam.agruino.view.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.agruino.R;
import com.dam.agruino.model.Key;

import java.util.List;

public class AdapterKeys extends RecyclerView.Adapter<AdapterKeys.KeysVH> {

    private List<Key> mKeys;
    private int mItemPos;
    private View.OnClickListener mListener;
    private Button btAccept,btCancel;

    public AdapterKeys() {
        this.mKeys = null;
        mItemPos = -1;
        mListener = null;
    }

    public void setKeys(List<Key> mKeys) {
        this.mKeys = mKeys;
    }

    public int getItemPos() {
        return mItemPos;
    }

    public void setItemPos(int itemPos) {
        this.mItemPos = itemPos;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public Key getItem(int pos) {
        return mKeys.get(pos);
    }

    @NonNull
    @Override
    public AdapterKeys.KeysVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_keys, parent, false);
        return new AdapterKeys.KeysVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKeys.KeysVH holder, int position) {
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

    public class KeysVH extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView tvKey;
        private ImageView imIcon;
        private View mItemView;

        public KeysVH(@NonNull View itemView) {
            super(itemView);
            tvKey = itemView.findViewById(R.id.tvTittleKeyLog);
            imIcon = itemView.findViewById(R.id.imbIcon);
            itemView.setOnClickListener(this);
            mItemView = itemView;
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

        public void setItem(Key key) {
            tvKey.setText(key.getKey());
            imIcon.setImageResource(key.getIcon());
        }
    }
}
