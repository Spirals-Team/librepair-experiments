package cn.edu.cqupt.erp.erpaccount;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.cqupt.erp.erpaccount.dao")
public class ErpAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpAccountApplication.class, args);
	}
}
