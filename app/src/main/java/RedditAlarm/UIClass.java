package RedditAlarm;

import android.app.Notification;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.support.v4.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class UIClass extends AppCompatActivity implements AlarmFragment.OnMessageReadListener {

    //ArrayList<Alarm> listOfArrays = new ArrayList<>();
    LogicHandler logicReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        logicReference = new LogicHandler(this);
        PriorityQueue<Alarm> que = new PriorityQueue<>();
        //for (int i = 0; i < 7; i++) {
            que.add(new Alarm());
        //}
        AlarmAdapter ad = new AlarmAdapter(this, que);


        // temporary call for Notifications testing
        //Notification.Builder tempNotification = Notifications.newNotification(this);

        AlarmFragment alarmFrag = new AlarmFragment();
        AlarmListFragment alarmListFrag = new AlarmListFragment();
            //the next two expressions are used to call and populate a frame layout with
            //a fragment (AlarmFragment)
            //frament1 is the name of the FrameLayout under base_layout.xml
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment1,alarmListFrag);
            //commits the fragment to the layout?
            fragmentTransaction.commit();

    }

    //used to pass information between fragment and activity
    //not used yet
    @Override
    public void onMessageRead(String message) {

    }
}
