import model.MapModel;
import view.game.GameFrame;
import view.level.LevelFrame;
import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);
            LevelFrame levelFrame = new LevelFrame(400, 560);
            loginFrame.setLevelFrame(levelFrame);
        });

    }
}
