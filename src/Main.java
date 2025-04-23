import model.MapModel;
import view.game.GameFrame;
import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);
            MapModel mapModel = new MapModel(new int[][]{
                    {1,2,2,2,2},
                    {0,0,0,4,4},
                    {0,0,0,4,4},
                    {1,2,2,2,2}
            });
            GameFrame gameFrame = new GameFrame(450, 450, mapModel);
            gameFrame.setVisible(false);
            loginFrame.setGameFrame(gameFrame);
        });
    }
}
