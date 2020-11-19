package model.objectStructure;

public class Round {
    private String system;
    private String time;

    public Round(String system, String time) {
        this.system = system;
        this.time = time;
    }

    public String getSystem() {
        return system;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return system + "/" + time;
    }
}
