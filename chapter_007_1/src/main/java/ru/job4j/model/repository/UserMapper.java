package ru.job4j.model.repository;

import ru.job4j.model.pojo.Address;
import ru.job4j.model.pojo.MusicType;
import ru.job4j.model.pojo.Roles;
import ru.job4j.model.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 */
class UserMapper {
	static User toUserMap(
			int id,
			String name,
			String login,
			String password,
			String country,
			String city,
			String role,
			Object music) {
		return new User(
				id,
				login,
				password,
				name,
				new Address(country, city),
				toRoles(role),
				toMusicType(music)
		);
	}

	private static List<MusicType> toMusicType(Object obj) {
		List<MusicType> music = new ArrayList<>();
		String[] strings = (String[]) obj;
		for (String item : strings) {
			music.add(MusicType.valueOf(item));
		}
		return music;
	}

	private static Roles toRoles(String str) {
		return Roles.valueOf(str);
	}
}
