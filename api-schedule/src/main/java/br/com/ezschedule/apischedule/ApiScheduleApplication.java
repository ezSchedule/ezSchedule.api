package br.com.ezschedule.apischedule;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileInputStream;
import java.io.IOException;

//@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class ApiScheduleApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ApiScheduleApplication.class, args);

        FileInputStream serviceAccount = new FileInputStream("src/main/resources/ezschedule-71596-firebase-adminsdk-qjgtx-a60e063638.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
