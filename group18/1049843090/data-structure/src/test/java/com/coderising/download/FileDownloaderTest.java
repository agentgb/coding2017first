package com.coderising.download;

import com.coderising.download.api.ConnectionManager;
import com.coderising.download.api.DownloadListener;
import com.coderising.download.impl.ConnectionManagerImpl;
import org.junit.After;
import  org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class FileDownloaderTest {


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDownload() {


        String url = "http://7xq43s.com1.z0.glb.clouddn.com/yunanding-6.jpg";//3.82M

        FileDownloader downloader = new FileDownloader(url,"E:\\down333.jpg");


        ConnectionManager cm = new ConnectionManagerImpl();
        downloader.setConnectionManager(cm);

        CountDownLatch countDownLatch = new CountDownLatch(3);

        downloader.setListener(new DownloadListener() {
            @Override
            public void notifyFinished() {
                countDownLatch.countDown();
            }

        });


        downloader.execute();

        // 等待多线程下载程序执行完毕
        while (countDownLatch.getCount() > 0) {
            try {
                System.out.println("下载中...");
                //休眠
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("下载完成！");


    }

}