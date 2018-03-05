package com.omer.iyzico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.omer.iyzico.model.Log;

@Transactional
public interface LogRepository extends JpaRepository<Log, Integer> {

	public Log findById(Integer id);

	public void deleteByProcessId(String processId);
}