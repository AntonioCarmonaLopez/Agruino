package com.dam.agruino.view.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.agruino.R;
import com.dam.agruino.model.Value;
import com.dam.agruino.model.ValueLog;

import java.util.List;

public class AdapterLog extends RecyclerView.Adapter<AdapterLog.LogVH>{
    private List<ValueLog> mValues;
    private int mItemPos;
    private View.OnClickListener mListener;

    public AdapterLog() {
        this.mValues = null;
        mItemPos = -1;
        mListener = null;
    }

    public void setValues(List<ValueLog> mValues) {
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
    public AdapterLog.LogVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_rv_log, parent, false);
        return new AdapterLog.LogVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLog.LogVH holder, int position) {
        if (mValues != null) {
            holder.setItem(mValues.get(position));
            holder.itemView.setBackgroundColor((mItemPos == position)
                    ? holder.itemView.getContext().getResources().getColor(R.color.colorPrimary)
                    : Color.TRANSPARENT);
            if(isPar(position))
                holder.itemView.setBackgroundColor(Color.GRAY);
            else
                holder.itemView.setBackgroundColor(Color.CYAN);

        }
    }

    private boolean isPar(int n){
        if ( ( n % 2 ) == 0 ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        else
            return 0;
    }

    public class LogVH extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView tvDateLog,tvTime,tvValueLog;
        private View mItemView;

        public LogVH(@NonNull View itemView) {
            super(itemView);

            tvDateLog = itemView.findViewById(R.id.tvDateLog);
            tvTime = itemView.findViewById(R.id.tvTimeLog);
            tvValueLog = itemView.findViewById(R.id.tvValueLog);

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

        public void setItem(ValueLog valueLog) {
            tvDateLog.setText(valueLog.getDate());
            tvTime.setText(valueLog.getTime());
            tvValueLog.setText(valueLog.getValue()+" "+valueLog.getMeasure());
        }
    }

}
