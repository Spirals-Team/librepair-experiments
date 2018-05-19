/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.rest.ExpandableStringEnum;

import java.util.Collection;

/**
 * The name of the configured Service Level Objective of a "Premium" Azure SQL Database.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_7_0)
public class SqlDatabasePremiumServiceObjective extends ExpandableStringEnum<SqlDatabasePremiumServiceObjective> {
    /** Static value P1 for ServiceObjectiveName. */
    public static final SqlDatabasePremiumServiceObjective P1 = fromString("P1");

    /** Static value P2 for ServiceObjectiveName. */
    public static final SqlDatabasePremiumServiceObjective P2 = fromString("P2");

    /** Static value P3 for ServiceObjectiveName. */
    public static final SqlDatabasePremiumServiceObjective P3 = fromString("P3");

    /** Static value P4 for ServiceObjectiveName. */
    public static final SqlDatabasePremiumServiceObjective P4 = fromString("P4");

    /** Static value P6 for ServiceObjectiveName. */
    public static final SqlDatabasePremiumServiceObjective P6 = fromString("P6");

    /** Static value P11 for ServiceObjectiveName. */
    public static final SqlDatabasePremiumServiceObjective P11 = fromString("P11");

    /** Static value P15 for ServiceObjectiveName. */
    public static final SqlDatabasePremiumServiceObjective P15 = fromString("P15");

    /**
     * Creates or finds a ServiceObjectiveName from its string representation.
     * @param name a name to look for
     * @return the corresponding ServiceObjectiveName
     */
    public static SqlDatabasePremiumServiceObjective fromString(String name) {
        return fromString(name, SqlDatabasePremiumServiceObjective.class);
    }

    /**
     * @return known ServiceObjectiveName values
     */
    public static Collection<SqlDatabasePremiumServiceObjective> values() {
        return values(SqlDatabasePremiumServiceObjective.class);
    }
}
