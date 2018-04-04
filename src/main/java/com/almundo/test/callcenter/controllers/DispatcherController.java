package com.almundo.test.callcenter.controllers;

import com.almundo.test.callcenter.entities.Call;
import com.almundo.test.callcenter.entities.Dispatcher;
import com.almundo.test.callcenter.services.CallService;
import com.almundo.test.callcenter.services.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dispatcher")
public class DispatcherController extends BaseController {

    @Autowired
    private DispatcherService dispatcherService;

    @Autowired
    private CallService callService;

    @RequestMapping(value = "/addCall", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Dispatcher> addCall() {
        Call call = new Call();
        callService.save(call);

        Dispatcher dispatcher = new Dispatcher();
        return new ResponseEntity(dispatcher, HttpStatus.OK);
    }
}
