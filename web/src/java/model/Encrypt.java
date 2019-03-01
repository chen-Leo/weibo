package model;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Encrypt {


    public static String EncryptPassword(String password) throws Exception {

        MessageDigest md5  = MessageDigest.getInstance("MD5");
        byte[] result = md5.digest(password.getBytes("utf-8"));    //md5加密
        byte[] results = Base64.encodeBase64(result);  //base64编码
        String rePassword = new String(results);
        return rePassword;
    }


    public static boolean StringFilter(String str)
            throws PatternSyntaxException {
        String regExp  = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    public static boolean isRight(String str) {
        String regExp  = "^[a-z0-9A-Z\\u4e00-\\u9fa5]+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
