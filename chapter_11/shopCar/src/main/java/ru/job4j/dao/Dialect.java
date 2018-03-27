package ru.job4j.dao;

import org.hibernate.dialect.HSQLDialect;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 25.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Dialect extends HSQLDialect {
    @Override
    public String getDropSequenceString(String sequenceName) {
        // Adding the "if exists" clause to avoid warnings
        return "drop sequence if exists " + sequenceName;
    }

    @Override
    public boolean dropConstraints() {
        // We don't need to drop constraints before dropping tables, that just
        // leads to error messages about missing tables when we don't have a
        // schema in the database
        return false;
    }
}
