package RedditAlarm;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the main menu of the alarm
 * Allows the user to add, edit and removes alarms
 */
public class MainMenuFragment extends Fragment{
    // declares and initializes the list of the alarms
    ArrayList<Alarm> alarmList = new ArrayList<>();

    // reference to UI
    UIClass ui;

    // declares the list view that will show the list of alarms
    ListView alarmListView;

    // declares the image view to add a new alarm
    ImageView addImgView;

    // declares the button to edit the alarms
    Button editBtn;

    // counts the amount of times the edit button has been pressed
    int buttonCounter = 0;

    // declares the logic handler
    LogicHandler logicReference;

    // declares how the fragment interacts with other fragments
    private OnFragmentInteractionListener mListener;

    // declares and initializes the main menu fragment
    final MainMenuFragment thisMenu = this;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new main menu fragment
     *
     * @return A new instance of fragment MainMenuFragment.
     */
    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_menu_fragment, container, false);

        // sorts the list
        alarmList = sortList();

        // declares and initializes the objects within the fragment
        alarmListView = rootView.findViewById(R.id.alarmListView);
        addImgView = rootView.findViewById(R.id.addImgView);
        editBtn = rootView.findViewById(R.id.editBtn);

        // declares and initializes the text of the edit button
        final String editText = "Edit";
        final String doneText = "Done";

        // inflate the layout for this fragment
        final AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarmList, logicReference);
        alarmListView.setAdapter(alarmAdapter);


        // when the image view is clicked, the screen changes to the alarm add fragment
        addImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmFragment addAlarmFrag = ui.addAlarmFrag(thisMenu);
                alarmList = sortList();
            }
        });

        // when the edit button is clicked, the user will able to edit the alarms
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCounter++;
                alarmList = sortList();

                // sets the edit button to edit the alarms
                if (buttonCounter % 2 == 0) {
                    editBtn.setText(editText);

                    // uses the alarm adapter class to modify the alarm list view
                    AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarmList, logicReference);
                    alarmListView.setAdapter(alarmAdapter);
                }
                // sets the edit button to instead bring the user back to the main menu
                else if (buttonCounter % 2 == 1) {
                    editBtn.setText(doneText);

                    // uses the alarm adapter class to modify the alarm list view
                    EditAlarmAdapter editAlarmAdapter = new EditAlarmAdapter(getActivity(), alarmList, logicReference);
                    editAlarmAdapter.logicHandler = logicReference;
                    alarmListView.setAdapter(editAlarmAdapter);

                    // allows the user to click on the alarm in order to edit it
                    alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            AlarmFragment addAlarmFrag = ui.addAlarmFrag(thisMenu);
                            addAlarmFrag.populate(alarmList.get(position));

                            // deletes the alarm
                            //logicReference.editAlarm(alarmList.get(position));
                            //alarmList.remove(position);

                            alarmList = sortList();
                        }
                    });
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateAdapter() {
        alarmList = (ArrayList<Alarm>) logicReference.alarmList;
        AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarmList, logicReference);
        alarmListView.setAdapter(alarmAdapter);
    }

    /**
     * Allows the fragment to interact with activities
     */
    public interface OnFragmentInteractionListener {
        void onMessageRead(String message);
    }

    // sorts the list by time of day
    public ArrayList<Alarm> sortList() {
        // declares and initializes the sorted list
        ArrayList<Alarm> newList = alarmList;

        // declares and initializes the number of the alarms
        int n = newList.size();

        // declares the temporary alarm used for the sort
        Alarm temp;

        // uses bubble sort
        if (n != 0) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    //if (newList.get(j).getMiliTime() > newList.get(j + 1).getMiliTime()){
                    if (getTotalMin(newList.get(j)) > getTotalMin(newList.get(j + 1))){
                        temp = newList.get(j);
                        newList.set(j, newList.get(j + 1));
                        newList.set(j + 1, temp);
                    }
                }
            }
        }

        // returns the sorted list
        return newList;
    }

    // converts the time to minutes
    public int getTotalMin(Alarm a) {
        int time = (60 * a.hour) + (a.minute);

        if (a.hour == 12 && !(a.PM)) {
            time = a.minute;
        }
        else if (a.hour == 24 && a.PM) {
            time = (60 * 11) * a.minute;
        }

        return time;
    }
}