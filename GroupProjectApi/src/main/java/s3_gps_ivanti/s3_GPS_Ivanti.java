package s3_gps_ivanti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import s3_gps_ivanti.repository.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableMongoRepositories
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })

public class s3_GPS_Ivanti {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	ReviewRepository reviewRepository;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(s3_GPS_Ivanti.class);
		builder.headless(false).run(args);
	}
}

