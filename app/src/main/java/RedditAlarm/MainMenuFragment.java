package RedditAlarm;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
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
    // declares the list of the alarms
    List<Alarm> alarmList = new ArrayList<>();

    // declares and initializes the list view that will show the list of alarms
    //ListView alarmListView = getActivity().findViewById(R.id.alarmListView);
    ListView alarmListView;

    // declares and initializes the image view to add a new alarm
    //ImageView addImgView = getActivity().findViewById(R.id.addImgView);
    ImageView addImgView;

    // declares and initializes the button to edit the alarms
    //Button editBtn = getActivity().findViewById(R.id.editBtn);
    Button editBtn;

    private OnFragmentInteractionListener mListener;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param al Parameter 1.
     * @return A new instance of fragment MainMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenuFragment newInstance(List<Alarm> al) {
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
            alarmList = (List<Alarm>) getArguments().get("0");
            //mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_menu_fragment, container, false);

        alarmListView = rootView.findViewById(R.id.alarmListView);
        addImgView = rootView.findViewById(R.id.addImgView);
        editBtn = rootView.findViewById(R.id.editBtn);

        // Inflate the layout for this fragment
        AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarmList);
        alarmListView.setAdapter(alarmAdapter);

        // when the image view is clicked, the screen changes to the alarm add fragment
        addImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creates a new fragment where the user will be able to add an alarm
                AlarmFragment addAlarm = new AlarmFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(v.getId(), addAlarm, "addAlarmFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                alarmList.add(addAlarm.alarm);

                // uses the alarm adapter class to modify the alarm list view
                AlarmAdapter alarmAdapter = new AlarmAdapter(getActivity(), alarmList);
                alarmListView.setAdapter(alarmAdapter);
            }
        });

        // when the edit button is clicked, the user will able to edit the alarms
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doneText = "Done";
                editBtn.setText(doneText);

                EditAlarmAdapter editAlarmAdapter = new EditAlarmAdapter(getActivity(), alarmList);
                alarmListView.setAdapter(editAlarmAdapter);
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
