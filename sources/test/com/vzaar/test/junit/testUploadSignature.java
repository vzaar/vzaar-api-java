package com.vzaar.test.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vzaar.UploadSignature;
import com.vzaar.Vzaar;
import com.vzaar.VzaarException;

public class testUploadSignature {
    private Vzaar api;

    @Before
    public void setUp() throws Exception {
        api = new Vzaar(TestConf.API_USERNAME, TestConf.API_TOKEN);
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
        UploadSignature signature = api.getUploadSignature(null);

        if (null != signature)
            System.out.println("Upload Signature - " + signature.toString());
        else
            fail("getUploadSignature() api failed");
    }

}
