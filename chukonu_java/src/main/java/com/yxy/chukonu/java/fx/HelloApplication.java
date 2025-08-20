/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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