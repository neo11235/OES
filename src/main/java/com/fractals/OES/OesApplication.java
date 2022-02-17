package com.fractals.OES;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OesApplication implements CommandLineRunner {
	boolean createNewDatabase=true;
	public static void main(String[] args) {

		SpringApplication.run(OesApplication.class, args);
	}
	@Override
	public void run(String... args)throws Exception
	{
		if(createNewDatabase)
		{
			CreateDatabase.create();
		}
	}
}
