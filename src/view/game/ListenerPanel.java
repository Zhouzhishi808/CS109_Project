package view.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class is only to enable key events.
 */
public abstract class ListenerPanel extends JPanel {
    public ListenerPanel() {
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setFocusable(true);
    }

    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                    try {
                        doMoveRight();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                    try {
                        doMoveLeft();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                    try {
                        doMoveUp();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                    try {
                        doMoveDown();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            doMouseClick(e.getPoint());
        }
    }
    public abstract void doMouseClick(Point point);

    public abstract void doMoveRight() throws IOException;

    public abstract void doMoveLeft() throws IOException;

    public abstract void doMoveUp() throws IOException;

    public abstract void doMoveDown() throws IOException;


}
