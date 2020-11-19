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
import javafx.stage.Stage;
import model.objectStructure.*;


import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ThreeDTheatersController {
    private int price = 0;
    private User user;
    private Movie movie;
    private Round round;
    private List<Ticket> order;

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    List<Button> chairOrder = new ArrayList<>();

    @FXML
    ImageView imageMovie,imageNormal,imageVip,imageScreen;

    @FXML
    Label textTitle,textPrice;

    @FXML
    Button A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, D1, D2, D3, D4, D5, D6, D7, D8, D9, D10;

    @FXML
    public void initialize(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Read read = new Read();
                try {
                    order = read.readOrder(movie.getName(),round);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Button[] chairs = {A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, D1, D2, D3, D4, D5, D6, D7, D8, D9, D10};
                for(Button e : chairs){
                    if(e.getId().charAt(0) == 'A'){
                        if(isVisit(e.getId())){
                            if(isUser(e.getId())){
                                e.setStyle("-fx-background-image: url('/images/c3m.png')");
                            }else{
                                e.setStyle("-fx-background-image: url('/images/c3c.png')");
                                e.setDisable(true);
                            }
                        }else{
                            e.setStyle("-fx-background-image: url('/images/c3.png')");
                        }
                    }else {
                        if(isVisit(e.getId())){
                            if(isUser(e.getId())){
                                e.setStyle("-fx-background-image: url('/images/c2m.png')");
                            }else{
                                e.setStyle("-fx-background-image: url('/images/c2c.png')");
                                e.setDisable(true);
                            }
                        }else{
                            e.setStyle("-fx-background-image: url('/images/c2.png')");
                        }
                    }
                }
                textTitle.setText(movie.getName()+" - 3D - "+round.getTime());
                try {
                    imageNormal.setImage(new Image(getClass().getResource("/images/c2.png").toURI().toString()));
                    imageVip.setImage(new Image(getClass().getResource("/images/c3.png").toURI().toString()));
                    imageScreen.setImage(new Image(getClass().getResource("/images/csr.jpg").toURI().toString()));
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
    public void handleChooseNormalAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        if(b.getStyle().equals("-fx-background-image: url('/images/c2m.png')")){
            int dialogResult = JOptionPane.showConfirmDialog (null, "Cancel reservation");
            if(dialogResult == JOptionPane.YES_OPTION){
                remove(b.getId());
            }
        }else if(b.getStyle().equals("-fx-background-image: url('/images/c2.png')")){
            b.setStyle("-fx-background-image: url('/images/c2c.png')");
            price += 200;
            chairOrder.add(b);
        }else{
            b.setStyle("-fx-background-image: url('/images/c2.png')");
            price -= 200;
            chairOrder.remove(b);
            if(price < 0){
                price = 0;
            }
        }
        textPrice.setText("Total : "+price+" Bath");
    }

    @FXML
    public void handleChooseHoneyAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        if(b.getStyle().equals("-fx-background-image: url('/images/c3m.png')")){
            int dialogResult = JOptionPane.showConfirmDialog (null, "Cancel reservation");
            if(dialogResult == JOptionPane.YES_OPTION){
                remove(b.getId());
            }
        }else if(b.getStyle().equals("-fx-background-image: url('/images/c3.png')")){
            b.setStyle("-fx-background-image: url('/images/c3c.png')");
            price += 250;
            chairOrder.add(b);
        }else{
            b.setStyle("-fx-background-image: url('/images/c3.png')");
            price -= 250;
            if(price < 0){
                price = 0;
            }
            chairOrder.remove(b);
        }
        textPrice.setText("Total : "+price+" Bath");
    }

    @FXML
    public void handleConAction(ActionEvent event) throws IOException {
        if(chairOrder.size() == 0){
            JOptionPane.showMessageDialog(null, "Please try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int dialogResult = JOptionPane.showConfirmDialog (null, "Confirm");
        if(dialogResult == JOptionPane.YES_OPTION){
            for(Button e:chairOrder){
                Ticket ticketOrder = new Ticket(user.getUsername(),movie.getName(),round);
                ticketOrder.setChair(e.getId());
                order.add(ticketOrder);
            }
            Write write = new Write();
            write.writeOrder(order);
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ticket.fxml"));
            stage.setScene(new Scene(loader.load(),950, 500));
            TicketController ticketController = loader.getController();
            ticketController.setUser(user);
            ticketController.setOrder(order);
            ticketController.setPrice(price);
            stage.show();
        }
    }

    @FXML
    public void handleBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        HomeController homeController = loader.getController();
        homeController.setUser(user);
        stage.show();
    }

    public boolean isVisit(String chair){
        for(Ticket e: order){
            if(e.getChair().equals(chair)){
                return true;
            }
        }
        return false;
    }

    public boolean isUser(String chair){
        for(Ticket e: order){
            if(e.getChair().equals(chair) && e.getUsername().equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }

    public void remove(String chair) throws IOException {
        for(Ticket e: order){
            if(e.getChair().equals(chair)){
                order.remove(e);
                break;
            }
        }
        Write write = new Write();
        write.writeOrder(order);
        initialize();
    }
}
