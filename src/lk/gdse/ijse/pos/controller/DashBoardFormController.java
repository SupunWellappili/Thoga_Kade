package lk.gdse.ijse.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashBoardFormController {
    public AnchorPane dashBoardContext;
    public Label lblTime;
    public Label lblDate;

    public void initialize() {
        setDateAndTime();
    }

    private void setDateAndTime() {
        //setTime
        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss" );
                    lblTime.setText(LocalTime.now().format(formatter));
                }),new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void openCustomerFormOnAction(ActionEvent actionEvent) {
    }

    public void openItemFormOnAction(ActionEvent actionEvent) {
    }

    public void openOrderFormOnAction(ActionEvent actionEvent) {
    }

    public void openPlaceOrderFormOnAction(ActionEvent actionEvent) {
    }
}
