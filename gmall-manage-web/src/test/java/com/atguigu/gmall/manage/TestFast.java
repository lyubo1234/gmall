package com.atguigu.gmall.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class TestFast {
    @Test
    public void contextLoads() throws IOException, MyException {
        String path = TestFast.class.getResource("/tracker.conf").getPath();
        ClientGlobal.init(path);

        TrackerClient trackerClient = new TrackerClient();

        TrackerServer connection = trackerClient.getConnection();

        StorageClient storageClient = new StorageClient();

        String[] uploadFile = storageClient.upload_file("d:/love.jpg", "jpg", null);

        String url="http:192.168.220.137";
        for (String s : uploadFile) {
            url+="/"+s;

        }
        System.out.println(url);
    }

}
