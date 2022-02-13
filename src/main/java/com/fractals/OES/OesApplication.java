package com.fractals.OES;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OesApplication implements CommandLineRunner {
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	boolean createNewDatabase=true;
	public static void main(String[] args) {

		SpringApplication.run(OesApplication.class, args);
	}
	@Override
	public void run(String... args)throws Exception
	{
//		if(createNewDatabase)
//		{
			//new data
//		}

//		String sql="CREATE TABLE C##OES.PERSON2 (NID VARCHAR2(15) PRIMARY KEY,NAME VARCHAR2(50) );";
//		try {
//			jdbcTemplate.execute(sql);
//		}catch (Exception e)
//		{
//			e.printStackTrace();
//			System.out.println("Life is so painful");
//		}
	}

//	@Override
//	public void run(String... args)throws Exception
//	{
//		String sql="SELECT * FROM HR.REGIONS";
//		List<Regions> regions=jdbcTemplate.query(sql,
//				BeanPropertyRowMapper.newInstance(Regions.class));
//
//		regions.forEach(System.out::println);
//	}

}
