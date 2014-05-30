package com.vzaar.test.junit;

import com.vzaar.Video;
import com.vzaar.Vzaar;
import com.vzaar.VzaarException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.fail;

public class testUploadThumb {

	private Vzaar api;
	@Before
	public void setUp() throws Exception {
		api = new Vzaar(TestConf.API_USERNAME, TestConf.API_TOKEN);
		//api = new Vzaar("ahmetunal", "GETUGkPFNC84JlzXkOMSYQFTOCAixOIiroh7oUj3k");
	}
	@Test
	public void test() {
		String user = new String();
		String uploadThumb = new String();
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

		try
		{
			String path = "D:\\_githubVzaar\\movie\\test.jpg";
			int videoId = 1628671;
			uploadThumb = api.uploadThumbnail((long)videoId, path);

		}catch (Exception e)
		{
			   e.printStackTrace();
		}

		if(uploadThumb.length()>0)
			System.out.println(uploadThumb);
		else
			fail("uploadThumb api failed");
	}
}
