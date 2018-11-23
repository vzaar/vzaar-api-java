package com.vzaar.subtitles

import spock.lang.Specification

class SubRipSubtitlesSpec extends Specification {

    def "I can create subtitles"() {
        when:
        String output = new SubRipSubtitles()
            .addCue(1*60000 + 2000 + 123, 1*60000 + 12000 + 321, "Second subtitle")
            .addCue(1*3600000 + 2*60000 + 3000 + 123, 2*3600000 + 1*60000 + 13000 + 321, "Third subtitle")
            .addCue(0 + 1000 + 123, 0 + 11000 + 321, "First subtitle")
            .toString()

        then:
        output == '''1
00:00:01,123 --> 00:00:11,321
First subtitle

2
00:01:02,123 --> 00:01:12,321
Second subtitle

3
01:02:03,123 --> 02:01:13,321
Third subtitle

'''
    }

    def "I can create subtitles with string times"() {
        when:
        String output = new SubRipSubtitles()
                .addCue("00:01:02,123", "00:01:12,321", "Second subtitle")
                .addCue("01:02:03,123", "02:01:13,321", "Third subtitle")
                .addCue("00:00:01,123", "00:00:11", "First subtitle")
                .toString()

        then:
        output == '''1
00:00:01,123 --> 00:00:11,000
First subtitle

2
00:01:02,123 --> 00:01:12,321
Second subtitle

3
01:02:03,123 --> 02:01:13,321
Third subtitle

'''
    }

    def "I can't have end times after start times"() {
        when:
        new SubRipSubtitles()
                .addCue(1000, 999, "Illegal subtitle")

        then:
        thrown(IllegalArgumentException)
    }

    def "I get an exception if time format is invalid"() {
        when:
        new SubRipSubtitles()
                .addCue("02:01:73,321", "03:01:13,321", "Illegal subtitle")

        then:
        thrown(IllegalArgumentException)
    }

    def "I can't overlap times"() {
        when:
        new SubRipSubtitles()
                .addCue(1000, 2000, "First subtitle")
                .addCue(1000, 1001, "Second subtitle")

        then:
        thrown(IllegalArgumentException)

        when:
        new SubRipSubtitles()
                .addCue(1000, 2000, "First subtitle")
                .addCue(999, 1001, "Second subtitle")

        then:
        thrown(IllegalArgumentException)

        when:
        new SubRipSubtitles()
                .addCue(1000, 2000, "First subtitle")
                .addCue(1999, 2001, "Second subtitle")

        then:
        thrown(IllegalArgumentException)
    }

}
