package com.vzaar.test.junit;


import com.vzaar.Vzaar;
import com.vzaar.VzaarException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class testGenerateThumb {
	private Vzaar api;
	@Before
	public void setUp() throws Exception {
		api = new Vzaar(TestConf.API_USERNAME, TestConf.API_TOKEN);
	}
	@Test
	public void test() {
		String user = new String();
		String generateThumb = new String();
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
			int videoId = 1628671;
			int thumbTime = 1000;
			generateThumb = api.generateThumbnail((long)videoId, thumbTime);

		}catch (Exception e)
		{
			e.printStackTrace();
		}

		if(generateThumb.length()>0)
			System.out.println(generateThumb);
		else
			fail("generateThumb api failed");
	}
}
