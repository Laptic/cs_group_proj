package RedditAlarm;

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

public class UIClass extends AppCompatActivity implements AlarmFragment.OnMessageReadListener {

    boolean[] daysOfWeek = new boolean[7];

    ArrayList<Alarm> listOfArrays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);

        Alarm alarm = new Alarm();

            AlarmFragment alarmFrag = new AlarmFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment1,alarmFrag);
            fragmentTransaction.commit();

    }


    @Override
    public void onMessageRead(String message) {

    }
}
