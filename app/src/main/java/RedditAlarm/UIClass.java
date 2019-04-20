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

import RedditAlarm.Models.RedditJSON;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UIClass extends AppCompatActivity implements AlarmFragment.passAlarm {

    List<Alarm> listOfAlarms = new ArrayList<>();
    LogicHandler logicReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);


        logicReference = new LogicHandler(this);
        listOfAlarms = logicReference.alarmList;


        PriorityQueue<Alarm> que = new PriorityQueue<>();
        //for (int i = 0; i < 7; i++) {
            que.add(new Alarm());
        //}
        //AlarmAdapter ad = new AlarmAdapter(this, que);


        // temporary call for Notifications testing
        //Notification.Builder tempNotification = Notifications.newNotification(this);
        MainMenuFragment mainMenuFrag = new MainMenuFragment();
        mainMenuFrag.ui = this;
        mainMenuFrag.logicReference = logicReference;
        mainMenuFrag.alarmList = (ArrayList<Alarm>) this.listOfAlarms;
            //the next two expressions are used to call and populate a frame layout with
            //a fragment (AlarmFragment)
            //fragment1 is the name of the FrameLayout under base_layout.xml
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment1,mainMenuFrag);

            //commits the fragment to the layout?
            fragmentTransaction.commit();

    }

    public AlarmFragment addAlarmFrag(MainMenuFragment menuIn) {
        AlarmFragment alarmFrag = new AlarmFragment();
        alarmFrag.ui = this;
        alarmFrag.logic = this.logicReference;
        alarmFrag.mainRef = menuIn;

        // changes the interface to the add alarm fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment1, alarmFrag)
                            .addToBackStack("add")
                            .commit();
        return alarmFrag;
    }

    public void killAlarmEdit(AlarmFragment fragIn) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .remove(fragIn);
        getSupportFragmentManager().popBackStack();
        fragmentTransaction.commit();

    }

    @Override
    public void addAlarmFromFragment(Alarm alarm) {
        //MainMenuFragment mainMenu = getSupportFragmentManager().findFragmentById(R.id.);
    }
}
