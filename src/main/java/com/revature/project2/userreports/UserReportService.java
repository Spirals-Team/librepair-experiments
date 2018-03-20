package com.revature.project2.userreports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserReportService {
  private final UserReportRepository userReportRepository;

  @Autowired
  public UserReportService(UserReportRepository userReportRepository) {
    this.userReportRepository = userReportRepository;
  }

  public Optional<UserReport> findByUserReportId(int id) {
    return userReportRepository.findById(id);
  }

  public Iterable<UserReport> findAllUserReports() {
    return userReportRepository.findAll();
  }
}
