//this class represent panel within game interface
//display information such as number of bombs, time, smile button
package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelNotification extends JPanel {

    private JPanel   p11, p12, p13;  

    private GamePanel game;

    private ButtonSmile bt;

    private LabelNumber lbBoom, lbTime;


    public PanelNotification(GamePanel game){ //constructor
        this.game = game;

        lbTime = game.getWorld().getLbTime();

        lbBoom = game.getWorld().getLbBoom();

        bt = game.getWorld().getButtonSmile();

        setLayout(new BorderLayout());

        setBorder(BorderFactory.createLoweredBevelBorder());
        add(p11 = new JPanel(), BorderLayout.WEST);
        add(p12 = new JPanel(), BorderLayout.EAST);
        add(p13 = new JPanel(), BorderLayout.CENTER);

        p11.add(lbBoom = new LabelNumber(this, "000"));
        p12.add(lbTime = new LabelNumber(this ,"000"));
        p13.add(bt = new ButtonSmile(this));
    }

//--------------------Get and set------------
    public GamePanel getGame() {
        return game;
    }

    public void setGame(GamePanel game) {
        this.game = game;
    }
}
