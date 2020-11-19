package model.objectStructure;

public class BillFormat implements Formatter {
    @Override
    public String format(Ticket tickets) {
        String cinema = "";
        if(tickets.getRound().getSystem().equals("Normal")){
            cinema = "1";
        }else if(tickets.getRound().getSystem().equals("3D")){
            cinema = "2";
        }else if(tickets.getRound().getSystem().equals("4D")){
            if(tickets.getRound().getTime().equals("10:30")){
                cinema = "3";
            }else{
                cinema = "4";
            }
        }else if(tickets.getRound().getSystem().equals("VIP")){
            cinema = "5";
        }
        return "" + tickets.getMovie() + "|\n" +
                "" + tickets.getChair() + "|Cinema:" + cinema + "|" + tickets.getRound().getSystem() + "-" + tickets.getRound().getTime() + "|\n";

    }
}
