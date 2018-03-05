package com.soa.rs.discordbot.bot.events;

import java.io.InputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TimeZone;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.soa.rs.discordbot.cfg.DiscordCfgFactory;
import com.soa.rs.discordbot.d4j.testimpl.CreateD4JObjects;
import com.soa.rs.discordbot.d4j.testimpl.MockChannel;
import com.soa.rs.discordbot.d4j.testimpl.MockDiscordClient;
import com.soa.rs.discordbot.d4j.testimpl.MockGuild;
import com.soa.rs.discordbot.d4j.testimpl.MockMessage;
import com.soa.rs.discordbot.d4j.testimpl.MockMsgRcvEvent;
import com.soa.rs.discordbot.d4j.testimpl.MockRole;
import com.soa.rs.discordbot.d4j.testimpl.MockUser;
import com.soa.rs.discordbot.jaxb.CurrentUser;
import com.soa.rs.discordbot.jaxb.DisplayNames;
import com.soa.rs.discordbot.jaxb.Guild;
import com.soa.rs.discordbot.jaxb.GuildUsers;
import com.soa.rs.discordbot.jaxb.LeftUser;
import com.soa.rs.discordbot.jaxb.LeftUsers;
import com.soa.rs.discordbot.jaxb.RankList;
import com.soa.rs.discordbot.jaxb.TrackedInformation;
import com.soa.rs.discordbot.jaxb.UserTrackingEvent;
import com.soa.rs.discordbot.util.SoaLogging;

import sx.blah.discord.handle.obj.StatusType;

public class UserTrackingQueryTest {

	@Before
	public void setupTest() throws DatatypeConfigurationException {
		TrackedInformation info = new TrackedInformation();

		Guild guild = new Guild();
		guild.setGuildId(1234567890);
		guild.setGuildName("Test Guild");

		GuildUsers gusers = new GuildUsers();

		CurrentUser user = new CurrentUser();
		user.setUserId(213456789);
		user.setUserName("@user#1234");
		DisplayNames names = new DisplayNames();
		names.getDisplayName().add("User");
		user.setDisplayNames(names);
		user.setJoined(getCurrentDate());
		user.setLastOnline(getCurrentDate());
		gusers.getUser().add(user);

		user = new CurrentUser();
		user.setUserId(312456789);
		user.setUserName("@user2#5678");
		names = new DisplayNames();
		names.getDisplayName().add("User2");
		user.setDisplayNames(names);
		user.setJoined(getCurrentDate());
		user.setLastOnline(getCurrentDate());
		gusers.getUser().add(user);

		LeftUsers lusers = new LeftUsers();

		LeftUser luser = new LeftUser();
		luser.setUserId(456789123);
		luser.setUserName("@leftUser#9012");
		names = new DisplayNames();
		names.getDisplayName().add("Left User");
		luser.setDisplayNames(names);
		luser.setLeft(getCurrentDate());

		guild.setGuildUsers(gusers);
		guild.setLeftUsers(lusers);

		info.getGuild().add(guild);

		UserTrackingUpdater.getInstance().setInformation(info);

		DiscordCfgFactory.getConfig().setUserTrackingEvent(new UserTrackingEvent());
		RankList list = new RankList();
		list.getRole().add("TestRole");
		DiscordCfgFactory.getConfig().getUserTrackingEvent().setCanUpdateQuery(list);
		DiscordCfgFactory.getConfig().setDefaultGuildId(1234567890);

		SoaLogging.initializeLogging();
	}

	@Test
	public void testQueryKnownUser() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		DiscordCfgFactory.getConfig().setDefaultGuildId(1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user setKnownUser");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser author = CreateD4JObjects.createMockUser("Author", "7890", 6497, StatusType.ONLINE);
		MockRole role = CreateD4JObjects.createMockRole(7031, "TestRole");
		author.addRole(role);
		message.setAuthor(author);
		guild.addUser(author);
		guild.addRole(role);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "setKnownUser", "-search", "User2", "-name", "SoAKnownUser" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
		Assert.assertEquals("User's known name updated", channel.getMessage());

		TrackedInformation newinfo = null;

		try {
			InputStream stream = UserTrackingUpdater.getInstance().writeInfoToStream();
			newinfo = unmarshallData(stream);
		} catch (Exception e) {
			Assert.fail();
		}

		Iterator<CurrentUser> mapIter = newinfo.getGuild().get(0).getGuildUsers().getUser().iterator();
		CurrentUser validateUser = null;
		while (mapIter.hasNext()) {
			validateUser = mapIter.next();
			if (validateUser.getUserName().equals("User2"))
				break;
		}
		Assert.assertEquals("SoAKnownUser", validateUser.getKnownName());

	}

