package backend.entity;

import java.util.List;

public class Event {

    private String id;
    private String name;
    private String startTime;
    private String endTime;
    private String description;
    private EventTag tag;


    private int pulseAverage;
    private List<Pulse> pulses;
    // private PulseRepository pulses;

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Event)) return false;
        return ((Event) other).getId().equals(this.getId());
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventTag getTag() {
        return tag;
    }

    public void setTag(EventTag tag) {
        this.tag = tag;
    }

    public List<Pulse> getPulses() {
        return pulses;
    }

    public void addPulse(Pulse pulse) {

        this.pulses.add(pulse);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String  endTime) {
        this.endTime = endTime;
    }

    public void saveAll(List<Pulse> p){
        pulses.addAll(p);
    }

    public void setPulses(List<Pulse> p){
        this.pulses=p;
    }

    public void setAverage() {
        int sum=0;
        for(Pulse pulse :pulses){
            sum+=pulse.getValue();
        }
        if(pulses.size() == 0){
            System.out.println("size is zero in SetAvg");
            return;
        }
        this.pulseAverage = sum /pulses.size();

    }


    public int getPulseAverage() {
        return pulseAverage;
    }

    public void setPulseAverage(int pulseAverage) {
        this.pulseAverage = pulseAverage;
    }
}