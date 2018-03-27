package com.weaverplatform;

import com.weaverplatform.sdk.Project;
import com.weaverplatform.sdk.Weaver;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import static com.weaverplatform.CliOptions.printUsage;

/**
 *
 * Shhh... this is actually not supposed to be here.
 *
 * Created by gijs on 08/06/2017.
 */
public class Main {

  private static CliOptions options;

  public static void signIn(Weaver w, String uri, String username, String password) {
    w.setUri(uri);
    w.setUsername(username);
    w.setPassword(password);
    w.login();
  }

  public static void printProjects(Weaver w) {
    Project[] projects = w.getProjects();
    for (Project p : projects) {
      System.out.println("id: " + p.getId() + ", name: " + p.getName());
    }
  }

  public static void performOperation(Weaver w, String project, String operation) {
    w.setProject(project);
    switch(operation) {
      case "history":
        System.out.println(w.getHistory());
        break;
      case "snapshot":
        System.out.println(w.getSnapshot(options.zip()));
        break;
      case "restore":
        w.restore(System.in);
        break;
      case "project-create":
        w.createProject(project);
        break;
      case "project-ready":
        w.projectReady(project);
        break;
      case "project-wipe":
        w.wipe();
        break;
      default:
        printUsage();
    }
  }

  public static void main(String[] args) {

    options = new CliOptions(args);
    ArrayList<String> arguments = new ArrayList<>();
    for(String arg : args) {
      if(!arg.startsWith("-")) {
        arguments.add(arg);
      }
    }

    try {
      fixHttps();
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
    Weaver w = new Weaver(options);
    if(arguments.size() == 0) {
      printUsage();
    } else {
      if (arguments.size() >= 3) {
        signIn(w, arguments.get(0), arguments.get(1), arguments.get(2));
        if(arguments.size() == 3) {
          printProjects(w);
        } else if(arguments.size() == 5) {
          performOperation(w, arguments.get(3), arguments.get(4));
        } else {
          printUsage();
        }
      } else {
        printUsage();
      }
    }
  }

  public static void fixHttps() throws NoSuchAlgorithmException, KeyManagementException {
    TrustManager[] trustAllCerts = new TrustManager[] {
      new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

      }
    };

    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    // Create all-trusting host name verifier
    HostnameVerifier allHostsValid = new HostnameVerifier() {
      public boolean verify(String hostname, SSLSession session) {
        return true;
      }
    };
    // Install the all-trusting host verifier
    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    /*
     * end of the fix
     */

  }
}
