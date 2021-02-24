import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
	
	private JLabel statusLabel;
	
	public StatusPanel() {
		
		statusLabel = new JLabel();
		statusLabel.setText("Camera is ON");
		
		 this.add(statusLabel);
	     statusLabel.setVisible(true);
	}
     
	public void setStatusLabel(String updatedText) {
		statusLabel.setText("Camera is " +updatedText);
	}
	
	public String getLabelText() {
		return statusLabel.getText();
	}
    
}
