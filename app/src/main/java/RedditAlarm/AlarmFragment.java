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
    int id;
    int hour;
    int minute;
    boolean PM = false;
    boolean[] dayBools;
    String url;
    boolean edit = false;

    LogicHandler logic;
    UIClass ui;
    MainMenuFragment mainRef;


    public AlarmFragment() {
        this.alarm = new Alarm();
        this.dayBools = new boolean[7];
        for (int i = 0; i < dayBools.length; i++) {
            dayBools[i] = false;
        }
        hour = 12;
        minute = 0;
        url = "";
    }



    //used to communicate information between fragment (AlarmFragment) and activity (UIClass)
    public interface logicHandler {
        public void addAlarm(Alarm alarm);
        public void editAlarm(Alarm alarm);
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

        //Create an object for the spinner box HOUR
        Spinner spinner_hour = view.findViewById(R.id.spinner_hour);

        //Create an object for the spinner box MINUTE
        Spinner spinner_minute = view.findViewById(R.id.spinner_min);

        //Create an object for the spinner box AMPM
        Spinner spinner_ampm = view.findViewById(R.id.spinner_AmOrPm);

        String hour_str = this.hour + "";

        String min_str = this.minute + "";

        //spinner_hour.setSelection(getIndex(spinner_hour,hour_str));

        //spinner_minute.setSelection(getIndex(spinner_minute,min_str));

        String PM_str = "";

        if(this.PM) {
            PM_str = "pm";
        }
        else {
            PM_str = "am";
        }



        spinner_ampm.setSelection(getIndex(spinner_ampm,PM_str));

        //Monday toggle button
        ToggleButton toggle_Mon = (ToggleButton) view.findViewById(R.id.toggle_mon);

        //The toggle buttons for tuesday-sunday follow the exact layout of the monday
        //toggle button

        ToggleButton toggle_Tues = (ToggleButton) view.findViewById(R.id.toggle_tues);

        ToggleButton toggle_Weds = (ToggleButton) view.findViewById(R.id.toggle_wed);

        ToggleButton toggle_Thurs = (ToggleButton) view.findViewById(R.id.toggle_thurs);

        ToggleButton toggle_Fri = (ToggleButton) view.findViewById(R.id.toggle_fri);

        ToggleButton toggle_Sat = (ToggleButton) view.findViewById(R.id.toggle_sat);

        ToggleButton toggle_Sun = (ToggleButton) view.findViewById(R.id.toggle_sun);

        toggle_Sun.setChecked(dayBools[0]);

        toggle_Mon.setChecked(dayBools[1]);

        toggle_Tues.setChecked(dayBools[2]);

        toggle_Weds.setChecked(dayBools[3]);

        toggle_Thurs.setChecked(dayBools[4]);

        toggle_Fri.setChecked(dayBools[5]);

        toggle_Sat.setChecked(dayBools[6]);



        //create an array that contains the strings representing HOURS
        ArrayAdapter<CharSequence> adapterHr =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                        R.array.hours,android.R.layout.simple_spinner_item);
        //allows the spinner object to show all the different values through a drop down list
        adapterHr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //populates the spinner list with values
        spinner_hour.setAdapter(adapterHr);
//
        spinner_hour.setSelection(getIndex(spinner_hour,hour_str));

        //Does something when the user clicks on the spinner box for hours
        spinner_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //parent is the the array of the hour spinner and position is the current place
                //of the item that the user picked
                String text = parent.getItemAtPosition(position).toString();

                hour = Integer.parseInt(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //create an array that contains the strings representing MINUTES
        ArrayAdapter<CharSequence> adapterMin =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                        R.array.minutes,android.R.layout.simple_spinner_item);

        //allows the spinner object to show all the different values through a drop down list
        adapterMin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //populates the spinner list with values
        spinner_minute.setAdapter(adapterMin);

        spinner_minute.setSelection(getIndex(spinner_minute,min_str));

        //Does something when the user clicks on the spinner box for minutes
        spinner_minute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = parent.getItemAtPosition(position).toString();

                minute = Integer.parseInt(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //create an array that contains the strings representing AMPM
        ArrayAdapter<CharSequence> adapterAmPm =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                        R.array.AMPM,android.R.layout.simple_spinner_item);
        //allows the spinner object to show all the different values through a drop down list
        adapterAmPm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //populates the spinner list with values
        spinner_ampm.setAdapter(adapterAmPm);


        spinner_ampm.setSelection(getIndex(spinner_ampm,PM_str));
        //Does something when the user clicks on the spinner box for AMPM
        spinner_ampm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();


                if (text.equals("pm")) {
                    PM = true;

                    if(hour == 12) {

                        return;
                    }
                    hour = hour + 12;
                }

                else {
                    PM = false;

                    if(hour > 12) {
                        hour = hour - 12;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //To get the inputs for the days of the week, we are gonna use toggle buttons
        //to get the days that the user wants the alarm to go off



        //.setOnCheckedChangeListener is used to listen for the button toggles
        //if the user makes a toggle, it will perform the following if-else statements
        toggle_Mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //checks if it is toggled, otherwise not

                if(isChecked) {

                    dayBools[1] = true;
                }

                else {

                    dayBools[1] = false;
                }
            }
        });



        toggle_Tues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    dayBools[2] = true;
                }
                else {
                    dayBools[2] = false;
                }
            }
        });



        toggle_Weds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    dayBools[3] = true;
                }
                else {
                    dayBools[3] = false;
                }
            }
        });

