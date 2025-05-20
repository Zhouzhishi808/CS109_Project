package model;

import java.io.Serial;
import java.io.Serializable;

public class Rank implements Comparable<Rank>, Serializable {
    @Serial
    private static final long serialVersionUID = 2579108766831477121L;
    private final String username;
    private final int finishTime;
    private final int step;

    public Rank(String username, int finishTime, int step) {
        this.username = username;
        this.finishTime = finishTime;
        this.step = step;
    }

    @Override
    public int compareTo(Rank o) {
        return Integer.compare(this.finishTime, o.finishTime);
    }

    public String getUsername() {return this.username;}
    public int getFinishTime() {return this.finishTime;}
    public int getStep() {return this.step;}
}
