package model;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.objectStructure.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class TicketController {
    private int price;
    private User user;
    private List<Ticket> order;
    private String dirTic;
    private Theme[] themes = Theme.push();

    public void setOrder(List<Ticket> order) {
        this.order = order;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @FXML
    AnchorPane wallpaper;

    void setWallpaper(){
        String filename = "/images/Team/"+ themes[user.getTheme()-1].getMovie();
        wallpaper.setStyle("-fx-background-image: url('"+filename+"')");
    }


    @FXML
    Pane paneTic,paneBill;

    @FXML
    ImageView imageMovie;

    @FXML
    Label textBill,textTotal,textData,textFile,textName;

    @FXML

    public void initialize() throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    imageMovie.setImage(new Image(getClass().getResource("/images/t.png").toURI().toString()));
                    setBill(new BillFormat());
                    setTicket(new TicketFormat());
                    textTotal.setText("Total : "+price);
                    textFile.setText(dirTic);
                    textName.setText(user.getName());
                    setWallpaper();
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void  setBill(Formatter formatter){
        String text = "";
        for(Ticket e : order){
            text += formatter.format(e) + " \n";
        }
        textBill.setText(text);
    }

    @FXML
    public void setTicket(Formatter formatter) throws IOException {
        for(Ticket e : order){
            textData.setText(formatter.format(e));
            saveAsPng(e.getChair());
        }
    }

    @FXML
    public void saveAsBill(){
        BufferedImage bufferedImage = new BufferedImage(240, 360, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(paneBill.snapshot(new SnapshotParameters(),new WritableImage(240, 360)), bufferedImage);
        SwingFXUtils.toFXImage(bufferedImage, new WritableImage(240, 360));
        String fs = File.separator;
        String dir = System.getProperty("user.dir") + fs + "Bill";
        String filename = dir + fs + "Bill.png";
        File fileDir = new File(dir);
        fileDir.mkdirs();
        try {
            ImageIO.write(bufferedImage, "png",new File(filename));
        } catch (IOException e) {
            e.getMessage();
        }
        JOptionPane.showMessageDialog(null, dir, "Save Bill As", JOptionPane.INFORMATION_MESSAGE);
    }

    public void saveAsPng(String chair) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(240, 360, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(paneTic.snapshot(new SnapshotParameters(),new WritableImage(240, 360)), bufferedImage);
        SwingFXUtils.toFXImage(bufferedImage, new WritableImage(240, 360));
        String fs = File.separator;
        String dir = System.getProperty("user.dir") + fs + "ticket";
        String filename = dir + fs + "ticket"+chair+".png";
        File fileDir = new File(dir);
        fileDir.mkdirs();
        try {
            ImageIO.write(bufferedImage, "png",new File(filename));
        } catch (IOException e) {
            e.getMessage();
        }
        dirTic = dir;
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
}
