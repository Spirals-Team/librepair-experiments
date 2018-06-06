package cz.polankam.pcrf.trafficgenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.polankam.pcrf.trafficgenerator.client.Client;
import cz.polankam.pcrf.trafficgenerator.config.Config;
import cz.polankam.pcrf.trafficgenerator.config.ProfileItem;
import cz.polankam.pcrf.trafficgenerator.config.ProfileValidator;
import cz.polankam.pcrf.trafficgenerator.config.ScenarioItem;
import cz.polankam.pcrf.trafficgenerator.scenario.ScenarioFactory;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Engine {

    private static final Logger logger = Logger.getLogger(Engine.class);

    private final Summary summary;
    private final String[] args;
    private CommandLine cmd;
    private PrintStream summaryOut;
    private ScheduledExecutorService executor;
    private final ScenarioFactory scenarioFactory;
    private final ProfileValidator profileValidator;

    private Engine(String[] args) {
        this.args = args;
        summary = new Summary();
        summaryOut = System.out;
        scenarioFactory = new ScenarioFactory();
        profileValidator = new ProfileValidator(scenarioFactory);
    }


    private void processCmdArguments() throws ParseException {
        Options options = new Options();

        options.addOption(Option.builder("c").longOpt("config").argName("file").hasArg()
                .desc("YAML configuration file for the generator").build());
        options.addOption(new Option("h", "help", false, "Print this message"));

        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);

        if (cmd.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("traffirator", "Traffic generator for PCRF server within LTE network", options, "");
            System.exit(0);
        }

        if (!cmd.hasOption("config")) {
            throw new ParseException("Missing required option 'config'");
        }
    }

    class ChangeActionRunnable implements Runnable {

        private final Queue<ProfileItem> queue;
        private final Client client;

        ChangeActionRunnable(Queue<ProfileItem> queue, Client client) {
            this.queue = queue;
            this.client = client;
        }

        @Override
        public void run() {
            ProfileItem current = queue.poll();
            for (ScenarioItem scenario : current.getScenarios()) {
                client.controlScenarios(scenario.getType(), scenario.getCount());
            }
            summary.addChange(current);

            if (!queue.isEmpty()) {
                long nextStart = queue.peek().getStart() - current.getStart();
                executor.schedule(new ChangeActionRunnable(queue, client), nextStart, TimeUnit.MILLISECONDS);
            }
        }
    }

    private void scheduleTestProfile(Config config, Client client) {
        Queue<ProfileItem> queue = new LinkedList<>(config.getProfile());
        executor.schedule(new ChangeActionRunnable(queue, client), queue.peek().getStart(), TimeUnit.MILLISECONDS);
    }

    private Config getClientConfig() throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        String filename = cmd.getOptionValue("config");
        try (InputStream configFile = new FileInputStream(filename)) {
            Config config = mapper.readValue(configFile, Config.class);
            profileValidator.validate(config);
            summaryOut = new PrintStream(config.getSummary());
            return config;
        }
    }

    private void waitForConnections() throws Exception {
        //wait for connection to peer
        logger.info("Waiting for connection to peer...");
        Thread.sleep(5000);
        logger.info("Enough waiting, lets roll");

    }

    private void start() throws Exception {
        logger.info("****************************************");
        logger.info("* STARTING TRAFFIRATOR *****************");
        logger.info("****************************************");

        Config config = getClientConfig();
        // prepare executor service based on given thread count and pass it to the client
        executor = Executors.newScheduledThreadPool(config.getThreadCount());
        Client client = new Client(executor, scenarioFactory);
        TimeoutsLogger timeouts = new TimeoutsLogger(client, config.getTimeouts());

        // initialization
        client.init();
        timeouts.init();
        summary.setClientConfig(config);

        //
        waitForConnections();

        // start sending/receiving messages on gx and rx interfaces
        summary.setStart();
        // schedule execution of test profile
        scheduleTestProfile(config, client);

        // schedule ending of the execution from the configuration
        executor.schedule(() -> {
            client.finish();
            logger.info("End trigger activated");
        }, config.getEnd(), TimeUnit.MILLISECONDS);

        // schedule timeouts logging
        executor.scheduleAtFixedRate(timeouts::log, config.getTimeouts().getSamplingPeriod(),
                config.getTimeouts().getSamplingPeriod(), TimeUnit.MILLISECONDS);

        // wait till client is finished
        while (!client.finished()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
        }

        try {
            // do not forget to destroy allocated stacks
            client.destroy();
            logger.info("All done... Good bye!");
        } finally {
            summary.setEnd();
            summary.setStatus(client.getFinishedReason());
            summary.printSummary(summaryOut);
            summaryOut.close();
            timeouts.close();
        }
    }


    public static void main(String[] args) {
        try {
            Engine engine = new Engine(args);
            engine.processCmdArguments();
            engine.start();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            System.exit(1);
        }

        // for some reasons jdiameter keeps some threads started even after destroying stack and such... so kill it
        System.exit(0);
    }

}
