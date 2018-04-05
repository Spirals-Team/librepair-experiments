package com.almundo.test.callcenter.controllers;

import com.almundo.test.callcenter.entities.Call;
import com.almundo.test.callcenter.services.CallService;
import com.almundo.test.callcenter.services.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class DispatcherController extends BaseController {

    @Autowired
    private DispatcherService dispatcherService;

    @Autowired
    private CallService callService;

    @RequestMapping(value = "/dispatch", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Call> dispatchCall() {
        Call call = dispatcherService.dispatchNewCall();

        if (call == null) {
            return new ResponseEntity(call, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(call, HttpStatus.OK);
    }

    @RequestMapping(value = "/dispatch/call/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Call> dispatchCallById(@PathVariable(value = "id") Long id) {
        Call call = callService.get(id);
        call = dispatcherService.dispatchCall(call);

        if (call == null) {
            return new ResponseEntity(call, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(call, HttpStatus.OK);
    }

    @RequestMapping(value = "/dispatch/{numberOfCalls}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Call>> dispatchCalls(@PathVariable(value = "numberOfCalls") int numberOfCalls) {
        List<Call> calls = dispatcherService.dispatchNewCalls(numberOfCalls);

        if (calls.isEmpty()) {
            return new ResponseEntity(calls, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(calls, HttpStatus.OK);
    }
}
