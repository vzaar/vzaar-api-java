package com.vzaar.test.junit;

import static org.junit.Assert.*;

import com.vzaar.*;
import org.junit.Before;
import org.junit.Test;

public class testGetAccountDetails {

    private Vzaar api;

    @Before
    public void setUp() throws Exception {
        api = new Vzaar(TestConf.API_TOKEN, TestConf.API_USERNAME);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
