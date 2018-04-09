package io.recruiter.application.MOS.services;

import io.recruiter.application.common.database.model.JobPosition;
import io.recruiter.application.common.database.repositories.JobPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static io.recruiter.application.common.ErrorMessages.JOB_POSITION_DOES_NOT_EXIST;


/**
 * Service with operations on JobPositions.
 */
@Service
public class JobPositionService {

    @Autowired
    private JobPositionRepository jobPositionRepository;

    /**
     *  Finds JobPosition for id
     * @param id - given id
     * @return
     */
    public Optional<JobPosition> findById(String id) {
        return jobPositionRepository.findById(id);
    }

    /**
     *  Finds all JobPositions
     * @return list of JobPositions
     */
    public List<JobPosition> findAll() {
        return jobPositionRepository.findAll();
    }

    /**
     *  Creates new JobPosition regarding given id
     * @param newJobPosition - new JobPosition
     */
    public void createJobPosition(JobPosition newJobPosition) {
        removeIllegalData(newJobPosition);
        jobPositionRepository.insert(newJobPosition);
    }

    private void removeIllegalData(JobPosition newJobPosition) {
        newJobPosition.setName(null);
    }

    /**
     *  Modify existing JobPosition
     *  @throws  IllegalArgumentException if JobPosition does not exist
     * @param newJobPosition - object to update
     */
    public void modifyJobPosition(JobPosition newJobPosition) {
       if(jobPositionRepository.existsById(newJobPosition.getName())){
           jobPositionRepository.save(newJobPosition);
       }else{
           throw new IllegalArgumentException(JOB_POSITION_DOES_NOT_EXIST);
       }
    }

    /**
     * Changes activation state of JobPosition.
     * @param id - object id
     * @param activation - activation flag
     */
    public void activateJobPosition(String id, Boolean activation) {
        Optional<JobPosition> jobPositionOptional = jobPositionRepository.findById(id);
        if(jobPositionOptional.isPresent()){
            JobPosition jobPosition = jobPositionOptional.get();
            jobPosition.setActive(activation);
            jobPositionRepository.save(jobPosition);
        } else{
            throw new IllegalArgumentException(JOB_POSITION_DOES_NOT_EXIST);
        }
    }
}
