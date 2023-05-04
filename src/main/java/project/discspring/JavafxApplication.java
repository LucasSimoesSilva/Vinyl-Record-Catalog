package project.discspring;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URL;
//@EnableAutoConfiguration(exclude= HibernateJpaAutoConfiguration.class)
public class JavafxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;


    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(DiscSpringApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        applicationContext.publishEvent(new StageReadyEvent(stage));
        /*FXMLLoader fxmlLoader = new FXMLLoader(JavafxApplication.class.getResource("mainDisc.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 473, 755);
        stage.setTitle("Catálogo de Discos");
        stage.setScene(scene);
        stage.show();*/
//        Parent root = fxmlLoader.load();
//        stage.setScene(new Scene(root));
//        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage(){
            return ((Stage) getSource());
        }
    }
}
