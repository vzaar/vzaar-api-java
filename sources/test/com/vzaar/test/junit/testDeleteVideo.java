package com.vzaar.test.junit;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.vzaar.AccountsType;
import com.vzaar.User;
import com.vzaar.Video;
import com.vzaar.VideoDetails;
import com.vzaar.VideoList;
import com.vzaar.Vzaar;
import com.vzaar.VzaarException;

public class testDeleteVideo {

    private Vzaar api;

    @Before
    public void setUp() throws Exception {
        api = new Vzaar(TestConf.API_TOKEN, TestConf.API_SECRET);
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
            User userDetails = api.getUserDetails(userName);

            if (null == userDetails)
                fail("getUserDetails() api failed");
            else
                System.out.println(userName + " User details - " + userDetails.toString());

            AccountsType accountsDetails = api.getAccountDetails(userDetails.authorAccount());

            if (null == accountsDetails)
                fail("getAccountDetails() api failed");
            else
                System.out.println(userName + " Account details - " + accountsDetails.toString());

            VideoList videoList = api.getVideoList(userName, false, 20, "", "");

            if (null == videoList)
                fail("getVideoList() api failed");
            else
                System.out.println(userName + " Video List - " + videoList.toString());
            Video video = videoList.videoList().get(1);

            VideoDetails videoDetails = api.getVideoDetails(video.id(), false);
            if (null == videoDetails)
                fail("getVideoDetails() api failed");
            else
                System.out.println(userName + " Video Details - " + videoDetails.toString());

            String deleteVideoResponse = api.deleteVideo(new BigInteger("936441"));
            System.out.println(deleteVideoResponse);

        } catch (VzaarException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
