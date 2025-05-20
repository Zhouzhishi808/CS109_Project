package view.game;

import controller.MusicController;
import model.Rank;
import model.RankManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import static controller.MusicController.isBGMMuted;

public class RankFrame extends JFrame {
    private final ArrayList<Rank> entries;
    private DefaultTableModel tableModel;

    public RankFrame(String levelName) {
        setTitle(levelName + " - 排行榜");
        setSize(400, 220);
        setLayout(new BorderLayout());

        entries = RankManager.loadRankData(levelName);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu sortMenu = new JMenu("排序方式");
        menuBar.add(sortMenu);

        // 按时间排序菜单项
        JMenuItem sortByTime = new JMenuItem("按时间排序");
        sortByTime.addActionListener(e -> {
            entries.sort(Comparator.comparingInt(Rank::getFinishTime));
            refreshTable();
        });
        sortMenu.add(sortByTime);

        // 按步数排序菜单项
        JMenuItem sortByStep = new JMenuItem("按步数排序");
        sortByStep.addActionListener(e -> {
            entries.sort(Comparator.comparingInt(Rank::getStep));
            refreshTable();
        });
        sortMenu.add(sortByStep);

        // 初始化表格
        setupTable();
        setLocationRelativeTo(null);
    }

    private void setupTable() {
        String[] columns = {"排名", "用户名", "时间", "步数"};
        Object[][] data = generateData();
        tableModel = new DefaultTableModel(data, columns);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private Object[][] generateData() {
        Object[][] data = new Object[entries.size()][4];
        for (int i = 0; i < entries.size(); i++) {
            Rank entry = entries.get(i);
            data[i][0] = i + 1; // 排名根据当前顺序生成
            data[i][1] = entry.getUsername();
            data[i][2] = formatTime(entry.getFinishTime());
            data[i][3] = entry.getStep();
        }
        return data;
    }

    private void refreshTable() {
        Object[][] newData = generateData();
        tableModel.setDataVector(newData, new String[]{"排名", "用户名", "时间", "步数"});
        tableModel.fireTableDataChanged(); // 通知表格数据更新
    }

    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}