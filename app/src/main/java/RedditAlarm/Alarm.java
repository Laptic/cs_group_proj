package RedditAlarm;

class Alarm {
    int id;
    String name = "Test";
    String url;
    boolean[] daysOfWeek;
    int hour = 12;
    int minute = 0;
    int status = 0;

    Alarm() {
        // sets values for days of week false as default
        daysOfWeek = new boolean[7];
        for (int i = 0; i< 7; i++) {
            daysOfWeek[i] = false;
        }
    }

    // creates string of 7 1s and 0s representing sat-sun for database storage
    // 0 = false, 1 = true
    String strRepDays() {
        StringBuilder weekRep = new StringBuilder(8);
        for (int i = 0; i < 7; i++) {
            if (daysOfWeek[i]) {
                weekRep.append("1");
            }
            else {
                weekRep.append("0");
            }
        }
        return weekRep.toString();
    }

    // returns time value as a string that can be parsed
    String getTimeStamp() {
        return hour + "," + minute;
    }

    // returns hour/minute alarm val in miliseconds
    long getMiliTime() {
        return (minute * 60 * 100 ) + (hour * 60 * 60 * 100);
    }

    // parses database entry for hour min value
    void setTime(String timestamp) {
        char[] timeString = timestamp.toCharArray();
        int i = 0;
        StringBuilder hourStr = new StringBuilder(2);
        StringBuilder minuteStr = new StringBuilder(2);
        while (timeString[i] != ',') {
            hourStr.append(timeString[i]);
            i++;
        }
        i++;
        for (int z = i; z < timeString.length; z++) {
            minuteStr.append(timeString[z]);
        }
        hour = Integer.parseInt(hourStr.toString());
        minute = Integer.parseInt(minuteStr.toString());
    }

    // populates bool array based on database format
    void setDays(String daysIn) {
        char[] days = daysIn.toCharArray();
        for(int i = 0; i < days.length; i++) {
            daysOfWeek[i] = (days[i] == 1);
        }
    }

}
