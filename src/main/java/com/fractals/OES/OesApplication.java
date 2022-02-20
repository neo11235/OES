package com.fractals.OES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OesApplication implements CommandLineRunner {
	boolean createNewDatabase=false;
	public static void main(String[] args) {

		SpringApplication.run(OesApplication.class, args);
	}
	@Autowired
	CreateDatabase createDatabase;
	@Override
	public void run(String... args)throws Exception
	{
		if(createNewDatabase)
		{
			createDatabase.create();
		}
	}
}
