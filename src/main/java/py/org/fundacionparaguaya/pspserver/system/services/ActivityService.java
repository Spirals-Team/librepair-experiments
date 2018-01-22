package py.org.fundacionparaguaya.pspserver.system.services;

import py.org.fundacionparaguaya.pspserver.system.dtos.ActivityDTO;

import java.util.List;

public interface ActivityService {
    ActivityDTO getActivityById(Long activityId);

    ActivityDTO addActivity(ActivityDTO activityDTO);

    List<ActivityDTO> getAllActivities();
}
