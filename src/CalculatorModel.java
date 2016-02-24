import java.math.BigDecimal;

/* File Name: CalculatorModel.java
 * Author: Sean Macfarlane, 040-779-100
 * Course:  CST8221 – JAP, Lab Section: 301
 * Assignment: 2
 * Date: 06 March 2015
 * Professor: Sv. Ranev
 * Purpose: Handles the calculations of the Calculator
 * Class list: 
 */
/**
 * Class for performing the Calculator's calculations
 * 
 * @author Sean Macfarlane
 * @version 1.0.0
 * @since 1.8_25
 */
public class CalculatorModel {
	/** Stores the operator received by Controller */
	private String operator = ""; // Operator for calculation
	/** Stores the first number received by Controller */
	private String operand1 = ""; // First Number received by Controller
	/** Stores the second number received by Controller */
	private String operand2 = ""; // Second Number received by Controller
	/** Stores the first number value */
	private double num1; // Value of First Number
	/** Stores the second number value */
	private double num2; // Value of Second Number
	/** Stores the result value of the calculation performed */
	private double total; // Value of the result of calculation
	/** Stores the mode recieved by Controller */
	private char mode; // the Operation Mode
	/** Stores the result value of the calculation performed to be returned to Controller */
	private String result; // The result of calculation to be returned to Controller
	/** Stores the Float precision format to be printed */
	private String format; // The Float precision mode
	/** Value that determines if it is in Error State */
	private boolean errorState = false; // Determines Error State

	/**
	 * Set Method for First Number
	 * 
	 * @param num
	 *          The number received by Controller
	 */
	public void setNum1(String num) {
		if (mode == 'i') { // If Integer mode convert number to Int value
			if (num.length() > 0) { // Checks for Run Time Error
				if (Double.parseDouble(num) > Integer.MAX_VALUE || (Double.parseDouble(num) > 0 && Double.parseDouble(num) < Integer.MIN_VALUE))
					errorState = true;
				else
					operand1 = Integer.toString((int)Double.parseDouble(num));
			}
		}
		else 
			operand1 = num;
	}

	/**
	 * Set Method for Second Number
	 * 
	 * @param num
	 *          The number received by Controller
	 */
	public void setNum2(String num) {
		if (mode == 'i') { // If Integer mode convert number to Int value
			if (num.length() > 0) { // Checks for Run Time Error
				if (Double.parseDouble(num) > Integer.MAX_VALUE || (Double.parseDouble(num) > 0 && Double.parseDouble(num) < Integer.MIN_VALUE))
					errorState = true;
				else
					operand2 = Integer.toString((int)Double.parseDouble(num));
			}
		}
		else 
			operand2 = num;
	}

	/**
	 * Get Method for First Number
	 * 
	 * @return String
	 */
	public String getNum1() {
		return operand1;
	}

	/**
	 * Get Method for Second Number
	 * 
	 * @return String
	 */
	public String getNum2() {
		return operand2;
	}

	/**
	 * Set Method for Operator
	 * 
	 * @param operator
	 *          The operator received by Controller
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Set Method for Operation Mode
	 * 
	 * @param mode
	 *          The mode received by Controller
	 */
	public void setMode(char mode) {
		this.mode = mode;
	}

	/**
	 * Set Method for Floating Point Precision Mode
	 * 
	 * @param precision
	 *          The precision mode received by Controller
	 */
	public void setFloatPrecision(String precision) {
		if (precision.equals(".0")) {
			format = "%.1f";
		} else if (precision.equals(".00"))
			format = "%.2f";
		else if (precision.equals("Sci"))
			format = "%e";
	}

	/**
	 * Get Method for Calculation Result
	 * 
	 * @return String
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Set Method for Error State
	 * 
	 * @param state
	 *          The error state of the Calculator received by Controller
	 */
	public void setErrorState(boolean state) {
		errorState = state;
	}

	/**
	 * Get Method for Error State
	 * 
	 * @return boolean
	 */
	public boolean getErrorState() {
		return errorState;
	}

	/**
	 * Method responsible for performing the calculations
	 */
	public void performCalculations() {

		if (operand1.equals(""))	// Checks for Run Time Error
			operand1 = "0";
		if (operand2.equals("")) {	// Checks for Run Time Error
			operand2 = operand1;
		}
		try {	// Tries to convert String Numbers to Double values
			num1 = Double.parseDouble(operand1);	
			num2 = Double.parseDouble(operand2);
		} catch (NumberFormatException e) {
			mode = 'e';
		}

		if (num1 > Float.MAX_VALUE || num2 > Float.MAX_VALUE)	// Checks for Invalid Number 
			mode = 'e';
		if (num1 > 0 && num1 < Float.MIN_VALUE || num2 > 0 && num2 < Float.MIN_VALUE) // Checks for Invalid Number 
			mode = 'e';

		if (operator.equals("+")) {	// Performs addition 
			total = (num1 + num2);
		} else if (operator.equals("-"))	// Performs subtraction 
			total = num1 - num2;
		else if (operator.equals("*"))	// Performs multiplication 
			total = num1 * num2;
		else if (operator.equals("\u00F7")) {	// Performs division
			if (num2 == 0)	//Checks for Division by 0
				mode = 'e';
			else
				total = num1 / num2;
		} else {	// If no operator entered returns the first number 
			total = num1;
		}

		if (total == Float.NaN || total == Float.POSITIVE_INFINITY || total == Float.NEGATIVE_INFINITY||  total > Float.MAX_VALUE || (total > 0 && total < Float.MIN_VALUE))	// Checks for Invalid Number
			mode = 'e';

		if (mode == 'i')	// Converts Result to Integer format
			result = String.format("%d", (long) total);
		else if (mode == 'f')	// Converts Result to Float format with precision mode specified
			result = String.format(format, (double)total);
		else {
			result = "--";
			errorState = true;
		}
	}// end performCalculations()
} // end Calculator Model
