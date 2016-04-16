package com.hzih.mmcs.utils;

import com.hzih.mmcs.domain.Account;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 13-1-15
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class KeyUtils {


    /**
     * CN=mmac 111111111111111111, OU=00, OU=00, O=00, L=00, L=00, ST=01, C=CN
     * @param dn
     * @return
     */
    public static Account getKeyAccount(String dn) throws Exception{
        Account account = new Account();
        String province = StringUtils.splitBetween2Str(dn,"ST=",",");
        String city = "00";
        String county = "00";
        if(dn.split("L=").length>2){
            city = dn.split("L=")[2].split(",")[0];
            county = dn.split("L=")[1].split(",")[0];
        }
        String districtCode = province + city + county;
        String user = StringUtils.splitBetween2Str(dn,"CN=",",");
        String org = StringUtils.splitBetween2Str(dn,"O=",",");
        String ou1 = "";
        String ou2 = "";
        if(dn.split("OU=").length>2){
            ou1 = dn.split("OU=")[2].split(",")[0];
            ou2 = dn.split("OU=")[1].split(",")[0];
        }
        account.setUserName(user.split(" ")[0]);
        account.setUserId(user.split(" ")[1]);
        account.setOrgCode(districtCode + org + ou1 + ou2);
        account.setDistrictCode(districtCode);
        return account;
    }

}
