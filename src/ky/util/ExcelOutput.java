package ky.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelOutput {
    public String exportExcel(String agent_number, List dataList, String str) {

        try {
            List<Map<String, String>> map = new ArrayList<Map<String, String>>();
            str = new String(str.getBytes("iso-8859-1"), "utf-8");
            String[] str1 = str.split(",");
            for (int i = 0; i < str1.length; i++) {
                String[] str2 = str1[i].split(":");
                if (str2.length == 2) {
                    Map<String, String> m = new HashMap<String, String>();
                    m.put(str2[0], str2[1]);
                    map.add(m);
                }
            }

            Date date = new Date();
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String time = formater.format(date);
            String fileName = agent_number + "-" + time + ".xls";
            String path = null;
            if (File.separator.equals("\\"))
                path = FileUtil.getProjectSystemPath() +
                        "file" + File.separator + fileName;
            else {
//		      path = "/home/ouda_pms/LKLM_PMS_TOMCAT/webapps/LKLM_AGENT/upload" + 
//		        File.separator + fileName;
            }

            //OutputStream os = new FileOutputStream(path);
            WritableWorkbook wbook = Workbook.createWorkbook(new File(path)); // 建立excel文件
            String tmptitle = "信息表"; // 标题
            WritableSheet wsheet = wbook.createSheet(tmptitle, 0); // sheet名称
            // 设置excel标题
            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,
                    WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.AQUA);
            wsheet.addCell(new Label(1, 0, tmptitle, wcfFC));
            wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14,
                    WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    Colour.BLACK);
            wcfFC = new WritableCellFormat(wfont);
            // 开始生成主体内容
            for (int i = 0; i < map.size(); i++) {
                Map<String, String> m = map.get(i);
                Iterator<String> it = m.keySet().iterator();
                while (it.hasNext()) {
                    String paramName = it.next();
                    //跳过的列
                    if (paramName.equals("hiddenTrans")) {
                        continue;
                    }
                    //跳过的列
                    if (paramName.equals("bz")) {
                        continue;
                    }
                    wsheet.addCell(new Label(i, 2, m.get(paramName)));
                }
            }
            for (int j = 0; j < dataList.size(); j++) {
                Object obj = dataList.get(j);
                for (int i = 0; i < map.size(); i++) {
                    Map<String, String> m = map.get(i);
                    Iterator<String> it = m.keySet().iterator();
                    while (it.hasNext()) {
                        String paramName = it.next();
                        //跳过的列
                        if (paramName.equals("hiddenTrans")) {
                            continue;
                        }
                        //跳过的列
                        if (paramName.equals("bz")) {
                            continue;
                        }
                        Field f = obj.getClass().getDeclaredField(paramName);
                        f.setAccessible(true);
                        Object o = f.get(obj);
                        if (o == null) {
                            o = "";
                        }
                        //需要格式化的列
                        if (paramName.equals("transamt")) {
                            wsheet.addCell(new jxl.write.Number(i, j + 3, Double.parseDouble(o.toString())));
                            continue;
                        }
                        if (paramName.equals("sumtransamt")) {
                            wsheet.addCell(new jxl.write.Number(i, j + 3, Double.parseDouble(o.toString())));
                            continue;
                        }
                        if (paramName.equals("maxmoney")) {
                            wsheet.addCell(new jxl.write.Number(i, j + 3, Double.parseDouble(o.toString())));
                            continue;
                        }
                        if (paramName.equals("minmoney")) {
                            wsheet.addCell(new jxl.write.Number(i, j + 3, Double.parseDouble(o.toString())));
                            continue;
                        }
                        if (paramName.equals("cantransamt")) {
                            wsheet.addCell(new jxl.write.Number(i, j + 3, Double.parseDouble(o.toString())));
                            continue;
                        }

                        if (paramName.equals("extends3")) {
                            String val = o.toString();
                            if (val.equals("0")) {
                                val = "T+0";
                            } else if (val.equals("1")) {
                                val = "T+1";
                            } else {
                                val = "";
                            }
                            wsheet.addCell(new Label(i, j + 3, val));
                            continue;
                        }

                        if (paramName.equals("channelno")) {
                            String val = o.toString();
                            if (val.equals("0000")) {
                                val = "成功";
                            } else if (val.equals("0013")) {
                                val = "失败:无效金额";
                            } else if (val.equals("0013")) {
                                val = "失败:有作弊嫌疑";
                            } else if (val.equals("0013")) {
                                val = "失败:无此储蓄卡账户";
                            } else if (val.equals("0013")) {
                                val = "失败:过期的卡";
                            } else if (val.equals("0013")) {
                                val = "失败:不正确的PIN";
                            } else if (val.equals("0013")) {
                                val = "失败:超出取款金额限制";
                            } else if (val.equals("0013")) {
                                val = "失败:受限制的卡";
                            } else if (val.equals("0013")) {
                                val = "失败:PIN转换失败";
                            } else if (val.equals("0013")) {
                                val = "失败:发送数据失败";
                            } else if (val.equals("0013")) {
                                val = "失败:接收数据失败";
                            } else if (val.equals("0013")) {
                                val = "失败:当批次金额已超限,请结算后再试";
                            } else if (val.equals("0013")) {
                                val = "失败:上游错误（关闭）";
                            } else if (val.equals("0013")) {
                                val = "失败:交易失败,请联系收单方";
                            } else if (val.equals("0013")) {
                                val = "失败:未知错误";
                            } else {
                                val = "其他错误";
                            }
                            wsheet.addCell(new Label(i, j + 3, val));
                            continue;
                        }
                        wsheet.addCell(new Label(i, j + 3, o.toString()));
                    }
                }
            }
            // 主体内容生成结束
            wbook.write(); // 写入文件
            wbook.close();
            //os.close(); // 关闭流
            return fileName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
    }


    public void download(String fileName, HttpServletResponse response) {
        try {

            String path = null;
            if (File.separator.equals("\\"))
                path = FileUtil.getProjectSystemPath() +
                        "file" + File.separator + fileName;
            else {
                path = "/home/ouda_pms/LKLM_PMS_TOMCAT/webapps/LKLM_AGENT/upload" +
                        File.separator + fileName;
            }

            // path是指欲下载的文件的路径。  
            File file = new File(path);
            // 取得文件名。  
            String filename = file.getName();
            // 以流的形式下载文件。  
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response  
            response.reset();
            // 设置response的Header  
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();

            // 下载完成后删除文件
            file.delete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
