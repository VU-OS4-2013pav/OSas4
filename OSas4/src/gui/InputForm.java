package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import os.Statiniai;

import rm.RM;

public class InputForm extends JFrame {
	JButton readLineButton;
	JTextField readField;
	JPanel formPanel;
	
	public InputForm() {
		super("Input Form");
		readLineButton = new JButton("OK");
		readLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RM.regOS = 1;
			}
		});
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
