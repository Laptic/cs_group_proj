package RedditAlarm;

import android.support.v4.app.Fragment;

class Alarm {
    int id;
    String name;
    String url;
    boolean[] daysOfWeek;
    int hour = 12;
    int minute = 0;
    boolean recurring = false;

    Alarm() {
        daysOfWeek = new boolean[7];
        for (int i = 0; i< 7; i++) {
            daysOfWeek[i] = false;
        }
    }

    String getName() {
        return name;
    }

    // need to deal with case where id is not assigned
    int getID() {
        return id;
    }

    String strRepDays() {
        String csvForm = "";
        for (int i = 0; i < 7; i++) {
            if (daysOfWeek[i]) {
                csvForm +=  "1";
            }
            else {
                csvForm += "0";
            }
        }
        return csvForm;
    }

    String getTimeStamp() {
        String timeStamp = hour + "," + minute;
        return timeStamp;
    }

    void setID(int idIn) {
        this.id = idIn;
    }

    void setName(String nameIn) {
        this.name = nameIn;
    }

    void setURL(String urlIn) {
        this.url = urlIn;
    }

    void setTime(String timestamp) {
        char[] timeString = timestamp.toCharArray();
        int i = 0;
        String hourStr = "";
        String minuteStr = "";
        while (timeString[i] != ',') {
            hourStr += timeString[i];
            i++;
        }
        i++;
        for (int z = i; z < timeString.length; z++) {
            minuteStr += timeString[z];
        }
        hour = Integer.parseInt(hourStr);
        minute = Integer.parseInt(minuteStr);
    }

    void setDays(String daysIn) {
        char[] days = daysIn.toCharArray();
        for(int i = 0; i < days.length; i++) {
            if (days[i] == 0) {
                daysOfWeek[i] = false;
            }
            else {
                daysOfWeek[i] = true;
            }
        }
    }



}
