package model.objectStructure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Write {
    public void writeUser(List<User> users) throws IOException {
        String fs = File.separator;
        String dir = System.getProperty("user.dir")+fs+"resources"+fs+"user";
        String filename = dir+fs+"user.csv";
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try{
            fileWriter = new FileWriter(filename);
            printWriter = new PrintWriter(fileWriter);
            for(User e:users) {
                printWriter.println(e);
            }
            printWriter.flush();
        } catch (IOException e) {
            System.out.println("Error IO");
        }finally {
            if(printWriter != null){
                printWriter.close();
            }
        }
    }

    public void writeMovie(Movie[] movies) throws IOException {
        String fs = File.separator;
        String dir = System.getProperty("user.dir")+fs+"resources"+fs+"movie";
        String filename = dir+fs+"movie.csv";
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try{
            fileWriter = new FileWriter(filename);
            printWriter = new PrintWriter(fileWriter);
            for(Movie e : movies){
                printWriter.println(e);
            }
            printWriter.flush();
        } catch (IOException e) {
            System.out.println("Error IO");
        }finally {
            if(printWriter != null){
                printWriter.close();
            }
        }

    }

    public void writeOrder(List<Ticket> tickets) throws IOException {
        String fs = File.separator;
        String dir = System.getProperty("user.dir")+fs+"resources"+fs+"BookingData";
        String filename = dir+fs+"BookingData.csv";
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try{
            fileWriter = new FileWriter(filename);
            printWriter = new PrintWriter(fileWriter);
            Formatter formatter = new FileFormat();
            for(Ticket e : tickets){
                printWriter.println(formatter.format(e));
            }
            printWriter.flush();
        } catch (IOException e) {
            System.out.println("Error IO");
        }finally {
            if(printWriter != null){
                printWriter.close();
            }
        }

    }
}
