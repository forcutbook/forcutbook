package konkuk.forcutbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ForcutbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForcutbookApplication.class, args);
	}

}
