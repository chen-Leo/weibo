package model;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class Encrypt {


    public static String EncryptPassword(String password) throws Exception {

        MessageDigest md5  = MessageDigest.getInstance("MD5");
        byte[] result = md5.digest(password.getBytes("utf-8"));    //md5加密
        byte[] results = Base64.encodeBase64(result);  //base64编码
        String rePassword = new String(results);
        return rePassword;
    }








}
