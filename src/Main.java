import view.level.LevelFrame;
import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(800, 480);
            LevelFrame levelFrame = new LevelFrame(480, 492, loginFrame);
            loginFrame.setLevelFrame(levelFrame);
            loginFrame.setVisible(true);
        });
    }
}
