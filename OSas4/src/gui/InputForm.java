package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Procesai.PL;

import os.Statiniai;

import resources.RSS;
import resources.VRSS;
import rm.GUI;
import rm.Memory;
import rm.RM;

public class InputForm extends JFrame {
	JButton readLineButton, RMbutton;
	JLabel readLabel, processLabel, resourcesLabel;
	JTextField readField;
	static JTextArea processField;
	static JTextArea resourcesField;
	JScrollPane scrollProcess, scrollResources;
	JPanel formPanel;
	
	GUI gui;
	
	
	
	public InputForm() {
		super("Input Form");
		formPanel = new JPanel();

		// procesu langas
		processLabel = new JLabel("Procesai: ");
		formPanel.add(processLabel);
		
		processField = new JTextArea(15, 52);
		processField.setEditable(false);
		scrollProcess = new JScrollPane(processField);
		processField.setWrapStyleWord(true);
		processField.setLineWrap(true);
		
		formPanel.add(scrollProcess);
		
		// resursu
		resourcesLabel = new JLabel("Resursai: ");
		formPanel.add(resourcesLabel);
		
		resourcesField = new JTextArea(15, 52);
		resourcesField.setEditable(false);
		scrollResources = new JScrollPane(resourcesField);
		resourcesField.setWrapStyleWord(true);
		resourcesField.setLineWrap(true);
		
		formPanel.add(scrollResources);
		
		// konsole
		readLabel = new JLabel("Ivedimo konsole: ");
		formPanel.add(readLabel);
		
		readLineButton = new JButton("OK");
		readLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (readField.getText().length() % 4 == 0) { // jeigu ivesta pilna komandu seka po 4 simbolius komandoje
					RM.regOS = 1; // vadinas ivedimas gali buti tvarkingas. statom interrupta ir kisam i memory
					for (int i = 0; i < readField.getText().length() / 4; i++) {
						Memory.addWord(Statiniai.vietaMem, readField.getText().substring(i*4, i*4+4));
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
		
		formPanel.add(readField);
		formPanel.add(readLineButton);
		RMbutton = new JButton("Rodyti RM ir Memory");
		RMbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Rodyti RM ir Memory")) {
					gui.showHide();
				}
				
			}
		});
		formPanel.add(RMbutton);
		add(formPanel);
		
		
		this.setMinimumSize(new Dimension(600, 600));
      	this.setMaximumSize(new Dimension(700, 700));
      	this.setBounds(716, 0, 650, 650);
      	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		gui = new GUI();
	}
	
	public static void refresh() {
		int i, j;
		
		processField.setText("");
		for (i = 0; i < PL.processList.size(); i++) {
			for (j = 0; j < PL.processList.get(i).processList.size(); j++) {
				processField.append(PL.processList.get(i).processList.get(j).toString()+"\n");
			}
		}
		
		resourcesField.setText("");
		for (i = 0; i < RSS.list.size(); i++) {
			resourcesField.append(RSS.list.get(i).resourceDescriptor.toString()+"\n");
		}
		for (i = 0; i < VRSS.list.size(); i++) {
			for (j = 0; j < VRSS.list.get(i).resourceList.size(); i++)
				resourcesField.append(VRSS.list.get(i).resourceList.get(j).toString()+"\n");
		}
		
		
	}
	

	

}
