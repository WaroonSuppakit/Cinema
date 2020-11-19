package model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.objectStructure.Movie;
import model.objectStructure.Read;
import model.objectStructure.Theme;
import model.objectStructure.User;

import java.io.*;

public class MovieController {
    private Movie[] movies;
    private User user;
    private Theme[] themes = Theme.push();

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    AnchorPane wallpaper;

    void setWallpaper(){
        String filename = "/images/Team/"+ themes[user.getTheme()-1].getMovie();
        wallpaper.setStyle("-fx-background-image: url('"+filename+"')");
    }

    @FXML
    Button movieBtn1, movieBtn2, movieBtn3;


    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setWallpaper();
                try {
                    read(new Read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                movieBtn1.setStyle("-fx-background-image: url('"+movies[0].getPoster()+"')");
                movieBtn2.setStyle("-fx-background-image: url('"+movies[1].getPoster()+"')");
                movieBtn3.setStyle("-fx-background-image: url('"+movies[2].getPoster()+"')");
            }
        });
    }

    @FXML
    public void handleMovie1Action(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieProfile.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        MovieProfileController movieProfileController = loader.getController();
        movieProfileController.setMovie(movies[0]);
        movieProfileController.setUser(user);
        stage.show();
    }

    @FXML
    public void handleMovie2Action(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieProfile.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        MovieProfileController movieProfileController = loader.getController();
        movieProfileController.setMovie(movies[1]);
        movieProfileController.setUser(user);
        stage.show();
    }

    @FXML
    public void handleMovie3Action(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieProfile.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        MovieProfileController movieProfileController = loader.getController();
        movieProfileController.setMovie(movies[2]);
        movieProfileController.setUser(user);
        stage.show();
    }

    @FXML
    public void handleBtnBackAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        HomeController homeController = loader.getController();
        homeController.setUser(user);
        stage.show();
    }

    private void read(Read read) throws IOException {
        movies = read.readMovie();
    }
}

