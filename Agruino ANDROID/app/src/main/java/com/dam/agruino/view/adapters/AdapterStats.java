package com.dam.agruino.view.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.agruino.R;
import com.dam.agruino.controler.Tools;
import com.dam.agruino.model.Value;
import com.dam.agruino.model.ValueHistory;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;
import java.util.Objects;

public class AdapterStats extends RecyclerView.Adapter<AdapterStats.StatsVH>{
    private List<Value> mValues;
    private int mItemPos;
    private View.OnClickListener mListener;
    private FirebaseFirestore db;
    private ValueHistory valueHistory;

    public AdapterStats() {
        this.mValues = null;
        mItemPos = -1;
        mListener = null;
        db = FirebaseFirestore.getInstance();
    }

    public void setValues(List<Value> mValues) {
        this.mValues = mValues;
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

    public Value getItem(int pos) {
        return mValues.get(pos);
    }

    @NonNull
    @Override
    public AdapterStats.StatsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_stats, parent, false);
        return new AdapterStats.StatsVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStats.StatsVH holder, int position) {
        if (mValues != null) {
            holder.setItem(mValues.get(position));
            holder.itemView.setBackgroundColor((mItemPos == position)
                    ? holder.itemView.getContext().getResources().getColor(R.color.colorPrimary)
                    : Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        else
            return 0;
    }

    public class StatsVH extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView tvTittleKey;
        private ImageView imIcon;
        private View mItemView;

        public StatsVH(@NonNull View itemView) {
            super(itemView);
            tvTittleKey = itemView.findViewById(R.id.tvTittleStats);
            imIcon = itemView.findViewById(R.id.imIconStats);
            itemView.setOnClickListener(this);
            mItemView = itemView;
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            notifyItemChanged(mItemPos);
            mItemPos = (mItemPos == pos) ? -1 : pos;

            notifyItemChanged(mItemPos);
            if (mListener != null)
                mListener.onClick(v);
        }

        public void setItem(Value value) {
            imIcon.setImageResource(R.drawable.grafica);
            if(value.getKey().contains("Temp"))
                tvTittleKey.setText(R.string.temperature);
            else
                tvTittleKey.setText(value.getKey());
        }
    }

}