	@Test
	public void testQueryKnownUserMissingRole() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		DiscordCfgFactory.getConfig().setDefaultGuildId(1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user setKnownUser");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser author = CreateD4JObjects.createMockUser("Author", "7890", 6497, StatusType.ONLINE);
		MockRole role = CreateD4JObjects.createMockRole(7031, "JunkRole");
		author.addRole(role);
		message.setAuthor(author);
		guild.addUser(author);
		guild.addRole(role);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "setKnownUser", "-search", "User2", "-name", "SoAKnownUser" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
		Assert.assertEquals("You do not have the role to perform this action", channel.getMessage());

	}

	@Test
	public void testQueryKnownUserNoUserFound() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		DiscordCfgFactory.getConfig().setDefaultGuildId(1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user setKnownUser");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser author = CreateD4JObjects.createMockUser("Author", "7890", 6497, StatusType.ONLINE);
		MockRole role = CreateD4JObjects.createMockRole(7031, "TestRole");
		author.addRole(role);
		message.setAuthor(author);
		guild.addUser(author);
		guild.addRole(role);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "setKnownUser", "-search", "JunkUser", "-name", "SoAKnownUser" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
		Assert.assertEquals("Was not able to find user", channel.getMessage());

	}

	@Test
	public void testQueryKnownUserInvalidArgument() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		DiscordCfgFactory.getConfig().setDefaultGuildId(1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user setKnownUser");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser author = CreateD4JObjects.createMockUser("Author", "7890", 6497, StatusType.ONLINE);
		MockRole role = CreateD4JObjects.createMockRole(7031, "TestRole");
		author.addRole(role);
		message.setAuthor(author);
		guild.addUser(author);
		guild.addRole(role);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "setKnownUser", "-search", "User2", "-displayname", "SoAKnownUser" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
		Assert.assertEquals("Arguments provided were invalid.", channel.getMessage());

	}

	@Test
	public void testQueryKnownUserValidMention() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		DiscordCfgFactory.getConfig().setDefaultGuildId(1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user setKnownUser");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser author = CreateD4JObjects.createMockUser("Author", "7890", 6497, StatusType.ONLINE);
		MockRole role = CreateD4JObjects.createMockRole(7031, "TestRole");
		author.addRole(role);
		message.setAuthor(author);
		guild.addUser(author);
		guild.addRole(role);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		message.addUserMention(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "setKnownUser", "-search", "<@name>", "-name", "SoAKnownUser" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
		Assert.assertEquals("User's known name updated", channel.getMessage());

	}

	@Test
	public void testQueryKnownUserFakeMention() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		DiscordCfgFactory.getConfig().setDefaultGuildId(1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user setKnownUser");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser author = CreateD4JObjects.createMockUser("Author", "7890", 6497, StatusType.ONLINE);
		MockRole role = CreateD4JObjects.createMockRole(7031, "TestRole");
		author.addRole(role);
		message.setAuthor(author);
		guild.addUser(author);
		guild.addRole(role);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "setKnownUser", "-search", "<@name>", "-name", "SoAKnownUser" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
		Assert.assertEquals("Arguments provided were invalid.", channel.getMessage());

	}

	@Test
	public void testQuerySearchNothingInResultSet() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user search junk");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "search" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
		Assert.assertEquals("Your search returned 0 results.", channel.getMessage());
	}

