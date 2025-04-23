package model;

import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private final String passwordHash;
    private GameSave saveData;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode()); // 简单哈希，实际应使用BCrypt等安全哈希
    }

    public boolean validatePassword(String password) {
        return passwordHash.equals(hashPassword(password));
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public GameSave getSaveData() { return saveData; }
    public void setSaveData(GameSave saveData) { this.saveData = saveData; }
}