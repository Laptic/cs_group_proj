package RedditAlarm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.PriorityQueue;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmListFragment extends Fragment {


    PriorityQueue<Alarm> alarmQueue = new PriorityQueue<>();

    public AlarmListFragment() {
        // Required empty public constructor
    }



//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_menu, container, false);
        // declares and initializes the button to edit the alarms


        return view;
    }

}
