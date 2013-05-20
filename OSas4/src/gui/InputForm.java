package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import os.Statiniai;

import rm.Memory;
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
				if (readField.getText().length() % 4 == 0) { // jeigu ivesta pilna komandu seka po 4 simbolius komandoje
					RM.regOS = 1; // vadinas ivedimas gali buti tvarkingas. statom interrupta ir kisam i memory
					for (int i = 0; i < readField.getText().length() / 4; i++) {
						Memory.addWord(Statiniai.vietaMem, readField.getText().substring(i, i+4));
						Statiniai.vietaMem++;
					}
				}
				readField.setText("");
				System.out.println("readmem: "+Statiniai.readMem);
				for (int i = 0x0A00; i < Statiniai.vietaMem; i++)
					System.out.println("i: "+i+". "+String.valueOf(Memory.get()[i].getWord()));
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
