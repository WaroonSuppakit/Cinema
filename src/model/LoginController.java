package model;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.objectStructure.*;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class LoginController {
    private List<User>  users;
    private Theme[] themes = Theme.push();

    @FXML
    TextField logUser,textUser,textName,textSur,textEmail;

    @FXML
    PasswordField textPass,logPass;

    @FXML
    public void initialize() throws IOException {
        read(new Read());
    }

    @FXML
    public void handleBtnRegister(Event event) throws IOException {
        if(textUser.getText().equals("") || textPass.getText().equals("") || textName.getText().equals("") || textSur.getText().equals("") || textEmail.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }else {
            boolean visit = false;
            for (User e : users) {
                if (e.getUsername().equals(textUser.getText())) {
                    visit = true;
                    break;
                }
            }
            if(visit) {
                JOptionPane.showMessageDialog(null, "This username cannot be used", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            }else {
                users.add(new User(textUser.getText(), textPass.getText(), textName.getText(), textSur.getText(), textEmail.getText(), "user"));
                write(new Write());
            }
        }
        textUser.setText("");
        textPass.setText("");
        textName.setText("");
        textSur.setText("");
        textEmail.setText("");
    }

    @FXML
    public void handleBtnLogin(Event event) throws IOException {
        if(logUser.getText().equals("") || logPass.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }else {
            boolean user = false;
            boolean pass = false;
            User cookie = null;
            for (User e : users) {
                if (e.getUsername().equals(logUser.getText())) {
                    user = true;
                    if (e.getPassword().equals(logPass.getText())) {
                        pass = true;
                        cookie = e;
                    }
                    break;
                }
            }
            if (!user) {
                JOptionPane.showMessageDialog(null, "Username not found", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            } else if (!pass) {
                JOptionPane.showMessageDialog(null, "Password is incorrect", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            } else if (cookie != null){
                Button b = (Button) event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                stage.setScene(new Scene(loader.load(),950, 500));
                HomeController homeController = loader.getController();
                homeController.setUser(cookie);
                stage.show();
            }
        }
        logUser.setText("");
        logPass.setText("");
    }

    private void read(Read read) throws IOException {
        users = read.readUser();
    }

    private void write(Write write) throws IOException {
        write.writeUser(users);
    }
}
