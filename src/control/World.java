//

package control;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import view.GamePanel;

import view.ButtonPlay;
import view.ButtonSmile;
import view.LabelNumber;

public class World {

    private Random rd;

    private Stack<int[]> flagHistory = new Stack<>();
    private Stack<int[]> unflagHistory = new Stack<>();

    private ButtonPlay[][] arrayButton;// mảng 2 chiều của số ô
    private int[][] arrayMin; // mảng 2 chiều chứa số bomb

    private ButtonSmile buttonSmile;
    private LabelNumber lbTime, lbBoom;

    private boolean isComplete, isEnd;
    private boolean[][] arrayBoolean, arrayCamCo;

    private int co;
    private int boom;

    private GamePanel game;

    public World(int w, int h, int boom, GamePanel game) { // Constructor

        this.game = game;
        this.boom = boom;

        arrayButton = new ButtonPlay[w][h];
        arrayMin = new int[w][h];
        arrayBoolean = new boolean[w][h];
        arrayCamCo = new boolean[w][h];

        rd = new Random();

        createArrayMin(boom, w, h);
        dienSo();

    }

    // điền số mìn vào ô
    public void dienSo() {
        for (int i = 0; i < arrayMin.length; i++) {
            for (int j = 0; j < arrayMin[i].length; j++) {
                if (arrayMin[i][j] == 0) {
                    int count = 0;
                    for (int l = i - 1; l <= i + 1; l++) {
                        for (int k = j - 1; k <= j + 1; k++) {
                            if (l >= 0 && l <= arrayMin.length - 1 && k >= 0 && k <= arrayMin[i].length - 1)
                                if (arrayMin[l][k] == -1) {
                                    count++;
                                }
                        }
                    }
                    arrayMin[i][j] = count;
                }
            }
        }
    }

    // method để tạo mìn
    public void createArrayMin(int boom, int w, int h) {
        int locationX = rd.nextInt(w);
        int locationY = rd.nextInt(h);

        arrayMin[locationX][locationY] = -1;
        int count = 1;
        while (count != boom) {
            locationX = rd.nextInt(w);
            locationY = rd.nextInt(h);
            if (arrayMin[locationX][locationY] != -1) {

                arrayMin[locationX][locationY] = -1;

                count = 0;
                for (int i = 0; i < arrayMin.length; i++) {
                    for (int j = 0; j < arrayMin[i].length; j++) {
                        if (arrayMin[i][j] == -1)
                            count++;
                    }
                }
            }
        }

    }

    // ---------------------------------------
    public boolean open(int i, int j) { // Now use iterative method
        /*
         * This method implements "Flood Fill" algorithm
         * which is a derivative of BFS. It is is used to reveal
         * all neighboring cells at the same level before moving
         * to the next level.
         */
        if (this.isComplete || this.isEnd) {
            return false;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { i, j });

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0];
            int y = cell[1];

            if (x < 0 || x >= this.arrayMin.length || y < 0 || y >= this.arrayMin[x].length
                    || this.arrayBoolean[x][y]) {
                continue;
            }

            this.arrayBoolean[x][y] = true;
            if (this.arrayMin[x][y] == -1) {
                this.arrayButton[x][y].setNumber(11);
                this.arrayButton[x][y].repaint();
                this.isComplete = true;
                revealAllMines();
                return false;
            }

            this.arrayButton[x][y].setNumber(this.arrayMin[x][y]);
            this.arrayButton[x][y].repaint();

            if (this.arrayMin[x][y] == 0) {
                for (int l = x - 1; l <= x + 1; l++) {
                    for (int k = y - 1; k <= y + 1; k++) {
                        if (l != x || k != y) {
                            queue.add(new int[] { l, k });
                        }
                    }
                }
            }

