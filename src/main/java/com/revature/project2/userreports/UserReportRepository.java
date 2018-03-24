package com.revature.project2.userreports;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportRepository extends CrudRepository<UserReport,Integer>{
}
