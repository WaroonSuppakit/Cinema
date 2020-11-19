package model.objectStructure;

public class Theme {
    private String home;
    private String movie;
    private String setting;
    private String movieProfile;
    private String movieRound;
    private String buttonProfile;
    private String buttonMovie;
    private String buttonSetting;

    public Theme(String home, String movie, String setting, String movieProfile, String movieRound, String buttonProfile, String buttonMovie, String buttonSetting) {
        this.home = home;
        this.movie = movie;
        this.setting = setting;
        this.movieProfile = movieProfile;
        this.movieRound = movieRound;
        this.buttonProfile = buttonProfile;
        this.buttonMovie = buttonMovie;
        this.buttonSetting = buttonSetting;
    }

    public String getHome() {
        return home;
    }

    public String getMovie() {
        return movie;
    }

    public String getSetting() {
        return setting;
    }

    public String getMovieProfile() {
        return movieProfile;
    }

    public String getMovieRound() {
        return movieRound;
    }

    public String getButtonProfile() {
        return buttonProfile;
    }

    public String getButtonMovie() {
        return buttonMovie;
    }

    public String getButtonSetting() {
        return buttonSetting;
    }

    public static Theme[] push(){
        Theme[] themes = new Theme[3];
        themes[0] = new Theme("team1home.png","team1movie.png","team1set.png","team1mpro.png","team1r.png","team1p.png","team1n.png","team1s.png");
        themes[1] = new Theme("team2home.png","team2movie.png","team2set.png","team2mpro.png","team2r.png","team2p.png","team2n.png","team2s.png");
        themes[2] = new Theme("team3home.png","team3movie.png","team3set.png","team3mpro.png","team3r.png","team3p.png","team3n.png","team3s.png");
        return themes;
    }
}
