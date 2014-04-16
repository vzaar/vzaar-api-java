package com.vzaar.test.junit;

import static org.junit.Assert.*;

import com.vzaar.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class testProcessVideo {

    private Vzaar api;

    @Before
    public void setUp() throws Exception {
        api = new Vzaar(TestConf.API_USERNAME, TestConf.API_TOKEN);
    }

    @Test
    public void test() {
        String userName = new String();
        try {
            userName = api.whoAmI();
            if (userName.length() > 0)
                System.out.println("Who AM I - " + userName);
            else
                fail("whoAmI() api failed");
            UserDetails userDetails = api.getUserDetails(userName);

            if (null == userDetails)
                fail("getUserDetails() api failed");
            else
                System.out.println(userName + " User details - " + userDetails.toString());

            AccountDetails accountsDetails = api.getAccountDetails(userDetails.authorAccount);

            if (null == accountsDetails)
                fail("getAccountDetails() api failed");
            else
                System.out.println(userName + " Account details - " + accountsDetails.toString());

            VideoListQuery videoListQuery = new VideoListQuery();
            List<Video> videoList = api.getVideoList(videoListQuery);

            if (null == videoList)
                fail("getVideoList() api failed");
            else
                System.out.println(userName + " Video List - " + videoList.toString());
            Video video = videoList.get(0);

            VideoDetails videoDetails = api.getVideoDetails(video.id);
            if (null == videoDetails)
                fail("getVideoDetails() api failed");
            else
                System.out.println(userName + " Video Details - " + videoDetails.toString());

            //String editVideoResponse = api.editVideo(video.id(), "OpenCoffee October 14, The story of Adobe Romania", "New description", false, "");
            VideoProcessQuery videoProcessQuery = new VideoProcessQuery();
            videoProcessQuery.guid = String.valueOf(video.id);
            videoProcessQuery.description = "Test Description";
            videoProcessQuery.labels = new String[] { "label1", "label2" };
            videoProcessQuery.title = "Title";
            Long processVideoResponse = api.processVideo(videoProcessQuery);
            System.out.println("Video Process Response - " +processVideoResponse);

        } catch (VzaarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
