
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StatusButton extends JButton{
	
	//instance variables
	private String cameraOption;
	private StatusPanel panel;
		
	//constructor
	StatusButton(String text, StatusPanel panel){
		super(text);
		this.panel = panel;
		cameraOption = text;
		
		this.addActionListener(new AvatarButtonActionListener());
	}

		private class AvatarButtonActionListener implements ActionListener {

			//when button is pressed, set avatar to new avatar and make avatar movable by keyboard
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setStatusLabel(cameraOption);

			}

		}

	
}