package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputForm extends JFrame {
	JButton readLineButton;
	JTextField readField;
	JPanel formPanel;
	
	public InputForm() {
		super("Input Form");
		readLineButton = new JButton("OK");
		readField = new JTextField(20);
		formPanel = new JPanel();
		formPanel.add(readField);
		formPanel.add(readLineButton);
		add(formPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
