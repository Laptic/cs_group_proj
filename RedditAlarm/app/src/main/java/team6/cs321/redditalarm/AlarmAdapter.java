package team6.cs321.redditalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AlarmAdapter extends BaseAdapter {

    // declares the layout inflater which defines the row layout of the list view
    LayoutInflater mInflater;

    // constructs the alarm adapter
    public AlarmAdapter(Context c){
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // gets the number of alarms
    @Override
    public int getCount() {
        return 0;
    }

    // gets the alarm at the specified position
    @Override
    public Object getItem(int position) {
        return null;
    }

    // gets the id of the alarm at the specified position
    @Override
    public long getItemId(int position) {
        return position;
    }

    // generates the items in the list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.alarm_listview_detail, null);

        TextView timeTextView = v.findViewById(R.id.timeTextView);
        TextView daysTextView = v.findViewById(R.id.daysTextView);

        return v;
    }
}
