package com.jcx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 文件上传服务端
 */
public class Server {
    public static void main(String[] args) throws IOException {
        //1.创建服务器ServerSocket对象，指定端口号
        ServerSocket server = new ServerSocket(6666);
        //2.使用serverSocket中accept方法  获取客户端Socket对象
        //服务器一直处于监听状态
        while (true){
            Socket socket = server.accept();
            //使用多线程，提高效率
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //3.使用Socket对象中getInputStream方法 获取网络字节输入流对象
                        InputStream is = socket.getInputStream();
                        //4.判断存储文件夹是否存在
                        File file = new File("d:\\upload");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        //自定义文件命名，防止同名覆盖
                        String fileName = "jcx" + System.currentTimeMillis() + new Random().nextInt(99999) + ".jpg";
                        //5.创建本地字节输出流FileOutputStream
                        FileOutputStream fos = new FileOutputStream(file + "\\" + fileName);
                        //6.使用网络字节输入流InputStream对象中read方法读取客户端上传的文件
                        int len = 0;
                        byte[] bytes = new byte[1024];
                        while ((len = is.read(bytes)) != -1) {
                            //7.使用本地字节输出流FileOutputStream中write方法把读取的文件保存到服务器磁盘
                            fos.write(bytes, 0, len);
                        }
                        //8.使用Socket中getOutputStream获取网络字节输出流对象
                        OutputStream os = socket.getOutputStream();
                        //9.使用网络字节输出流对象OutputStream中write方法给客户端会写"上传成功"
                        os.write("上传成功".getBytes());
                        //10.释放资源
                        fos.close();
                        socket.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {

                    }
                }
            }).start();
        }

    }
}
