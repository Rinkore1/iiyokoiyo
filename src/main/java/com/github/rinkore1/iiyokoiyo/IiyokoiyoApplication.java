package com.github.rinkore1.iiyokoiyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.github.rinkore1.iiyokoiyo.util.Log;

import java.io.IOException;
import java.nio.file.Paths;

@SpringBootApplication
public class IiyokoiyoApplication {

    public static void main(String[] args) throws IOException {
        Log.setApplicationLogFile(Paths.get(System.getProperty("user.dir")));
        Log.info("log测试%s","114514");
        Log.debug("debug测试%s","一一四五一四");
        Log.warning("warning测试%s","いいよ来いよ");
        SpringApplication.run(IiyokoiyoApplication.class, args);
    }

}
