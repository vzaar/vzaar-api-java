package com.vzaar.test.junit;

import static org.junit.Assert.*;

import java.math.BigInteger;

import com.vzaar.*;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.fail;

public class testUploadSubtitle {
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
                SubtitleQuery subtitleQuery = new SubtitleQuery();
                subtitleQuery.videoId = 1600326;
                subtitleQuery.language = "English";
                subtitleQuery.body = "This is test subtitle";

                api.uploadSubtitle(subtitleQuery);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

}
