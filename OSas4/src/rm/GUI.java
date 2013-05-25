package rm;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener {
	private static final int WIDTH = 450, HEIGHT = 750;
	
	JButton showHDDTable = new JButton("Show HDD table");
	JFrame hddFrame;
	private int showFrom = 0;
	private int showTo = Memory.MEMORY_SIZE-1;
	private int showFromHDD = 0;
	private int showToHDD = HDD.HDD_SIZE-1;
	TableRowSorter<TableModel> sorter;
	TableRowSorter<TableModel> HDDsorter;
	JPanel hddMemoryPanel = new JPanel(); //
	JTextField fromFieldHDD = new JTextField(5);
	JTextField toFieldHDD = new JTextField(5);
	JPanel memoryPanel = new JPanel();
	JLabel from = new JLabel("Show memory from: ");
	JLabel to = new JLabel("to: ");
	JLabel HDDfrom = new JLabel("Show memory from: ");
	JLabel HDDto = new JLabel("to: ");
	JTextField fromField = new JTextField(5);
	JTextField toField = new JTextField(5);
	JButton showButton = new JButton("Show");
	JButton hddShowButton = new JButton("Show HDD");
	
	JRadioButton decButton = new JRadioButton("Decimal numbers");
	JRadioButton hexButton = new JRadioButton("Hexadecimal numbers");
	JRadioButton HDDdecButton = new JRadioButton("Decimal numbers ");
	JRadioButton HDDhexButton = new JRadioButton("Hexadecimal numbers ");
	
	JPanel framePanel = new JPanel();
	JPanel registersPanel = new JPanel();
	JPanel panelRM = new JPanel();
	JLabel AA = new JLabel("AA");
	JLabel BB = new JLabel("BB");
	JLabel CC = new JLabel("CC");
	JLabel SP = new JLabel("SP");
	JLabel PC = new JLabel("PC");
	JLabel PTR = new JLabel("PTR");
	JLabel SI = new JLabel("SI");
	JLabel DI = new JLabel("DI");
	JLabel TI = new JLabel("TI");
	JLabel PI = new JLabel("PI");
	JLabel SR = new JLabel("SR");
	JLabel MODE = new JLabel("MODE");
	JTextField[] fieldsRM = new JTextField[12];
	
	JPanel panelMD = new JPanel();
	JLabel IA = new JLabel("IA");
	JLabel IO = new JLabel("IO");
	JLabel OA = new JLabel("OA");
	JLabel OO = new JLabel("OO");
	JTextField[] fieldsMD = new JTextField[4];
	
	JPanel panelButtons = new JPanel();
//	JButton nextCommand = new JButton("Next");
	
	JScrollPane scrollPane;
	JTable memoryTable;
	
	JScrollPane HDDscrollPane;
	JTable HDDmemoryTable;
	
//	JScrollPane scrollConsole;
//	static JTextArea console = new JTextArea();
	
	public GUI() {
		showHDDTable.addActionListener(this);
		for (int i = 0; i < 6; i++) {
			fieldsRM[i] = new JTextField(3);
			fieldsRM[i].setText(RM.registerToString(i));
			fieldsRM[i].setEditable(false);
		}
		
		for (int i = 6; i < 11; i++) {
			fieldsRM[i] = new JTextField(3);
			fieldsRM[i].setText(RM.registerToString1B(i));
			fieldsRM[i].setEditable(false);
		}
		
		
		fieldsRM[11] = new JTextField(3);
		fieldsRM[11].setText(String.format("%d", RM.MODE));
		fieldsRM[11].setEditable(false);
		
		registersPanel.setLayout(new BoxLayout(registersPanel, BoxLayout.Y_AXIS));
		registersPanel.add(createPanel(PC, fieldsRM[0]));
		registersPanel.add(createPanel(AA, fieldsRM[1]));
		registersPanel.add(createPanel(BB, fieldsRM[2]));
		registersPanel.add(createPanel(CC, fieldsRM[3]));
		registersPanel.add(createPanel(PTR, fieldsRM[4]));
		registersPanel.add(createPanel(SP, fieldsRM[5]));
		registersPanel.add(createPanel(SR, fieldsRM[6]));
		registersPanel.add(createPanel(SI, fieldsRM[7]));
		registersPanel.add(createPanel(PI, fieldsRM[8]));
		registersPanel.add(createPanel(DI, fieldsRM[9]));
		registersPanel.add(createPanel(TI, fieldsRM[10]));
		registersPanel.add(createPanel(MODE, fieldsRM[11]));
		
		panelRM.setLayout(new BoxLayout(panelRM, BoxLayout.X_AXIS));
		panelRM.add(registersPanel);
		
		createMemoryTable();
		memoryPanel.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.VERTICAL;
		cons.weightx = 1;
		cons.gridx = 0;
		memoryPanel.add(radioButtonsPanel(decButton, hexButton), cons);
		memoryPanel.add(createMemoryFieldsPanel(fromField, toField, showButton, from, to), cons);
		memoryPanel.add(scrollPane, cons);
		
		panelRM.add(memoryPanel);
		
		createHDDMemoryTable();
		hddMemoryPanel.setLayout(new GridBagLayout());
		GridBagConstraints consH = new GridBagConstraints();
		consH.fill = GridBagConstraints.VERTICAL;
		consH.weightx = 1;
		consH.gridx = 0;
		hddMemoryPanel.add(radioButtonsPanel(HDDdecButton, HDDhexButton), consH);
		hddMemoryPanel.add(createMemoryFieldsPanel(fromFieldHDD, toFieldHDD, hddShowButton, HDDfrom, HDDto), consH);
		hddMemoryPanel.add(HDDscrollPane, consH);
		
		hddFrame = new JFrame("HDD");
		hddFrame.add(hddMemoryPanel);
		hddFrame.pack();
		hddFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.Y_AXIS));
		framePanel.add(panelRM);
        for (int i = 2; i < 4; i++) {
			fieldsMD[i] = new JTextField(3);
			fieldsMD[i].setText(ChannelDevice.registerToString1B(i));
			fieldsMD[i].setEditable(false);
		}
        
        for (int i = 0; i < 2; i++) {
			fieldsMD[i] = new JTextField(3);
			fieldsMD[i].setText(ChannelDevice.registerToString(i));
			fieldsMD[i].setEditable(false);
		}
        
        panelMD.add(createPanel(IA, fieldsMD[0]));
        panelMD.add(createPanel(OA, fieldsMD[1]));
        panelMD.add(createPanel(IO, fieldsMD[2]));
        panelMD.add(createPanel(OO, fieldsMD[3]));      
        framePanel.add(panelMD);
        
