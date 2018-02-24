package me.maxwu.genre.app;

import me.maxwu.genre.IGenreCmd;
import me.maxwu.genre.htmlUnit.HtmlUnitBase;
import me.maxwu.genre.selenide.SelenideBase;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Configuration.screenshots;
import static me.maxwu.genre.GenreTerm.isDefaultGenreList;

/**
 * Created by maxwu on 3/15/17.
 */
public class App{
    Map<Integer, Map<String, Object>> map;
    private IGenreCmd commander;
    private static final String defaultClientOpt = "HTMLUNIT";
    private static final int defaultSize = 10;

    public App() {
        this(defaultClientOpt, defaultSize, null, null);
    }

    public App(String clientOpt, int size) {
        this(clientOpt, size, null, null);
    }

    public App(String clientOpt, int size, String song, String artist){
        if (clientOpt.equals("HTMLUNIT")){
            commander = new HtmlUnitBase();
        }else if (clientOpt.equals("SELENIDE")){
            screenshots = false;
            commander = new SelenideBase();
        }

        if ((song != null) && (!song.isEmpty())){
            map = new HashMap<Integer, Map<String, Object>>(){{
                put(Integer.valueOf(1), new HashMap<String, Object>(){{
                    put("song", song);
                    put("artist", artist);
                }});
            }};
        }else {
            map = commander.getBillboardTop100Map(size);
            System.err.println("Got total " + map.size() + " songs");
        }

        map.forEach((k, v) -> v.put("genres", commander.getSongGenres(v.get("song").toString(), v.get("artist").toString())));

    }

    public Map<Integer, Map<String, Object>> getMap() {
        return map;
    }

    public static void showHelpAndExit(int status){
        System.err.println("To use Genre-Clawer as maven lib, please visit https://github.com/maxwu/Genre-Clawer for more information.\n"
            + "For supports or a python version with cache and scaling, please see contact on http://maxwu.me\n"
            + "Further information, please consider http://cv.maxwu.me as a candidate."
        );
        System.exit(status);
    }

    public static App parseCli(String[] args){
        Options options = new Options();
        options.addOption("h", false, "Show help and exit");
        options.addOption("c", true, "Sets client to HtmlUnit(default), Selenide or Jsoup");
        options.addOption("n", true, "Size of song list from Billboard Top100");
        options.addOption("s", true, "Song name");
        options.addOption("a", true, "Artist name if song is specified");
        CommandLineParser parser = new DefaultParser();

        String clientOpt = defaultClientOpt;
        int size = defaultSize;
        String song = null;
        String artist = null;

        try {
            CommandLine cli = parser.parse(options, args);
            if(cli.hasOption("h")) {
                showHelpAndExit(0);
            }
            if(cli.hasOption("c")) {
                clientOpt = cli.getOptionValue("c").toUpperCase();
            }
            if(cli.hasOption("n")) {
                size = Integer.valueOf(cli.getOptionValue("n")).intValue();
            }
            if(cli.hasOption("s")) {
                song = cli.getOptionValue("s");
            }
            if(cli.hasOption("a")) {
                artist = cli.getOptionValue("a");
            }
        }catch (Exception e){
            e.printStackTrace();
            showHelpAndExit(-1);
        }

        return new App(clientOpt, size, song, artist);
    }

    public static void main(String[] args){
        Map<Integer, Map<String, Object>> map = parseCli(args).map;
        System.out.println(new Yaml().dump(map));
        if (!map.isEmpty()) {
            System.err.printf("Success rate = %.2f%%\n",
                    map.values().stream()
                            .filter(v -> !isDefaultGenreList((List<String>)v.get("genres")))
                            .count() * 100.0f / map.size()
            );
        }
    }
}
