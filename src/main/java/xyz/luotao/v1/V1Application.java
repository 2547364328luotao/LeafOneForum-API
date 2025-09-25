package xyz.luotao.v1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("xyz.luotao.v1.mapper")
public class V1Application {
	public static void main(String[] args) {
		SpringApplication.run(V1Application.class, args);
	}

}
