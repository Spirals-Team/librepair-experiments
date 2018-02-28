package fr.iut.nantes.quizomdb.controler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.iut.nantes.quizomdb.entite.Quizz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @since 1.0
 */
public class ControlerOMDB {
    private HashMap<String, Quizz> actualsQuizz;

    public ControlerOMDB() {
        this.actualsQuizz = new HashMap<>();
    }

    /**
     * Change the currents question/answers of the user
     *
     * @param login of the user
     * @return the new question
     */
    public String generateQuestion(String login) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String poster = "", question, answers, movie;
        // get a random movie
        movie = this.randomMovie();
        JsonObject jobj = gson.fromJson(movie, JsonObject.class);
        // get a random question/response
        int id = (int) (Math.random() * 1);
        switch (id) {
            case 1:
                question = jobj.get("Plot").toString() +
                        "\n What is the title of this movie ?";
                answers = jobj.get("Title").toString();
                break;
            default:
                question = "What is the year of released of this movie ?";
                poster = jobj.get("Poster").toString();
                answers = jobj.get("Year").toString();
        }
        // Register the changes
        final Map<String, String> valeurs = new HashMap<String, String>();
        valeurs.put("question", question);
        valeurs.put("poster", poster);

        this.addQuizz(login, gson.toJson(valeurs), answers);
        return gson.toJson(valeurs);
    }

    /**
     * add a quizz in the local memory
     *
     * @param login
     * @param question
     * @param answers
     */
    public void addQuizz(String login, String question, String answers) {
        this.actualsQuizz.put(login, new Quizz(question, answers));
    }

    /**
     * delete a quizz from the local memory after register it in the database
     *
     * @param login
     */
    public void disconnect(String login) {
        this.actualsQuizz.remove(login);
    }


    /**
     * @param login of the user
     * @return the actual question of the user
     */
    public String getQuestion(String login) {
        Quizz quizz = this.actualsQuizz.get(login);
        if (quizz != null) return quizz.getQuestion();
        return null;
    }

    /**
     * @param login of the user
     * @return the actual answers
     */
    public String getAnswers(String login) {
        Quizz quizz = this.actualsQuizz.get(login);
        if (quizz != null) return quizz.getAnswers();
        return null;
    }

    /**
     * @return a random movie in json
     */
    private String randomMovie() {
        String id = Integer.toString((int) (Math.random() * (900000)) + 1);
        while (id.length() < 6) {
            id = "0" + id;
        }
        id = "tt0" + id;
        String film = "";
        try {
            film = getFromOmdb(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return film;
    }

    /**
     * do a HTTP GET
     *
     * @param id the id of movie to get
     * @return a movie in format json
     * @throws Exception
     */
    private String getFromOmdb(String id) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://www.omdbapi.com/?i=" + id + "&apikey=1666e2ee");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }


}