//
        toggle_Thurs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    dayBools[4] = true;
                }
                else {
                    dayBools[4] = false;
                }
            }
        });


        toggle_Fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    dayBools[5] = true;
                }
                else {
                    dayBools[5] = false;

                }
            }
        });


        toggle_Sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    dayBools[6] = true;
                }
                else {
                    dayBools[6] = false;
                }
            }
        });
        //Toggle button


        toggle_Sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    dayBools[0] = true;
                }
                else {
                    dayBools[0] = false;
                }

            }
        });

        final EditText urlText = (EditText) view.findViewById(R.id.url_text);

        urlText.setText(url);
/*
        Button url_button = (Button) view.findViewById(R.id.url_button);

        url_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
*/

        Button create_btn = (Button) view.findViewById(R.id.create_btn);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = urlText.getText().toString();
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
    public void populate(Alarm alarmIn) {

        this.id = alarmIn.id;
        if(alarmIn.hour  > 12) {
            this.hour = alarmIn.hour - 12;
        }
        else {
            this.hour = alarmIn.hour;
        }

        this.minute = alarmIn.minute;

        this.PM = alarmIn.PM;

        this.url = alarmIn.url;

/*
      spinner_min.setSelection(getIndex(spinner_amPm,amPm));
      ToggleButton sun = getView().findViewById(R.id.toggle_sun);
      ToggleButton mon = getView().findViewById(R.id.toggle_mon);
      ToggleButton tue = getView().findViewById(R.id.toggle_tues);
      ToggleButton wed = getView().findViewById(R.id.toggle_wed);
      ToggleButton thur = getView().findViewById(R.id.toggle_thurs);
      ToggleButton fri = getView().findViewById(R.id.toggle_fri);
      ToggleButton sat = getView().findViewById(R.id.toggle_sat);
*/


        if(alarmIn.daysOfWeek[0]) {
            dayBools[0] = true;
        }
        else {
            dayBools[0] = false;
        }

        if(alarmIn.daysOfWeek[1]) {
            dayBools[1] = true;
        }
        else {
            dayBools[1] = false;
        }
        if(alarmIn.daysOfWeek[2]) {
            dayBools[2] = true;
        }
        else {
            dayBools[2] = false;
        }

        if(alarmIn.daysOfWeek[3]) {
            dayBools[3] = true;
        }
        else {
            dayBools[3] = false;
        }

        if(alarmIn.daysOfWeek[4]) {
            dayBools[4] = true;
        }
        else {
            dayBools[4] = false;
        }

        if(alarmIn.daysOfWeek[5]) {
            dayBools[5] = true;
        }
        else {
            dayBools[5] = false;
        }

        if(alarmIn.daysOfWeek[6]) {
            dayBools[6] = true;
        }
        else {
            dayBools[6] = false;
        }

//

    }


    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                return i;
            }
        }
        return 0;
    }


    public void finish(View view) {
        if (edit) {
            this.alarm.hour = hour;
            this.alarm.minute = minute;
            this.alarm.PM = PM;
            this.alarm.url = url;
            this.alarm.daysOfWeek = dayBools;
            this.alarm.id = id;
            this.logic.editAlarm(alarm);
            this.mainRef.updateEditAdapter();
            this.ui.killAlarmEdit(this);
        }
        else {
            this.alarm.hour = hour;
            this.alarm.minute = minute;
            this.alarm.PM = PM;
            this.alarm.url = url;
            this.alarm.daysOfWeek = dayBools;
            this.alarm.id = logic.getKey();
            this.logic.addAlarm(alarm);
            this.mainRef.updateAdapter();
            this.ui.killAlarmEdit(this);
        }

    }

    public void finish_back(View view) {
        this.ui.killAlarmEdit(this);
    }

}