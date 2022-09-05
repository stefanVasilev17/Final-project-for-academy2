package com.example.stefan;

import com.example.stefan.service.ContractService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.*;

@SpringBootApplication
public class StefanApplication
{

  public static void main(String[] args) throws SQLException
  {
    SpringApplication.run(StefanApplication.class, args);
  }
}

