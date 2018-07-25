package com.yxy.chukonu.java.fx;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btnHello = new Button("Hello");
        btnHello.setOnAction(event->{
            btnHello.setText("Hello World, I am JavaFX!");
        });

        BorderPane pane = new BorderPane();
        pane.setCenter(btnHello);

        Scene scene = new Scene(pane, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}