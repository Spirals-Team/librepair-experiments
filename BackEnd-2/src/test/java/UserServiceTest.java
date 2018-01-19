package backend.Testing;


import backend.entity.AppUser;
import backend.entity.Event;
import backend.entity.EventTag;
import backend.entity.Pulse;
import backend.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;

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
        event.setPulses(new ArrayList<Pulse>());
        return event;
    }


    @Test
    public void addEvent() {
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
    public void deleteEvent() {
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
        assertTrue(user.getEvents().size()!=N/2);

    }
    @Test
    public void getEvents() {
    }

    @Test
    public void updateTokens() {
    }
}
