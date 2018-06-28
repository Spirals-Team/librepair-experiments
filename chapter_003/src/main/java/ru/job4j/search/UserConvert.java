package ru.job4j.search;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap; 

public class UserConvert {
	
	 /**
     * Метод конвертирует список пользователей в мапу с ключем id  и значением пользователь.
     * @param список пользователей
	 * @return мапа из айди и пользователей
     */
	public HashMap<Integer, User> process(List<User> list) {
		HashMap<Integer, User> map = new HashMap<>();
		for (User user : list) {
			map.put(user.getId(), user);
		}
		return map;
	}
}