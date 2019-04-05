package RedditAlarm;

import android.support.v4.app.Fragment;

//Alarm class that will represent an alarm object
public class Alarm {

     //url holds a string representing a reddit url
     String url;
     //daysOfWeek holds the days of the week and whether it is checked or not
     boolean[] daysOfWeek;
     //self-explanatory
     int hour;
     int minute;
     int second;
     int time;
     //checks if the alarm is recurring
     boolean recurring;

     public Alarm() {

         daysOfWeek = new boolean[7];
     }





}
