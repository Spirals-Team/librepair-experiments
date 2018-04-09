package io.recruiter.application.MOS;

import io.recruiter.application.MOS.services.JobPositionService;
import io.recruiter.application.common.database.model.JobPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * JobPosition Endpoint
 */
@RestController()
@RequestMapping("job-positions")
@Secured("ROLE_MODERATOR")
public class JobPositionController {

    @Autowired
    JobPositionService jobPositionService;


    /**
     * Endpoint method for getting list of job positions.
     *
     * @return list of job positions
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<JobPosition> getAllJobPositions() {
        return jobPositionService.findAll();
    }

    /**
     * Endpoint method for getting specified job position.
     * Used before edition.
     *
     * @param id Job position id
     * @return Job position information
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getJobPositionById(@PathVariable(value = "id") String id) {
        Optional<JobPosition> jobPosition = jobPositionService.findById(id);
        if (jobPosition.isPresent()) {
            JobPosition foundJobPosition = jobPosition.get();
            return ResponseEntity.ok(foundJobPosition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint method to add new Job Position.
     * @param newJobPosition - object to add
     */
    @RequestMapping(method = RequestMethod.POST)
    public void createJobPosition(@RequestBody JobPosition newJobPosition) {
        jobPositionService.createJobPosition(newJobPosition);
    }

    /**
     * Endpoint method to modify Job Position
     * @param newJobPosition - object to add
     */
    @RequestMapping(method = RequestMethod.PUT)
    public void modifyJobPosition(@RequestBody JobPosition newJobPosition) {
        jobPositionService.modifyJobPosition(newJobPosition);
    }

    /**
     * Endpoint method for job position activation
     * @param id - job position id
     * @param activation - activation state
     */
    @RequestMapping(path = "/{id}/activation", method = RequestMethod.PUT)
    public void modifyJobPosition(@PathVariable(value = "id") String id,
                                  @RequestBody Boolean activation) {
        jobPositionService.activateJobPosition(id,activation);
    }
}
