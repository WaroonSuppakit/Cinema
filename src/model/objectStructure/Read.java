package model.objectStructure;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Read {
    public List<User> readUser() throws IOException {
        List<User> list = new ArrayList<>();
        String fs = File.separator;
        String dir = System.getProperty("user.dir") + fs + "resources" + fs + "user";
        String filename = dir + fs + "user.csv";
        File fileDir = new File(dir);
        fileDir.mkdirs();
        File file = new File(filename);
        if (file.createNewFile()) {
            FileWriter fileWriter = null;
            PrintWriter printWriter = null;
            try {
                fileWriter = new FileWriter(filename);
                printWriter = new PrintWriter(fileWriter);
                printWriter.println("adminTest,cs1398,admin,test,admintest@ku.th,admin,1");
                printWriter.println("userTest,cs1398,user,test,usertest@ku.th,user,1");
                printWriter.flush();
            } catch (IOException e) {
                System.out.println("Error IOException");
            } finally {
                if (printWriter != null) {
                    printWriter.close();
                }
            }
        }
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(filename);
            bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                String name = data[2];
                String surname = data[3];
                String email = data[4];
                String priority = data[5];
                String team = data[6];
                User user = new User(username, password, name, surname, email, priority);
                user.setTheme(Integer.parseInt(team));
                list.add(user);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("Error IO");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("ERROR Close");
            }
        }
        return list;
    }

    public Movie[] readMovie() throws IOException{
        Movie[] movies = new Movie[3];
        String fs = File.separator;
        String dir = System.getProperty("user.dir")+fs+"resources"+fs+"movie";
        String filename = dir+fs+"movie.csv";
        File fileDir = new File(dir);
        fileDir.mkdirs();
        File file = new File(filename);
        if(file.createNewFile()){
            FileWriter fileWriter = null;
            PrintWriter printWriter = null;
            try{
                fileWriter = new FileWriter(filename);
                printWriter = new PrintWriter(fileWriter);
                printWriter.println("Star Wars: The Rise of Skywalker,แอ็คชัน / ผจญภัย / แฟนตาซี,-,155 นาที,/images/mm1.jpg,"+ getClass().getResource("/trailer/trailer1.mp4").toURI().toString()+",Normal/10:30>3D/10:30>4D/10:30>VIP/10:30>");
                printWriter.println("Jurassic World 2,แอ็คชัน / ผจญภัย / วิทยาศาสตร์,Jurassic World: Fallen Kingdom กำกับโดย J.A. Bayona,128 นาที,/images/mm2.jpg,"+getClass().getResource("/trailer/trailer2.mp4").toURI().toString()+",Normal/14:40>3D/14:40>4D/14:40>VIP/14:40>");
                printWriter.println("Fast And Furious Hobbs and Shaw,แอ็คชัน,Hobbs & Shaw เป็นการกระแทกเปิดประตูใหม่สู่จักรวาล Fast เมื่อแอ็กชั่นของมันพุ่งทะยานไปทั่วโลก จากลอสแองเจลิสสู่ลอนดอน และจากเมืองร้างที่เต็มไปด้วยสารพิษอย่างเชอร์โนบิล สู่ความงามที่เขียวชอุ่มของซามัว,133 นาที,/images/mm3.jpg,"+getClass().getResource("/trailer/trailer3.mp4").toURI().toString()+",Normal/19:30>3D/19:30>4D/19:30>VIP/19:30>");
                printWriter.flush();
            } catch (IOException | URISyntaxException e) {
                System.out.println("Error IOException");
            }finally {
                if(printWriter != null){
                    printWriter.close();
                }
            }
        }
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(filename);bufferedReader = new BufferedReader(fileReader);
            String line = null;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                String type = data[1];
                String description = data[2];
                String minute = data[3];
                String poster = data[4];
                String trailer = data[5];
                String[] round = data[6].split(">");
                movies[i] = new Movie(name,type,description,minute,poster,trailer);
                for(String e : round){
                    String[] roundData = e.split("/");
                    movies[i].add(new Round(roundData[0],roundData[1]));
                }
                i++;
            }
        } catch (FileNotFoundException e){
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("Error IO");
        }finally {
            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("ERROR Close");
            }
        }
        return movies;
    }

    public List<Ticket> readOrder(String movie,Round round) throws IOException{
        List<Ticket> order = new ArrayList<>();
        String fs = File.separator;
        String dir = System.getProperty("user.dir")+fs+"resources"+fs+"BookingData";
        String filename = dir+fs+"BookingData.csv";
        File fileDir = new File(dir);
        fileDir.mkdirs();
        File file = new File(filename);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(filename);bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0];
                String inputMovie = data[1];
                String inputRound = data[2];
                String chair = data[3];
                String[] roundData = inputRound.split("/");
                if(movie.equals(inputMovie) && round.getSystem().equals(roundData[0]) && round.getTime().equals(roundData[1])){
                    Ticket ticket = new Ticket(username,inputMovie,new Round(roundData[0],roundData[1]));
                    ticket.setChair(chair);
                    order.add(ticket);
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("Error IO");
        }finally {
            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("ERROR Close");
            }
        }
        return order;
    }
}
