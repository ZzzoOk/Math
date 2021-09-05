package ru.suraj.math;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private EditText tasksCountField;
    private EditText usernameField;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        loadSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        usernameField = view.findViewById(R.id.username);

        tasksCountField = view.findViewById(R.id.tasksCount);

        loadSettings();

        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String username = usernameField.getText().toString();
        String count = tasksCountField.getText().toString();
        editor.putString("Username", username);
        editor.putString("TasksCount", count);
        editor.apply();

        ((MainActivity) getActivity()).setTasksCount(Integer.parseInt(count));
    }

    private void loadSettings() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString("Username", "Noname");
        String count = sharedPref.getString("TasksCount", "50");

        tasksCountField.setText(count);
        usernameField.setText(username);
    }
}
