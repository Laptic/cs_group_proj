package RedditAlarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AlarmFragment extends Fragment {
    Alarm alarm;
    int hour;
    int minute;
    int second;
    boolean[] dayBools;

    OnMessageReadListener messageReadListener;

    public AlarmFragment() {

    }

    public interface OnMessageReadListener {

        public void onMessageRead(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            messageReadListener = (OnMessageReadListener) activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must override onMessageRead...");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alarm_fragment, container,false);

        alarm = new Alarm();

        //Create an object for the spinner box HOUR
        Spinner spinner_hour = view.findViewById(R.id.spinner_hour);
        //create an array that contains the strings representing HOURS
        ArrayAdapter<CharSequence> adapterHr =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.hours,android.R.layout.simple_spinner_item);
        //allows the spinner object to show all the different values through a drop down list
        adapterHr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //populates the spinner list with values
        spinner_hour.setAdapter(adapterHr);

        //Does something when the user clicks on the spinner box for hours
        spinner_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //parent is the the array of the hour spinner and position is the current place
                //of the item that the user picked
                String text = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Create an object for the spinner box MINUTE
        Spinner spinner_minute = view.findViewById(R.id.spinner_min);
        //create an array that contains the strings representing MINUTES
        ArrayAdapter<CharSequence> adapterMin =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.minutes,android.R.layout.simple_spinner_item);

        //allows the spinner object to show all the different values through a drop down list
        adapterMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //populates the spinner list with values
        spinner_minute.setAdapter(adapterMin);
        //Does something when the user clicks on the spinner box for minutes
        spinner_minute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Create an object for the spinner box AMPM
        Spinner spinner_ampm = view.findViewById(R.id.spinner_AmOrPm);
        //create an array that contains the strings representing AMPM
        ArrayAdapter<CharSequence> adapterAmPm =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                        R.array.AMPM,android.R.layout.simple_spinner_item);
        //allows the spinner object to show all the different values through a drop down list
        adapterAmPm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //populates the spinner list with values
        spinner_ampm.setAdapter(adapterAmPm);


        //Does something when the user clicks on the spinner box for AMPM
        spinner_ampm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        final TextView txt_name = (TextView) view.findViewById(R.id.text_NAME);

        ToggleButton toggle_Mon = (ToggleButton) view.findViewById(R.id.toggle_mon);

        toggle_Mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {

                    txt_name.setText("IS COOL");

                }

                else {
                    txt_name.setText("John M");

                }
            }
        });

        ToggleButton toggle_Tues = (ToggleButton) view.findViewById(R.id.toggle_tues);

        toggle_Tues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    alarm.daysOfWeek[1] = true;
                }
                else {
                    alarm.daysOfWeek[1] = false;
                }
            }
        });

        ToggleButton toggle_Weds = (ToggleButton) view.findViewById(R.id.toggle_wed);

        toggle_Weds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    alarm.daysOfWeek[2] = true;
                }
                else {
                    alarm.daysOfWeek[2] = false;
                }
            }
        });

        ToggleButton toggle_Thurs = (ToggleButton) view.findViewById(R.id.toggle_thurs);

        toggle_Thurs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    alarm.daysOfWeek[3] = true;
                }
                else {
                    alarm.daysOfWeek[3] = false;
                }
            }
        });

        ToggleButton toggle_Fri = (ToggleButton) view.findViewById(R.id.toggle_fri);

        toggle_Fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    alarm.daysOfWeek[4] = true;
                }
                else {
                    alarm.daysOfWeek[4] = false;

                }
            }
        });

        ToggleButton toggle_Sat = (ToggleButton) view.findViewById(R.id.toggle_sat);

        toggle_Sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    alarm.daysOfWeek[5] = true;
                }
                else {
                    alarm.daysOfWeek[5] = false;
                }
            }
        });

        ToggleButton toggle_Sun = (ToggleButton) view.findViewById(R.id.toggle_sun);

        toggle_Sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    alarm.daysOfWeek[6] = true;
                }
                else {
                    alarm.daysOfWeek[6] = false;
                }

            }
        });
        return view;
    }

    private void populate() {
        dayBools = alarm.daysOfWeek;

    }


}
