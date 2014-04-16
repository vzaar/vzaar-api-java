package com.vzaar.test.junit;

import static org.junit.Assert.*;

import com.vzaar.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class testEditVideo {

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
            Video video = videoList.get(1);

            VideoDetails videoDetails = api.getVideoDetails(video.id);
            if (null == videoDetails)
                fail("getVideoDetails() api failed");
            else
                System.out.println(userName + " Video Details - " + videoDetails.toString());

            VideoEditQuery videoEditQuery = new VideoEditQuery();
            videoEditQuery.description = "test";
            videoEditQuery.id = video.id;
            videoEditQuery.title = "Test Title";

            boolean editVideoResponse = api.editVideo(videoEditQuery);
            System.out.println("Video Edit Response - " + editVideoResponse);

            VideoDetails videoDetails2 = api.getVideoDetails(video.id);
            if (null == videoDetails2)
                fail("getVideoDetails() api failed");
            else
                System.out.println(userName + " Video Details (After Edit)- " + videoDetails2.toString());

            Assert.assertEquals(videoDetails2.title.equals("Test Title"), true);
            Assert.assertEquals(videoDetails2.description.equals("test"), true);


            videoEditQuery = new VideoEditQuery();
            videoEditQuery.description = "test 2";
            videoEditQuery.id = video.id;
            videoEditQuery.title = "Test Title 2";

            editVideoResponse = api.editVideo(videoEditQuery);
            System.out.println("Video Edit Response - " + editVideoResponse);

            VideoDetails videoDetails3 = api.getVideoDetails(video.id);
            if (null == videoDetails3)
                fail("getVideoDetails() api failed");
            else
                System.out.println(userName + " Video Details (After 2nd Edit)- " + videoDetails3.toString());

            Assert.assertEquals(videoDetails3.title.equals("Test Title 2"), true);
            Assert.assertEquals(videoDetails3.description.equals("test 2"), true);


        } catch (VzaarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
