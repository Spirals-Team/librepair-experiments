package com.prussia.test.myspring;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] values = {};// 存储取得的值
		ArrayList<String> list1 = new ArrayList<String>();

		list1.add("a");// 将取得的值存入list中
		for (int i = 0; i < list1.size(); i++) {
			System.out.println(list1.get(i));
		}

		values = list1.toArray(values);// 最后将list转换成数组

		for (String attribute : list1) {
			System.out.println(attribute);
		}

		// 在jsp中输出的代码

		for (int i = 0; i < values.length; i++) {

			System.out.println(values[i]);

		}
		System.out.print(values.length);
	}

}
