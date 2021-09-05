package ru.suraj.math;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context mContext;
    List<row> mData;

    public Adapter(Context mContext, List<row> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.row, viewGroup, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.username.setText(mData.get(i).getUsername());
        myViewHolder.stat.setText(mData.get(i).getStat());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(String username, String stat) {
        mData.add(new row(username, stat));
    }

    public void clear() {
        mData.clear();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView username, stat;

        public myViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameRow);
            stat = itemView.findViewById(R.id.statRow);
        }
    }
}
