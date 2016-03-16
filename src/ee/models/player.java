package ee.models;

public class player {

    private int id;
    private String name;
    private int score;

    public player(int id, String name, int score) {
        super();
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name + ", score=" + score
                + "]";
    }

}
