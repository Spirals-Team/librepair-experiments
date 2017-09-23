package com.alibaba.json.bvt.support.spring;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

import junit.framework.TestCase;

import org.junit.Assert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

public class FastJsonHttpMessageConverter4Test extends TestCase {
    public FastJsonHttpMessageConverter4Test() {
        
    }

	public void test_1() throws Exception {

		FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();

		Assert.assertNotNull(converter.getFastJsonConfig());
		converter.setFastJsonConfig(new FastJsonConfig());
		
		converter.canRead(VO.class, VO.class, MediaType.APPLICATION_JSON_UTF8);

		converter.canWrite(VO.class, VO.class, MediaType.APPLICATION_JSON_UTF8);
		
		Method method1 = FastJsonHttpMessageConverter4.class.getDeclaredMethod(
				"supports", Class.class);
		method1.setAccessible(true);
		method1.invoke(converter, int.class);
		
		HttpInputMessage input = new HttpInputMessage() {

			public HttpHeaders getHeaders() {
				// TODO Auto-generated method stub
				return null;
			}

			public InputStream getBody() throws IOException {
				return new ByteArrayInputStream("{\"id\":123}".getBytes(Charset
						.forName("UTF-8")));
			}

		};
		VO vo = (VO) converter.read(VO.class, VO.class, input);
		Assert.assertEquals(123, vo.getId());

		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		HttpOutputMessage out = new HttpOutputMessage() {

			public HttpHeaders getHeaders() {
				return new HttpHeaders();
			}

			public OutputStream getBody() throws IOException {
				return byteOut;
			}
		};
		converter.write(vo, VO.class, MediaType.TEXT_PLAIN, out);

		byte[] bytes = byteOut.toByteArray();
		Assert.assertEquals("{\"id\":123}", new String(bytes, "UTF-8"));
		
		Method method2 = FastJsonHttpMessageConverter4.class.getDeclaredMethod(
				"readInternal", Class.class, HttpInputMessage.class);
		method2.setAccessible(true);
		method2.invoke(converter, VO.class, input);
	}
	
	public static class VO {

		private int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	}
}
