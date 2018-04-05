package com.almundo.test.callcenter.controllers;

import com.almundo.test.callcenter.entities.Call;
import com.almundo.test.callcenter.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/call")
public class CallController extends BaseController {

    @Autowired
    private CallService callService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Call>> getAll() {
        List<Call> calls = callService.getAll();
        return new ResponseEntity(calls, HttpStatus.OK);
    }

    @RequestMapping(value = "/finished", method = RequestMethod.GET)
    public ResponseEntity<List<Call>> getAllFinished() {
        List<Call> calls = callService.getAllFinished();
        return new ResponseEntity(calls, HttpStatus.OK);
    }

    @RequestMapping(value = "/inprogress", method = RequestMethod.GET)
    public ResponseEntity<List<Call>> getAllInProgress() {
        List<Call> calls = callService.getAllInProgress();
        return new ResponseEntity(calls, HttpStatus.OK);
    }

    @RequestMapping(value = "/unanswered", method = RequestMethod.GET)
    public ResponseEntity<List<Call>> getAllUnanswered() {
        List<Call> calls = callService.getAllUnaswered();
        return new ResponseEntity(calls, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Call> get(@PathVariable(value = "id") Long id) {
        Call call = callService.get(id);
        return new ResponseEntity(call, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Call> create(@RequestBody Call call) {
        Call callCreated = callService.save(call);
        return new ResponseEntity(callCreated, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Call> update(@PathVariable("id") Long id, @RequestBody Call call) {
        Call callUpdated = callService.update(id, call);
        return new ResponseEntity(callUpdated, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Call> delete(@PathVariable("id") Long id) {
        Call call = callService.delete(id);
        return new ResponseEntity(call, HttpStatus.OK);
    }

}
