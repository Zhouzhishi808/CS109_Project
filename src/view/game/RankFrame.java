package view.game;

import model.Rank;
import model.RankManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RankFrame extends JFrame {
    public RankFrame(String levelName) {
        setTitle(levelName + " - 排行榜");
        setSize(300, 220);
        setLayout(new BorderLayout());

        // 加载数据
        ArrayList<Rank> entries = RankManager.loadRankData(levelName);

        // 构建表格
        String[] columns = {"排名", "用户名", "时间"};
        Object[][] data = new Object[entries.size()][3];
        for (int i = 0; i < entries.size(); i++) {
            Rank entry = entries.get(i);
            data[i][0] = i + 1;
            data[i][1] = entry.getUsername();
            data[i][2] = formatTime(entry.getFinishTime());
        }

        JTable table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}