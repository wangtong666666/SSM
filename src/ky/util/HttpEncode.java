package ky.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Repository;

@Repository
public class HttpEncode {

    // 加密
    public String createEncode(String json) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        byte[] desKyeByte = My3des.getDesKey();
        if (desKyeByte == null) {
            return "";
        }
        String desKey16 = StringTools.byteToHexString(desKyeByte);
        String desKeyHalfOne = desKey16.substring(0, desKey16.length() / 2);
        String desKeyHalfTwo = desKey16.substring(desKey16.length() / 2,
                desKey16.length());

        byte[] byteEnc = My3des.DesEncode(json.getBytes("UTF-8"), desKyeByte);
        if (byteEnc == null) {
            return "";
        }
        String encode16 = StringTools.byteToHexString(byteEnc);
        StringBuffer sb = new StringBuffer();
        sb.append(desKeyHalfOne);
        sb.append("|");
        sb.append(encode16);
        sb.append("|");
        sb.append(desKeyHalfTwo);
        sb.append("|");
        String md516 = MyMd5.getMD5Code(desKeyHalfOne + encode16 + desKeyHalfTwo);
        sb.append(md516);
        return sb.toString();
    }

    // 解析
    public String parseDecode(String encodeStr) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        String[] splits = encodeStr.split("\\|");
        if (splits.length == 4) {
            String currentMd5 = MyMd5.getMD5Code(splits[0] + splits[1]
                    + splits[2]);
            // 比较MD5是否正确
            boolean bool = MessageDigest.isEqual(
                    StringTools.hexStringToBytes(splits[3]),
                    StringTools.hexStringToBytes(currentMd5));
            if (bool) {
                byte[] desKey = StringTools.hexStringToBytes(splits[0]
                        + splits[2]);
                byte[] byteDec = My3des.DesDecode(
                        StringTools.hexStringToBytes(splits[1]), desKey);
                String result = new String(byteDec, "UTF-8");
                return result;
            }
        }
        return "";
    }
}
