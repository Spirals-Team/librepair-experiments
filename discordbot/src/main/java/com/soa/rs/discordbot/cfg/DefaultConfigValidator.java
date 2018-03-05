package com.soa.rs.discordbot.cfg;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.soa.rs.discordbot.jaxb.AdminEvent;
import com.soa.rs.discordbot.jaxb.DiscordConfiguration;
import com.soa.rs.discordbot.jaxb.EventListingEvent;
import com.soa.rs.discordbot.jaxb.ListingEvent;
import com.soa.rs.discordbot.jaxb.MusicPlayer;
import com.soa.rs.discordbot.jaxb.UserTrackingEvent;
import com.soa.rs.discordbot.util.SoaLogging;

/**
 * A reference implementation for validating a DiscordConfiguration
 */
public class DefaultConfigValidator implements ConfigValidator {

	/*
	 * (non-Javadoc)
	 * @see com.soa.rs.discordbot.cfg.ConfigValidator#validateListingEvent(com.soa.rs.discordbot.jaxb.ListingEvent)
	 */
	@Override
	public boolean validateListingEvent(ListingEvent event) throws InvalidBotConfigurationException {
		EventTypes eventType = null;
		if (event instanceof EventListingEvent) {
			eventType = EventTypes.EVENT_LISTING_EVENT;
		} else {
			eventType = EventTypes.NEWS_LISTING_EVENT;
		}
		if (!validateURL(event.getUrl())) {
			throw new InvalidBotConfigurationException("An invalid URL was provided", eventType);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.soa.rs.discordbot.cfg.ConfigValidator#validateMusicPlayer(com.soa.rs.discordbot.jaxb.MusicPlayer)
	 */
	@Override
	public boolean validateMusicPlayer(MusicPlayer event) {
		if (event.getCanDisableRankCheck() == null || event.getCanDisableRankCheck().getRole().isEmpty()) {
			// List has no roles, by default set this to be Staff roles
			if (DiscordCfgFactory.getConfig().getStaffRoles() != null
					&& !DiscordCfgFactory.getConfig().getStaffRoles().getRole().isEmpty()) {
				event.setCanDisableRankCheck(DiscordCfgFactory.getConfig().getStaffRoles());
				DiscordCfgFactory.getConfig().setMusicPlayer(event);
				SoaLogging.getLogger()
						.debug("No disable rank check ranks in Music Player config, setting to staff roles");
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.soa.rs.discordbot.cfg.ConfigValidator#validateAdminEvent(com.soa.rs.discordbot.jaxb.AdminEvent)
	 */
	@Override
	public boolean validateAdminEvent(AdminEvent event) {
		if (event.getAllowedRoles() == null || event.getAllowedRoles().getRole().isEmpty()) {
			// List has no roles, by default set this to be Staff roles
			if (DiscordCfgFactory.getConfig().getStaffRoles() != null
					&& !DiscordCfgFactory.getConfig().getStaffRoles().getRole().isEmpty()) {
				event.setAllowedRoles(DiscordCfgFactory.getConfig().getStaffRoles());
				DiscordCfgFactory.getConfig().setAdminEvent(event);
				SoaLogging.getLogger().debug("No allowed roles in Admin event, setting to staff roles");
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.soa.rs.discordbot.cfg.ConfigValidator#validateUserTrackingEvent(com.soa.rs.discordbot.jaxb.UserTrackingEvent)
	 */
	@Override
	public boolean validateUserTrackingEvent(UserTrackingEvent event) throws InvalidBotConfigurationException {
		if (event.getCanUpdateQuery() == null || event.getCanUpdateQuery().getRole().isEmpty()) {
			// List has no roles, by default set this to be Staff roles
			if (DiscordCfgFactory.getConfig().getStaffRoles() != null
					&& !DiscordCfgFactory.getConfig().getStaffRoles().getRole().isEmpty()) {
				event.setCanUpdateQuery(DiscordCfgFactory.getConfig().getStaffRoles());
				DiscordCfgFactory.getConfig().setUserTrackingEvent(event);
				SoaLogging.getLogger().debug("No can update query roles in Admin event, setting to staff roles");
			}
		}

		if (event.getTrackingFile() == null || event.getTrackingFile().trim().length() == 0) {
			throw new InvalidBotConfigurationException("No tracking file path was specified.",
					EventTypes.USER_TRACKING_EVENT);
		}
		File trackingFile = new File(event.getTrackingFile());
		if (!trackingFile.exists()) {
			trackingFile = trackingFile.getParentFile();
			if (!trackingFile.exists()) {
				// This path is WRONG, die
				throw new InvalidBotConfigurationException("The file nor the parent directory exists",
						EventTypes.USER_TRACKING_EVENT);
			}
		} else {
			if (trackingFile.isDirectory()) {
				// The file must be a file, not a directory
				throw new InvalidBotConfigurationException("The specified file path leads to a directory, not a file.",
						EventTypes.USER_TRACKING_EVENT);
			}
		}
		if (!trackingFile.canRead() || !trackingFile.canWrite()) {
			// Can't read or write to file, die
			throw new InvalidBotConfigurationException("Cannot read or write to this location",
					EventTypes.USER_TRACKING_EVENT);
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.soa.rs.discordbot.cfg.ConfigValidator#validateConformsToSchema(com.soa.rs.discordbot.jaxb.DiscordConfiguration)
	 */
	@Override
	public boolean validateConformsToSchema(DiscordConfiguration cfg) {
		boolean retval = true;
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance("com.soa.rs.discordbot.jaxb");
			JAXBSource source = new JAXBSource(jaxbContext, cfg);

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(this.getClass().getResource("/xsd/discordConfiguration.xsd"));

			Validator validator = schema.newValidator();
			validator.validate(source);
		} catch (Exception e) {
			SoaLogging.getLogger().error("Could not validate config against schema", e);
			retval = false;
		}
		return retval;
	}

	/**
	 * Validates that a URL is actually a URL
	 * 
	 * @param url
	 *            A string to check if is a URL
	 * @return True if is a URL, false otherwise
	 */
	private boolean validateURL(String url) {
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			SoaLogging.getLogger().error("URL does not conform to URL schema");
			return false;
		}
		return true;
	}

}
