package util;

import com.tangly.util.RSAEncrypUtil;
import org.junit.Assert;
import org.junit.Test;

import static com.tangly.util.RSAEncrypUtil.RSADecode;
import static com.tangly.util.RSAEncrypUtil.RSAEncode;

/**
 * date: 2018/5/10 13:55 <br/>
 *
 * @author Administrator
 * @since JDK 1.7
 */
public class RSAEncrypUtilTest {
    @Test
    public void testEnCodeAndDeCode() throws Exception {
        //验证加密再 解密后的值相同
        String plainText = "床前明月光疑是地上霜举头望明月低头思故乡";
        String enCodeText = RSAEncode(plainText, RSAEncrypUtil.PUBLIC_KEY);
        Assert.assertEquals(plainText,RSADecode(RSAEncrypUtil.PRIVATE_KEY,enCodeText));
    }
}