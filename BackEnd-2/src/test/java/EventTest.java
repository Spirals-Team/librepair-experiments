package backend.entity;

import backend.entity.Event;
import backend.entity.EventTag;
import backend.entity.Pulse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static backend.entity.EventTag.Rest;
import static backend.entity.EventTag.Sport;
import static org.junit.Assert.assertTrue;


public class EventTest {

    ArrayList<Event> events=new ArrayList<Event>();
   ArrayList<Event> eventsTesting=new ArrayList<Event>();


    public Event CreateEvent(String id, String name , String startTime, String endTime, String description , EventTag tag){
        Event event=new Event();
        event.setId(id);
        event.setName(name);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        event.setTag(tag);
        event.setDescription(description);
        return event;
    }

    @Before
    public void setUp() throws Exception {

        for(int i=0;i<100;i++){
        //   String aux
            events.add(CreateEvent(Integer.toString(i),"event number"+i,Integer.toString(i) ,Integer.toString(i + 1),"event dis"+i,Sport)) ;
           eventsTesting.add(CreateEvent(Integer.toString(i),"event number"+i, Integer.toString(i), Integer.toString(i + 1),"event dis"+i,Rest)) ;
           events.get(i).setPulses(new ArrayList<Pulse>());
            eventsTesting.get(i).setPulses(new ArrayList<Pulse>());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void equals() {
         for(int i=0;i<100;i++){
            assertTrue(events.get(i).equals(eventsTesting.get(i)));
         }
    }

    @Test
    public void getName() {

       for(int i = 0; i < 100;i++){

          assertTrue(events.get(i).getName().equals(eventsTesting.get(i).getName()));
          if(i != 99) {
              assertTrue(!(events.get(i).getName().equals(eventsTesting.get(i + 1).getName())));
          }
       }
    }

    @Test
    public void setName() {

      for(int i=0;i<100;i++){
         events.get(i).setName(Integer.toString(i+1));
      }
       for(int i=0;i<99;i++){
          assertTrue(events.get(i).getName().equals(events.get(i).getName()));
       }
       for(int i=0;i<100;i++){
          events.get(i).setName(Integer.toString(i));
       }
       for(int i=0;i<100;i++){
          assertTrue(events.get(i).getName().equals(events.get(i).getName()));
       }

    }

    @Test
    public void getDescription() {
       for(int i=0;i<100;i++){

          assertTrue(events.get(i).getDescription().equals(eventsTesting.get(i).getDescription()));
           if(i != 99) {
               assertTrue(!(events.get(i).getDescription().equals(eventsTesting.get(i + 1).getDescription()
               )));
           }
       }

    }

    @Test
    public void setDescription() {
       for(int i=0;i<100;i++){
          events.get(i).setDescription(Integer.toString(i+1));
       }
       for(int i=0;i<99;i++){
          assertTrue(events.get(i).getDescription().equals(Integer.toString(i+1)));
       }
       for(int i=0;i<100;i++){
          events.get(i).setDescription("event dis"+i);
       }
       for(int i=0;i<100;i++){
          assertTrue(events.get(i).getDescription().equals(eventsTesting.get(i).getDescription()));
       }

    }

    @Test
    public void getTag() {
       for(int i=0;i<99;i++) {
          assertTrue(events.get(i).getTag()==events.get(i+1).getTag());
       }
    }

    @Test
    public void setTag() {
       for (int i = 0; i < 100; i++) {
          events.get(i).setTag(Rest);
       }
       for (int i = 0; i < 100; i++) {
          assertTrue(events.get(i).getTag() == eventsTesting.get(i).getTag());
       }
       for (int i = 0; i < 100; i++) {
          events.get(i).setTag(Sport);
       }
       for (int i = 0; i < 100; i++) {
          assertTrue(events.get(i).getTag() == Sport);
       }
    }

    @Test
    public void getPulses() {
        for(int i=0 ;i< 100;i++){
            events.get(i).addPulse(new Pulse(i));
        }
        for(int i=0 ;i< 100;i++){
            for(Pulse pulse : events.get(i).getPulses()){
                assertTrue(pulse.getValue()==i);
            }

        }

    }

    @Test
    public void addPulse() {
        ArrayList<Pulse> pulses=new ArrayList<Pulse>();
        pulses.add(new Pulse(1));
        pulses.add(new Pulse(2));
        pulses.add(new Pulse(3));
        pulses.add(new Pulse(4));
        assertTrue(pulses.size()==4);
        pulses.add(new Pulse(4));
        assertTrue(pulses.size()==5);
        pulses.remove(new Pulse(4));
        assertTrue(pulses.size()==4);

        events.get(0).addPulse(new Pulse(1));
        events.get(0).addPulse(new Pulse(2));
        events.get(0).addPulse(new Pulse(3));
        events.get(0).addPulse(new Pulse(4));
        assertTrue(events.get(0).getPulses().equals(pulses));

        events.get(0).getPulses().remove(new Pulse(1));
        events.get(0).getPulses().remove(new Pulse(2));
        events.get(0).getPulses().remove(new Pulse(3));
        events.get(0).getPulses().remove(new Pulse(4));
        for(int i=0 ;i< 100;i++){
            events.get(i).addPulse(new Pulse(i));
        }
        for(int i=0 ;i< 100;i++){
            assertTrue(events.get(i).getPulses().contains(new Pulse(i)));

        }
        for(int i=0 ;i< 100;i++){
            assertTrue(events.get(i).getPulses().remove(new Pulse(i)));
            assertTrue(events.get(i).getPulses().size()==0);

        }


    }

    @Test
    public void getStartTime() {

        for(int i=0; i<100;i++){
            assertTrue(events.get(i).getStartTime().equals(Integer.toString(i)));
        }
    }


    @Test
    public void getEndTime() {
        for(int i=0; i<100;i++){
            assertTrue(events.get(i).getEndTime().equals(Integer.toString(i+1)));
        }
    }


    @Test
    public void saveAll() {
        ArrayList<Pulse> pulses=new ArrayList<Pulse>();
        pulses.add(new Pulse(1));
        pulses.add(new Pulse(2));
        pulses.add(new Pulse(3));
        pulses.add(new Pulse(4));
        Event event=new Event();
        event.setPulses(new ArrayList<>());
        event.saveAll(pulses);
        assertTrue(pulses.size()==event.getPulses().size());
        for(int i=0;i<4;i++){
            assertTrue(event.getPulses().get(i).getValue()==i+1);
        }

        ArrayList<Pulse> pulses1=new ArrayList<Pulse>();
        pulses1.add(new Pulse(5));
        pulses1.add(new Pulse(6));
        pulses1.add(new Pulse(7));
        pulses1.add(new Pulse(8));
        event.saveAll(pulses1);
        assertTrue(pulses1.size()+pulses.size()==event.getPulses().size());
        for(int i=0;i<8;i++){
            assertTrue(event.getPulses().get(i).getValue()==i+1);
        }
    }

    @Test
    public void setPulses() {
        ArrayList<Pulse> pulses=new ArrayList<Pulse>();
        pulses.add(new Pulse(1));
        pulses.add(new Pulse(2));
        pulses.add(new Pulse(3));
        pulses.add(new Pulse(4));
        Event event=new Event();
        event.setPulses(pulses);
        assertTrue(pulses.size()==event.getPulses().size());
        for(int i=0;i<4;i++){
            assertTrue(event.getPulses().get(i).getValue()==i+1);
        }
        pulses=new ArrayList<Pulse>();
        pulses.add(new Pulse(5));
        pulses.add(new Pulse(6));
        pulses.add(new Pulse(7));
        pulses.add(new Pulse(8));
        event.setPulses(pulses);
        assertTrue(pulses.size()==event.getPulses().size());
        for(int i=0;i<4;i++){
            assertTrue(event.getPulses().get(i).getValue()==i+5);
        }

    }

    @Test
    public void setAverage() {

        ArrayList<Pulse> pulses=new ArrayList<Pulse>();
        pulses.add(new Pulse(1));
        pulses.add(new Pulse(2));
        pulses.add(new Pulse(3));
        pulses.add(new Pulse(4));
        Event event=new Event();
        event.setPulses(pulses);
        event.setAverage();
        assertTrue(event.getPulseAverage()== 2); // 10/4=2.5 int(2.5)=2
    }

    @Test
    public void getPulseAverage() {

        Event event=new Event();
        event.setPulses(new ArrayList<Pulse>());
        assertTrue(event.getPulseAverage()==0);
        ArrayList<Pulse> pulses=new ArrayList<Pulse>();
        pulses.add(new Pulse(2));
        pulses.add(new Pulse(2));
        pulses.add(new Pulse(4));
        pulses.add(new Pulse(4));
        event.saveAll(pulses);
        event.setAverage();
        assertTrue(event.getPulseAverage()== 3 ); // 12/4



    }

    @Test
    public void setPulseAverage() {

        for(int i=0;i<100;i++){
            events.get(i).setPulseAverage(i);
        }
        for(int i=0;i<100;i++){
            assertTrue(events.get(i).getPulseAverage()!=i);
        }

    }
}
