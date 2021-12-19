module flappybirds {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.demo3 to javafx.fxml;
    exports flappybirds;
}