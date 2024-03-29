package com.coderising.download;

import com.coderising.download.api.Connection;
import com.coderising.download.api.ConnectionException;
import com.coderising.download.api.ConnectionManager;
import com.coderising.download.api.DownloadListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class FileDownloader {


    private String url;

    private String filePath;

    DownloadListener listener;

    ConnectionManager cm;


    public FileDownloader(String _url,String filePath) {
        this.url = _url;
        this.filePath = filePath;

    }

    // 在这里实现你的代码， 注意： 需要用多线程实现下载
    // 这个类依赖于其他几个接口, 你需要写这几个接口的实现代码
    // (1) ConnectionManager , 可以打开一个连接，通过Connection可以读取其中的一段（用startPos, endPos来指定）
    // (2) DownloadListener, 由于是多线程下载， 调用这个类的客户端不知道什么时候结束，所以你需要实现当所有
    //     线程都执行完以后， 调用listener的notifiedFinished方法， 这样客户端就能收到通知。
    // 具体的实现思路：
    // 1. 需要调用ConnectionManager的open方法打开连接， 然后通过Connection.getContentLength方法获得文件的长度
    // 2. 至少启动3个线程下载，  注意每个线程需要先调用ConnectionManager的open方法
    // 然后调用read方法， read方法中有读取文件的开始位置和结束位置的参数， 返回值是byte[]数组
    // 3. 把byte数组写入到文件中
    // 4. 所有的线程都下载完成以后， 需要调用listener的notifiedFinished方法
    public void execute() {
        Connection conn = null;
        try {
            conn = cm.open(this.url);
            int length = conn.getContentLength();
            File file = new File(filePath);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.setLength(length);
            randomAccessFile.close();
            int threadCount = 3;
            //计算每个线程下载的块的大小
            System.out.println("总长度：" + length);
            int blockSize = length / threadCount;
            for (int i = 0; i < threadCount; i++) {
                //每个线程的起始下载点
                int startPos = blockSize * i;
                //每个线程的结束下载点
                int endPos = blockSize * (i + 1);
                //如果是最后一条线程，将其下载的终止点设为文件的终点
                if (i == threadCount - 1) {
                    endPos = length;
                }
                new DownloadThread(file, cm.open(this.url), blockSize * i, (endPos - 1), listener).start();
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }


    }

    public void setListener(DownloadListener listener) {
        this.listener = listener;
    }


    public void setConnectionManager(ConnectionManager ucm) {
        this.cm = ucm;
    }

    public DownloadListener getListener() {
        return this.listener;
    }

}