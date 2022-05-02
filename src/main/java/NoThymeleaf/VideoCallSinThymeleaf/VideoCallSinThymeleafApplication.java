package NoThymeleaf.VideoCallSinThymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "VideoCallSinThymeleaf.entity")
@AutoConfigurationPackage
@EnableScheduling
public class VideoCallSinThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoCallSinThymeleafApplication.class, args);
	}

}
