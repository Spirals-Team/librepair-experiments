package codeu.model.data;

import java.util.ArrayList;
import java.util.UUID;

public class ProfilePage {
	
	private String aboutMe;
	private String name;
	private User user;
	private UUID userID;
	private ArrayList<Message> recentConvos; 

	/**
	 * Constructs a new Profile Page for a specific user
	 * @param user The user this Page is being made for
	 */
	public ProfilePage(User user){
		this.user = user;
		name = user.getName();
		this.userID = user.getId();
		recentConvos = new ArrayList<Message>();
	}
	
	/**Returns the name of the user whose ProfilePage this is*/
	public String getName() {
		return name;
	}

	/**Sets the name of the user whose ProfilePage this is*/
	public void setName(String name) {
		this.name = name;
	}

	/**Returns the "about me" of the user whose ProfilePage this is*/
	public String getAboutMe() {
		return aboutMe;
	}

	/**Returns the user of this profile page*/
	public User getUser() {
		return user;
	}
	
	/**Sets the "about me" of the user whose ProfilePage this is*/
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	/**Returns the recent conversations of the user whose ProfilePage this is*/
	public ArrayList<Message> getRecentConvos() {
		return recentConvos;
	}

	/**Sets the recent conversations of the user whose ProfilePage this is*/
	public void setRecentConvos(ArrayList<Message> recentConvos) {
		this.recentConvos = recentConvos;
	}
	
	/**Adds a Message to the list of recent conversations*/
	public void addMessage(Message message){
		recentConvos.add(message);
	}

}
