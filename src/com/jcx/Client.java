package com.jcx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 文件上传客户端
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //1.创建本地字节输入流FileInputStream对象
        FileInputStream fis = new FileInputStream("C:\\Users\\12851\\Pictures\\壁纸\\thumb-1920-673328.png");
        //2.创建客户端Socket对象，绑定服务器IP地址和端口号
        Socket socket = new Socket("127.0.0.1", 6666);
        //3.使用Socket中getOutPutStream获取网络字节输出流对象
        OutputStream os = socket.getOutputStream();
        //4.使用本地字节输入流FileInputStream对象read方法读取本地文件
        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fis.read(bytes)) != -1) {
            //5.使用网络字节输出流OutPutStream中write方法 把读取到的文件上川岛服务器
            os.write(bytes, 0, len);
        }
        //上传完文件 给服务器一个结束标志
        socket.shutdownOutput();
        //6.使用Socket中getInputStream获取网络字节输入流InputStream对象
        InputStream is = socket.getInputStream();
        while ((len = is.read(bytes)) != -1) {
            System.out.println(new String(bytes,0,len));
        }
        //7.释放资源
        fis.close();
        socket.close();
    }
}
