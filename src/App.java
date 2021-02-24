

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class App extends javax.swing.JFrame
{
	//instance variables
	private StatusPanel panel;
	private JPanel lightPanel;
	private MotionDetector detector;
	
	//constructor
    public App(String title) throws Exception
    {
    	
        super(title);
        this.setSize(500, 250);
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(4,1));
       panel = new StatusPanel();
       lightPanel = new JPanel();
       lightPanel.setBackground(new Color(150,0,150));
       
       
        //adding panels to the Frame
       
        this.add(panel);
        
        this.add(new StatusButton("ON", panel));
        this.add(new StatusButton("OFF", panel));
        this.add(lightPanel);
        
        
        
        this.setVisible(true);
  
        try{
     	   detector = new MotionDetector(lightPanel, panel);
        } catch (Exception e) {
     	   e.printStackTrace();
        }
        
    }

    //main method: runs the game
    public static void main(String[] args)
    {
        try{
        	App app = new App("Camereminder");
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}