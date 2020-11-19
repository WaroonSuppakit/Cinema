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

public class NormalTheatersController {
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

    private List<Button> chairOrder = new ArrayList<>();

    @FXML
    ImageView imageMovie,imageChair,imageScreen;

    @FXML
    Label textTitle,textPrice;

    @FXML
    Button A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3, B4, B5, B6, B7, B8, C1, C2, C3, C4, C5, C6, C7, C8, D1, D2, D3, D4, D5, D6, D7, D8;

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
                Button[] chairs = {A1, A2, A3, A4, A5, A6, A7, A8, B1, B2, B3, B4, B5, B6, B7, B8, C1, C2, C3, C4, C5, C6, C7, C8, D1, D2, D3, D4, D5, D6, D7, D8};
                for(Button e : chairs){
                    if(isVisit(e.getId())){
                        if(isUser(e.getId())){
                            e.setStyle("-fx-background-image: url('/images/c1m.png')");
                        }else{
                            e.setStyle("-fx-background-image: url('/images/c1c.png')");
                            e.setDisable(true);
                        }
                    }else{
                        e.setStyle("-fx-background-image: url('/images/c1.png')");
                    }
                }
                textTitle.setText(movie.getName()+" - Normal - "+round.getTime());
                try {
                    imageChair.setImage(new Image(getClass().getResource("/images/c1.png").toURI().toString()));
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
    public void handleBtnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        stage.setScene(new Scene(loader.load(),950, 500));
        HomeController homeController = loader.getController();
        homeController.setUser(user);
        stage.show();
    }

    @FXML
    public void handleChooseNorAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        if(b.getStyle().equals("-fx-background-image: url('/images/c1m.png')")){
            int dialogResult = JOptionPane.showConfirmDialog (null, "Cancel reservation");
            if(dialogResult == JOptionPane.YES_OPTION){
                remove(b.getId());
            }
        }else if(b.getStyle().equals("-fx-background-image: url('/images/c1.png')")){
            b.setStyle("-fx-background-image: url('/images/c1c.png')");
            price += 100;
            chairOrder.add(b);
        }else{
            b.setStyle("-fx-background-image: url('/images/c1.png')");
            price -= 100;
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
