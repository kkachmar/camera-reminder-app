import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;

public class MotionDetector {

	private Scanner scanner;
	private int distance;
	private boolean tooFar;
	private Light light;
	private int warningLength;
	private int warningCounter;
	private JPanel lightBox;
	private StatusPanel statusPanel;
	private File audioFile;
	
	private AudioInputStream ais;
	private Clip clip;
	private DurationTimer durationTimer;
	
	public MotionDetector(JPanel lightBox, StatusPanel statusPanel) throws Exception {
		this.statusPanel = statusPanel;
		this.lightBox = lightBox;
		distance = 0;
		tooFar = false;
		light = new Light();
		scanner = new Scanner(System.in);
		clip = AudioSystem.getClip();
		System.out.println("Enter the length of the warning alert in seconds (whole numbers only).");
		warningLength = scanner.nextInt();
		warningCounter = 0;
		System.out.println("(Enter user distance in feet at any time for simulation purposes.)");

		audioFile = new File("resources/buzz.wav");
		
		durationTimer = new DurationTimer();
		durationTimer.start();
		
		update();
	}
	
	public void update() {
		while (true) {
			if (statusPanel.getLabelText().equals("Camera is ON")) {
				scan();
				if (distance >= 6 && !tooFar) {
					tooFar = true;
				} else if (distance < 6) {
					tooFar = false;
					warningCounter = 0;
				}
				flashLight();
				try {
					playSound();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				clip.stop();
				clip.close();
				light.setLight(false);
			}
		}
	}

	public void scan() {
		//scan the distance between the sensor and the user
		distance = scanner.nextInt();
	}
	
	public void flashLight() {
		//flash the camera light until user approaches
		if (tooFar) {
			light.setLight(true);
		} else light.setLight(false);
	}
	
	public void playSound() throws Exception {
		if (tooFar && warningCounter<warningLength) {
			if (!clip.isActive()) {
			ais = AudioSystem.getAudioInputStream(audioFile);
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();	
			}
		} else {
			clip.stop();
			clip.close();
		}
	}
	
	private class Light {
		
		private Timer timer;
		private boolean lightOn;
		
		public Light() {
			lightOn = false;
			timer = new Timer(1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (lightOn) {
						lightBox.setBackground(new Color(150,0,150));;
						warningCounter++;
						lightOn = false;
					} else {
						lightBox.setBackground(new Color(200,200,0));;
						warningCounter++;
						lightOn = true;
					}
					if (warningCounter == warningLength) {
						clip.stop();
						clip.close();
						light.setLight(false);
						
					}
				}
			});
		}
		
		public void setLight(boolean on) {
			if (on && warningCounter<warningLength) {
				timer.start();
				
			} else {
				if (lightOn) {
					lightBox.setBackground(new Color(150,0,150));;
					lightOn = false;
				}
				timer.stop();
			}
		}
		
	}
	
	private class DurationTimer {
		private Timer totalTimer;
		private int timePassed; 
		
		public DurationTimer() {
			timePassed = 0;
			totalTimer = new Timer(1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					timePassed++;
				
					if (timePassed%7 == 0 && statusPanel.getLabelText().equals("Camera is ON")) {
						System.out.println("Hey! Your webcam is on!");
						JFrame popUp = new JFrame("Reminder");
						popUp.setSize(300, 250);
						popUp.setLayout(new BorderLayout());
						JLabel reminder = new JLabel("     Reminder! Your camera is on!");
						popUp.add(BorderLayout.WEST, new JLabel(" "));
						popUp.add(BorderLayout.CENTER, reminder);
						
						JButton terminate = new JButton ("Dismiss");
						terminate.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								popUp.dispose();
								
							}
							
						});
						popUp.add(BorderLayout.SOUTH, terminate);
						popUp.setVisible(true);
					}
						
				}
				
			});
			
		}
		public void start(){
			totalTimer.start();
		}
	}
	
	private class PopUpPanel{
		
	}
	
}
