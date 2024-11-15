package episen.sirius.ing2.proto_back;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

import episen.sirius.ing2.proto_back.model.Student;

@SpringBootApplication
public class ProtoBackApplication {

	public static void main(String[] args) {
	 ApplicationContext context = SpringApplication.run(ProtoBackApplication.class, args);
	
	}

}
