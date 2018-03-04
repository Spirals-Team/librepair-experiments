package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.business.CalendarManagerService;
import edu.itu.cavabunga.controller.wrapper.ParticipantResponse;
import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.Parameter;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.Property;
import edu.itu.cavabunga.core.entity.component.ComponentType;
import edu.itu.cavabunga.core.entity.parameter.ParameterType;
import edu.itu.cavabunga.core.entity.participant.ParticipantType;
import edu.itu.cavabunga.core.entity.property.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/participant")
public class ParticipantController {
   @Autowired
   private CalendarManagerService calendarManagerService;

   @Autowired
   private ParticipantResponse participantResponse;

   @GetMapping(path = "/testcalendarcreate")
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody String test(){
       Participant celikd = calendarManagerService.createParticipant("celikd", ParticipantType.User);
       Participant sdg = calendarManagerService.createParticipant("sdg", ParticipantType.Group);
       calendarManagerService.saveParticipant(celikd);
       calendarManagerService.saveParticipant(sdg);


       Component calendar = calendarManagerService.createComponentForParticipant(ComponentType.Calendar, "celikd");
       Component event = calendarManagerService.createComponent(ComponentType.Event);
       Component alarm = calendarManagerService.createComponent(ComponentType.Alarm);

       Property uid = calendarManagerService.createProperty(PropertyType.Uid);
            uid.setValue("23734BC-AD123DEF-CC-D123");

       Property calscale = calendarManagerService.createProperty(PropertyType.Calscale);
            calscale.setValue("GREGORIAN");

       Property dtsamp = calendarManagerService.createProperty(PropertyType.Dtstamp);
            dtsamp.setValue("160620018092822 UTC+3");

       Property attach = calendarManagerService.createProperty(PropertyType.Attach);
            attach.setValue("A FILE");

       Parameter encoding = calendarManagerService.createParameter(ParameterType.Encoding);
            encoding.setValue("UTF-8");


            event.setOwner(celikd);
            alarm.setOwner(celikd);
            calendar.setOwner(celikd);

       attach.addParameter(encoding);
       event.addProperty(attach);
       event.addProperty(dtsamp);
       event.addComponent(alarm);


       calendar.addComponent(event);
       calendar.addProperty(uid);
       calendar.addProperty(calscale);



       calendarManagerService.saveComponent(calendar);


       return "ok";
   }

   @GetMapping
   @ResponseStatus(HttpStatus.OK)
   public ParticipantResponse getAllParticipants(){
       return participantResponse.createParticipantResponseForList(0,null, calendarManagerService.getAllParticipants());
   }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantResponse newParticipant(@RequestBody Participant participant){
        calendarManagerService.addParticipant(participant);
        return participantResponse.createParticipantReponseForSingle(0,"created", null);
    }

   @GetMapping("/{user_key}")
   @ResponseStatus(HttpStatus.OK)
   public ParticipantResponse getParticipant(@PathVariable(value = "user_key") String userKey){
       return participantResponse.createParticipantReponseForSingle(0,null,calendarManagerService.getParticipantByKey(userKey));
   }

   @DeleteMapping("/{user_id}")
   @ResponseStatus(HttpStatus.OK)
   public ParticipantResponse deleteParticipant(@PathVariable(value = "user_id") Long id){
       calendarManagerService.deleteParticipantById(id);
       return participantResponse.createParticipantReponseForSingle(0,"deleted", null);
   }

   @PutMapping("/{user_id}")
   @ResponseStatus(HttpStatus.OK)
   public ParticipantResponse updateParticipant(@PathVariable(value = "user_id") Long id, @RequestBody Participant participant){
       calendarManagerService.updateParticipant(id, participant);
       return participantResponse.createParticipantReponseForSingle(0,"update", null);
   }



}
