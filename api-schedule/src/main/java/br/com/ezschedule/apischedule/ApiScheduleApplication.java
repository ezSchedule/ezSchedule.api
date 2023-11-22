package br.com.ezschedule.apischedule;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@SpringBootApplication
@EnableSwagger2
public class ApiScheduleApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ApiScheduleApplication.class, args);

        InputStream serviceAccount = new FileInputStream("ezschedule-71596-firebase-adminsdk-qjgtx-a60e063638.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }
}
