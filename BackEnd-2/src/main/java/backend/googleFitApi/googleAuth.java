//package backend.googleFitApi;
//import backend.entity.AppUser;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.store.DataStoreFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.fitness.Fitness;
//import com.google.api.services.fitness.model.AggregateRequest;
//import com.google.api.services.fitness.model.BucketByActivity;
//import com.google.api.services.oauth2.Oauth2;
//import com.google.api.services.oauth2.model.Tokeninfo;
//import com.google.api.services.oauth2.model.Userinfoplus;
//import com.google.api.services.fitness.Fitness;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Arrays;
//import java.util.List;
//
//public class googleAuth {
//    private static final String APPLICATION_NAME = "dailyPulse";
//
//    /**
//     * Directory to store user credentials.
//     */
//    private static final java.io.File DATA_STORE_DIR =
//            new java.io.File(System.getProperty("user.home"), ".store/oauth2_sample");
//    /**
//     * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
//     * globally shared instance across your application.
//     */
//    private static FileDataStoreFactory dataStoreFactory;
//
//    /**
//     * Global instance of the HTTP transport.
//     */
//    private static HttpTransport httpTransport;
//
//    /**
//     * Global instance of the JSON factory.
//     */
//    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//
//    /**
//     * OAuth 2.0 scopes.
//     */
//    private static final List<String> SCOPES = Arrays.asList(
//            "https://www.googleapis.com/auth/fitness.blood_pressure.read",
//            "https://www.googleapis.com/auth/fitness.body.read");
//
//    private static Oauth2 oauth2;
//    private static GoogleClientSecrets clientSecrets;
//
//    private static Credential authorize() throws Exception {
//        httpTransport = GoogleNetHttpTransport.newTrustedTransport(); // i have to change the place of those two
////        dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
//
//        // load client secrets
//        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//                new InputStreamReader(googleAuth.class.getResourceAsStream("/clientSecret.json")));
//        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
//                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
//            System.out.println("Enter Client ID and Secret from https://code.google.com/apis/console/ "
//                    + "into oauth2-cmdline-sample/src/main/resources/client_secrets.json");
//            System.exit(1);
//        }
//        // set up authorization code flow
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, JSON_FACTORY, clientSecrets, SCOPES).build();
//
//        // authorize
//        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver.Builder().setPort(8080).build()).authorize("user");
//
//    }
//
//    public static void setAccessToken(AppUser user) {
//        try {
//            // authorization
//
//
//            Credential credential = authorize();
//            httpTransport.shutdown();
//            user.setAccessToken(credential.getAccessToken());
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//
//    }
//    public static void getpulse(AppUser user) {
//
////        try {
////            Fitness newRequest =new Fitness.Builder(httpTransport, JSON_FACTORY, user.getGoogleToken()).setApplicationName(
////                    APPLICATION_NAME).build();
////            AggregateRequest tmp=new AggregateRequest();
////            tmp.setBucketByActivityType(new BucketByActivity().setActivityDataSourceId("com.google.heart_rate.bpm").setMinDurationMillis((long)8640000));
////            tmp.setStartTimeMillis(System.currentTimeMillis()-30000000);
////            tmp.setEndTimeMillis(System.currentTimeMillis());
////            System.out.println(newRequest.users().dataset().aggregate("me",tmp).execute().toPrettyString());
////
////        } catch (IOException e) {
////            System.err.println(e.getMessage());
////        } catch (Throwable t) {
////            t.printStackTrace();
////        }
//
//    }
//
//}
