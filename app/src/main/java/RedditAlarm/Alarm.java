package RedditAlarm;

class Alarm {
    int id;
    String name;
    String url;
    boolean[] daysOfWeek;
    int hour = 12;
    int minute = 0;
    int status = 0;

    Alarm() {
        daysOfWeek = new boolean[7];
        for (int i = 0; i< 7; i++) {
            daysOfWeek[i] = false;
        }
    }

    String strRepDays() {
        StringBuilder csvForm = new StringBuilder(8);
        for (int i = 0; i < 7; i++) {
            if (daysOfWeek[i]) {
                csvForm.append("1");
            }
            else {
                csvForm.append("0");
            }
        }
        return csvForm.toString();
    }

    String getTimeStamp() {
        return hour + "," + minute;
    }

    long getMiliTime() {
        return (minute * 60 * 100 ) + (hour * 60 * 60 * 100);
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
            daysOfWeek[i] = (days[i] == 1);
        }
    }

}
