package com.omer.iyzico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omer.iyzico.model.Log;
import com.omer.iyzico.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;

	public Log findById(Integer Id) {
		return logRepository.findById(Id);
	}

	public List<Log> findAll() {
		return logRepository.findAll();
	}

	public Log save(Log log) {
		return logRepository.save(log);
	}

	public void delete(String processId) {
		logRepository.deleteByProcessId(processId);
	}
}
