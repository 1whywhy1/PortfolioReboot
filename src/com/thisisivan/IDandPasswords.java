package com.thisisivan;

import java.util.HashMap;

public class IDandPasswords {

    HashMap<String,String> loginInfo = new HashMap<String,String>();
    IDandPasswords()
    {
        loginInfo.put("Bro", "pass");
        loginInfo.put("java", "rocks");
        loginInfo.put("Who", "pAss@");
        loginInfo.put("1", "1");
    }

    protected HashMap getLoginInfo()
    {
        return loginInfo;
    }
}
