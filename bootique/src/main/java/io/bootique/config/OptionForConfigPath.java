package io.bootique.config;

import io.bootique.meta.application.OptionMetadata;
import io.bootique.meta.application.OptionValueCardinality;

/**
 * CLI option, associating with a configuration path.
 *
 * @since 0.27
 */
public class OptionForConfigPath extends OptionMetadata {
    private String configPath;
    private String defaultValue;

    /**
     * Builds a new CLI option, associating it with a config path. The option runtime value is assigned to the
     * configuration property denoted by the path. Default value provided here will be used if the option is present,
     * but no value is specified on the command line.
     *
     * @param name         the name of the new CLI option.
     * @param configPath   a dot-separated "path" that navigates configuration tree to the desired property. E.g.
     *                     "jdbc.myds.password".
     * @param defaultValue default value used if the option is present,
     *                     but no value is specified on the command line.
     * @return the option builder
     */
    public static Builder<OptionForConfigPath> builder(String name, String configPath, String defaultValue) {
        return new OptionForConfigPathBuilder()
                .name(name)
                .configPath(configPath)
                .defaultValue(defaultValue)
                .valueOptional();
    }

    public static Builder<OptionForConfigPath> builder(String name, String configPath) {
        return new OptionForConfigPathBuilder()
                .name(name)
                .configPath(configPath)
                .valueRequired();
    }

    /**
     * @return a dot-separated "path" that navigates configuration tree to the property associated with this
     * option. E.g. "jdbc.myds.password".
     */
    public String getConfigPath() {
        return configPath;
    }

    /**
     * @return the default value for the option. I.e. the value that will be used if the option is provided on
     * command line without an explicit value.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    public static class OptionForConfigPathBuilder extends OptionMetadata.Builder<OptionForConfigPath> {

        protected OptionForConfigPathBuilder() {
            this.option = new OptionForConfigPath();
            this.option.valueCardinality = OptionValueCardinality.NONE;
        }

        public OptionForConfigPathBuilder configPath(String configPath) {
            hasText(configPath, "A dot-separated \"configuration path\" must not be empty. E.g.  \"jdbc.myds.password\"");

            this.option.configPath = configPath;
            return this;
        }

        public OptionForConfigPathBuilder defaultValue(String defaultValue) {
            this.option.defaultValue = defaultValue;
            return this;
        }
    }

}
