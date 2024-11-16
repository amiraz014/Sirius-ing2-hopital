package episen.sirius.ing2.proto_back;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;



@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProtoBackApplication {

	public static void main(String[] args) {
	 ApplicationContext context = SpringApplication.run(ProtoBackApplication.class, args);
	
	}

}
