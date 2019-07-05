package ky.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    private static final String KEY_MD5 = "MD5";

    public static byte[] encryptToMD5(String data) {

        byte[] digestdata = null;
        try {
            // 得到�?��md5的消息摘�?
            MessageDigest alga = MessageDigest.getInstance(KEY_MD5);
            alga.update(data.getBytes());
            digestdata = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return digestdata;
    }

    /**
     * 计算使用MD5加密的签名�?
     *
     * @param data
     * @param token
     * @return
     */
    public static String MD5(String pwd) {
        StringBuffer signatureData = new StringBuffer(pwd);
        byte[] byteMD5 = encryptToMD5(signatureData.toString());

        return toHexString(byteMD5);

    }

    public static String MD5new(String loginname, String pwd) {

        String param = "login=" + loginname + "&pwd=" + pwd;//拼装url
        String url = new PropertiesLoader("app.properties").getProperty("ecode_url");//获取密文路径

        return HttpRequest.httpRequest(url, "POST", param);//返回密文
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toHexString(byte[] b) { // String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Encryption en = new Encryption();
    }
}
