package com.example.projectnotes.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projectnotes.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmFragment extends Fragment implements View.OnClickListener {

    View addAlarmBtn;
    View daysCon;
    TimePicker timePicker;
    EditText descTV;
    CheckBox[] checkBoxes = new CheckBox[7];


    int[] days = {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
            Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY,
            Calendar.SUNDAY};
    int[] checkboxid = {R.id.edit_alarm_mon, R.id.edit_alarm_tues, R.id.edit_alarm_wed,
            R.id.edit_alarm_thurs, R.id.edit_alarm_fri, R.id.edit_alarm_sat,
            R.id.edit_alarm_sun};

    public static AlarmFragment newInstance() {
        AlarmFragment alarmFragment = new AlarmFragment();
        return alarmFragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        clearCheckbox();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_alarm, container, false);

        addAlarmBtn = root.findViewById(R.id.addAlarmBtn);
        timePicker = root.findViewById(R.id.edit_alarm_time_picker);
        descTV = root.findViewById(R.id.edit_alarm_label);
        daysCon = root.findViewById(R.id.daysCon);
        for (int i = 0; i < checkboxid.length; i++) {
            checkBoxes[i] = root.findViewById(checkboxid[i]);
            checkBoxes[i].setOnClickListener(this);
        }
        addAlarmBtn.setOnClickListener(this);
        clearCheckbox();

        return root;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAlarmBtn:
                setAlarm(timePicker, descTV.getText().toString(), getSelectedDays(checkBoxes));
                break;
            //checkboxes
        }

    }

    private ArrayList<Integer> getSelectedDays(CheckBox[] checkBoxes) {
        ArrayList<Integer> selected = new ArrayList<Integer>();

        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isChecked()) {
                selected.add(days[i]);
            }
        }
        return selected;
    }
    private void clearCheckbox() {
        int current = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < days.length; i++) {
            if (days[i] == current) {
                checkBoxes[i].setChecked(true);
            } else {
                checkBoxes[i].setChecked(false);
            }
        }
        daysCon.jumpDrawablesToCurrentState();
    }

    private void setAlarm(TimePicker timePicker, String desc, ArrayList<Integer> days) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.putExtra(AlarmClock.EXTRA_HOUR, timePicker.getHour());
            intent.putExtra(AlarmClock.EXTRA_MINUTES, timePicker.getMinute());
            if (desc != null) intent.putExtra(AlarmClock.EXTRA_MESSAGE, desc);
            intent.putExtra(AlarmClock.EXTRA_DAYS, desc);
            intent.putExtra(AlarmClock.EXTRA_DAYS, days);
        } else {

        }
        startActivity(intent);
    }
}
