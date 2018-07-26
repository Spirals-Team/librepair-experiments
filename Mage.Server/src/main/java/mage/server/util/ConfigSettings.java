
package mage.server.util;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import mage.server.util.config.Config;
import mage.server.util.config.GamePlugin;
import mage.server.util.config.Plugin;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public enum ConfigSettings {
    instance;
    private final Logger logger = Logger.getLogger(ConfigSettings.class);

    private Config config;

    ConfigSettings() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance("mage.server.util.config");
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            config = (Config) unmarshaller.unmarshal(new File("config/config.xml"));
        } catch (JAXBException ex) {
            logger.fatal("ConfigSettings error", ex);
        }
    }

    public String getServerAddress() {
        return config.getServer().getServerAddress();
    }

    public String getServerName() {
        return config.getServer().getServerName();
    }

    public int getPort() {
        return config.getServer().getPort().intValue();
    }

    public int getSecondaryBindPort() {
        return config.getServer().getSecondaryBindPort().intValue();
    }

    public int getLeasePeriod() {
        return config.getServer().getLeasePeriod().intValue();
    }

    public int getSocketWriteTimeout() {
        return config.getServer().getSocketWriteTimeout().intValue();
    }

    public int getMaxPoolSize() {
        return config.getServer().getMaxPoolSize().intValue();
    }

    public int getNumAcceptThreads() {
        return config.getServer().getNumAcceptThreads().intValue();
    }

    public int getBacklogSize() {
        return config.getServer().getBacklogSize().intValue();
    }

    public int getMaxGameThreads() {
        return config.getServer().getMaxGameThreads().intValue();
    }

    public int getMaxSecondsIdle() {
        return config.getServer().getMaxSecondsIdle().intValue();
    }

    public int getMinUserNameLength() {
        return config.getServer().getMinUserNameLength().intValue();
    }

    public int getMaxUserNameLength() {
        return config.getServer().getMaxUserNameLength().intValue();
    }

    public String getInvalidUserNamePattern() {
        return config.getServer().getInvalidUserNamePattern();
    }

    public int getMinPasswordLength() {
        return config.getServer().getMinPasswordLength().intValue();
    }

    public int getMaxPasswordLength() {
        return config.getServer().getMaxPasswordLength().intValue();
    }

    public String getMaxAiOpponents() {
        return config.getServer().getMaxAiOpponents();
    }

    public Boolean isSaveGameActivated() {
        return config.getServer().isSaveGameActivated();
    }

    public Boolean isAuthenticationActivated() {
        return config.getServer().isAuthenticationActivated();
    }

    public String getGoogleAccount() {
        return config.getServer().getGoogleAccount();
    }

    public String getMailgunApiKey() {
        return config.getServer().getMailgunApiKey();
    }

    public String getMailgunDomain() {
        return config.getServer().getMailgunDomain();
    }

    public String getMailSmtpHost() {
        return config.getServer().getMailSmtpHost();
    }

    public String getMailSmtpPort() {
        return config.getServer().getMailSmtpPort();
    }

    public String getMailUser() {
        return config.getServer().getMailUser();
    }

    public String getMailPassword() {
        return config.getServer().getMailPassword();
    }

    public String getMailFromAddress() {
        return config.getServer().getMailFromAddress();
    }

    public List<Plugin> getPlayerTypes() {
        return config.getPlayerTypes().getPlayerType();
    }

    public List<GamePlugin> getGameTypes() {
        return config.getGameTypes().getGameType();
    }

    public List<GamePlugin> getTournamentTypes() {
        return config.getTournamentTypes().getTournamentType();
    }

    public List<Plugin> getDraftCubes() {
        return config.getDraftCubes().getDraftCube();
    }

    public List<Plugin> getDeckTypes() {
        return config.getDeckTypes().getDeckType();
    }

}
