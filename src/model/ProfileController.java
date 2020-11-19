package model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.objectStructure.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class ProfileController {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    Label labelData;

    @FXML
    ImageView imageProfile;

    @FXML
    public void initialize(){
        try {
            imageProfile.setImage(new Image(getClass().getResource("/images/profile.jpg").toURI().toString()));
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        labelData.setText("6110451398\n\n" +
                          "Waroon   Suppakit\n\n" +
                          "Kasetsart University\n\n" +
                          "Faculty of Science\n\n" +
                          "Computer Science");
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
}
