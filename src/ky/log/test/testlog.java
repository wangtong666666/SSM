package ky.log.test;


//import java.util.Hashtable;

import java.util.Random;

import ky.log.GeneralLog;
import ky.log.devlog.DevLog;

//
//
//  @ Project : 2007-09-17
//  @ File Name : testlog.java
//  @ Date : 2007-9-27
//  @ Author : @yang yunchun
//
//

class LogThread implements Runnable {
    Thread t = null;


    static Random myran = new Random(10);

    LogThread() {
        t = new Thread(this);

        t.start();
    }

    public void run() {

        String[] name = {"comm", "tcp",
                "tcp", "comm",
                "comm", "tcp",
                "tcp", "comm",
                "10.50.100.66", "B00066",
                "10.50.100.65", "B00065",
                "10.50.100.67", "B00064",
                "10.50.100.64", "B00063",
                "10.50.100.63", "B00062",
                "10.50.100.62", "B00061",
                "10.50.100.60", "B00060",};


        int index = myran.nextInt(10);
        System.out.println("Random index =" + index);
        if (index < 4) index += 5;
        if (index < 4) {
/*		String outbuf = "[" +name[index*2] +"]  " + t.getName() + "  " ;
		String outname = name[index*2];
		if(GeneralLog.isFatalEnabled(outname))
			GeneralLog.debug(outname, outbuf + "this is a debug");
		if(GeneralLog.isInfoEnabled(outname))
			GeneralLog.info(outname,outbuf + "this is a info");
		if(GeneralLog.isWarnEnabled(outname))
			GeneralLog.warn(outname,outbuf + "this is a warn");
		if(GeneralLog.isErrorEnabled(outname))
			GeneralLog.error(outname,outbuf + "this is a error");
		if(GeneralLog.isFatalEnabled(outname))
			GeneralLog.fatal(outname,outbuf + "this is a fatal");
*/
        } else {

            String outpara1 = name[index * 2 + 1];
            String outpara2 = name[index * 2];
            String outbuf = "[" + outpara1 + "_" + outpara2 + "]  " + t.getName() + "  ";

            DevLog.setLogLevel(outpara1, outpara2, index - 4);

            System.out.println("setLogLevel " + outpara1 + "_" + outpara2 + " level=" + (index - 4));

            DevLog.isDebugEnabled(outpara1, outpara2);
            DevLog.isDebugEnabled(outpara1, outpara2);
            DevLog.isDebugEnabled(outpara1, outpara2);
            DevLog.isDebugEnabled(outpara1, outpara2);

            if (DevLog.isDebugEnabled(outpara1, outpara2))
                DevLog.debug(outpara1, outpara2, outbuf + "this is a debug");
            if (DevLog.isInfoEnabled(outpara1, outpara2))
                DevLog.info(outpara1, outpara2, outbuf + "this is a info");
            if (DevLog.isWarnEnabled(outpara1, outpara2))
                DevLog.warn(outpara1, outpara2, outbuf + "this is a warn");
            if (DevLog.isErrorEnabled(outpara1, outpara2))
                DevLog.error(outpara1, outpara2, outbuf + "this is a error");
            if (DevLog.isFatalEnabled(outpara1, outpara2))
                DevLog.fatal(outpara1, outpara2, outbuf + "this is a fatal");

        }
    }
}


public class testlog {
    public static void main(String args[]) {
        for (int cycle = 0; cycle < 1; cycle++) {
            for (int concurrent = 0; concurrent < 20; concurrent++) {
                new LogThread();
                //System.out.println("�ڲ�ѭ�� �� "+ concurrent +" ��!!!");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
            System.out.println("------------------------------------------�ⲿѭ�� �� " + cycle + " ��!!!------------");
        }
        //Thread.sleep(10000);
        System.out.println("done over!!!");
    }
}
