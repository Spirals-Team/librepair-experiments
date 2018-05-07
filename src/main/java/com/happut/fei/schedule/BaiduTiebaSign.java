package com.happut.fei.schedule;

import com.alibaba.fastjson.JSON;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Profile("tencent")
public class BaiduTiebaSign {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${baidu.sign.baiduid}")
    private String baiduid;

    @Value("${baidu.sign.bduss}")
    private String bduss;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 7 * * ?")
    public void Job() throws IOException {

        logger.info("baiduid:{}", baiduid);
        logger.info("bduss:{}", bduss);


        Connection.Response execute = Jsoup.connect("http://tieba.baidu.com/dc/common/tbs")
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .cookie("BAIDUID", baiduid)
                .cookie("BDUSS", bduss)
                .execute();
        logger.info(execute.body());
        String tbs = JSON.parseObject(execute.body()).getString("tbs");

        Connection.Response execute2 = Jsoup.connect("https://tieba.baidu.com/tbmall/onekeySignin1")
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .data("ie", "utf-8")
                .data("tbs", tbs)
                .cookie("BAIDUID", baiduid)
                .cookie("BDUSS", bduss)
                .execute();
        logger.info(execute2.body());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("happut@163.com");
        message.setTo("littlewangfei@vip.qq.com");
        message.setSubject("[fei space]签到邮件");
        message.setText("签到结果：\n" + JSON.toJSONString(execute2.body(), true));

        mailSender.send(message);

    }
}
