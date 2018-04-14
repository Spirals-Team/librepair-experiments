package com.prussia.play.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseAccountServiceBean implements AccountService {

	private RiskAssessorService riskAssessor;

	@Autowired
	public DatabaseAccountServiceBean(RiskAssessorService riskAssessor) {
		super();
		this.riskAssessor = riskAssessor;
	}
	
	public void setRiskAssessor(RiskAssessorService riskAssessor) {
		this.riskAssessor = riskAssessor;
	}

	// ...

	public void createAcctount(String accountNo, String username) {
		riskAssessor.createAcctount(accountNo, username);
	}

}