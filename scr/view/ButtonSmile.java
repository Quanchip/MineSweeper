// this class use to custom smile button
package view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

public class ButtonSmile extends JButton {

    private PanelNotification p;

    public ButtonSmile(PanelNotification p){
        this.p = p;
        setPreferredSize(new Dimension(50, 50));
    }

    // lấy hình và vẽ mặt cười
    @Override
    public void paint(Graphics g) {
        g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("smile"),0,0,
            getPreferredSize().width , getPreferredSize().height ,null);
    }
}
