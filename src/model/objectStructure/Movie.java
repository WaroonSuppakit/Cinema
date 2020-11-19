package model.objectStructure;

import java.util.ArrayList;

public class Movie {
    private String name;
    private String type;
    private String description;
    private String minute;
    private String poster;
    private String trailer;
    private ArrayList<Round> rounds = new ArrayList<>();

    public Movie(String name, String type, String description, String minute, String poster, String trailer) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.minute = minute;
        this.poster = poster;
        this.trailer = trailer;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getMinute() {
        return minute;
    }

    public String getPoster() {
        return poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void add(Round round){
        rounds.add(round);
    }

    public String roundFormat(){
        if(rounds.size() == 0){
            return ">";
        }
        String format = "";
        for(Round e : rounds){
            format += e +  ">";
        }
        return format;
    }

    @Override
    public String toString() {
        return  name + "," + type + "," + description + "," + minute + "," + poster + "," + trailer + "," + roundFormat();
    }
}
