package microservicios.proveedores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProveedoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProveedoresApplication.class, args);
	}

}
