//

package control;

import java.util.Random;

import javax.swing.JButton;

import view.ButtonPlay;
import view.ButtonSmile;
import view.LabelNumber;


public class World {

    private Random rd;

    private ButtonPlay[][] arrayButton;// mảng 2 chiều của số ô
    private int[][] arrayMin; // mảng 2 chiều chứa số bomb

    private ButtonSmile buttonSmile;
    private LabelNumber lbTime,lbBoom;



    public World(int w, int h, int boom){ //Constructor
        arrayButton = new ButtonPlay[w][h];
        arrayMin = new int[w][h];

        rd = new Random();

        creatArrayMin(boom,w ,h);
        dienSo(); // 

        for (int i = 0; i < arrayButton.length; i++) {
            for (int j = 0; j < arrayButton[i].length; j++) {
                System.out.print(arrayMin[i][j] + " ");
            }
            System.out.println();
        }
    }

    // điền số mìn vào ô
    private void dienSo() {
        for (int i = 0; i < arrayMin.length; i++) {
            for (int j = 0; j < arrayMin.length; j++) {
                if(arrayMin[i][j] == 0){
                    int count = 0;
                    for (int l = i - 1 ; l <= i + 1; l++) {
                        for (int k = j-1; k <= j + 1; k++) {
                            if (l >= 0 && l <= arrayMin.length - 1 && k > 0 && k < arrayMin[i].length -1){
                                if (arrayMin[l][k] == -1)
                                    count ++;
                            }
                        }
                    }
                    arrayMin[i][j] = count;
                }
            }
        }

    }
// method để tạo mìn
    public void creatArrayMin(int boom, int w , int h){ 
        int locationX = rd.nextInt(w);  //random vị trí của mìn
        int locationY = rd.nextInt(h);

        arrayMin[locationX][locationY] = -1;

        int count = 1;

        while (count != boom) {                //repeatedly generate random location until
            locationX = rd.nextInt(w);         // match the number of boom
            locationY = rd.nextInt(h);

            if (arrayMin[locationX][locationY] != -1){

                arrayMin[locationX][locationY] = -1;

                count = 0;
                for (int i = 0; i < arrayMin.length; i++) {
                    for (int j = 0; j < arrayMin.length; j++) {
                        if (arrayMin[i][j] == -1)
                            count ++;
                    }
                }
            }
        }
    }

    public boolean open(int i, int j) {
        int number = arrayMin[i][j];

        if (number != -1) {
            arrayButton[i][j].setNumber(number);
            arrayButton[i][j].repaint();
            
            return true;
        }else  
            return false;
    }

    //------------Get and set-----------------------------
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
//---------------------------------------


    
}
