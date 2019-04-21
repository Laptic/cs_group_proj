package RedditAlarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.List;

public class EditAlarmAdapter extends AlarmAdapter {

    // declares the list of alarms
    private List<Alarm> alarmList;

    // declares the logic handler
    public LogicHandler logicHandler;

    // constructs the edit alarm adapter
    public EditAlarmAdapter(Context c, List<Alarm> al, LogicHandler lh) {
        super(c, al, lh);
        alarmList = al;
        logicHandler = lh;
    }

    // generates the items in the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.editalarm_listview_detail, null);

        final int alarmNumber = position;

        // declares and initializes the parts of the alarm within the list
        TextView timeTextView = v.findViewById(R.id.timeTextView2);
        TextView daysTextView = v.findViewById(R.id.daysTextView2);
        Switch alarmSwitch = v.findViewById(R.id.alarmSwitch2);
        ImageView removeImageView = v.findViewById(R.id.removeImageView);

        // sets the image view to the minus image
        removeImageView.setImageResource(R.drawable.minus);

        // removes the alarm if the remove image view is clicked
        removeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm temp = alarmList.get(alarmNumber);
                logicHandler.deleteAlarm(temp);
                alarmList = logicHandler.alarmList;
                notifyDataSetChanged();
            }
        });

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
                }
                else {
                    alarmViewed.status = false;
                }

                logicHandler.editAlarm(alarmViewed);
            }
        });

        return v;
    }

}
