package model;

import java.io.Serial;
import java.io.Serializable;

public class Rank implements Comparable<Rank>, Serializable {
    @Serial
    private static final long serialVersionUID = 2579108766831477121L;
    private final String username;
    private final int finishTime;

    public Rank(String username, int finishTime) {
        this.username = username;
        this.finishTime = finishTime;
    }

    @Override
    public int compareTo(Rank o) {
        return Integer.compare(this.finishTime, o.finishTime);
    }

    public String getUsername() {return this.username;}
    public int getFinishTime() {return this.finishTime;}
}
