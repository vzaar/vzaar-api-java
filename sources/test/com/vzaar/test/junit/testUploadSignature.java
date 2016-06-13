package com.vzaar.test.junit;

import static org.junit.Assert.*;

import com.vzaar.UploadSignatureQuery;
import org.junit.Before;
import org.junit.Test;

import com.vzaar.UploadSignature;
import com.vzaar.Vzaar;
import com.vzaar.VzaarException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    }

    @Test
    public void testUploadSignature() {
        UploadSignature signature = null;
        UploadSignatureQuery query = new UploadSignatureQuery();

        try {
            query.path =  URLEncoder.encode("/path/to/file/video-this-(is)_a VAL1D.filename!.mkv", "UTF-8");
            query.filename = URLEncoder.encode("video-this-(is)_a VAL1D.filename!.mk", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        query.fileSize = 465756543;
        query.multipart = true;
        try{
            signature = api.getUploadSignature(query);
        }catch (VzaarException e){
        }

        if (null != signature)
            System.out.println("Upload Signature - " + signature.toString());
        else
            fail("getUploadSignature() api failed");
    }
}
