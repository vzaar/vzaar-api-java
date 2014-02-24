package com.vzaar.test.junit;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.vzaar.ProgressListener;
import com.vzaar.UploadSignature;
import com.vzaar.VideoDetails;
import com.vzaar.Vzaar;
import com.vzaar.VzaarException;

public class testUpload {

    private Vzaar api;

    @Before
    public void setUp() throws Exception {
        api = new Vzaar(TestConf.API_TOKEN, TestConf.API_SECRET);
    }

    @Test
    public void testWhoAmIAPI() {
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
            String guid = api.uploadVideo("E:/downloads/dinosaur.mp4", new Progress());
            String videoId = api.processVideo(guid, "New Title", "New description", "new");
            VideoDetails vdetails = api.getVideoDetails(new BigInteger(videoId), false);
            System.out.println(vdetails.toString());
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
