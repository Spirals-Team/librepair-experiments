package com.tmejs.web;

import com.tmejs.db.domain.Nauczyciel;
import com.tmejs.db.service.NauczycielManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Simple web api demo -- try implementning post method
 * 
 * Created by tp on 24.04.17.
 */
@RestController
public class NauczycielApi {

    @Autowired
    NauczycielManager nauczycielManager;



    @RequestMapping("/")
    public String index() {
        return "This is non rest, just checking if everything works.";
    }

    @RequestMapping(value = "/nauczyciel/{id}",
            method=RequestMethod.GET
           )
    @ResponseBody
    public Nauczyciel getPerson(@PathVariable("id") int id) throws SQLException {
        return nauczycielManager.getNauczyciel(id);
    }

    @RequestMapping(value = "/nauczyciele", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Nauczyciel> getPersons(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<Nauczyciel> persons = new LinkedList<Nauczyciel>();
        for (Nauczyciel p : nauczycielManager.getAllNauczyciels()) {
            if (f == null) {
                persons.add(p);
            } else if (p.Imie.contains(f) || p.Nazwisko.contains(f)) {
                persons.add(p);
            }
        }
        return persons;
    }

    @RequestMapping(value = "/nauczyciel",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Nauczyciel addPerson(@RequestBody Nauczyciel p) {
        if (nauczycielManager.addNauczyciel(p) < 1) return null;
        return p;
    }

    @RequestMapping(value = "/nauczycielDel/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer deletePerson(@PathVariable("id") int id) throws SQLException {
        if(nauczycielManager.deleteNauczyciel(nauczycielManager.getNauczyciel(id))) return 1;
        else return 0;
    }

}
