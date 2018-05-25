package me.pagar.util;

import com.google.common.base.Strings;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {
	public static String mapToString(final Map<String, Object> map) {
		final StringBuilder stringBuilder = new StringBuilder();

		for (String key : map.keySet()) {

			if (stringBuilder.length() > 0) {
				stringBuilder.append("&");
			}

			final String value = String.valueOf(map.get(key));

			try {
				stringBuilder.append(URLEncoder.encode(Strings.nullToEmpty(key), "UTF-8"));
				stringBuilder.append("=");
				stringBuilder.append(URLEncoder.encode(Strings.nullToEmpty(value), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("This method requires UTF-8 encoding support", e);
			}

		}

		return stringBuilder.toString();
	}

	public static Map<String, String> queryToMap(final String query) {
		final Map<String, String> map = new HashMap<String, String>();
		final String[] keyValuePairs = query.split("&");

		for (String KeyValuePair : keyValuePairs) {
			final String[] KeyValue = KeyValuePair.split("=");

			try {
				map.put(URLDecoder.decode(KeyValue[0], "UTF-8"), Strings.nullToEmpty(KeyValue[1]));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("This method requires UTF-8 encoding support", e);
			}

		}

		return map;
	}

	public static Map<String, Object> objectToMap(final Object obj) {
		return objectToMap(obj, new ArrayList<String>());
	}

	public static Map<String, Object> objectToMap(final Object obj, final List<String> whitelist) {
		final Map<String, Object> result = new HashMap<String, Object>();

		try {
			final BeanInfo info = Introspector.getBeanInfo(obj.getClass());

			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				final Method reader = pd.getReadMethod();

				if (reader != null && (whitelist.size() == 0 || whitelist.contains(pd.getName()))) {
					result.put(pd.getName(), reader.invoke(obj));
				}

			}

		} catch (Exception ignored) {
		}

		return result;
	}

}  