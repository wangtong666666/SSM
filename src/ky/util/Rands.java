package ky.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 生成八位随机数
 */

public class Rands {

    public String Rands(int start, int stop) {
        Date date = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String number = df1.format(date);
        Long l = null;
        try {
            l = df1.parse(number).getTime();
            int i = 0;
            int c = (int) (Math.random() * 10);
            for (int j = 0; j < 10; j++) {
                i = (int) (Math.random() * 900) + 100;
                l = l + ((i + ((int) (Math.random() * 12233))) * ((int) (Math.random() * 23)));   //加上八位随机数
                l = l - ((i + ((int) (Math.random() * 899))) * ((int) (Math.random() * 999))) << ((int) (Math.random() * 10));
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   //取得毫秒数
        String a = l.toString();  //转换成String
        String n = a.substring(start, stop);  //截取八位作为随机数
        return n;
    }
}

