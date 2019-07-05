package ky.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ExcelInput {
    public static void inExcel(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    System.out.println("表单参数名:" + item.getFieldName()
                            + "，表单参数值:" + item.getString("UTF-8"));
                } else {
                    if (item.getName() != null && !item.getName().equals("")) {
                        System.out.println("上传文件的大小:" + item.getSize());
                        System.out.println("上传文件的类型:" + item.getContentType());
                        // item.getName()返回上传文件在客户端的完整路径名称
                        System.out.println("上传文件的名称:" + item.getName());

                        File tempFile = new File(item.getName());
                        // 上传文件的保存路径
                        File file = new File("/"
                                + FileUtil.getProjectSystemPath() + "upload"
                                + File.separator, tempFile.getName());
                        item.write(file);
                        request.setAttribute("upload.message", "上传文件成功！");
                    } else {
                        request.setAttribute("upload.message", "没有选择上传文件！");
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("upload.message", "上传文件失败！");
        }
        // request.getRequestDispatcher("/uploadResult.jsp").forward(request,response);
    }
}