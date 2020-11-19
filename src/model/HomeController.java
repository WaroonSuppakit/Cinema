package model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.objectStructure.Theme;
import model.objectStructure.User;

import java.io.*;

public class HomeController {
    private User user;
    private Theme[] themes = Theme.push();

    @FXML
    Label labelName;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    AnchorPane wallpaper;

    @FXML
    Button btnProfile,btnMovie,btnSetting;

    void setTeams(){
        String filename = "/images/Team/"+ themes[user.getTheme()-1].getHome();
        wallpaper.setStyle("-fx-background-image: url('"+filename+"')");
        filename = "/images/Team/"+ themes[user.getTheme()-1].getButtonProfile();
        btnProfile.setStyle("-fx-background-image: url('"+filename+"')");
        filename = "/images/Team/"+ themes[user.getTheme()-1].getButtonMovie();
        btnMovie.setStyle("-fx-background-image: url('"+filename+"')");
        filename = "/images/Team/"+ themes[user.getTheme()-1].getButtonSetting();
        btnSetting.setStyle("-fx-background-image: url('"+filename+"')");

    }

    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setTeams();
                labelName.setText(user.getName()+"      "+user.getSurname());
            }
        });
    }

    @FXML
    public void handleBtnCinemaAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movie.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        MovieController movieController = loader.getController();
        movieController.setUser(user);
        stage.show();
    }

    @FXML
    public void handleBtnProfileAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        ProfileController profileController = loader.getController();
        profileController.setUser(user);
        stage.show();
    }

    @FXML
    public void handleBtnSettingAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("setting.fxml"));
        stage.setScene(new Scene(loader.load(), 950, 500));
        SettingController settingController = loader.getController();
        settingController.setUser(user);
        stage.show();
    }

    @FXML
    public void handleBtnLogout(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        stage.show();
    }

}
