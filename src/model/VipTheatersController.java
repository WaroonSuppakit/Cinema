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
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class VipTheatersController {
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
    ImageView imageMovie,imageVipChair,imageScreen;

    @FXML
    Button B1_2, B3_4, B5_6, A1_2, A3_4, A5_6;

    @FXML
    Label textTitle,textPrice;

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
                Button[] chairs = {B1_2, B3_4, B5_6, A1_2, A3_4, A5_6};
                for(Button e : chairs){
                    if(isVisit(e.getId())){
                        if(isUser(e.getId())){
                            e.setStyle("-fx-background-image: url('/images/c5m.png')");
                        }else{
                            e.setStyle("-fx-background-image: url('/images/c5c.png')");
                            e.setDisable(true);
                        }
                    }else{
                        e.setStyle("-fx-background-image: url('/images/c5.png')");
                    }
                }
                textTitle.setText(movie.getName()+" - VIP - "+round.getTime());
                try {
                    imageVipChair.setImage(new Image(getClass().getResource("/images/c5.png").toURI().toString()));
                    imageScreen.setImage(new Image(getClass().getResource("/images/csr.jpg").toURI().toString()));
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
    public void handleChooseVIPAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        if(b.getStyle().equals("-fx-background-image: url('/images/c5m.png')")){
            int dialogResult = JOptionPane.showConfirmDialog (null, "Cancel reservation");
            if(dialogResult == JOptionPane.YES_OPTION){
                remove(b.getId());
            }
        }else if(b.getStyle().equals("-fx-background-image: url('/images/c5.png')")){
            b.setStyle("-fx-background-image: url('/images/c5c.png')");
            price += 2000;
            chairOrder.add(b);
        }else{
            b.setStyle("-fx-background-image: url('/images/c5.png')");
            price -= 2000;
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
        int dialogResult = JOptionPane.showConfirmDialog (null,"Confirm");
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
