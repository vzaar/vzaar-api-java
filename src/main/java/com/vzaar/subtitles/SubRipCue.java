package com.vzaar.subtitles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubRipCue implements Comparable<SubRipCue> {

    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{2}):([0-5]\\d):([0-5]\\d)(?:,(\\d{3}))?");

    private final int startTime;
    private final int endTime;
    private final String text;

    public SubRipCue(int startTime, int endTime, String text) {
        if (endTime <= startTime) {
            throw new IllegalArgumentException(String.format("Start time %s must be before end time %s", startTime, endTime));
        }

        this.startTime = startTime;
        this.endTime = endTime;
        this.text = text;
    }

    public SubRipCue(String startTime, String endTime, String text) {
        this(parse(startTime), parse(endTime), text);
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(SubRipCue o) {
        return startTime - o.startTime;
    }

    boolean overlaps(SubRipCue cue) {
        return !(startTime > cue.endTime || cue.startTime > endTime);
    }

    String toString(int i) {
        return String.format("%d\n%s --> %s\n%s\n",
                i, format(startTime), format(endTime), text);
    }

    private static String format(int time) {
        return String.format("%02d:%02d:%02d,%03d", time / 3600000, (time % 3600000) / 60000, (time % 60000) / 1000, time % 1000);
    }

    private static int parse(String time) {
        Matcher matcher = TIME_PATTERN.matcher(time);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid time format " + time + ", should be HH:MM:SS,mmm");
        }

        return (Integer.parseInt(matcher.group(1)) * 3600000) +
                (Integer.parseInt(matcher.group(2)) * 60000) +
                (Integer.parseInt(matcher.group(3)) * 1000) +
                (Integer.parseInt(matcher.group(4) == null ? "0" : matcher.group(4)));
    }


}
