/* File Name: CalculatorView.java
 * Author: Sean Macfarlane, 040-779-100
 * Course:  CST8221 – JAP, Lab Section: 301
 * Assignment: 2
 * Date: 06 March 2015
 * Professor: Sv. Ranev
 * Purpose: Builds the GUI for the Calculator
 * Class list: Controller
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

/**
 * Class for building the Calculator GUI
 * 
 * @author Sean Macfarlane
 * @version 2.0.0
 * @since 1.8_25
 */
public class CalculatorView extends JPanel {
	/** TextField for the Calculator text display */
	private JTextField display; // the calculator display field reference
	/** Label for the Mode or Error display */
	private JLabel error; // the error display label reference
	/** JButton reference for the decimal point button */
	private JButton dotButton; // the decimal point (dot) button reference
	/** The font size of the button text {@value} */
	private final int FONT_SIZE = 20; // the font size of the buttons
	/** JCheckBox reference for operation mode */
	private JCheckBox cbox; // the mode checkbox
	/** Stores the Operation Mode, either Integer or Float */
	private char mode = 'f'; // Stores the Operational Mode
	/** Stores the Floating Point Precision Mode */
	private String precision = ".00"; // Stores the Precision Mode
	/** CalculatorModel reference for Controller */
	private CalculatorModel model = new CalculatorModel();
	/** Flag for determining if first number or second number is being entered */
	private int numFlag = 1; // Flag for determining if entering number 1 or 2
	/** Stores the first number entered */
	private String num1 = ""; // String to hold number 1
	/** Stores the second number entered */
	private String num2 = ""; // String to hold number 2
	/** Stores the operator entered */
	private String operator = ""; // String to hold operator
	/** Flag for determining if the expression has been calculated yet */
	private int calculated = 0; // Flag to determine if expression has been calculated
	/** Flag for determing if the number already has a decimal point */
	private int dotFlag = 1; // Flag to determine if number already has decimal
	/** An Array that stores all the Colors used */
	private Color colours[] = {Color.BLACK, Color.WHITE, Color.BLUE, Color.YELLOW, Color.RED, Color.GRAY, Color.GREEN};	
	/** JRadioButton references for precision modes */
	private JRadioButton onedec, twodec, sci;
	
