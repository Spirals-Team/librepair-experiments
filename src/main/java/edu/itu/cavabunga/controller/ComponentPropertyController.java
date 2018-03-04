package edu.itu.cavabunga.controller;

import edu.itu.cavabunga.controller.wrapper.ComponentPropertyResponse;
import edu.itu.cavabunga.core.entity.Component;
import edu.itu.cavabunga.core.entity.ComponentProperty;
import edu.itu.cavabunga.exception.ComponentPropertyNotFound;
import edu.itu.cavabunga.exception.ComponentNotFound;
import edu.itu.cavabunga.core.services.ComponentPropertyService;
import edu.itu.cavabunga.core.services.IcalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calendarprop")
public class ComponentPropertyController {
    @Autowired
    IcalService icalService;

    @Autowired
    ComponentPropertyService componentPropertyService;

    @PostMapping("/{componentId}")
    @ResponseStatus(HttpStatus.CREATED)
    ComponentPropertyResponse saveComponentProperty(@PathVariable("componentId") Long id, @RequestBody ComponentProperty componentProperty){
        Component checkComponent = icalService.getComponentById(id);
        if(checkComponent == null){
            throw new ComponentNotFound(id + "ile iliskilendirilmis bir bilesen bulunamadi");
        }

        componentProperty.setComponent(checkComponent);
        componentPropertyService.saveComponentProperty(componentProperty);
        return new ComponentPropertyResponse(0,"Bilesen ozellikleri basari ile kaydedildi", null);
    }

    @GetMapping("/{componentId}")
    @ResponseStatus(HttpStatus.OK)
    ComponentPropertyResponse getCalendarProperty(@PathVariable("componentId") Long id){
        Component checkComponent = icalService.getComponentById(id);
        if(checkComponent == null){
            throw new ComponentNotFound(id + "ile iliskilendirilmis bir bilesen bulunamadi");
        }

        ComponentProperty calendarProperties = componentPropertyService.getComponentPropertyByComponent(checkComponent);
        List<ComponentProperty> result = new ArrayList<ComponentProperty>();
        result.add(calendarProperties);
        return new ComponentPropertyResponse(0,null,result);
    }

    @DeleteMapping("/{componentId}")
    @ResponseStatus(HttpStatus.OK)
    ComponentPropertyResponse deleteCalendarProperty(@PathVariable("componentId") Long id){
        Component checkComponent = icalService.getComponentById(id);
        if(checkComponent == null){
            throw new ComponentNotFound(id + "ile iliskilendirilmis bir bilesen bulunamadi");
        }

        ComponentProperty checkProperty = componentPropertyService.getComponentPropertyByComponent(checkComponent);
        if(checkComponent == null){
            throw new ComponentPropertyNotFound(id + "ile iliskilendirilmis bir bilesen ozelligi bulunamadi");
        }

        componentPropertyService.deleteComponentProperty(checkProperty);
        return new ComponentPropertyResponse(0,"basari ile silindi",null);
    }

    @PutMapping("/{componentId}")
    @ResponseStatus(HttpStatus.OK)
    ComponentPropertyResponse updateCalendarProperty(@PathVariable("componentId") Long id){
        Component checkComponent = icalService.getComponentById(id);
        if(checkComponent == null){
            throw new ComponentNotFound(id + "ile iliskilendirilmis bir bilesen bulunamadi");
        }

        ComponentProperty checkProperty = componentPropertyService.getComponentPropertyByComponent(checkComponent);
        if(checkComponent == null){
            throw new ComponentPropertyNotFound(id + "ile iliskilendirilmis bir bilesen ozelligi bulunamadi");
        }

        componentPropertyService.saveComponentProperty(checkProperty);
        return new ComponentPropertyResponse(0,"basarı ile guncellendi",null);
    }

}
