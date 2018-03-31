package ch.maurer.oop.vaadin.nfldashboard.common.business.impl;

import ch.maurer.oop.vaadin.nfldashboard.common.business.DataProvider;
import ch.maurer.oop.vaadin.nfldashboard.user.db.UserRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataProviderImpl implements DataProvider {

    @Override
    public String getDataFromUrl(String fromUrl) {
        try {
            URL url = new URL(fromUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + connection.getResponseCode());
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()
            ));

            String output;
            while ((output = bufferedReader.readLine()) != null) {
                return output;
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public UserRecord authenticate(String userName, String password) {
        UserRecord userRecord = new UserRecord();
        userRecord.setFirstName("Patrick");
        userRecord.setLastName("Maurer");
        userRecord.setRole("admin");
        String email = userRecord.getFirstName().toLowerCase() + "."
                + userRecord.getLastName().toLowerCase() + "c@gmail.com";
        userRecord.setEmail(email.replaceAll(" ", ""));
        userRecord.setLocation("Olten");
        userRecord.setBio("Quis aute iure reprehenderit in voluptate velit esse."
                + "Cras mattis iudicium purus sit amet fermentum.");
        return userRecord;
    }

}
