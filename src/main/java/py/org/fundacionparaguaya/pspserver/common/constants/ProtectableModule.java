package py.org.fundacionparaguaya.pspserver.common.constants;

/**
 * Created by rodrigovillalba on 11/23/17.
 */
public interface ProtectableModule {

    String[] getUrls();

    String[] getReadRoles();

    String[] getWriteRoles();
}