//        panelButtons.add(nextCommand);
        panelButtons.add(showHDDTable);
        framePanel.add(panelButtons);
        
//        scrollConsole = new JScrollPane(console);
//        console.setWrapStyleWord(true);
//        console.setLineWrap(true);
//        framePanel.add(scrollConsole);      

//        nextCommand.addActionListener(this);
        add(framePanel);
        
        framePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    //    framePanel.setMaximumSize(new Dimension(700, 700));
        
        
      	panelRM.setMaximumSize(new Dimension(WIDTH, 100));
      	panelMD.setMaximumSize(new Dimension(WIDTH, 50));
      	panelButtons.setMaximumSize(new Dimension(WIDTH, 50));
//      	scrollConsole.setSize(WIDTH, 200);
      	
      	this.setBounds(0, 0, WIDTH, HEIGHT-250);
      	this.setMinimumSize(new Dimension(WIDTH, HEIGHT-250));
      	this.setMaximumSize(new Dimension(WIDTH+50, HEIGHT));
        
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private JPanel radioButtonsPanel(JRadioButton button1, JRadioButton button2) {
		JPanel radioButtonsPanel = new JPanel();
		button1.setSelected(true);
		radioButtonsPanel.setLayout(new BoxLayout(radioButtonsPanel, BoxLayout.X_AXIS));
		button1.addActionListener(this);
		button2.addActionListener(this);
		radioButtonsPanel.add(button1);
		radioButtonsPanel.add(button2);
		return radioButtonsPanel;
	}
	
	private JPanel createMemoryFieldsPanel(JTextField field1, JTextField field2, JButton button, JLabel label1, JLabel label2) {
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.add(label1);
		fieldsPanel.add(field1);
		fieldsPanel.add(label2);
		fieldsPanel.add(field2);
		button.addActionListener(this);
		fieldsPanel.add(button);
		return fieldsPanel;
	}
	
	private JPanel createPanel(JLabel label, JTextField field) {
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(field);
		return panel;
	}
	
	public void refresh() {
		for (int i = 0; i < 6; i++) {
			fieldsRM[i].setText(RM.registerToString(i));
		}
		
		for (int i = 6; i < 11; i++) {
			fieldsRM[i].setText(RM.registerToString1B(i));
		}
		
		for (int i = 2; i < 4; i++) {
			fieldsMD[i].setText(ChannelDevice.registerToString1B(i));
		}
        
        for (int i = 0; i < 2; i++) {
			fieldsMD[i].setText(ChannelDevice.registerToString(i));
		}

	    for (int i = 0; i <= showTo - showFrom; i++)
	    	memoryTable.setValueAt(String.valueOf(Memory.get()[showFrom + i]), i, 2);
        
	    for (int i = 0; i <= showToHDD - showFromHDD; i++)
	    	HDDmemoryTable.setValueAt(String.valueOf(HDD.get()[showFromHDD + i]), i, 2);
	    
        fieldsRM[11].setText(String.format("%d", RM.MODE));
        
        int pc;			
    	
    	if (RM.MODE == 0)
			pc = Integer.parseInt(RM.registerToString(RM.PC), 16);			
    	else 
    		pc = Integer.parseInt(String.valueOf(RM.virtualToReal(RM.registerToString(RM.PC).toCharArray())), 16);
    			
    	if (((pc - showFrom) >= 0) && ((pc - showTo) <= 0))
			memoryTable.setRowSelectionInterval(pc - showFrom, pc - showFrom);
		else memoryTable.clearSelection();
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Next")) {
			RM.runPC();
			this.refresh();
			
		}
		
		else if(e.getActionCommand().equals("Show")) {
			try {
				if (decButton.isSelected()) {
					showFrom = Integer.parseInt(fromField.getText());
					showTo = Integer.parseInt(toField.getText());
				}
				else {
					showFrom = Integer.parseInt(fromField.getText(), 16);
					showTo = Integer.parseInt(toField.getText(), 16);
				}
				if (showTo >= Memory.MEMORY_SIZE)
					showTo = Memory.MEMORY_SIZE - 1;
				if ((showFrom >= 0) && (showFrom <= showTo)) {
					List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);
					filters.add(RowFilter.numberFilter(ComparisonType.AFTER, showFrom - 1));
					filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, showTo + 1));
					RowFilter<Object,Object> filtras = RowFilter.andFilter(filters);

					sorter.setRowFilter(filtras);
					refresh();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Wrong number format!");
			}
		}
		
		else if(e.getActionCommand().equals("Show HDD")) {
			try {
				if (HDDdecButton.isSelected()) {
					showFromHDD = Integer.parseInt(fromFieldHDD.getText());
					showToHDD = Integer.parseInt(toFieldHDD.getText());
				}
				else {
					showFromHDD = Integer.parseInt(fromFieldHDD.getText(), 16);
					showToHDD = Integer.parseInt(toFieldHDD.getText(), 16);
				}
				if (showToHDD >= HDD.HDD_SIZE)
					showToHDD = HDD.HDD_SIZE - 1;
				if ((showFromHDD >= 0) && (showFromHDD <= showToHDD)) {
					List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);
					filters.add(RowFilter.numberFilter(ComparisonType.AFTER, showFromHDD - 1));
					filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, showToHDD + 1));
					RowFilter<Object,Object> filtras = RowFilter.andFilter(filters);

					HDDsorter.setRowFilter(filtras);
					refresh();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Wrong number format!");
			}
		}
		
		else if(e.getActionCommand().equals("Show HDD table")) {
			hddFrame.setVisible(true);
		}
		
		else if(e.getActionCommand().equals("Decimal numbers")) {
			hexButton.setSelected(false);
		}
		else if(e.getActionCommand().equals("Hexadecimal numbers")) {
			decButton.setSelected(false);
		}
		else if(e.getActionCommand().equals("Decimal numbers ")) {
			HDDhexButton.setSelected(false);
		}
		else if(e.getActionCommand().equals("Hexadecimal numbers ")) {
			HDDdecButton.setSelected(false);
		}
	}
	
	private void createMemoryTable() {
		
		Object[][] data = new Object[Memory.MEMORY_SIZE][3];
		String[] columnName = {"Dec no.", "Hex no.", "word"};
		for (int i = showFrom; i <= showTo; i++) {
			data[i][1] = Integer.toHexString(i).toUpperCase();
			data[i][0] = i;
			data[i][2] = String.valueOf(Memory.get()[i]);
		}
		memoryTable = new JTable(data, columnName);
		sorter = new TableRowSorter<TableModel>(memoryTable.getModel());
		memoryTable.setRowSorter(sorter);
		memoryTable.setPreferredScrollableViewportSize(new Dimension(150, memoryTable.getRowHeight() * 15));
		scrollPane = new JScrollPane(memoryTable);
		memoryTable.setFillsViewportHeight(true);
		memoryTable.setEnabled(false);
	}
	
	private void createHDDMemoryTable() {
		Object[][] data = new Object[HDD.HDD_SIZE][3];
		String[] columnName = {"Dec no.", "Hex no.", "word"};
		for (int i = showFromHDD; i <= showToHDD; i++) {
			data[i][1] = Integer.toHexString(i).toUpperCase();
			data[i][0] = i;
			data[i][2] = String.valueOf(HDD.get()[i]);
		}
		HDDmemoryTable = new JTable(data, columnName);
		HDDsorter = new TableRowSorter<TableModel>(HDDmemoryTable.getModel());
		HDDmemoryTable.setRowSorter(HDDsorter);
		HDDmemoryTable.setPreferredScrollableViewportSize(new Dimension(150, HDDmemoryTable.getRowHeight() * 15));
		HDDscrollPane = new JScrollPane(HDDmemoryTable);
		HDDmemoryTable.setFillsViewportHeight(true);
		HDDmemoryTable.setEnabled(false);
	}
	
	/*public static void printChar(char c) {
		console.append(Character.toString(c));
	}
	
	public static void printStream(String s) {
		console.append(s+"\n");
	}*/
	
	public void showHide() {
		if (isVisible()) {
			setVisible(false);
		}
		else
			setVisible(true);
	}
	
}
