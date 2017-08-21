package com.yztc;




import jdk.internal.instrumentation.TypeMapping;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Administrator on 2017/8/19.
 */
public class ListFileServlet extends HttpServlet  {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //上传文件的时候保存的路径
        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
//      System.out.println(uploadPath);
        //创建一个Map用于保存该目录下的所有文件
        Map<String,String> files = new HashMap<String,String>();
        //遍历该目录，并保存到该map中
        listFileAndSave(new File(uploadPath),files);
        //返回给浏览器端
        request.setAttribute("files", files);
        request.getRequestDispatcher("listFile.jsp").forward(request, response);
    }

    private void listFileAndSave(File file, Map<String, String> files) {
        //首先判断是否是一个目录
        if(!file.isFile()){//表示这是一个目录
            //得到该目录下的所有文件
            File[] fs = file.listFiles();
            //遍历列出的所有文件，直到全部都是文件而不是目录为止
            for (File file2 : fs) {
                listFileAndSave(file2, files);
            }
        }else{//表示这是一个文件
            //需要得到这个文件真实的文件名，比如一个文件为
            //D:\apache-tomcat-7.0.42\webapps\\uploadAndDownload\WEB-INF\\upload\c.jpg
            //那么其真实的文件名应该是c.jpg才对，所以需要对该文件进行字符串的截取
               System.out.println(file.getPath());
            String fileName = file.getPath().substring(file.getPath().lastIndexOf("\\")+1);
            files.put(file.getPath(), fileName);
        }
    }
}

