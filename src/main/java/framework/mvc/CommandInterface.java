/**
 *  @title       CommandInterface.java
 *  @description See Command Pattern [GOF:233]
 *	Created      27-Sep-2004
 **/

package framework.mvc;

import java.util.Properties;

/**
 * Command Interface.
 */
public interface CommandInterface {

    public Properties execute(Properties parameters);
}