	/**
	 * Default constructor. Builds the Calculator GUI
	 */
	public CalculatorView() {

		// Create and Set Layouts
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, colours[0]));
		// Create Top Pane of Calculator
		JPanel topPane = new JPanel();
		topPane.setLayout(new BorderLayout());
		add(topPane, BorderLayout.NORTH); // Add top Pane to North of main Pane
		JPanel topPaneNorth = new JPanel(); // Pane for the North of Top Pane
		JPanel topPaneSouth = new JPanel(); // Pane for the South of Top Pane
		// Set Layouts of North and South Panes of Top Pane
		topPaneNorth.setLayout(new FlowLayout());
		topPaneNorth.setBackground(colours[1]);
		topPaneSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 7));
		topPaneSouth.setBackground(colours[0]);

		// Mode/Error Label
		error = new JLabel("F");
		error.setHorizontalAlignment(JLabel.CENTER); // Text aligned to Center
		error.setPreferredSize(new Dimension(25, 25));
		error.setOpaque(true);
		error.setBackground(colours[3]);
		topPaneNorth.add(error);

		// TextField
		display = new JTextField(16); // Creates new TextField 16 columns wide
		display.setPreferredSize(new Dimension(display.getColumns(), 30)); // Sets height of textfield to 30
		display.setBackground(colours[1]);
		display.setEditable(false); // Sets the text field not editable
		display.setText("0.0"); // Sets the start up display to 0.0
		display.setHorizontalAlignment(JTextField.RIGHT); // Sets the text right aligned
		topPaneNorth.add(display);

		// Backspace Button
		JButton backspace = new JButton("\u2190");
		backspace.setOpaque(false); // Sets button Transparent
		backspace.setContentAreaFilled(false); // Sets button Transparent
		backspace.setForeground(Color.red);
		backspace.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		backspace.setPreferredSize(new Dimension(25, 25));
		backspace.setActionCommand("\b");
		backspace.addActionListener(new Controller());
		// Adds KeyBinding for backspace
		backspace.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "b");
		backspace.getActionMap().put("b", new Controller());
		backspace.setToolTipText("Backspace (Alt-B)"); // Creates a tool tip for the backspace button
		backspace.setMnemonic(KeyEvent.VK_B); // Create keyboard shortcut: ALT-B clicks backspace button
		topPaneNorth.add(backspace);
		// Add Pane to the North of Top Pane
		topPane.add(topPaneNorth, BorderLayout.NORTH);

		// Int CheckBox
		cbox = new JCheckBox("Int");
		cbox.setBackground(colours[6]);
		cbox.addActionListener(new Controller());
		// Adds KeyBinding for Operation Mode
		cbox.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("M"), ".");
		cbox.getActionMap().put(".", new Controller());
		cbox.setToolTipText("Mode (M)"); // Creates a tool tip for the checkbox

		// Radio Buttons
		onedec = new JRadioButton(".0"); // .0 radio button
		onedec.setBackground(colours[3]); // Sets background colour of button
		onedec.setToolTipText("Precision (P)"); // Creates a tool tip for the RadioButton
		onedec.addActionListener(new Controller());
		onedec.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "P");
		onedec.getActionMap().put("P", new Controller());
		twodec = new JRadioButton(".00"); // .00 radio button
		twodec.setBackground(colours[3]); // Sets background colour of button
		twodec.setToolTipText("Precision (P)"); // Creates a tool tip for the RadioButton
		twodec.addActionListener(new Controller());
		twodec.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "P");
		twodec.getActionMap().put("P", new Controller());
		sci = new JRadioButton("Sci"); // Sci radio button
		sci.setBackground(colours[3]); // Sets background colour of button
		sci.addActionListener(new Controller());
		sci.setToolTipText("Precision (P)"); // Creates a tool tip for the RadioButton
		sci.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "P");
		onedec.getActionMap().put("P", new Controller());
		ButtonGroup radio = new ButtonGroup(); // Create group for radio buttons
		radio.add(onedec);
		radio.add(twodec);
		radio.add(sci);		
		twodec.setSelected(true); // Sets the .00 radio button to be selected by default

		// Create Horizontal Box container for checkbox and radio buttons
		Box hBox = Box.createHorizontalBox();
		hBox.add(Box.createHorizontalStrut(63));
		hBox.add(cbox);
		hBox.add(Box.createHorizontalStrut(20));
		hBox.add(onedec);
		hBox.add(twodec);
		hBox.add(sci);
		hBox.add(Box.createHorizontalStrut(63));
		topPaneSouth.add(hBox);
		// Add Pane to South of Top Pane
		topPane.add(topPaneSouth, BorderLayout.SOUTH);

		// Center Pane of main Pane for Clear, Numeric Keypad, Equals buttons
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());
		centerPane.add(createButton("C", "c", colours[0], colours[4], new Controller()), BorderLayout.NORTH);

		// Grid Pane for numeric keypad buttons
		JPanel gridPane = new JPanel();
		gridPane.setLayout(new GridLayout(4, 4, 5, 5));
		gridPane.setBorder(BorderFactory.createEmptyBorder(4, 2, 3, 2));

		// String array of Button labels
		String[] keyValues = { "7", "8", "9", "\u00F7", "4", "5", "6", "*", "1", "2", "3", "-", "\u00B1", "0", ".", "+" };
		// Loops through the String array of Button text values and creates a button for each one
		for (String key : keyValues) {
			// If text is an operator create button with yellow text
			if (key.equals("\u00F7") || key.equals("*") || key.equals("-") || key.equals("+"))
				gridPane.add(createButton(key, key, colours[3], colours[2], new Controller()));
			// If it is the decimal button assign to dotButton reference
			else if (key.equals(".")) {
				dotButton = createButton(key, key, colours[0], colours[2], new Controller());
				// Adds KeyBindings for decimal point
				dotButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DECIMAL, 0), key);
				dotButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), key);
				dotButton.getActionMap().put(key, new Controller());

				gridPane.add(dotButton);
			}
			// Otherwise create numeric button with black text
			else
				gridPane.add(createButton(key, key, colours[0], colours[2], new Controller()));
		}
		centerPane.add(gridPane, BorderLayout.CENTER);
		centerPane.add(createButton("=", "=", colours[0], colours[3], new Controller()), BorderLayout.SOUTH);
		// Add Center Pane to the Center of main Pane
		add(centerPane, BorderLayout.CENTER);
	}

	/**
	 * Creates a button and sets the text, action command, foreground color, background color, and registers (adds) an ActionListener to process the events generated by the button.
	 * 
	 * @param text
	 *          button text
	 * @param ac
	 *          button action command
	 * @param fg
	 *          foreground color
	 * @param bg
	 *          background color
	 * @param handler
	 *          ActionListener of button
	 * @return JButton
	 */
	private JButton createButton(String text, String ac, Color fg, Color bg, Action handler) {
		// Create a button
		JButton button = new JButton(text);
		button.setBackground(bg); // Sets background colour of button
		button.setForeground(fg); // Sets text colour of button
		button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), FONT_SIZE)); // Changes the font size to 20
		button.addActionListener(handler); // Add Action Listener to button
		if (ac.equals("+")) { // Adds KeyBindings for Addition operator
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.SHIFT_MASK), ac);
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), ac);
			button.getActionMap().put(ac, handler);
		} else if (ac.equals("-")) { // Adds KeyBindings for Subtraction operator
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), ac);
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), ac);
			button.getActionMap().put(ac, handler);
		} else if (ac.equals("*")) { // Adds KeyBindings for Multiplication operator
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_8, InputEvent.SHIFT_MASK), ac);
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), ac);
			button.getActionMap().put(ac, handler);
		} else if (ac.equals("=")) { // Adds KeyBindings for Equals operator
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), ac);
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), ac);
			button.getActionMap().put(ac, handler);
		} else if (ac.equals("\u00F7")) { // Adds KeyBindings for Division operator
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), ac);
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0), ac);
			button.getActionMap().put(ac, handler);
		} else { // Adds KeyBindings for Numbers
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(text), ac);
			button.getActionMap().put(ac, handler);
		}
		if (ac != null)
			button.setActionCommand(ac); // Set the Action command for the button

		button.setToolTipText(text + "(" + text + ")"); // Creates a tool tip for the Button
		return button;
	}

	/**
	 * Class for handling events generated by the GUI
	 * 
	 * @author Sean Macfarlane
	 * @version 2.0.0
	 * @since 1.8_25
	 */
	private class Controller extends AbstractAction {
		/**
		 * Handles when an action is performed and communicates with the CalculatorModel
		 * 
		 * @param e
		 *          GUI Event
		 */
		public void actionPerformed(ActionEvent e) {
			model.setFloatPrecision(precision);
			model.setMode(mode);
			if (e.getActionCommand().equals("Int") || e.getActionCommand().equals("m")) { // If Operation Mode CheckBox or "M" Key is pressed
				if (mode == 'f') { // If the current Mode is Float, change to Int
					mode = 'i';
					model.setMode(mode);
					cbox.setSelected(true);
					error.setBackground(colours[6]);
					error.setText("I");
					dotFlag = 0;
					if (model.getErrorState() == false) {
						if (num2.length() > 0) {
							model.setNum1(num1); // Sets number 1 to Int format
							model.setNum2(num2); // Sets number 2 to Int format
							num1 = model.getNum1(); // Store the formated number 1
							num2 = model.getNum2(); // Store the formated number 2
						} else {
							model.setNum1(num1);
							num1 = model.getNum1();
						}
					}
				} else if (mode == 'i') { // If the current Mode is Int, change to Float
					mode = 'f';
					model.setMode(mode);
					cbox.setSelected(false);
					error.setBackground(colours[3]);
					error.setText("F");
					dotFlag = 1;
				}
			} else if (e.getActionCommand().equals("c") || e.getActionCommand().equals("C")) { // If the Clear Button or "C" Key is pressed
				num2 = "";
				operator = "";
				numFlag = 1;
				if (mode == 'i') {
					num1 = "0";
					error.setBackground(colours[6]);
					error.setText("I");
				} else if (mode == 'f') {
					num1 = "0.0";
					error.setBackground(colours[3]);
					error.setText("F");
				}
				model.setErrorState(false);
			} else if (e.getActionCommand().equals(".00") || e.getActionCommand().equals(".0") || e.getActionCommand().equals("Sci") || e.getActionCommand().equals("p")) { // If one of the Radio Buttons are clicked
				if(e.getActionCommand().equals("p")){	// If the "P" key is pressed then the next precision mode is enabled
					if(precision.equals(".0")){
						precision = ".00";
						twodec.setSelected(true);
					}
					else if(precision.equals(".00")){
						precision = "Sci";
						sci.setSelected(true);
					}else{
						precision = ".0";
						onedec.setSelected(true);
					}
				}
				else{				
					precision = e.getActionCommand();
				}
			}	else if (e.getActionCommand().equals("\b")) { // If the backspace button or backspace key is pressed
				if (calculated == 1) {
					numFlag = 1;
					calculated = 0;
				}
				if (numFlag == 2 && num2.length() > 0) { // If number 2 is present
					if (num2.contains("e")) // If in Scientific mode remove exponent
						num2 = num2.substring(0, num2.length() - 4);
					else
						num2 = num2.substring(0, num2.length() - 1);
					if (num2.contains(".") == false)
						dotFlag = 1;
				} else if (numFlag == 2 && num2.length() == 0 && operator.length() > 0) // If deleting operator
					operator = operator.substring(0, operator.length() - 1);
				else if (num1.length() > 0)
					if (num1.contains("e"))
						num1 = num1.substring(0, num1.length() - 4);
					else
						num1 = num1.substring(0, num1.length() - 1);

			} else if (e.getActionCommand().equals("\u00B1")) { // If Sign change(+/-) button is pressed
				if (numFlag == 2 && num2.contains("-")) {
					num2 = num2.substring(1);
				} else if (numFlag == 2) {
					num2 = "-" + num2;
				} else if (numFlag == 1 && num1.contains("-")) {
					num1 = num1.substring(1);
				} else {
					num1 = "-" + num1;
				}
			} else if (model.getErrorState() == true) { // If the calculator is in Error State
				error.setBackground(colours[4]);
				error.setText("E");
				display.setText("--");
			} else if (e.getActionCommand().equals(".")) { // If the decimal point button or "." Key is pressed

				if (numFlag == 1 && num1.length() > 0)
					num1 = num1.concat(e.getActionCommand());
				else if (numFlag == 1 && num1.length() == 0)
					num1 = "0.";
				else if (numFlag == 2 && num1.length() > 0)
					num2 = num2.concat(e.getActionCommand());
				else if (numFlag == 2 && num1.length() == 0)
					num2 = "0.";

			} else { // If an operator or number is pressed

				if (e.getActionCommand().equals("=") || e.getActionCommand().equals("\n")) { // If the Equals button or "=" Key or Enter Key is pressed
					model.setNum1(num1);
					model.setNum2(num2);
					model.performCalculations();

					num1 = model.getResult();
					num2 = "";
					operator = "";
					calculated = 1;
					model.setOperator(operator);
				} else if (e.getActionCommand().equals("/") || e.getActionCommand().equals("\u00F7") || e.getActionCommand().equals("*") || e.getActionCommand().equals("-")
						|| e.getActionCommand().equals("+")) { // If an Operator is Pressed
					if (e.getActionCommand().equals("/"))
						operator = "\u00F7";
					else
						operator = e.getActionCommand();
					model.setOperator(operator);
					numFlag = 2;
					calculated = 0;
				} else if (numFlag == 2 && calculated == 0) { // If current number is second number
					num2 = num2.concat(e.getActionCommand());
				} else { // If first number is current number
					// If this is the first button pressed after being Cleared or Calculation being performed
					if (num1.equals("0") || (num1.equals("0.0") && !e.getActionCommand().equals("0")) || calculated == 1) {
						num1 = e.getActionCommand();
						calculated = 0;
						numFlag = 1;
					} else
						num1 = num1.concat(e.getActionCommand());
				}
			}

			// Determines the State of the decimal point button
			if (numFlag == 1 && num1.contains(".") == false && calculated == 0)
				dotFlag = 1;
			else if (numFlag == 1 && num1.contains(".") == true)
				dotFlag = 0;
			else if (numFlag == 2 && num2.contains(".") == false)
				dotFlag = 1;
			else if (numFlag == 2 && num2.contains(".") == true)
				dotFlag = 0;
			// Modifies decimal point button depending on state
			if (dotFlag == 0 || mode == 'i') {
				dotButton.setEnabled(false);
				dotButton.setBackground(colours[5]);
			} else {
				dotButton.setEnabled(true);
				dotButton.setBackground(colours[2]);
			}
			model.setFloatPrecision(precision);
			model.setMode(mode);
			if (model.getErrorState() == false) { // If not Error state display Expression
				if (num1.equals("-") || num1.equals("")) {
					if (mode == 'i')
						num1 = "0";
					else if (mode == 'f')
						num1 = "0.0";
				}
				display.setText(num1 + operator + num2);
			}
			if (model.getErrorState() == true) { // If Error State display Error
				error.setBackground(colours[4]);
				error.setText("E");
				display.setText("--");
			}
			requestFocusInWindow();
		}// end actionPerformed()
	} // end Controller
}// end CalculatorView
