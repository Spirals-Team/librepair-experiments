package ru.job4j.servlets.utils;

import ru.job4j.model.pojo.MusicType;
import ru.job4j.model.pojo.Roles;
import ru.job4j.model.pojo.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 */
public class AppUtils {
	// IStore user info in Session.
	public static void storeLoginedUser(HttpSession session, User loginedUser) {
		session.setAttribute("loginedUser", loginedUser);
	}

	// Get the user information stored in the session.
	public static User getLoginedUser(HttpSession session) {
		return (User) session.getAttribute("loginedUser");
	}

	//converts from a String[] of Music type to a list of MusicType
	public static List<MusicType> toListMusicType(String[] strings) {
		List<MusicType> music = new ArrayList<>();
		for (String item : strings) {
			music.add(MusicType.valueOf(item));
		}
		return music;
	}

	//converts String to releted to enum of Roles
	public static Roles toRoles(String string) {
		return Roles.valueOf(string);
	}

	//converts String to releted to enum of MusicType
	public static MusicType toMusicType(String string) {
		return MusicType.valueOf(string);
	}

	/**
	 * Delets all admins from <code>List</code> of
	 * all users(like ADMIN, MODERATOR, USER). Just
	 * for MODERATOR, because MODEDATOR can't update
	 * an ADMIN user
	 *
	 * @param list <code>List</code> of <code>User</code>'s
	 *             which are existed in a db
	 * @return <code>List</code> of users without ADMINs
	 */
	public static List<User> listWithoutAdmin(List<User> list) {
		List<User> users = new ArrayList<>(list);
		for (User user : list) {
			if (user.getRole().equals(Roles.ADMIN)) {
				users.remove(user);
			}
		}
		return users;
	}
}
