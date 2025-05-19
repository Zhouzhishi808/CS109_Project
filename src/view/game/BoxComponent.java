package view.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoxComponent extends JComponent {
    private BufferedImage image;
    private int row;
    private int col;
    private boolean isSelected;


    public BoxComponent(String imagePath, int row, int col) {
        try {
            this.image = ImageIO.read(new File(imagePath));// 加载本地图片
        } catch (IOException e) {
            e.printStackTrace();
            this.image = null;
        }
        this.row = row;
        this.col = col;
        isSelected = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // 绘制图片（自适应组件大小）
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
        Border border ;
        if(isSelected){
            border = BorderFactory.createLineBorder(Color.red,1);
        }else {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
        }
        this.setBorder(border);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        this.repaint();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
