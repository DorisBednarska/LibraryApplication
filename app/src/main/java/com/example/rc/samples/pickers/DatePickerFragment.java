package com.example.rc.samples.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private TextView birthdayButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int month = 05;
        int day = 05;
        int year = 1992;
        String[] date = birthdayButton.getText().toString().split("-");
        if (date.length == 3) {
            day = Integer.valueOf(date[0]);
            month = Integer.valueOf(date[1]) - 1;
            year = Integer.valueOf(date[2]);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            datePickerDialog.getDatePicker().setLayoutMode(1);
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DAY_OF_MONTH, 1);
            datePickerDialog.getDatePicker().setMaxDate(instance.getTimeInMillis());
        }
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        birthdayButton.setText(String.format("%02d", (dayOfMonth)) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
    }

    public void setBirthdayButton(TextView birthdayButton) {
        this.birthdayButton = birthdayButton;
    }
}
