package fr.iut.nantes.quizomdb.controler;

import com.google.gson.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;


/**
 * Created by E155722N on 28/02/18.
 */
public class ControlerDatamuse {


    /**
     * Check if response has a spelling close to answers
     *
     * @param response proposed
     * @param answers  that we want
     * @return true or false
     */
    public boolean hasCloseSpelling(String response, String answers){
        if (response.equalsIgnoreCase(answers)) return true;
        String[] responseWords = response.split(" ");
        String[] responseAnswers = answers.split(" ");
        if (responseAnswers.length==1) return this.compareWithDatamuse(responseWords[0], responseAnswers[0]);
        if (responseWords.length==responseAnswers.length) return false;
        for (int i = 0; i < responseAnswers.length; i++) {
            if (!this.compareWithDatamuse(responseWords[i], responseAnswers[i])) return false;
        }
        return true;
    }

    /**
     * Check if the 2 words in parameter has a close spelling
     *
     * @param responseWord
     * @param answersWord
     * @return true of false
     */
    private boolean compareWithDatamuse(String responseWord, String answersWord){

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        try {
            JsonArray possibilities = gson.fromJson(this.getFromDatamuse(answersWord), JsonArray.class);
            for (JsonElement json : possibilities) {
                String possibility = json.getAsJsonObject().get("word").toString();
                if (possibility.equalsIgnoreCase(responseWord)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get from the api of datamuse word that have a close spelling
     *
     * @param word that we want to have the spelling words close.
     * @return array of json
     * @throws Exception
     */
    private String getFromDatamuse(String word) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://api.datamuse.com/words?sp=" + word);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        // TODO correct connection denied
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    // CODE NON FONCTIONNEL : Connexion refusée
    private String getFromDatamuse2(String word) throws Exception {
        int port = 80;

            try {
                URL u = new URL("https://api.datamuse.com/words?sp=" + word);
                if (u.getPort() != -1) port = u.getPort();
                if (!(u.getProtocol().equalsIgnoreCase("https"))) {
                    System.err.println("Sorry. I only understand http.");
                }else {

                    Socket s = new Socket(u.getHost(), port);
                    OutputStream theOutput = s.getOutputStream();
                    // no auto-flushing
                    PrintWriter pw = new PrintWriter(theOutput, false);
                    // native line endings are uncertain so add them manually
                    pw.print("GET " + u.getFile() + " HTTP/1.0\r\n");
                    pw.print("Accept: text/plain, text/html, text/*\r\n");
                    pw.print("\r\n");
                    pw.flush();
                    InputStream in = s.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);
                    BufferedReader br = new BufferedReader(isr);
                    int c;
                    while ((c = br.read()) != -1) {
                        System.out.print((char) c);
                    }
                }
            }
            catch (MalformedURLException ex) {
                System.err.println("https://api.datamuse.com/words?sp=" + word + " is not a valid URL");
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        return "coucou";
    }

}
