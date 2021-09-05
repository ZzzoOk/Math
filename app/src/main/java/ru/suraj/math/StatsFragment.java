package ru.suraj.math;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {
    private TextView stat;

    List<row> stats;
    Adapter adapter;
    long elapsedTime;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && getArguments() != null && getArguments().containsKey("elapsedTime")) {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String username = sharedPref.getString("Username", "Noname");

            elapsedTime = getArguments().getLong("elapsedTime");
            stat.setText(String.format("%.2f sec", (double)elapsedTime / 1000));
            getArguments().clear();

            row r = new row(username, stat.getText().toString());
            stats.add(0, r);
            adapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        stat = view.findViewById(R.id.stat);
        RecyclerView history = view.findViewById(R.id.history);

        stats = new ArrayList<>();

        adapter = new Adapter(getActivity().getApplicationContext(), stats);
        history.setAdapter(adapter);
        history.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        stat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                stats.clear();
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return view;
    }
}
