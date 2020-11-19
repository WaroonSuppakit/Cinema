package model;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.objectStructure.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class MovieRoundController {
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
        String filename = "/images/Team/"+ themes[user.getTheme()-1].getMovieRound();
        wallpaper.setStyle("-fx-background-image: url('"+filename+"')");
    }

    @FXML
    Button btnNor1,btnNor2,btnNor3,btn3D1,btn3D2,btn3D3,btn4D1,btn4D2,btn4D3,btnVIP1,btnVIP2,btnVIP3;

    @FXML
    ImageView imageMovie,imageSystemNor,imageSystem3D,imageSystem4D,imageSystemVIP;

    @FXML
    public void initialize() throws URISyntaxException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setWallpaper();
                try {
                    imageMovie.setImage(new Image(getClass().getResource(movie.getPoster()).toURI().toString()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    imageMovie.setImage(new Image(movie.getPoster()));
                } finally {
                    try {
                        imageSystemNor.setImage(new Image(getClass().getResource("/images/bar1.jpg").toURI().toString()));
                        imageSystem3D.setImage(new Image(getClass().getResource("/images/bar2.jpg").toURI().toString()));
                        imageSystem4D.setImage(new Image(getClass().getResource("/images/bar3.jpg").toURI().toString()));
                        imageSystemVIP.setImage(new Image(getClass().getResource("/images/bar4.jpg").toURI().toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                String round = movie.roundFormat();
                String[] rounds = round.split(">");
                for(String e : rounds){
                    String[] data = e.split("/");
                    if(data[0].equals("Normal")){
                        if(data[1].equals("10:30")){
                            btnNor1.setDisable(false);
                        }else if(data[1].equals("14:40")){
                            btnNor2.setDisable(false);
                        }else if(data[1].equals("19:30")){
                            btnNor3.setDisable(false);
                        }
                    }else if(data[0].equals("3D")){
                        if(data[1].equals("10:30")){
                            btn3D1.setDisable(false);
                        }else if(data[1].equals("14:40")){
                            btn3D2.setDisable(false);
                        }else if(data[1].equals("19:30")){
                            btn3D3.setDisable(false);
                        }
                    }else if(data[0].equals("4D")){
                        if(data[1].equals("10:30")){
                            btn4D1.setDisable(false);
                        }else if(data[1].equals("14:40")){
                            btn4D2.setDisable(false);
                        }else if(data[1].equals("19:30")){
                            btn4D3.setDisable(false);
                        }
                    }else if(data[0].equals("VIP")){
                        if(data[1].equals("10:30")){
                            btnVIP1.setDisable(false);
                        }else if(data[1].equals("14:40")){
                            btnVIP2.setDisable(false);
                        }else if(data[1].equals("19:30")){
                            btnVIP3.setDisable(false);
                        }
                    }
                }
            }
        });
    }

    @FXML
    public void handleLoadNorTheaterAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("normalTheaters.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        NormalTheatersController normalTheatersController = loader.getController();
        normalTheatersController.setUser(user);
        normalTheatersController.setMovie(movie);
        normalTheatersController.setRound(new Round("Normal",b.getText()));
        stage.show();
    }


    @FXML
    public void handleLoad3DTheaterAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("threeDTheaters.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        ThreeDTheatersController threeDTheatersController = loader.getController();
        threeDTheatersController.setUser(user);
        threeDTheatersController.setMovie(movie);
        threeDTheatersController.setRound(new Round("3D",b.getText()));
        stage.show();
    }

    @FXML
    public void handleLoad4DTheaterAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fourDTheaters.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        FourDTheatersController fourDTheatersController = loader.getController();
        fourDTheatersController.setUser(user);
        fourDTheatersController.setMovie(movie);
        fourDTheatersController.setRound(new Round("4D",b.getText()));
        stage.show();
    }

    @FXML
    public void handleLoad4DMixTheaterAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fourDMixTheaters.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        FourDMixTheatersController fourDMixTheatersController = loader.getController();
        fourDMixTheatersController.setUser(user);
        fourDMixTheatersController.setMovie(movie);
        fourDMixTheatersController.setRound(new Round("4D",b.getText()));
        stage.show();
    }

    @FXML
    public void handleLoadVIPTheaterAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vipTheaters.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        VipTheatersController vipTheatersController = loader.getController();
        vipTheatersController.setUser(user);
        vipTheatersController.setMovie(movie);
        vipTheatersController.setRound(new Round("VIP",b.getText()));
        stage.show();
    }

    @FXML
    public void handleBackAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieProfile.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        MovieProfileController movieProfileController = loader.getController();
        movieProfileController.setMovie(movie);
        movieProfileController.setUser(user);
        stage.show();
    }
}