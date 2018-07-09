/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * GNU General Public License as published by
 * License, or
 * (at your option) any later version.
 *
 * hope that it will be useful,
 * implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package coaching.jdbc;

import java.sql.SQLException;

/**
 * A dynamic data access object.
 */
public final class DynamicDao extends JdbcBase {

    /**
     * The Constructor.
     */
    public DynamicDao() {
        super();
    }

    /**
     * Process.
     */
    public void process() {
        try {
            super.query();
        } catch (final SQLException e) {
            log.error(e.toString());
        }
    }
}
