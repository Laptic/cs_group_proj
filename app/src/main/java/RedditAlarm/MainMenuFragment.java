package RedditAlarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainMenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {
    // declares and initializes the list of the alarms
    //List<Alarm> alarmList = new ArrayList<>();
    Alarm[] aL = {new Alarm()};
    ArrayList<Alarm> alarmList = new ArrayList<>(Arrays.asList(aL));

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

    // declares how the fragment interacts with other fragments
    private OnFragmentInteractionListener mListener;

    public MainMenuFragment() {

        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param  Parameter 1.
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        //args.putParcelableArrayList("Alarm List", al);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_menu_fragment, container, false);

        // declares and initializes the objects within the fragment
        alarmListView = rootView.findViewById(R.id.alarmListView);
        addImgView = rootView.findViewById(R.id.addImgView);
        editBtn = rootView.findViewById(R.id.editBtn);

        // declares and initializes the text of the edit button
        final String editText = "Edit";
        final String doneText = "Done";

        // inflate the layout for this fragment
        final AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarmList);
        alarmListView.setAdapter(alarmAdapter);

        // when the image view is clicked, the screen changes to the alarm add fragment
        addImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlarmFragment addAlarmFrag = ui.addAlarmFrag();

                //editBtn.setText(editText);

                // adds the alarm into the alarm list
                //alarmList.add(addAlarmFrag.alarm);

                // uses the alarm adapter class to modify the alarm list view
                AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarmList);
                alarmListView.setAdapter(alarmAdapter);
            }
        });

        // when the edit button is clicked, the user will able to edit the alarms
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCounter++;

                // sets the edit button to edit the alarms
                if (buttonCounter % 2 == 0) {
                    editBtn.setText(editText);

                    // uses the alarm adapter class to modify the alarm list view
                    AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarmList);
                    alarmListView.setAdapter(alarmAdapter);
                }
                // sets the edit button to instead bring the user back to the main menu
                else if (buttonCounter % 2 == 1) {
                    editBtn.setText(doneText);

                    // uses the alarm adapter class to modify the alarm list view
                    EditAlarmAdapter editAlarmAdapter = new EditAlarmAdapter(getActivity(), alarmList);
                    alarmListView.setAdapter(editAlarmAdapter);

                }
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        //}
    //}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        //if (context instanceof OnFragmentInteractionListener) {
            //mListener = (OnFragmentInteractionListener) context;
        //} else {
            //throw new RuntimeException(context.toString()
                    //+ " must implement OnFragmentInteractionListener");
        //}
        try {
            mListener = (OnFragmentInteractionListener) activity;
        }catch (ClassCastException e) {
            //throw new ClassCastException(activity.toString() + " must override onMessageRead...");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMessageRead(String message);
    }


}
