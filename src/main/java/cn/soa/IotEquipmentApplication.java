package cn.soa;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "cn.soa",exclude = SecurityAutoConfiguration.class)
public class IotEquipmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotEquipmentApplication.class, args);
	}

}
