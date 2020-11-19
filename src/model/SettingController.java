package model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.objectStructure.*;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SettingController {
    private List<User> users = new ArrayList<>();
    private Movie[] movies = new Movie[3];
    private List<Round> rounds;
    private User user;
    private Theme[] themes = Theme.push();


    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    AnchorPane wallpaper;

    @FXML
    ImageView imageTeam;

    void setWallpaper(){
        String filename = "/images/Team/"+ themes[user.getTheme()-1].getSetting();
        wallpaper.setStyle("-fx-background-image: url('"+filename+"')");
    }

    @FXML
    Label labelName,labelSet;

    @FXML
    AnchorPane userPane,moviePane,teamPane;

    @FXML
    Button setBtn,delBtn,userBtn,movieBtn,teamBtn;

    @FXML
    TextArea textDes;

    @FXML
    TextField textUserSet,textUserDelete,textMin, textPos,textTra,textType,textName;

    @FXML
    MenuButton menuPri,menuNumMovSet,menuNumRouAdd,menuSys,menuTime, menuTheme;

    @FXML
    TableView<User> tableUser;

    @FXML
    TableColumn<User,String> colUser,colName,colSur,colEmail,colPri;

    @FXML
    TableView<Movie> tableMovie;

    @FXML
    TableColumn<Movie,String> movName,movType,movDes,movMin,movRou,movPos,movTra;

    //----------------main method----------------

    @FXML
    public void initialize() throws IOException {
        Platform.runLater(() -> {
            setWallpaper();
            try {
                readUser(new Read());
                readMovie(new Read());
                getRound();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                String filename = "/images/Team/team"+user.getTheme()+"show.png";
                imageTeam.setImage(new Image(getClass().getResource(filename).toURI().toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if(user.getPriority().equals("user")){
                userBtn.setVisible(false);
                movieBtn.setVisible(false);
                teamBtn.setVisible(false);
            }
            labelName.setText(user.getName()+"      "+user.getSurname());
            labelSet.setText("Team Setting");
            if(user.getTheme() == 2){
                labelSet.setStyle("-fx-text-fill:  black;");
            }else{
                labelSet.setStyle("-fx-text-fill:  white;");
            }
            showUser();
            showMovie();
        });
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

    @FXML
    public void handleBtnLogout(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        stage.show();
    }

    @FXML
    public void handleBtnUserAction(ActionEvent event){
        labelSet.setText("User Setting");
        userPane.setVisible(true);
        moviePane.setVisible(false);
        teamPane.setVisible(false);
    }

    @FXML
    public void handleBtnMovieAction(ActionEvent event){
        labelSet.setText("Movie Setting");
        userPane.setVisible(false);
        moviePane.setVisible(true);
        teamPane.setVisible(false);
    }

    @FXML
    public void handleBtnTeamAction(ActionEvent event){
        labelSet.setText("Team Setting");
        userPane.setVisible(false);
        moviePane.setVisible(false);
        teamPane.setVisible(true);
    }

    @FXML
    public void getRound(){
        rounds = new ArrayList<>();
        for(Movie e : movies){
            rounds.addAll(e.getRounds());
        }
    }

    //----------------user method----------------

    @FXML
    public void handleBtnSetPriAction(ActionEvent event){
        MenuItem m = (MenuItem) event.getSource();
        menuPri.setText(m.getText());
    }

    @FXML
    public void handleBtnSetAction(ActionEvent event) throws IOException {
        String userSetText = textUserSet.getText();
        String pri = menuPri.getText();
        textUserSet.setText("");
        menuPri.setText("-");
        if(userSetText.equals(user.getUsername())){
            JOptionPane.showMessageDialog(null, "You cannot set yourself", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else if(userSetText.equals("")){
            JOptionPane.showMessageDialog(null, "Please try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else {
            for(User e : users){
                if(e.getUsername().equals(userSetText)){
                    e.setPriority(pri);
                    writeUser(new Write());
                    initialize();
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Username not found", "ERROR", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    public void handleBtnDeleteAction(ActionEvent event) throws IOException {
        String userDelete = textUserDelete.getText();
        textUserDelete.setText("");
        if(userDelete.equals("")){
            JOptionPane.showMessageDialog(null, "Please try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else if(userDelete.equals(user.getUsername())){
            JOptionPane.showMessageDialog(null, "You cannot delete yourself", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else{
            for(User e : users){
                if(e.getUsername().equals(userDelete)){
                    users.remove(e);
                    writeUser(new Write());
                    initialize();
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Username not found", "ERROR", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    public void handleBtnFilePos(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            String uri = file.toURI().toString();
            textPos.setText(uri);
        }
    }

    @FXML
    public void handleBtnFilePTra(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            String uri = file.toURI().toString();
            textTra.setText(uri);
        }
    }

    private void readUser(Read read) throws IOException {
        users = read.readUser();
    }

    private void writeUser(Write write) throws IOException {
        write.writeUser(users);
    }

    public void showUser(){
        ObservableList<User> usersTable = FXCollections.observableArrayList();
        for(User e:users){
            usersTable.add(e);
        }
        colUser.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colSur.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPri.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        tableUser.setItems(usersTable);
    }

    //----------------movie method----------------

    @FXML
    public void handleBtnSetNum1(ActionEvent event){
        MenuItem m = (MenuItem) event.getSource();
        menuNumMovSet.setText(m.getText());
        setText(Integer.parseInt(m.getText())-1);
    }


    @FXML
    public void handleBtnSetNum2(ActionEvent event){
        MenuItem m = (MenuItem) event.getSource();
        menuNumRouAdd.setText(m.getText());
    }

    @FXML
    public void handleBtnSetItem(ActionEvent event){
        MenuItem m = (MenuItem) event.getSource();
        menuSys.setText(m.getText());
    }

    @FXML
    public void handleBtnSetTime(ActionEvent event){
        MenuItem m = (MenuItem) event.getSource();
        menuTime.setText(m.getText());
    }

    @FXML
    public void setText(int index){
        textName.setText(movies[index].getName());
        textType.setText(movies[index].getType());
        textDes.setText(movies[index].getDescription());
        textMin.setText(movies[index].getMinute());
        textPos.setText(movies[index].getPoster());
        textTra.setText(movies[index].getTrailer());
    }

    @FXML
    public void handleBtnEdit(ActionEvent event) throws IOException {
        if(menuNumMovSet.getText().equals("Number") || textName.getText().equals("") || textMin.getText().equals("") || textType.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        movies[Integer.parseInt(menuNumMovSet.getText()) - 1] = new Movie(textName.getText(),textType.getText(),textDes.getText(),textMin.getText(),textPos.getText(),textTra.getText());
        menuNumMovSet.setText("Number");
        textName.clear();
        textType.clear();
        textDes.clear();
        textMin.clear();
        textPos.clear();
        textTra.clear();
        writeMovie(new Write());
        initialize();
    }

    @FXML
    public void handleBtnAddRound(ActionEvent event) throws IOException {
        if(menuNumRouAdd.getText().equals("Number") || menuSys.getText().equals("System") || menuTime.getText().equals("Time")){
            JOptionPane.showMessageDialog(null, "Please try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for(Round e : rounds){
            if(e.getSystem().equals(menuSys.getText()) && e.getTime().equals(menuTime.getText())){
                JOptionPane.showMessageDialog(null, "Has been used", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        movies[Integer.parseInt(menuNumRouAdd.getText()) - 1].add(new Round(menuSys.getText(), menuTime.getText()));
        writeMovie(new Write());
        menuNumRouAdd.setText("Number");
        menuSys.setText("System");
        menuTime.setText("Time");
        initialize();
    }

    private void readMovie(Read read) throws IOException {
        movies = read.readMovie();
    }

    private void writeMovie(Write write) throws IOException {
        write.writeMovie(movies);
    }


    public void showMovie(){
        ObservableList<Movie> movieTable = FXCollections.observableArrayList();
        for(Movie e : movies){
            movieTable.add(e);
        }
        movName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        movType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        movDes.setCellValueFactory(new PropertyValueFactory<>("Description"));
        movMin.setCellValueFactory(new PropertyValueFactory<>("Minute"));
        movRou.setCellValueFactory(new PropertyValueFactory<>("Rounds"));
        movPos.setCellValueFactory(new PropertyValueFactory<>("Poster"));
        movTra.setCellValueFactory(new PropertyValueFactory<>("Trailer"));
        tableMovie.setItems(movieTable);
    }

    //----------------team method----------------
    @FXML
    public void handleBtnSetTeam(ActionEvent event){
        MenuItem m = (MenuItem) event.getSource();
        if(m.getText().equals("1")){
            menuTheme.setText("Cinema");
        }else if(m.getText().equals("2")){
            menuTheme.setText("Adventure Time");
        }else{
            menuTheme.setText("Snoopy");
        }
        try {
            String filename = "/images/Team/team"+m.getText()+"show.png";
            imageTeam.setImage(new Image(getClass().getResource(filename).toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBtnUseTeam(ActionEvent event) throws IOException {
       if(menuTheme.getText().equals("Cinema")){
           user.setTheme(1);
           changeTeam(user);
        }else if(menuTheme.getText().equals("Adventure Time")){
           user.setTheme(2);
           changeTeam(user);
        }else if(menuTheme.getText().equals("Snoopy")){
           user.setTheme(3);
           changeTeam(user);
        }else{
            JOptionPane.showMessageDialog(null, "Please try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Write write = new Write();
        write.writeUser(users);
        initialize();
    }

    public void changeTeam(User user){
        for(User e: users){
            if(e.getUsername().equals(user.getUsername())){
                e.setTheme(user.getTheme());
                return;
            }
        }
    }
}
