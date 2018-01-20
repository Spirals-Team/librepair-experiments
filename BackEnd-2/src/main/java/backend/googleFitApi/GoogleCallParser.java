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
    /*
    after calling this,re-add user to repo.
     */


	/* verifyAndRefresh verifies that the access token is still valid, if it's not refresh token is used to generate new one
	@param user that his access token will be refreshed
	@return true of the refreshing process passed ok , otherwise false
	 */
    public static boolean verifyAndRefresh(AppUser user) {
        String refreshed;
        HttpGet get = new HttpGet("https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + user.getGoogleFitAccessToken());
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = client.execute(get);

            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 400 ) { //if it's not a bad request and the respond isn't OK return false
                System.out.println(response.getStatusLine().getStatusCode());
                System.out.println("-------1---------");
                return false;
            }
        } catch (Exception e) {
            System.out.println("-------2---------");
            return false;
        }
        try {

            BufferedReader br = new BufferedReader(	 //putting the respond of google in string buffer
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }

			/* if the response is token not valid , new one should be generated */
            if(content.toString().compareTo("{\"error\":\"invalid_token\"}") == 0 || content.toString().compareTo("{ \"error_description\": \"Invalid Value\"}") == 0) {

				refreshed = refreshToken(user);  //calling  refresh token, to get new accessToken from the refresh token
                if(refreshed.compareTo("Refresh token expired") == 0) {
                    System.out.println("-------3---------");
                    return false;
                } else {
                    user.setGoogleFitAccessToken(refreshed);  // update the user access token with a new one
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("-------4---------");
            return false;
        }
        return true;
    }

	
    /*this function generates new access token from the refresh token
     @param user which his  access token will be refreshed by his refresh token
     @return new access token
     */
    public static String refreshToken(AppUser user) {
        int ACCESS_START = 15;
        String refresh = user.getGoogleFitRefreshToken();
        String access = "error";

		/*the post request should be sent to google so they will generate access token */
        HttpPost post = new HttpPost("https://www.googleapis.com/oauth2/v4/token");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        StringEntity str=null;
        try {

			/* building the request based on the way that Google defined it ,using our client id, and the refresh token */
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

            if (response.getStatusLine().getStatusCode() != 200) { //if the respond isn't OK , that means Refresh token expired

               return "Refresh token expired";
            }


            BufferedReader br = new BufferedReader( //putting the response in string
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
			
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }

			/* now for extract the accessToken from the whole response, REGEX will be used */
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



    /*this function returns list of Pulse , between startTime and endTime where
    the interval between Consecutive pulses is bucketTime
     @param user which his  Pulse data will be returned
     @param startTime which is string time of the requested period
     @param endTime which is end time of the  requested period
     @param bucket which the interval between two Consecutive pulses
     @return list of Pulse between startTime and endTime  (getting the data from GOOGLE FIT)
     */
    public static List<Pulse> getPulses(AppUser user,String startTime,String endTime , String bucket) throws RefreshTokenExpiredException{
        List<Pulse> pulses=new ArrayList<>();
        String accessToken=user.getGoogleFitAccessToken();
        //// check if the access token has expired TODO
		
		/* POST request for getting the pulses from Google Fit  */
        HttpPost post=new HttpPost("https://www.googleapis.com/fitness/v1/users/me/dataset:aggregate");
        post.addHeader("Content-Type","application/json;encoding=utf-8");
        post.addHeader("Authorization" , "Bearer "+ accessToken );
        StringEntity str=null;
        try {
			/*the interval, startTime, EndTime, should be sent based on the way that Google has defined */
            str = new StringEntity("{\n" +
                    "  \"aggregateBy\": [{\n" +
                    "    \"dataTypeName\": \"com.google.heart_rate.bpm\"\n" +
                    "  }],\n" +
                    "  \"bucketByTime\": { \"durationMillis\": "+ bucket+ " },\n" + //the interval
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

			/*if the token is not valid , we generate new one using the refresh and call the function again with the new token*/
            if (response.getStatusLine().getStatusCode() != 200) {
                accessToken = refreshToken(user);
                if(accessToken.compareTo("Refresh token expired") == 0) {
                    throw new RefreshTokenExpiredException();
                } else {
                    user.setGoogleFitAccessToken(accessToken);
                    return getPulses( user, startTime, endTime ,  bucket);
                }
            }

            BufferedReader br = new BufferedReader(  //putting the response in string
                    new InputStreamReader(
                            (response.getEntity().getContent())
                    )
            );
            StringBuilder content = new StringBuilder();
            String line;
            while (null != (line = br.readLine())) {
                content.append(line);
            }
            Pattern p=Pattern.compile("([0-9]+)\\.([0-9])"); //using regex we get the pulses and save them
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
