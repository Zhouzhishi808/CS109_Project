package model;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 4675739331147943844L;
    private final String username;
    private final String passwordHash;
    private GameSave saveData;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    public boolean validatePassword(String password) {
        return passwordHash.equals(hashPassword(password));
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public GameSave getSaveData() { return saveData; }
    public void setSaveData(GameSave saveData) { this.saveData = saveData; }

    //检查是否有有存档文件
    public boolean hasSaveDataForLevel(String levelName) {
        String savePath = Constants.SAVE_DIRECTORY + username + "_" + levelName + ".save";
        return new File(savePath).exists();
    }
}