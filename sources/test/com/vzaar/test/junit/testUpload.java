package com.vzaar.test.junit;

import static org.junit.Assert.*;

import java.math.BigInteger;

import com.vzaar.*;
import org.junit.Before;
import org.junit.Test;

public class testUpload {

    private Vzaar api;

    @Before
    public void setUp() throws Exception {
        api = new Vzaar(TestConf.API_USERNAME, TestConf.API_TOKEN);
    }

    @Test
    public void test() {
        //fail("Not yet implemented");
        String user = new String();
        try {
            user = api.whoAmI();
        } catch (VzaarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (user.length() > 0)
            System.out.println(user);
        else
            fail("whoAmI api failed");

        try {
            String guid = api.uploadVideo("c:\\songs\\12 - RESHAM KI DORI.mp4", new Progress());
            VideoProcessQuery videoProcessQuery = new VideoProcessQuery();
            videoProcessQuery.guid = guid;
            videoProcessQuery.description = "Test Description";
            videoProcessQuery.labels = new String[] { "label1", "label2" };
            videoProcessQuery.title = "Title";
            Long processVideoResponse = api.processVideo(videoProcessQuery);
            System.out.println("Video Process Response - " +processVideoResponse);
            System.out.println("Waiting for video to process");
            Thread.sleep(1000 * 90 );
            VideoDetails videoDetails = api.getVideoDetails(processVideoResponse);
            System.out.println(videoDetails.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class Progress implements ProgressListener {
        public void update(long pBytesRead) {
            System.out.print("\r" + pBytesRead / 1024 + "              ");
        }

    }


}