	@Test
	public void testQuerySearch() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user search User");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "search" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
	}

	@Test
	public void testQuerySearchInvalidServer() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user search User -server D4J");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "search" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
		Assert.assertEquals("We didn't recognize the server that was entered", channel.getMessage());
	}

	@Test
	public void testQuerySearchValidServer() throws InterruptedException {
		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		MockGuild guild = CreateD4JObjects.createMockGuild("Test Guild", 1234567890);
		guild.setClient(client);
		client.addGuild(guild);

		channel.setClient(client);
		channel.setGuild(guild);

		MockMessage message = CreateD4JObjects.createMockMessage(".user search User -server Test Guild");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser user = CreateD4JObjects.createMockUser("user", "1234", 213456789, StatusType.ONLINE);
		user.setDisplayName("User");
		guild.addUser(user);

		MockUser user2 = CreateD4JObjects.createMockUser("user2", "5678", 312456789, StatusType.ONLINE);
		user.setDisplayName("User2");
		guild.addUser(user2);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "search" });

		query.executeEvent();

		// Sleep is needed due to the requestbuffer not updating the boolean until after
		// it thinks it sent the message
		Thread.sleep(500);
		Assert.assertTrue(channel.wasMessageSent());
	}

	@Test
	public void testRecentActions() throws InterruptedException {

		UserTrackingUpdater.getInstance().setInformation(new TrackedInformation());

		MockDiscordClient client = CreateD4JObjects.createMockClient();

		MockGuild guild = CreateD4JObjects.createMockGuild("Bot_Testing", 1234567890);
		MockChannel channel = CreateD4JObjects.createMockChannel(13579);
		channel.setClient(client);
		channel.setGuild(guild);

		MockUser muser1 = CreateD4JObjects.createMockUser("Test user 1", "1234", 678910, StatusType.ONLINE);
		MockUser muser2 = CreateD4JObjects.createMockUser("Test user 2", "5678", 111213, StatusType.ONLINE);

		guild.addUser(muser1);
		guild.addUser(muser2);

		client.addGuild(guild);

		UserTrackingUpdater.getInstance().setClient(client);
		UserTrackingUpdater.getInstance().populateInformation();

		guild.removeUser(muser2);

		UserTrackingUpdater.getInstance().setClient(client);
		UserTrackingUpdater.getInstance().populateInformation();

		muser1.setDiscriminator("9876");

		UserTrackingUpdater.getInstance().setClient(client);
		UserTrackingUpdater.getInstance().populateInformation();

		muser1.setDisplayName("Mah name!");

		UserTrackingUpdater.getInstance().setClient(client);
		UserTrackingUpdater.getInstance().populateInformation();

		MockMessage message = CreateD4JObjects.createMockMessage(".user getRecentActions");
		message.setChannel(channel);
		message.setGuild(guild);
		message.setClient(client);

		MockUser author = CreateD4JObjects.createMockUser("Author", "7890", 6497, StatusType.ONLINE);
		MockRole role = CreateD4JObjects.createMockRole(7031, "TestRole");
		author.addRole(role);
		message.setAuthor(author);
		guild.addUser(author);
		guild.addRole(role);

		MockMsgRcvEvent event = new MockMsgRcvEvent(message);
		event.setClient(client);

		UserTrackingQuery query = new UserTrackingQuery(event);
		query.setArgs(new String[] { "user", "getRecentActions" });

		query.executeEvent();
		Thread.sleep(500);

		Assert.assertTrue(channel.getMessage().contains("@Test user 1#1234 has joined the server."));
		Assert.assertTrue(channel.getMessage().contains("@Test user 2#5678 has left the server."));
		Assert.assertTrue(
				channel.getMessage().contains("@Test user 1#1234 has changed their user handle to @Test user 1#9876."));
		Assert.assertTrue(channel.getMessage()
				.contains("@Test user 1#9876 has changed their display name from Test user 1 to Mah name!."));

	}

	/*
	 * Non-test support methods below
	 */
	private XMLGregorianCalendar getCurrentDate() throws DatatypeConfigurationException {
		return getDate(new Date());
	}

	private XMLGregorianCalendar getDate(Date date) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		c.setTimeZone(TimeZone.getTimeZone("UTC"));
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		return date2;
	}

	private TrackedInformation unmarshallData(InputStream stream) throws Exception {

		TrackedInformation info = null;

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("com.soa.rs.discordbot.jaxb");

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(this.getClass().getResource("/xsd/userTracking.xsd"));

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbUnmarshaller.setSchema(schema);
			info = (TrackedInformation) jaxbUnmarshaller.unmarshal(stream);
		} catch (Exception e) {
			// fail test
			throw e;
		} finally {
			stream.close();
		}

		return info;
	}

}
