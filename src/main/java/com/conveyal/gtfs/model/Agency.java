package com.conveyal.gtfs.model;

import com.conveyal.gtfs.GTFSFeed;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

public class Agency extends Entity {

    private static final long serialVersionUID = -2825890165823575940L;
    public String agency_id;
    public String agency_name;
    public URL    agency_url;
    public String agency_timezone;
    public String agency_lang;
    public String agency_email;
    public String agency_phone;
    public URL    agency_fare_url;
    public URL    agency_branding_url;
    public String feed_id;

    @Override
    public String getId () {
        return agency_id;
    }

    /**
     * Sets the parameters for a prepared statement following the parameter order defined in
     * {@link com.conveyal.gtfs.loader.Table#AGENCY}. JDBC prepared statement parameters use a one-based index.
     */
    @Override
    public void setStatementParameters(PreparedStatement statement, boolean setDefaultId) throws SQLException {
        int oneBasedIndex = 1;
        if (!setDefaultId) statement.setInt(oneBasedIndex++, id);
        statement.setString(oneBasedIndex++, agency_id);
        statement.setString(oneBasedIndex++, agency_name);
        statement.setString(oneBasedIndex++, agency_url != null ? agency_url.toString() : null);
        statement.setString(oneBasedIndex++, agency_timezone);
        statement.setString(oneBasedIndex++, agency_lang);
        statement.setString(oneBasedIndex++, agency_phone);
        statement.setString(oneBasedIndex++, agency_branding_url != null ? agency_branding_url .toString() : null);
        statement.setString(oneBasedIndex++, agency_fare_url != null ? agency_fare_url.toString() : null);
        statement.setString(oneBasedIndex++, agency_email);
    }

    public static class Loader extends Entity.Loader<Agency> {

        public Loader(GTFSFeed feed) {
            super(feed, "agency");
        }

        @Override
        protected boolean isRequired() {
            return true;
        }

        @Override
        public void loadOneRow() throws IOException {
            Agency a = new Agency();
            a.id = row + 1; // offset line number by 1 to account for 0-based row index
            a.agency_id    = getStringField("agency_id", false); // can only be absent if there is a single agency -- requires a special validator.
            a.agency_name  = getStringField("agency_name", true);
            a.agency_url   = getUrlField("agency_url", true);
            a.agency_lang  = getStringField("agency_lang", false);
            a.agency_email = getStringField("agency_email", false);
            a.agency_phone = getStringField("agency_phone", false);
            a.agency_timezone = getStringField("agency_timezone", true);
            a.agency_fare_url = getUrlField("agency_fare_url", false);
            a.agency_branding_url = getUrlField("agency_branding_url", false);
            a.feed = feed;
            a.feed_id = feed.feedId;

            // TODO clooge due to not being able to have null keys in mapdb
            if (a.agency_id == null) a.agency_id = "NONE";

            feed.agency.put(a.agency_id, a);
        }

    }

    public static class Writer extends Entity.Writer<Agency> {
        public Writer(GTFSFeed feed) {
            super(feed, "agency");
        }

        @Override
        public void writeHeaders() throws IOException {
            writer.writeRecord(new String[] {"agency_id", "agency_name", "agency_url", "agency_lang",
                    "agency_phone", "agency_email", "agency_timezone", "agency_fare_url", "agency_branding_url"});
        }

        @Override
        public void writeOneRow(Agency a) throws IOException {
            writeStringField(a.agency_id);
            writeStringField(a.agency_name);
            writeUrlField(a.agency_url);
            writeStringField(a.agency_lang);
            writeStringField(a.agency_phone);
            writeStringField(a.agency_email);
            writeStringField(a.agency_timezone);
            writeUrlField(a.agency_fare_url);
            writeUrlField(a.agency_branding_url);
            endRecord();
        }

        @Override
        public Iterator<Agency> iterator() {
            return this.feed.agency.values().iterator();
        }
    }

}
