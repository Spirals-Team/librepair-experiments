package com.dangdang.ddframe.job.lite.internal.instance;

import com.dangdang.ddframe.job.lite.api.strategy.JobInstance;
import com.dangdang.ddframe.job.lite.internal.schedule.JobRegistry;
import com.dangdang.ddframe.job.lite.internal.schedule.JobScheduleController;
import com.dangdang.ddframe.job.lite.internal.storage.JobNodeStorage;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.unitils.util.ReflectionUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public final class InstanceTriggerListenerManagerTest {
    
    @Mock
    private JobNodeStorage jobNodeStorage;
    
    @Mock
    private InstanceService instanceService;
    
    @Mock
    private JobScheduleController jobScheduleController;
    
    private InstanceTriggerListenerManager instanceTriggerListenerManager;
    
    @Before
    public void setUp() throws NoSuchFieldException {
        JobRegistry.getInstance().addJobInstance("test_job", new JobInstance("127.0.0.1@-@0"));
        instanceTriggerListenerManager = new InstanceTriggerListenerManager(null, "test_job");
        MockitoAnnotations.initMocks(this);
        ReflectionUtils.setFieldValue(instanceTriggerListenerManager, "instanceService", instanceService);
        ReflectionUtils.setFieldValue(instanceTriggerListenerManager, instanceTriggerListenerManager.getClass().getSuperclass().getDeclaredField("jobNodeStorage"), jobNodeStorage);
    }
    
    @Test
    public void assertStart() {
        instanceTriggerListenerManager.start();
        verify(jobNodeStorage).addDataListener(Matchers.<TreeCacheListener>any());
    }
    
    @Test
    public void assertNotTriggerWhenIsNotTriggerOperation() {
        instanceTriggerListenerManager.new JobTriggerStatusJobListener().dataChanged("/test_job/instances/127.0.0.1@-@0", Type.NODE_UPDATED, "");
        verify(instanceService, times(0)).clearTriggerFlag();
    }
    
    @Test
    public void assertNotTriggerWhenIsNotLocalInstancePath() {
        instanceTriggerListenerManager.new JobTriggerStatusJobListener().dataChanged("/test_job/instances/127.0.0.2@-@0", Type.NODE_UPDATED, InstanceOperation.TRIGGER.name());
        verify(instanceService, times(0)).clearTriggerFlag();
    }
    
    @Test
    public void assertNotTriggerWhenIsNotUpdate() {
        instanceTriggerListenerManager.new JobTriggerStatusJobListener().dataChanged("/test_job/instances/127.0.0.1@-@0", Type.NODE_ADDED, InstanceOperation.TRIGGER.name());
        verify(instanceService, times(0)).clearTriggerFlag();
    }
    
    @Test
    public void assertTriggerWhenJobScheduleControllerIsNull() {
        instanceTriggerListenerManager.new JobTriggerStatusJobListener().dataChanged("/test_job/instances/127.0.0.1@-@0", Type.NODE_UPDATED, InstanceOperation.TRIGGER.name());
        verify(instanceService).clearTriggerFlag();
        verify(jobScheduleController, times(0)).triggerJob();
    }
    
    @Test
    public void assertTriggerWhenJobIsRunning() {
        JobRegistry.getInstance().addJobScheduleController("test_job", jobScheduleController);
        JobRegistry.getInstance().setJobRunning("test_job", true);
        instanceTriggerListenerManager.new JobTriggerStatusJobListener().dataChanged("/test_job/instances/127.0.0.1@-@0", Type.NODE_UPDATED, InstanceOperation.TRIGGER.name());
        verify(instanceService).clearTriggerFlag();
        verify(jobScheduleController, times(0)).triggerJob();
        JobRegistry.getInstance().setJobRunning("test_job", false);
    }
    
    @Test
    public void assertTriggerWhenJobIsNotRunning() {
        JobRegistry.getInstance().addJobScheduleController("test_job", jobScheduleController);
        instanceTriggerListenerManager.new JobTriggerStatusJobListener().dataChanged("/test_job/instances/127.0.0.1@-@0", Type.NODE_UPDATED, InstanceOperation.TRIGGER.name());
        verify(instanceService).clearTriggerFlag();
        verify(jobScheduleController).triggerJob();
    }
}
