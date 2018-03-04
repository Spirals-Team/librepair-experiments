package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.controller.wrapper.ParticipantPropertyResponse;
import edu.itu.cavabunga.core.entity.Participant;
import edu.itu.cavabunga.core.entity.ParticipantProperty;
import edu.itu.cavabunga.exception.ParticipantNotFound;
import edu.itu.cavabunga.exception.ParticipantPropertyNotFound;
import edu.itu.cavabunga.core.services.ParticipantPropertyService;
import edu.itu.cavabunga.core.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/participantprop")
public class ParticipantPropertyController {
    @Autowired
    private ParticipantPropertyService participantPropertyService;

    @Autowired
    private ParticipantService participantService;

    @GetMapping("/{participantId}")
    @ResponseStatus(HttpStatus.OK)
    public ParticipantPropertyResponse getParticipantProperty(@PathVariable(value = "participantId") Long id){
        Participant checkParticipant = participantService.getParticipantById(id);
        if(checkParticipant == null){
            throw new ParticipantNotFound(id + "ile eslestirilen bir kullanici bulunamdi");
        }

        ParticipantProperty checkParticipantProperty = participantPropertyService.getParticipantPropertyByParticipant(checkParticipant);
        if(checkParticipantProperty == null){
            throw new ParticipantPropertyNotFound(id + "ile eslestirilmis kullanici icin bir ozellik bulunamadi");
        }

        List<ParticipantProperty> result = new ArrayList<ParticipantProperty>();
        result.add(checkParticipantProperty);
        return new ParticipantPropertyResponse(0,null,result);
    }

    @PostMapping("/{participantId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantPropertyResponse saveParticipantProperty(@PathVariable(value = "participantId") Long id, @RequestBody ParticipantProperty participantProperty){
        Participant checkParticipant = participantService.getParticipantById(id);
        if(checkParticipant == null){
            throw new ParticipantNotFound(id + "ile eslestirilmis kullanici icin bir ozellik bulunamadi");
        }

        participantPropertyService.saveParticipantProperty(participantProperty);
        return new ParticipantPropertyResponse(0,"basari ile kaydedildi",null);
    }

    @DeleteMapping("/{participantId}")
    @ResponseStatus(HttpStatus.OK)
    public ParticipantPropertyResponse deleteParticipantProperty(@PathVariable(value = "participantId") Long id){
        Participant checkParticipant = participantService.getParticipantById(id);
        if(checkParticipant == null){
            throw new ParticipantNotFound(id + "ile eslestirilmis kullanici icin bir ozellik bulunamadi");
        }

        ParticipantProperty checkParticipantProperty = participantPropertyService.getParticipantPropertyByParticipant(checkParticipant);
        if(checkParticipantProperty == null){
            throw new ParticipantPropertyNotFound(id + "ile eslestirilmis kullanici icin bir ozellik bulunamadi");
        }

        participantPropertyService.deleteParticipantProperty(checkParticipantProperty);
        return new ParticipantPropertyResponse(0,"basari ile silindi", null);
    }

}
