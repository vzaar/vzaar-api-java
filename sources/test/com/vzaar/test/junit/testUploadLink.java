package com.vzaar.test.junit;

import com.vzaar.UploadLinkQuery;
import com.vzaar.VideoDetails;
import com.vzaar.Vzaar;
import com.vzaar.VzaarException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class testUploadLink {
	private Vzaar api;

	@Before
	public void setUp() throws Exception {
		api = new Vzaar(TestConf.API_USERNAME, TestConf.API_TOKEN);
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		String user = new String();
		Long videoId = null;
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
			UploadLinkQuery uploadLinkQuery = new UploadLinkQuery();
			uploadLinkQuery.url = "http://samples.mplayerhq.hu/MPEG-4/turn-on-off.mp4";
			uploadLinkQuery.title = "Upload Link Test";
			uploadLinkQuery.description = "a simple test for upload link functionality";
			uploadLinkQuery.width = 640;
			uploadLinkQuery.bitrate = 256;
			uploadLinkQuery.transcoding = false;

			videoId = api.uploadLink(uploadLinkQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (videoId != null)
			System.out.println("video was uploaded successfully ---" + videoId);
		else
			fail("uploadLink api failed");
		try
		{
			VideoDetails videoDetails = api.getVideoDetails(videoId);
			if (null == videoDetails)
				fail("getVideoDetails() api failed");
			else {
				System.out.println(" Video Details - " + videoDetails.toString());
				System.out.println(videoDetails.title + " -- " + videoDetails.duration);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
