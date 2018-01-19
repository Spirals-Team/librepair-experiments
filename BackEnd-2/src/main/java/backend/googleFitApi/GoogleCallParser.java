package backend.googleFitApi;

import backend.entity.AppUser;
import backend.entity.Pulse;
import backend.entity.RefreshTokenExpiredException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleCallParser {
    //after calling this, re-add user to repo.
    public static boolean verifyAndRefresh(AppUser user) {
        String refreshed;
        HttpGet get = new HttpGet("https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + user.getGoogleFitAccessToken());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get);

            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 400 ) {
                System.out.println(response.getStatusLine().getStatusCode());
                System.out.println("-------1---------");
                return false;
            }
        } catch (Exception e) {
            System.out.println("-------2---------");
            return false;
        }
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }
            //System.out.println("----" + content.toString());
            if(content.toString().compareTo("{\"error\":\"invalid_token\"}") == 0 || content.toString().compareTo("{ \"error_description\": \"Invalid Value\"}") == 0) {
                refreshed = refreshToken(user);
                if(refreshed.compareTo("Refresh token expired") == 0) {
                    System.out.println("-------3---------");
                    return false;
                } else {
                    //we update the user with a new access token
                    user.setGoogleFitAccessToken(refreshed);
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("-------4---------");
            return false;
        }
        return true;
    }


    public static String refreshToken(AppUser user) {
        int ACCESS_START = 15;
        String refresh = user.getGoogleFitRefreshToken();
        String access = "error";
        HttpPost post = new HttpPost("https://www.googleapis.com/oauth2/v4/token");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        StringEntity str=null;
        try {
            str = new StringEntity("client_id=895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com&" +
                    "client_secret=FGLsX3PBtIHEypj88z7UkI6R&" +
                    "refresh_token="+refresh+"&" +
                    "grant_type=refresh_token");
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        post.setEntity(str);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(post);

            if (response.getStatusLine().getStatusCode() != 200) {
                //System.out.println(response.getStatusLine().getStatusCode());
               return "Refresh token expired";
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }
            Pattern p = Pattern.compile("\"access_token\".[^\\,]*");
            Matcher m = p.matcher(content.toString());
            m.find();
            access = m.group();
            String[] arr = access.split("\"");
            access = arr[3];
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return access;
    }
    public static List<Pulse> getPulses(AppUser user,String startTime,String endTime , String bucket) throws RefreshTokenExpiredException{
        List<Pulse> pulses=new ArrayList<>();
        String accessToken=user.getGoogleFitAccessToken();
        //// check if the access token has expired TODO
        HttpPost post=new HttpPost("https://www.googleapis.com/fitness/v1/users/me/dataset:aggregate");
        post.addHeader("Content-Type","application/json;encoding=utf-8");
        post.addHeader("Authorization" , "Bearer "+ accessToken );
        StringEntity str=null;
        try {
            str = new StringEntity("{\n" +
                    "  \"aggregateBy\": [{\n" +
                    "    \"dataTypeName\": \"com.google.heart_rate.bpm\"\n" +
                    "  }],\n" +
                    "  \"bucketByTime\": { \"durationMillis\": "+ bucket+ " },\n" +
                    "  \"startTimeMillis\": "+ startTime +",\n" +
                    "  \"endTimeMillis\": "+endTime +"\n" +
                    "}");

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        post.setEntity(str);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(post);

            if (response.getStatusLine().getStatusCode() != 200) {
                accessToken = refreshToken(user);
                if(accessToken.compareTo("Refresh token expired") == 0) {
                    throw new RefreshTokenExpiredException();
                } else {
                    user.setGoogleFitAccessToken(accessToken);
                    return getPulses( user, startTime, endTime ,  bucket);
                }
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }
            Pattern p=Pattern.compile("([0-9]+)\\.([0-9])");
            Matcher m=p.matcher(content.toString());
            int counter=0;
            while(m.find() ) {
                if(counter%3==0){
                    Pulse tmp=new Pulse((int)Double.parseDouble(m.group()));
                    pulses.add(tmp);
                }
                counter++;
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return pulses;
    }
}
