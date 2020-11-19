package model.objectStructure;

public class Ticket {
    private String username;
    private String movie;
    private Round round;
    private String chair;

    public Ticket(String username, String movie, Round round) {
        this.username = username;
        this.movie = movie;
        this.round = round;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getUsername() {
        return username;
    }

    public String getMovie() {
        return movie;
    }

    public Round getRound() {
        return round;
    }

    public String getChair() {
        return chair;
    }
}
