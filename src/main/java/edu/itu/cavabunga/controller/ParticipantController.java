package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.business.CalendarManagerService;
import edu.itu.cavabunga.controller.wrapper.ParticipantResponse;
import edu.itu.cavabunga.core.entity.Participant;
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
