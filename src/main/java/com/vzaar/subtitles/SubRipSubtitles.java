package com.vzaar.subtitles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubRipSubtitles {
    private List<SubRipCue> cues = new ArrayList<>();

    public SubRipSubtitles addCue(String startTime, String endTime, String text) {
        validateAndAdd(new SubRipCue(startTime, endTime, text));
        return this;
    }

    public SubRipSubtitles addCue(int startTimeMillis, int endTimeMillis, String text) {
        validateAndAdd(new SubRipCue(startTimeMillis, endTimeMillis, text));
        return this;
    }

    private void validateAndAdd(SubRipCue cue) {
        int index = Collections.binarySearch(cues, cue);

        if (index > -1) {
            throw new IllegalArgumentException("Cue with start time " + cue.getStartTime() + " already exists");
        }

        int insertIndex = index * -1 - 1;
        if (insertIndex > 0 && cues.get(0).overlaps(cue)) {
            throw new IllegalArgumentException(String.format(
                    "Cue with start time %s and end time %s overlaps cue with start time %s and end time %s ",
                    cue.getStartTime(),
                    cue.getEndTime(),
                    cues.get(0).getStartTime(),
                    cues.get(0).getEndTime()));
        }

        if (insertIndex < cues.size() && cues.get(insertIndex).overlaps(cue)) {
            throw new IllegalArgumentException(String.format(
                    "Cue with start time %s and end time %s overlaps cue with start time %s and end time %s ",
                    cue.getStartTime(),
                    cue.getEndTime(),
                    cues.get(insertIndex).getStartTime(),
                    cues.get(insertIndex).getEndTime()));
        }

        cues.add(insertIndex, cue);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cues.size(); ++i) {
            builder.append(cues.get(i).toString(i + 1)).append("\n");
        }
        return builder.toString();
    }
}
