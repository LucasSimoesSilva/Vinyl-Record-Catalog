package project.discspring;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"project.discspring.model"})
public class DiscSpringApplication {

    public static void main(String[] args) {
        Application.launch(JavafxApplication.class, args);
    }

}
