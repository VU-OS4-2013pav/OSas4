package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

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
	JLabel readLabel, processLabel, resourcesLabel, waitingLabel;
	JTextField readField;
	static JTextArea processField;
	static JTextArea resourcesField, waitingField;
	JScrollPane scrollProcess, scrollResources, scrollWaiting;
	JPanel formPanel;
	
	public static GUI gui;
	
	public InputForm() {
		super("Input Form");
		formPanel = new JPanel();

		// procesu langas
		processLabel = new JLabel("Procesai: ");
		formPanel.add(processLabel);
		
		processField = new JTextArea(11, 52);
		processField.setEditable(false);
		scrollProcess = new JScrollPane(processField);
		processField.setWrapStyleWord(true);
		processField.setLineWrap(true);
		
		formPanel.add(scrollProcess);
		
		// resursu
		resourcesLabel = new JLabel("Resursai: ");
		formPanel.add(resourcesLabel);
		
		resourcesField = new JTextArea(11, 52);
		resourcesField.setEditable(false);
		scrollResources = new JScrollPane(resourcesField);
		resourcesField.setWrapStyleWord(true);
		resourcesField.setLineWrap(true);
		
		formPanel.add(scrollResources);
		
		// waiting for list
		waitingLabel = new JLabel("Kas ko laukia:");
		formPanel.add(waitingLabel);
		
		waitingField = new JTextArea(11, 52);
		waitingField.setEditable(false);
		scrollWaiting = new JScrollPane(waitingField);
		waitingField.setWrapStyleWord(true);
		waitingField.setLineWrap(true);
		
		formPanel.add(scrollWaiting);
		
		
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
//				System.out.println("readmem: "+Statiniai.readMem);
//				for (int i = 0x0A00; i < Statiniai.vietaMem; i++)
//					System.out.println("i: "+i+". "+String.valueOf(Memory.get()[i].getWord()));
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
      	this.setMaximumSize(new Dimension(700, 760));
      	this.setBounds(716, 0, 650, 700);
      	
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
			if (RSS.list.get(i).resourceDescriptor != null)
				resourcesField.append(RSS.list.get(i).resourceDescriptor.toString()+"\n");
		}
		for (i = 0; i < VRSS.list.size(); i++) {
			for (j = 0; j < VRSS.list.get(i).resourceList.size(); i++)
				resourcesField.append(VRSS.list.get(i).resourceList.get(j).toString()+"\n");
		}
		
		waitingField.setText("");
		for (i = 0; i < RSS.list.size(); i++) {
			if (!RSS.list.get(i).list.isEmpty()) {
				waitingField.append("Resurso "+RSS.list.get(i).resourceDescriptor.nameO+" laukia:\n");
				for (j = 0; j < RSS.list.get(i).list.size(); j++)
					waitingField.append("  "+RSS.list.get(i).list.get(j).process.nameI+" "+RSS.list.get(i).list.get(j).process.nameO+"\n");
			}
		}
		for (i = 0; i < VRSS.list.size(); i++) {
			if (!VRSS.list.get(i).processList.isEmpty()) {
				waitingField.append("Resurso "+VRSS.list.get(i).vardas+" laukia:\n");
				for (j = 0; j < VRSS.list.get(i).processList.size(); j++)
					waitingField.append("  "+VRSS.list.get(i).processList.get(j).process.nameI+" "+VRSS.list.get(i).processList.get(j).process.nameO+"\n");
			}
		}
		
		
	}
	
	public static void refreshAll() {
//		Scanner scan = new Scanner(System.in);
//		scan.nextLine();
		
		InputForm.refresh();
		GUI.refresh();
	}
	

	

}
