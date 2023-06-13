import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int height = 400;
    int width = 400;
    int dotSize = 10;
    int maxDots = (height * width)/(dotSize * dotSize);
    int dots;
    int[] x = new int[maxDots];
    int[] y = new int[maxDots];
    int appleX;
    int appleY;
    Image head;
    Image body;
    Image apple;
    Timer timer;
    int delay = 150;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;
    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.BLACK);
        initialize();
        loadImage();
        timer = new Timer(delay, this);
        timer.start();
    }
    //Initialize game
    public void initialize(){
        //Initialize snake's length
        dots = 3;
        //Initialize Snake's position
        x[0] = 270;
        y[0] = 80;
        for(int i=0;i<dots;i++){
            x[i] = x[0] + (dotSize * i);
            y[i] = y[0];
        }
        //Randomly initialize Apple's position
        locateApple();
    }
    //Load images from resources
    public void loadImage(){
        ImageIcon appleIcon = new ImageIcon("src/Resources/Apple.png");
        apple = appleIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/Resources/Head.png");
        head = headIcon.getImage();
        ImageIcon bodyIcon = new ImageIcon("src/Resources/Body.png");
        body = bodyIcon.getImage();
    }
    //Draw images at snake's & apple's position
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for(int i=0;i<dots;i++){
                //Head
                if(i==0)
                    g.drawImage(head,x[0],y[0],this);
                else
                    g.drawImage(body,x[i],y[i],this);
            }
        }
        else{
            gameOver(g);
            timer.stop();
        }
    }
    //Randomize apple's position
    public void locateApple(){
        appleX = (int)(Math.random() * ((width/dotSize) - 1))*dotSize;
        appleY = (int)(Math.random() * ((height/dotSize) - 1))*dotSize;
    }
    //Border & body collisions
    public void checkCollision(){
        //Body Collision
        for(int i=1;i<dots;i++){
            if(x[0]==x[i] && y[0]==y[i])
                inGame = false;
        }
        //Border Collision
        if(x[0]<0)
            inGame = false;
        if(x[0]>=width)
            inGame = false;
        if(y[0]<0)
            inGame = false;
        if(y[0]>=height)
            inGame = false;
    }
    //Display Game Over
    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (dots - 3)*10;
        String scoremsg = "Your Score : "+score;
        Font small = new Font("Helvetica",Font.BOLD,width/10);
        FontMetrics fontMetrics = getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(width-fontMetrics.stringWidth(msg))/2,height/3);
        g.drawString(scoremsg,(width-fontMetrics.stringWidth(scoremsg))/2,(2*height)/3);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    //Snake's movement
    public void move(){
        for(int i=dots-1;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection)
            x[0] -= dotSize;
        if(rightDirection)
            x[0] += dotSize;
        if(upDirection)
            y[0] -= dotSize;
        if(downDirection)
            y[0] += dotSize;
    }
    //Snake eats apple
    public void checkApple(){
        if(appleX==x[0] && appleY==y[0]){
            dots++;
            locateApple();
        }
    }
    //Implement controls
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key==KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key==KeyEvent.VK_UP && !downDirection){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if(key==KeyEvent.VK_DOWN && !upDirection){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
