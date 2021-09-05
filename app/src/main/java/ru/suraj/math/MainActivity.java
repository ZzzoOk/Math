package ru.suraj.math;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    final Fragment main = new MainFragment();
    final Fragment stats = new StatsFragment();
    final Fragment settings = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = new StatsFragment();

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (active != main) {
                        fm.beginTransaction().hide(active).show(main).commit();
                        active = main;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (active != stats) {
                        fm.beginTransaction().hide(active).show(stats).commit();
                        active = stats;
                    }
                    return true;
                case R.id.navigation_settings:
                    if (active != settings) {
                        fm.beginTransaction().hide(active).show(settings).commit();
                        active = settings;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.container, main).commit();
        fm.beginTransaction().add(R.id.container, stats).hide(stats).commit();
        fm.beginTransaction().add(R.id.container, settings).hide(settings).commit();
        active = main;

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        RecyclerView historyView = findViewById(R.id.history);
        List<row> history = ((Adapter)historyView.getAdapter()).mData;

        StringBuilder stats = new StringBuilder();
        for (int i = 0; i < history.size(); ++i)
            stats.append(history.get(i).getUsername()).append("/")
                    .append(history.get(i).getStat()).append(";");

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("Stats");
        editor.putString("Stats", stats.toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String stats = sharedPref.getString("Stats", null);

        if (stats == null || stats.isEmpty()) {
            return;
        }

        RecyclerView historyView = findViewById(R.id.history);
        Adapter adapter = (Adapter) historyView.getAdapter();

        adapter.clear();

        for (String s : stats.split(";")) {
            String[] row = s.split("/");
            adapter.add(row[0], row[1]);
        }

        adapter.notifyDataSetChanged();
    }

    public void showStats(long elapsedTime) {
        Bundle args = new Bundle();
        args.putLong("elapsedTime", elapsedTime);
        stats.setArguments(args);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }

    public void setTasksCount(int tasksCount) {
        Bundle args = new Bundle();
        args.putInt("tasksCount", tasksCount);
        main.setArguments(args);
    }
}
