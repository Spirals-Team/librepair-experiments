package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.business.CalendarManagerService;
import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import edu.itu.cavabunga.core.entity.property.PropertyValueType;
import edu.itu.cavabunga.core.service.IcalService;
import edu.itu.cavabunga.core.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.geom.AreaOp;

@RestController
@RequestMapping(path="/seeddb")
public class SeederController {
    @Autowired
    private CalendarManagerService calendarManagerService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private IcalService icalService;




    @GetMapping
    public String seedDataBase(){
        calendarManagerService.addParticipant(participantService.createParticipant("testuser", ParticipantType.User));
        calendarManagerService.addParticipant(participantService.createParticipant("testgroup", ParticipantType.Group));


        Component calendar = icalService.createComponent(ComponentType.Calendar);
            Property prodid = icalService.createProperty(PropertyType.Prodid);
                prodid.setValue("-//Test Inc//Cavabunga Calendar//");
            Property version = icalService.createProperty(PropertyType.Version);
                version.setValue("2.0");
            Property calscale = icalService.createProperty(PropertyType.Calscale);
                calscale.setValue("GREGORIAN");
            Property method = icalService.createProperty(PropertyType.Method);
                method.setValue("PUBLISH");
        calendar.addProperty(prodid);
        calendar.addProperty(version);
        calendar.addProperty(calscale);
        calendar.addProperty(method);
        calendar.setOwner(calendarManagerService.getParticipantByUserName("testuser"));


        Component timezone = icalService.createComponent(ComponentType.Timezone);
            Property tzid = icalService.createProperty(PropertyType.Tzid);
                tzid.setValue("Europe/Istanbul");
            timezone.addProperty(tzid);
                Component standard = icalService.createComponent(ComponentType.Standard);
                    Property tzoffsetfrom = icalService.createProperty(PropertyType.Tzoffsetfrom);
                        tzoffsetfrom.setValue("+0300");
                    Property tzoffsetto = icalService.createProperty(PropertyType.Tzoffsetto);
                        tzoffsetto.setValue("+0300");
                    Property tzname = icalService.createProperty(PropertyType.Tzname);
                        tzname.setValue("+03");
                    standard.addProperty(tzoffsetfrom);
                    standard.addProperty(tzoffsetto);
                    standard.addProperty(tzname);
            timezone.addComponent(standard);
        calendar.addComponent(timezone);

        Component event = icalService.createComponent(ComponentType.Event);
            Property dtstart = icalService.createProperty(PropertyType.Dtstart);
                dtstart.setValue("20160423T170000Z");
            Property dtend = icalService.createProperty(PropertyType.Dtend);
                dtend.setValue("20160423T180000Z");
            Property dtstamp = icalService.createProperty(PropertyType.Dtstamp);
                dtstamp.setValue("20180520T181535Z");
            Property organizer = icalService.createProperty(PropertyType.Organizer);
                organizer.setValue("mailto:unknownorganizer@calendar.cavabunga.com");
                    Parameter cn = icalService.createParameter(ParameterType.Cn);
                        cn.setValue("unknownorganizer@calendar.google.com");
                organizer.addParameter(cn);
            Property uid = icalService.createProperty(PropertyType.Uid);
                uid.setValue("7kukuqrfedlm2f9t0vr42q2qc8cm9l3o7vn9g00q3j3s5mhdo2ovuahsd9hf54qk3j60");
            Property attendee = icalService.createProperty(PropertyType.Attendee);
                attendee.setValue("mailto:dgkncelik@gmail.com");
                Parameter cutype = icalService.createParameter(ParameterType.Cutype);
                    cutype.setValue("INDIVIDUAL");
                Parameter role = icalService.createParameter(ParameterType.Role);
                    role.setValue("REQ-PARTICIPANT");
                Parameter partstat = icalService.createParameter(ParameterType.Partstat);
                    partstat.setValue("ACCEPTED");
                attendee.addParameter(cutype);
                attendee.addParameter(role);
                attendee.addParameter(partstat);
            Property classs = icalService.createProperty(PropertyType.Class);
                classs.setValue("PRIVATE");
            Property created = icalService.createProperty(PropertyType.Created);
                created.setValue("20160111T065955Z");
            Property description = icalService.createProperty(PropertyType.Description);
                description.setValue("---description_here---");
            Property lastmod = icalService.createProperty(PropertyType.Lastmod);
                lastmod.setValue("20160112T053844Z");
            Property seq = icalService.createProperty(PropertyType.Seq);
                seq.setValue("0");
            event.addProperty(dtstart);
            event.addProperty(dtend);
            event.addProperty(dtstamp);
            event.addProperty(organizer);
            event.addProperty(uid);
            event.addProperty(attendee);
            event.addProperty(classs);
            event.addProperty(created);
            event.addProperty(description);
            event.addProperty(lastmod);
            event.addProperty(seq);
            calendar.addComponent(event);

            icalService.saveComponent(calendar);

            return "ok";
    }
}
