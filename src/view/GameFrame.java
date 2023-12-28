//This class is for adding the game modes objects into the frame
// It also handles starting/ending the app

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.LoadData;

public class GameFrame extends JFrame {

	private LoadData loadData;

	private GamePanel gamePanel;

	private JMenuBar mnb;
	private JMenu menu, undo_redo;
	private JMenuItem Easy, nomal, hard, newGame, exit, undo, redo;

	public GameFrame(int w, int h, int boom) {

		loadData = new LoadData();

		setJMenuBar(mnb = new JMenuBar());
		mnb.add(menu = new JMenu("Game"));
		mnb.add(undo_redo = new JMenu("Undo/Redo"));

		menu.add(newGame = new JMenuItem("New game"));
		menu.addSeparator();
		menu.add(Easy = new JMenuItem("Easy"));
		menu.add(nomal = new JMenuItem("Nomal"));
		menu.add(hard = new JMenuItem("Hard"));
		menu.addSeparator();
		menu.add(exit = new JMenuItem("Exit"));

		undo_redo.add(undo = new JMenuItem("Undo"));
		undo_redo.add(redo = new JMenuItem("Redo"));

		if (w == 8) {
			Easy.setIcon(new ImageIcon(loadData.getListImage().get("tich")));
		} else if (w == 16) {
			nomal.setIcon(new ImageIcon(loadData.getListImage().get("tich")));
		} else {
			hard.setIcon(new ImageIcon(loadData.getListImage().get("tich")));
		}

		Easy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new GameFrame(8, 8, 10);
			}
		});

		nomal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new GameFrame(16, 16, 40);
			}
		});

		hard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new GameFrame(16, 30, 99);
			}
		});

		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new GameFrame(w, h, boom);
			}
		});

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.undo();
			}
		});

		redo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.redo();
			}
		});

		add(gamePanel = new GamePanel(w, h, boom, this));

		setIconImage(loadData.getListImage().get("title"));
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new GameFrame(8, 8, 10);
	}

	public LoadData getLoadData() {
		return loadData;
	}

	public void setLoadData(LoadData loadData) {
		this.loadData = loadData;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

}
