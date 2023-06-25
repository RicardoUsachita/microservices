package microservicios.valoresImport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ValoresImportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValoresImportApplication.class, args);
	}

}
