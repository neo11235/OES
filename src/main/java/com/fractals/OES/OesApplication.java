package com.fractals.OES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
public class OesApplication implements CommandLineRunner {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {

		SpringApplication.run(OesApplication.class, args);
	}
	@Override
	public void run(String... args)throws Exception
	{
		 String sql="SELECT * FROM HR.REGIONS";
		List<Regions> regions=jdbcTemplate.query(sql,
				BeanPropertyRowMapper.newInstance(Regions.class));

		regions.forEach(System.out::println);
	}

}
