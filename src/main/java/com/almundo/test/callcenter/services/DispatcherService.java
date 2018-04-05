package com.almundo.test.callcenter.services;

import com.almundo.test.callcenter.components.CallCenterThread;
import com.almundo.test.callcenter.entities.Call;
import com.almundo.test.callcenter.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class DispatcherService {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherService.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CallService callService;

    @Autowired
    private EmployeeService employeeService;

    public List<Call> dispatchNewCalls(int numberOfCalls) {
        List<Call> calls = new LinkedList<>();

        for (int i = 0; i < numberOfCalls; i++) {
            Call call = dispatchNewCall();
            if (call != null) {
                calls.add(call);
            }
        }

        return calls;
    }

    public Call dispatchNewCall() {
        Call call = new Call();
        call = callService.save(call);

        return dispatchCall(call);
    }

    public Call dispatchCall(Call call) {
        if (call != null && call.getFinishedDate() == null) {
            logger.info("Sending call to be answering");
            CallCenterThread thread = applicationContext.getBean(CallCenterThread.class);
            ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) taskExecutor;
            ThreadPoolExecutor threadPoolExecutor = threadPoolTaskExecutor.getThreadPoolExecutor();

            List<Employee> employees = employeeService.getAllThatCanAttendACall();

            if (!employees.isEmpty()) {
                try {
                    thread.setCall(call);
                    taskExecutor.execute(thread);
                } catch (TaskRejectedException e) {
                    logger.error("Executor [" + threadPoolExecutor + "] did not accept task. Queue limit was reached. The call " + call.getId() + " will be answered later");
                }
            } else {
                logger.info("All employees are busy. The call " + call.getId() + " will be answered later");
            }

            logger.info("Thread Pool active count: " + threadPoolExecutor.getActiveCount());
        } else if (call.getFinishedDate() != null) {
            logger.error("The call has already been answered");
        } else {
            logger.error("The call to be dispatched is null");
        }

        return call;
    }
}