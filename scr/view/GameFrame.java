package view;

import javax.swing.JFrame;

import model.LoadData;

public class GameFrame extends JFrame {

    private LoadData loadData;

    private GamePanel gamePanel;

    public GameFrame(int w, int h, int boom){

        loadData = new LoadData();

        add(gamePanel = new GamePanel(w, h , boom, this));

        setIconImage(loadData.getListImage().get("title")); //hiển thị icon của game

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)  ;
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new GameFrame(9, 9 , 10);
    }

    public LoadData getLoadData() {
        return loadData;
    }

    public void setLoadData(LoadData loadData) {
        this.loadData = loadData;
    }

}
