package backend.service;


import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.EventTag;
import backend.entity.Pulse;
import backend.helperClasses.TwoStrings;
import backend.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static backend.entity.EventTag.Sport;
import static org.junit.Assert.assertTrue;


public class UserServiceTest {

    UserService service=new UserService();
    AppUser user= new AppUser();
    @Before
    public void setUp() throws Exception {
        user.setId("DailyPulseUser");
        user.setName("DailyPulseMe@gmail.com");
        user.setName("AnadilMuhammedMuhammedNageebRoberPelegYotam");
        user.setEvents(new ArrayList<Event>());
    }

    @After
    public void tearDown() throws Exception {
        //nothingTodoAfterTheTestIsDone
    }

    public Event CreateEvent(String id, String name , String startTime, String endTime, String description , EventTag tag){
        Event event=new Event();
        event.setId(id);
        event.setName(name);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setTag(tag);
        event.setDescription(description);
		ArrayList<Pulse> listPulses=new ArrayList<Pulse>();
		for(int i=0;i<5;i++) {
            listPulses.add(new Pulse(i + 90));
        }
		//updating the list of pulses
        event.setPulses(listPulses);
        return event;
    }


    @Test
    public void addEvent(){
        int N=100;
        //Adding Events , the space between two events is 50 {[100,150],[200,250],[300,350]....}
        for(int i = 1;i <= N;i++) {
            assertTrue(service.addEvent(user,CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100*i),  Integer.toString(100*i+50), "boring Event", Sport)));
        }
        assertTrue(user.getEvents().size()== N);

        //firstpossibility of intersection
        for(int i = 1; i <= N ;i++){
            assertTrue(!service.addEvent(user,CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100*i-1),  Integer.toString(100*i+50-1), "boring Event", Sport)));
        }

        assertTrue(user.getEvents().size()== N);

        //second  possibility of intersection
        for(int i = 1; i <= N ;i++){
            assertTrue(!service.addEvent(user,CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100*i+50-1),  Integer.toString(100*i+50+1), "boring Event", Sport)));
        }

        assertTrue(user.getEvents().size()== N);

        //3th  possibility of intersection
        for(int i = 1; i <= N ;i++){
            assertTrue(!service.addEvent(user,CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100*i-1),  Integer.toString(100*i+50+1), "boring Event", Sport)));
        }
        assertTrue(user.getEvents().size()== N);

        //4th possibility of intersection
        for(int i = 1; i <= N ;i++){
            assertTrue(!service.addEvent(user,CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100*i+1),  Integer.toString(100*i+50-1), "boring Event", Sport)));
        }
        assertTrue(user.getEvents().size()== N);



        for(int i = 1; i < N ;i++){
            assertTrue(service.addEvent(user,CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100*i+50),  Integer.toString(100*i+75), "boring Event", Sport)));
        }


        assertTrue(user.getEvents().size()== 2*N-1);
        for(int i = 1; i < N ;i++){
            assertTrue(service.addEvent(user,CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100*i+75),  Integer.toString(100*i+100), "boring Event", Sport)));
        }

        assertTrue(user.getEvents().size()== 3*N-2);
    }

    @Test
    public void deleteEvent(){
        int N = 100;
        //Adding Events , the space between two events is 50 {[100,150],[200,250],[300,350]....}
        for (int i = 1; i <= N; i++) {
            assertTrue(service.addEvent(user, CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100 * i), Integer.toString(100 * i + 50), "boring Event", Sport)));
        }
        assertTrue(user.getEvents().size() == N);

        for (int i = 1; i <= N/2; i++) {
            assertTrue(service.deleteEvent(user, Integer.toString(i*2*100)));
        }
        for (int i = 1; i <= N/2; i++) {
            assertTrue(!service.deleteEvent(user, Integer.toString(i*2*100)));
        }
        int i=1;
        for (Event event : user.getEvents()){
            assertTrue(event.getStartTime().equals(Integer.toString(i*100)));
            i+=2;
        }
        assertTrue(user.getEvents().size()==N/2);

    }
    @Test
    public void getEvents() {
		//create N events , their times [100,150] , [200,250] ..
		int N=5;
		 for (int i = 1; i <= N; i++) {
		service.addEvent(user,CreateEvent(Integer.toString(i),
                    "Event 1 for testing", Integer.toString(100*i),  Integer.toString(100*i+50), "boring Event", Sport));
		 
		 }
		 //now the user have 5 events with the same list of Pulses 
		 
		 
        TwoStrings s1 = new TwoStrings(); //all the events are in this time
        s1.setFirst(Integer.toString(100));
        s1.setSecond(Integer.toString(550));
        List result = service.getEvents(user,s1);
        assertTrue(result.size()==5);
        for(Event event: (ArrayList<Event>) result) {
            //check the events if there timing is right
            int i = 0;
            List<Pulse> pulses = event.getPulses();
            for (Pulse pulse : event.getPulses()) {

                assertTrue(pulse.getValue() == 90 + i);
                i++;

            }
        }

        TwoStrings s2 = new TwoStrings(); //all the events are in this time
        s2.setFirst(Integer.toString(100));
        s2.setSecond(Integer.toString(150));

		 //in this time interval , getEvents should return only the first event. 
		 result = service.getEvents(user,s2);
		  assertTrue(result.size()==1);
		  
		  //there is only one event so this should pass 
		  for(Event event: (ArrayList<Event>) result)
		   assertTrue(event.getStartTime().equals(Integer.toString(100)));


            TwoStrings s3 = new TwoStrings(); //all the events are in this time
            s3.setFirst(Integer.toString(105));
            s3.setSecond(Integer.toString(256));

		   //there is only the second event in this time interval 
		    result = service.getEvents(user,s3);
		  assertTrue(result.size()==1);
		  
		  //there is only one event so this should pass 
		  for(Event event : (ArrayList<Event>) result)
		   assertTrue(event.getStartTime().equals(Integer.toString(200)));



		
    }

    @Test
    public void updateTokens() {

        TwoStrings s1 = new TwoStrings(); //all the events are in this time
        s1.setFirst("thisIsAccessToken");
        s1.setSecond("thisIsRefreshToken");

		service.updateTokens(user,s1);
		//assert it is updated
		assertTrue(user.getGoogleFitAccessToken().equals("thisIsAccessToken"));
		assertTrue(user.getGoogleFitRefreshToken().equals("thisIsRefreshToken"));

        TwoStrings s2 = new TwoStrings(); //all the events are in this time
        s2.setFirst("newAccessToken");
        s2.setSecond("newRefreshToken");
		service.updateTokens(user,s2);
		
		assertTrue(user.getGoogleFitAccessToken().equals("newAccessToken"));
		assertTrue(user.getGoogleFitRefreshToken().equals("newRefreshToken"));
		
		
		
    }
}
