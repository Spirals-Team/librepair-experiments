package com.almundo.test.callcenter.services;

import com.almundo.test.callcenter.entities.Call;
import com.almundo.test.callcenter.repositories.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallService {

    @Autowired
    CallRepository callRepository;

    public Call save(Call call) {
        Call callSaved = callRepository.save(call);
        return callSaved;
    }

    public Call update(Long id, Call call) {
        if (id == call.getId()) {
            call = callRepository.save(call);
        }

        return call;
    }

    public Call get(Long id) {
        Call call = callRepository.getOne(id);
        return call;
    }

    public Call delete(Long id) {
        Call toDelete = get(id);
        callRepository.delete(toDelete);

        return toDelete;
    }

    public List<Call> getAll() {
        List<Call> calls = callRepository.findAll();
        return calls;
    }

    public List<Call> getAllFinished() {
        List<Call> calls = callRepository.findAllFinished();
        return calls;
    }

    public List<Call> getAllInProgress() {
        List<Call> calls = callRepository.findAllInProgress();
        return calls;
    }

    public List<Call> getAllUnaswered() {
        List<Call> calls = callRepository.findAllUnanswered();
        return calls;
    }
}
