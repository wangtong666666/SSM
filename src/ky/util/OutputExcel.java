package ky.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
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

public class OutputExcel {
    public boolean exportExcel(HttpServletResponse response, List dataList, List<Map<String, String>> map, String tmptitle) {
        try {
            response.setCharacterEncoding("utf-8");
            OutputStream os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename=fine.xls");// 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型
            WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
            //String tmptitle = "快易信息表"; // 标题
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
                    //跳过的列
                    if (paramName.equals("type")) {
                        continue;
                    }
                    //跳过的列
                    if (paramName.equals("posnumb")) {
                        continue;
                    }
                    //跳过的列
                    if (paramName.equals("state")) {
                        continue;
                    }
                    //跳过的列
                    if (paramName.equals("isChange")) {
                        continue;
                    }
                    //跳过的列
                    if (paramName.equals("lockStatus")) {
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
                        //跳过的列
                        if (paramName.equals("type")) {
                            continue;
                        }
                        //跳过的列
                        if (paramName.equals("posnumb")) {
                            continue;
                        }
                        //跳过的列
                        if (paramName.equals("state")) {
                            continue;
                        }
                        //跳过的列
                        if (paramName.equals("isChange")) {
                            continue;
                        }
                        //跳过的列
                        if (paramName.equals("lockStatus")) {
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
            os.close(); // 关闭流
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
