//Custom button that displays image from PanelPlayer

package view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

public class ButtonPlay extends JButton {
    
    private PanelPlayer p;
    private int number; 

    public ButtonPlay(PanelPlayer p){
        number = -1;
        this.p = p;
        setPreferredSize(new Dimension(30,30));
    }

    @Override
    public void paint(Graphics g) {
        switch (number) {
            
            case -1:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("nouse"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                break;

            case 0:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b0"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                break;
            case 1:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b1"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                 break;   

            case 2:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b2"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                break;   

            case 3:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b3"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                 break;  

            case 4:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b4"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                break;   

            case 5:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b5"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                break;   

            case 6:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b6"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                break;   

            case 7:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b7"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                break;   

            case 8:
                g.drawImage(p.getGame().getGameFrame().getLoadData().getListImage().get("b8"), 0, 0,
                            getPreferredSize().width, getPreferredSize().height, null);
                break;    

            default:
                break;
        }
    }

    public void setNumber(int number){
        this.number = number;
    }

}
