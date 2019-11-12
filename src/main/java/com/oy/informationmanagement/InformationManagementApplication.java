package com.oy.informationmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.oy.informationmanagement.dao")
@SpringBootApplication
@EnableTransactionManagement
public class InformationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformationManagementApplication.class, args);
    }

}
