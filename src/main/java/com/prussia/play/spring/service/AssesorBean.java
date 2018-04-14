package com.prussia.play.spring.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AssesorBean implements RiskAssessorService {

	public AssesorBean(){
		super();
	}

	@Override
	public void createAcctount(String accountNo, String username) {
		try {
			log.error("account = ", accountNo);
			log.error("username = ", username);
			
		} catch (Exception e) {
			log.error("issue happened", e);
		}
	}
}
