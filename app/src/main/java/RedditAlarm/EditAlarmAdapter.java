package RedditAlarm;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;

public class EditAlarmAdapter extends AlarmAdapter {

    private List<Alarm> alarmList;
    public LogicHandler logicReference;

    public EditAlarmAdapter(Context c, List<Alarm> al) {
        super(c, al);
        alarmList = al;
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
        //final ListView alarmListView = v.findViewById(R.id.alarmListView);

        removeImageView.setImageResource(R.drawable.minus);

        // removes the alarm if the remove image view is clicked
        removeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm temp = alarmList.get(alarmNumber);
                logicReference.deleteAlarm(temp);
                alarmList.remove(alarmNumber);
                notifyDataSetChanged();


            }
        });

        // declares and initializes the current alarm
        Alarm alarmViewed = getItem(position);

        // declares the text of the time
        String timeText;

        // sets the time of the alarm
        if (alarmViewed.minute < 10) {
            timeText = (alarmViewed.hour + ":");
            timeText += String.format("%02d",  alarmViewed.minute);
        }
        else {
            timeText = alarmViewed.hour + ":" + alarmViewed.minute;
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
            daysText += "M ";
        }
        if (alarmViewed.daysOfWeek[1]) {
            daysText += "T ";
        }
        if (alarmViewed.daysOfWeek[2]) {
            daysText += "W ";
        }
        if (alarmViewed.daysOfWeek[3]) {
            daysText += "TH ";
        }
        if (alarmViewed.daysOfWeek[4]) {
            daysText += "F ";
        }
        if (alarmViewed.daysOfWeek[5]) {
            daysText += "SA ";
        }
        if (alarmViewed.daysOfWeek[6]) {
            daysText += "SU ";
        }

        daysTextView.setText(daysText);

        // sets the alarm switch based on the status variable
        if (getItem(position).status == 1) {
            alarmSwitch.setChecked(true);
        }
        else {
            alarmSwitch.setChecked(false);
        }

        return v;
    }

}

