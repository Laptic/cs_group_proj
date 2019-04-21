package RedditAlarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.List;

public class AlarmAdapter extends BaseAdapter {

    // declares the layout inflater which defines the row layout of the list view
    public LayoutInflater mInflater;

    // declares the list of the alarms
    private List<Alarm> alarmList;

    // declares the logic handler
    public LogicHandler logicHandler;

    // constructs the alarm adapter
    public AlarmAdapter(Context c, List<Alarm> al, LogicHandler lh){
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        alarmList = al;
        logicHandler = lh;
    }

    // gets the number of alarms
    @Override
    public int getCount() {
        return alarmList.size();
    }

    // gets the alarm at the specified position
    @Override
    public Alarm getItem(int position) {
        Alarm[] alarmArray = alarmList.toArray(new Alarm[getCount()]);
        return alarmArray[position];
    }

    // gets the id of the alarm at the specified position
    @Override
    public long getItemId(int position) {
        return position;
    }

    // generates the items in the list view
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.alarm_listview_detail, null);

        // declares and initializes the parts of the alarm within the list
        TextView timeTextView = v.findViewById(R.id.timeTextView);
        TextView daysTextView = v.findViewById(R.id.daysTextView);
        Switch alarmSwitch = v.findViewById(R.id.alarmSwitch);

        // declares and initializes the current alarm
        final Alarm alarmViewed = getItem(position);

        // declares the text of the time
        String timeText = "";

        // sets the time of the alarm
        if (alarmViewed.hour > 12) {
            timeText += (alarmViewed.hour - 12);
        }
        else {
            timeText += alarmViewed.hour;
        }

        if (alarmViewed.minute < 10) {
            timeText += ":" + String.format("%02d",  alarmViewed.minute);
        }
        else {
            timeText += ":" + alarmViewed.minute;
        }

        if (alarmViewed.PM) {
            timeText += " PM";
        }
        else {
            timeText += " AM";
        }

        timeTextView.setText(timeText);

        // sets the days of the week
        String daysText = "";

        if (alarmViewed.daysOfWeek[0]) {
            daysText += "SU ";
        }
        if (alarmViewed.daysOfWeek[1]) {
            daysText += "M ";
        }
        if (alarmViewed.daysOfWeek[2]) {
            daysText += "T ";
        }
        if (alarmViewed.daysOfWeek[3]) {
            daysText += "W ";
        }
        if (alarmViewed.daysOfWeek[4]) {
            daysText += "TH ";
        }
        if (alarmViewed.daysOfWeek[5]) {
            daysText += "F ";
        }
        if (alarmViewed.daysOfWeek[6]) {
            daysText += "SA ";
        }

        daysTextView.setText(daysText);

        // sets the alarm switch based on the status variable
        if (alarmViewed.status) {
            alarmSwitch.setChecked(true);
        }
        else {
            alarmSwitch.setChecked(false);
        }

        // sets the alarm on or off
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if (on) {
                    alarmViewed.status = true;
                    logicHandler.systemAddAlarm(alarmViewed);
                }
                else {
                    alarmViewed.status = false;
                    logicHandler.deleteSystemAlarm(alarmViewed);
                }

                logicHandler.editAlarm(alarmViewed);
            }
        });

        return v;
    }
}