import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener {


    static  final  int SCREEN_WIDTH = 600 ;
    static final  int SCREEN_HIGHT  = 600;
    static final  int UNIT_SIZE = 25;
    static final  int GAME_UNITS = ( SCREEN_WIDTH * SCREEN_HIGHT)/ UNIT_SIZE ;
    static final  int DELAY  =75 ;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6 ;
    int applesEaten  ;
     int applex;
     int appley;
     char direction = 'R';
     boolean running = false ;
     Timer timer ;
     Random random ;



    gamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdpter());
        startGame();


   }
   public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

   }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void  draw(Graphics g){

        if(running) {
            for (int i = 0; i < SCREEN_HIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
        else {
            gameOver(g);
        }
   }
    public void newApple() {
            applex = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
            appley = random.nextInt((int)(SCREEN_HIGHT/UNIT_SIZE))*UNIT_SIZE;

    }

    public  void move(){
        for(int i = bodyParts ; i>0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U' :
                y[0] = y[0] -UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L' :
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R' :
                x[0] = x[0] + UNIT_SIZE;
                break;


        }


   }
    public void checkApple() {
        // check if the head of the snake collides with the apple
        if((x[0] == applex) && (y[0] == appley)) {
            // increase the number of body parts by 1
            bodyParts++;
            // increase the number of apples eaten by 1
            applesEaten++;
            // generate a new apple at a random location
            newApple();
        }
    }

    public void checkCollision() {
        // check if head collision with body
        for (int i = bodyParts - 1; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // check if head touches the left border
        if (x[0] < 0) {
            running = false;
        }
        // check if head touches the right border
        if (x[0] > SCREEN_WIDTH - UNIT_SIZE) {
            running = false;
        }
        // check if head touches the top border
        if (y[0] < 0) {
            running = false;
        }
        // check if head touches the bottom border
        if (y[0] > SCREEN_HIGHT - UNIT_SIZE) {
            running = false;
        }
    }

    public void gameOver(Graphics g){
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD , 75));
            FontMetrics metrics = getFontMetrics(g.getFont()) ;
            g.drawString("Game Over" , (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2 ,SCREEN_HIGHT/2);
            String apple_count = Integer.toString(applesEaten) ;
            g.drawString("Score:" + " " + apple_count,SCREEN_WIDTH/2,SCREEN_HIGHT/4);

   }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();

    }

    public  class  myKeyAdpter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT :
                    if(direction != 'R'){
                        direction = 'L';

                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';

                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';

                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';

                    }
                    break;
            }

        }
    }
}
