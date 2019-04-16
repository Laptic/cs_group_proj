package RedditAlarm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.List;

public class EditAlarmAdapter extends AlarmAdapter {

    private List<Alarm> alarmList;

    public EditAlarmAdapter(Context c, List<Alarm> al) {
        super(c, al);
        alarmList = al;
    }

    // generates the items in the list view
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.editalarm_listview_detail, null);

        // declares and initializes the parts of the alarm within the list
        TextView timeTextView = v.findViewById(R.id.timeTextView);
        TextView daysTextView = v.findViewById(R.id.daysTextView);
        Switch alarmSwitch = v.findViewById(R.id.alarmSwitch);
        ImageView removeImageView = v.findViewById(R.id.removeImageView);

        removeImageView.setImageResource(R.drawable.minus);

        // removes the alarm if the remove image view is clicked
        removeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmList.remove(position);
            }
        });

        // declares and initializes the current alarm
        Alarm alarmViewed = getItem(position);

        // sets the time of the alarm
        String timeText = alarmViewed.hour + ":" + alarmViewed.minute;
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
        if (getItem(position).status == 1) {
            alarmSwitch.setChecked(true);
        }
        else {
            alarmSwitch.setChecked(false);
        }

        return v;
    }
}

