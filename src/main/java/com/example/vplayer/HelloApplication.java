package com.example.vplayer;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //creating objects
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        Slider volume = new Slider();
        Slider duration = new Slider();


        //inserting images representing play, pause and stop
        ImageView pause = new ImageView("/pause-button.png");
        pause.setFitHeight(30);
        pause.setFitWidth(30);
        ImageView play = new ImageView("/play.png");
        play.setFitWidth(30);
        play.setFitHeight(30);
        ImageView stop = new ImageView("/stop-button.png");
        stop.setFitHeight(30);
        stop.setFitWidth(30);

        //displaying the video
        String src = getClass().getResource("/Yemi Alade - Oh My Gosh (Official Video).mp4").toExternalForm();
        Media media = new Media(src);
        MediaPlayer player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);
        mediaView.setFitHeight(600);
        mediaView.setMediaPlayer(player);
        vBox.getChildren().addAll(mediaView,duration);
        vBox.setAlignment(Pos.CENTER);
        player.play();


        //duration
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                duration.setValue(newValue.toSeconds());
            }
        });
        duration.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(duration.getValue()));
            }
        });

        duration.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                player.seek(Duration.seconds(duration.getValue()));
            }
        });

        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total=media.getDuration();
                duration.setMax(total.toSeconds());
            }
        });

        play.setOnMouseClicked(event -> {
            player.play();
        });
        stop.setOnMouseClicked(event -> {
            player.stop();
        });

        pause.setOnMouseClicked(event -> {
            player.pause();
        });

        HBox hBox = new HBox(20,play,pause,stop,volume);

     //volume
        volume.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                volume.setValue(player.getVolume()*200);
                volume.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        player.setVolume(volume.getValue()/100);
                        volume.setMaxHeight(0);
                        volume.setMaxWidth(100);
                    }
                });

            }
        });



        String style = getClass().getResource("/style.css").toExternalForm();
        Scene scene = new Scene(borderPane, 600, 400);
        borderPane.setBottom(hBox);
        borderPane.setCenter(vBox);
        scene.getStylesheets().add(style);
        stage.setTitle("Shirley's media player");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}