            if (this.checkWin()) {
                this.isEnd = true;
                return false;
            }
        }

        return true;
    }

    // This is the reveal all mines method
    private void revealAllMines() {
        for (int l = 0; l < this.arrayBoolean.length; l++) {
            for (int k = 0; k < this.arrayBoolean[l].length; k++) {
                if (this.arrayMin[l][k] == -1 && !this.arrayBoolean[l][k]) {
                    this.arrayButton[l][k].setNumber(10);
                    this.arrayButton[l][k].repaint();
                }
            }
        }
    }
    // ---------------------------------------

    public void camCo(int i, int j) { // This implements undo/redo feature
        if (!this.arrayBoolean[i][j]) {
            if (this.arrayCamCo[i][j]) {
                --this.co;
                this.arrayCamCo[i][j] = false;
                this.arrayButton[i][j].setNumber(-1);
                this.arrayButton[i][j].repaint();
                this.game.getP1().updateLbBoom();

                // Add the action to the unflag history
                unflagHistory.push(new int[] { i, j });
            } else if (this.co < this.boom) {
                ++this.co;
                this.arrayCamCo[i][j] = true;
                this.arrayButton[i][j].setNumber(9);
                this.arrayButton[i][j].repaint();
                this.game.getP1().updateLbBoom();

                // Add the action to the flag history
                flagHistory.push(new int[] { i, j });
            }
        }
    }

    public void undo() {
        if (!flagHistory.isEmpty()) {
            int[] lastFlag = flagHistory.pop();
            camCo(lastFlag[0], lastFlag[1]);
        }
    }

    public void redo() {
        if (!unflagHistory.isEmpty()) {
            int[] lastUnflag = unflagHistory.pop();
            camCo(lastUnflag[0], lastUnflag[1]);
        }
    }

    // ---------------------------------------

    public boolean checkWin() { // Linear Traversal
        int count = 0;

        for (int i = 0; i < this.arrayBoolean.length; ++i) {
            for (int j = 0; j < this.arrayBoolean[i].length; ++j) {
                if (!this.arrayBoolean[i][j]) {
                    ++count;
                }
            }
        }

        if (count == this.boom) {
            return true;
        } else {
            return false;
        }
    }

    // ---------------------------------------

    //Handles doubclicking a cell
    public boolean clickDouble(int i, int j) {
        boolean isCoMin = false;

        outerloop: for (int l = i - 1; l <= i + 1; ++l) {
            for (int k = j - 1; k <= j + 1; ++k) {
                if (l >= 0 && l <= this.arrayMin.length - 1 && k >= 0 && k <= this.arrayMin[i].length - 1
                        && !this.arrayCamCo[l][k]) {
                    if (this.arrayMin[l][k] == -1) {
                        isCoMin = true;
                        revealCell(l, k, 12);
                        break outerloop;
                    } else if (!this.arrayBoolean[l][k]) {
                        if (this.arrayMin[l][k] == 0) {
                            this.open(l, k);
                        } else {
                            revealCell(l, k, this.arrayMin[l][k]);
                        }
                    }
                }
            }
        }

        if (!isCoMin) {
            return true;
        } else {
            Arrays.fill(this.arrayBoolean, Boolean.TRUE);
            return false;
        }
    }

    //Reveal given cell
    private void revealCell(int i, int j, int number) {
        this.arrayButton[i][j].setNumber(number);
        this.arrayButton[i][j].repaint();
        this.arrayBoolean[i][j] = true;
    }

    // ---------------------------------------

    //Set cells across the grid to all true
    public void fullTrue() { // Linear Traversal
        for (int i = 0; i < this.arrayBoolean.length; ++i) {
            for (int j = 0; j < this.arrayBoolean[i].length; ++j) {
                if (!this.arrayBoolean[i][j]) {
                    this.arrayBoolean[i][j] = true;
                }
            }
        }

    }

    // ------------Get and set-----------------------------
    public ButtonPlay[][] getArrayButton() {
        return arrayButton;
    }

    public void setArrayButton(ButtonPlay[][] arrayButton) {
        this.arrayButton = arrayButton;
    }

    public ButtonSmile getButtonSmile() {
        return buttonSmile;
    }

    public void setButtonSmile(ButtonSmile buttonSmile) {
        this.buttonSmile = buttonSmile;
    }

    public LabelNumber getLbTime() {
        return lbTime;
    }

    public void setLbTime(LabelNumber lbTime) {
        this.lbTime = lbTime;
    }

    public LabelNumber getLbBoom() {
        return lbBoom;
    }

    public void setLbBoom(LabelNumber lbBoom) {
        this.lbBoom = lbBoom;
    }

    public boolean[][] getArrayBoolean() {
        return arrayBoolean;
    }

    public void setArrayBoolean(boolean[][] arrayBoolean) {
        this.arrayBoolean = arrayBoolean;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public boolean[][] getArrayCamCo() {
        return arrayCamCo;
    }

    public void setArrayCamCo(boolean[][] arrayCamCo) {
        this.arrayCamCo = arrayCamCo;
    }

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }
}
