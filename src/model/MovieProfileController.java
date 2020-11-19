package model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import model.objectStructure.Movie;
import model.objectStructure.Theme;
import model.objectStructure.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class MovieProfileController {
    private User user;
    private Movie movie;
    private Theme[] themes = Theme.push();

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @FXML
    AnchorPane wallpaper;

    void setWallpaper(){
        String filename = "/images/Team/"+ themes[user.getTheme()-1].getMovieProfile();
        wallpaper.setStyle("-fx-background-image: url('"+filename+"')");
    }

    @FXML
    MediaView mediaMovie;

    @FXML
    ImageView imageMovie;

    @FXML
    Label textData;

    @FXML
    public void initialize() throws URISyntaxException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setWallpaper();
                textData.setText(movie.getName() + "\n" + movie.getType() + "\n" + movie.getMinute() + "\n" + movie.getDescription());
                String filePosition = movie.getTrailer();
                Media media = new Media(filePosition);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaMovie.setMediaPlayer(mediaPlayer);
                try {
                    imageMovie.setImage(new Image(getClass().getResource(movie.getPoster()).toURI().toString()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    imageMovie.setImage(new Image(movie.getPoster()));
                }
            }
        });
    }

    @FXML
    public void handleBtnSelectAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieRound.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        MovieRoundController movieRoundController = loader.getController();
        movieRoundController.setMovie(movie);
        movieRoundController.setUser(user);
        closeStage();
        stage.show();
    }

    @FXML
    public void handleBtnBackAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movie.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        MovieController movieController = loader.getController();
        movieController.setUser(user);
        closeStage();
        stage.show();
    }

    private void closeStage(){
        MediaPlayer mediaPlayer = mediaMovie.getMediaPlayer();
        mediaPlayer.stop();
    }
}
