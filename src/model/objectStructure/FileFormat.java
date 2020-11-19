package model.objectStructure;

public class FileFormat implements Formatter {
    @Override
    public String format(Ticket tickets) {
        return tickets.getUsername() + "," + tickets.getMovie() + "," + tickets.getRound() + "," + tickets.getChair();
    }
}
