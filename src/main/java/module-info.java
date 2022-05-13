module com.example.vplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.vplayer to javafx.fxml;
    exports com.example.vplayer;
}