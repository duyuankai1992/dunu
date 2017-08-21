package com.yztc;

/**
 * Created by Administrator on 2017/8/19.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class downLoadServlet  extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //获取要下载文件的服务器路径
        String filepath = request.getParameter("filepath");
        //获取文件名
        String filename = filepath.substring(filepath.lastIndexOf("\\")+1);
        //设置响应头，控制浏览器下载该文件
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition", "attachment;filename="+ new String(filename.getBytes("utf-8"), "ISO-8859-1"));
        //创建输入流
        FileInputStream fis = new FileInputStream(filepath);
        //读取文件至缓冲区中
        byte[] data = new byte[fis.available()];
        fis.read(data);
        //关闭流
        fis.close();
        //创建输出流
        OutputStream os = response.getOutputStream();
        //写入到文件中
        os.write(data);
        os.close();
    }
}

