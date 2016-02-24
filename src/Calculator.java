/* File Name: Calculator.java
 * Author: Sean Macfarlane, 040-779-100
 * Course:  CST8221 – JAP, Lab Section: 301
 * Assignment: 2
 * Date: 06 March 2015
 * Professor: Sv. Ranev
 * Purpose: Main class for Calculator GUI 
 */
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Main Class of Calculator GUI.
 * 
 * @author Sean Macfarlane
 * @version 2.0.0
 * @since 1.8_25
 */
public class Calculator {
	/** The duration of the SplashScreen {@value} */
	private final static int duration = 100;

	/**
	 * The main method.The GUI will start with the showing the SplashScreen Runs the application GUI as a thread in the event queue. Anonymous class is used to create a runnable object.
	 * 
	 * @param args
	 *          not used
	 */
	public static void main(String[] args) {

		CalculatorSplashScreen splashWindow = new CalculatorSplashScreen(duration); // Create SplashScreen object
		splashWindow.showSplashWindow(); // show SplashScreen
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("Calculator"); // Set title of frame
				frame.setMinimumSize(new Dimension(330, 400)); // Set minimum size of frame
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation of frame
				frame.setContentPane(new CalculatorView()); // Set content Pane of frame to CalculatorView object
				frame.setLocationByPlatform(true); // Set location of frame relative to Platform
				frame.setVisible(true); // show frame
			}
		});
	}
}// end Calculator
