package com.github.houbb.charming.core.core;

import com.github.houbb.charming.api.domain.CharmingResult;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
* MyCharming Tester.
*
* @author houbinbin
* @since 08/21/2018
* @version 1.0
*/
public class MyCharmingTest {

    /**
    *
    * Method: charming(String string)
    *
    */
    @Test
    public void charmingTest() throws Exception {
        CharmingResult result = new MyCharming().charimg("我的3天啊12 岁,15 斤");
        Assert.assertEquals("我的 3 天啊 12 岁, 15 斤", result.getResult());
    }

    @Test
    public void fileTest() throws IOException {
        final String filePath = "/Users/houbinbin/IT/code/houbb.github.io/_posts/2018-08-25-https.md";
        List<String> stringList = Files.readAllLines(Paths.get(filePath));
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : stringList) {
            stringBuilder.append(s).append("\n");
        }
        String full = stringBuilder.toString();
        CharmingResult result = new MyCharming().charimg(full);
        System.out.println(result.getResult());
    }

}
