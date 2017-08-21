package com.yztc;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */
public class uploadServlet  extends HttpServlet {

    @SuppressWarnings("unchecked")
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //1、创建DiskFileItemFactory工厂
        FileItemFactory factory = new DiskFileItemFactory();
        //2、创建一个文件上传解析器
        ServletFileUpload su = new ServletFileUpload(factory);
        //解决上传文件名乱码
        su.setHeaderEncoding("UTF-8");
        List<FileItem> list = null;
        //获取输出流对象
        PrintWriter out = response.getWriter();
        try {
            //3、解析Request对象，以便对文件上传域对象进行处理
            list= su.parseRequest(request);
            for (FileItem fileItem : list) {
                if(fileItem.isFormField()){//普通表单域
                    if(fileItem.getFieldName().equals("username")){
                        //处理表单提交字段,解决普通输入域中文乱码问题
                        String username = fileItem.getString("utf-8");
                        out.print(username + "，");
                    }
                }else{//文件上传域
                    //得到上传文件的保存目录，将上传的文件保存到WebRoot下的upload文件夹中
                    String path = this.getServletContext().getRealPath("/WEB-INF/upload");
                    //浏览器请求路径，这是一个相对路径
                    String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/upload/";
                    //获取上传文件的文件名
                    String fileName = fileItem.getName();
                    //得到上传文件的扩展名
                    String extName = fileName.substring(fileName.lastIndexOf(".")+1);
                    //服务器只支持jpg和gif两种格式的文件
                    if(extName.equals("jpg") || extName.equals("gif")){
                        File uFile = new File(path);
                        //如果保存的目录不存在就创建一个目录
                        if(!uFile.exists()){
                            uFile.mkdirs();
                        }
                        File file = new File(path,fileName);
                        //写入文件到保存的目录中
                        fileItem.write(file);
                    }else{
                        System.out.println("文件格式不支持");
                    }

                }
            }
            out.print("上传成功");
        } catch (Exception e) {
            out.print("对不起，上传失败");
            e.printStackTrace();
        }
    }
}

