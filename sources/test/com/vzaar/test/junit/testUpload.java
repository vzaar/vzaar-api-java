package com.vzaar.test.junit;

import static org.junit.Assert.*;
import com.vzaar.*;
import org.junit.Before;
import org.junit.Test;

public class testUpload {

    private Vzaar api;
    private String path;
    @Before
    public void setUp() throws Exception {
        api = new Vzaar(TestConf.API_USERNAME, TestConf.API_TOKEN);
        path = "/path/to/file/video-this-(is)_a VAL1D.filename!.mkv";
    }

    @Test
    public void testWhoAmI(){
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
    }

    @Test
    public void testUpload(){

        try {
            String guid = api.uploadVideo(path);
            System.out.println(guid);

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

            SubtitleQuery subtitleQuery = new SubtitleQuery();
            subtitleQuery.videoId = 1600326;
            subtitleQuery.language = "English";
            subtitleQuery.body = "This is test subtitle";

            api.uploadSubtitle(subtitleQuery);
        } catch (Exception e) {
            e.printStackTrace();

            fail();
        }
    }

    @Test
    public void testUploadWithProgress(){

        try {
            String guid = api.uploadVideo(path,new Progress());
            System.out.println(guid);

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

            SubtitleQuery subtitleQuery = new SubtitleQuery();
            subtitleQuery.videoId = 1600326;
            subtitleQuery.language = "English";
            subtitleQuery.body = "This is test subtitle";

            api.uploadSubtitle(subtitleQuery);
        } catch (Exception e) {
            e.printStackTrace();

            fail();
        }
    }

    class Progress implements ProgressListener {
        public void update(long pBytesRead) {
            System.out.print("\r" + pBytesRead / 1024 + "              ");
        }

    }
}
