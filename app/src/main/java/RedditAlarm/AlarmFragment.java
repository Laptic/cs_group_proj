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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;


public class AlarmFragment extends Fragment {
    Alarm alarm;
    int hour;
    int minute;
    int second;
    boolean[] dayBools;

    logicHandler logic;
    passAlarm passLogic;
    UIClass ui;
    MainMenuFragment mainRef;


    public AlarmFragment() {
        this.alarm = new Alarm();
    }

    //used to communicate information between fragment (AlarmFragment) and activity (UIClass)
    public interface logicHandler {
        public void addAlarm(Alarm alarm);
    }

    //used to pass an alarm object to mainMenu fragment
    public interface passAlarm {
        public void addAlarmFromFragment(Alarm alarm);
    }

    //ensure that the host activity implements the proper interface
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //populates the fragment with the layout of alarm_fragment, which is under res
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

                alarm.hour = Integer.parseInt(text);
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

                alarm.minute = Integer.parseInt(text);
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

                alarm.ampm = text;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //To get the inputs for the days of the week, we are gonna use toggle buttons
        //to get the days that the user wants the alarm to go off

        //Monday toggle button
        ToggleButton toggle_Mon = (ToggleButton) view.findViewById(R.id.toggle_mon);

        //.setOnCheckedChangeListener is used to listen for the button toggles
        //if the user makes a toggle, it will perform the following if-else statements
        toggle_Mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //checks if it is toggled, otherwise not

                if(isChecked) {

                    alarm.daysOfWeek[0] = true;
                }

                else {

                    alarm.daysOfWeek[0] = false;
                }
            }
        });

        //The toggle buttons for tuesday-sunday follow the exact layout of the monday
        //toggle button

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
        //Toggle button
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



        //the url is inputted now
        EditText urlText = (EditText) view.findViewById(R.id.urlText);
        //check if its empty
        alarm.url = urlText.getText().toString();


        //
        Button create_btn = (Button) view.findViewById(R.id.create_btn);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(view);
            }
        });


        Button back_btn = (Button) view.findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish_back(view);

            }
        });


        //returns the fragment
        return view;
    }

    //Not sure what to do with this anymore, ask ben
    private void populate() {
        dayBools = alarm.daysOfWeek;
    }

    public void finish(View view) {
        this.logic.addAlarm(this.alarm);
        //this.mainRef.addToList(this.alarm);
        this.ui.killAlarmEdit(this);
    }

    public void finish_back(View view) {
        this.ui.killAlarmEdit(this);
    }

}
