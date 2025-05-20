package model;

import java.io.*;
import java.util.*;

public class RankManager {
    private static final int MAX_ARRAY = 5;

    public static void saveRankData(String levelName, Rank rank) {
        String path = Constants.RANK_DIRECTORY +  levelName + ".rank";
        ArrayList<Rank> array = loadRankData(levelName);

        System.out.println("Saving rank data to " + path);

        array.add(rank);
        array.sort(Comparator.naturalOrder());
        if (array.size() > MAX_ARRAY) {
            array.removeLast();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Rank> loadRankData(String levelName)
    {
        String path = Constants.RANK_DIRECTORY + levelName + ".rank";
        File file = new File(path);

        if (!file.exists()) {return new ArrayList<>();}

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (ArrayList<Rank>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
