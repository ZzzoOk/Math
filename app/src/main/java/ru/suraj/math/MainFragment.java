package ru.suraj.math;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainFragment extends Fragment implements View.OnClickListener {
    String num;
    int defaultColor;
    int tasksCount;
    int tasksSolved;
    long startTime, elapsedTime;
    private TextView task;
    private TextView counter;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && getArguments() != null
                && getArguments().containsKey("tasksCount")) {
            tasksCount = getArguments().getInt("tasksCount");
            reset();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String count = sharedPref.getString("TasksCount", "50");

        task = view.findViewById(R.id.task);
        counter = view.findViewById(R.id.counter);
        defaultColor = task.getCurrentTextColor();

        num = "";
        startTime = 0;
        tasksCount = Integer.parseInt(count);
        tasksSolved = -1;
        counter.setText(String.format("0/%d", tasksCount));

        Button button0 = view.findViewById(R.id.button0);
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);
        Button button4 = view.findViewById(R.id.button4);
        Button button5 = view.findViewById(R.id.button5);
        Button button6 = view.findViewById(R.id.button6);
        Button button7 = view.findViewById(R.id.button7);
        Button button8 = view.findViewById(R.id.button8);
        Button button9 = view.findViewById(R.id.button9);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        task.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.task) {
            reset();
        } else {
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }

            String[] nums = task.getText().toString().split("\\+");
            num += ((Button) v).getText().toString();
            String result = String.valueOf(Integer.parseInt(nums[0].trim())
                    + Integer.parseInt(nums[1].trim()));

            if (result.equals(num)) {
                num = "";
                task.setTextColor(defaultColor);

                tasksSolved++;
                if (tasksSolved == tasksCount) {
                    elapsedTime = System.currentTimeMillis() - startTime;

                    counter.setText(String.format("%d/%d", tasksCount, tasksCount));

                    ((MainActivity) getActivity()).showStats(elapsedTime);

                    reset();

                    return;
                }

                Random r = new Random();
                task.setText(String.format("%d + %d", r.nextInt(9) + 1, r.nextInt(9) + 1));
                counter.setText(String.format("%d/%d", tasksSolved, tasksCount));
            } else if (result.startsWith(num)) {
                task.setTextColor(defaultColor);
            } else {
                task.setTextColor(Color.RED);
                num = "";
            }
        }
    }

    private void reset() {
        num = "";
        startTime = 0;
        tasksSolved = -1;

        task.setTextColor(defaultColor);
        task.setText("1 + 1");
        counter.setText(String.format("0/%d", tasksCount));
    }
}
