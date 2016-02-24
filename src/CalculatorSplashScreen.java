/* File Name: CalculatorSplashScreen.java
 * Author: Sean Macfarlane, 040-779-100
 * Course:  CST8221 – JAP, Lab Section: 301
 * Assignment: 2
 * Date: 06 March 2015
 * Professor: Sv. Ranev
 * Purpose: Builds a SplashScreen 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * Building a SplashScreen
 * 
 * @author Sean Macfarlane
 * @version 2.0.0
 * @since 1.8_25
 */
public class CalculatorSplashScreen extends JWindow {

	/** The duration of the SplashScreen visible */
	private int duration;
	/** SplashScreen Loading Bar */
	private JProgressBar progressBar;
	/** SplashScreen Label for Name */
	private JLabel name;
	/** The Progress Bar Minimum {@value} */
	public static final int PB_MINIMUM = 0;

	/**
	 * Default constructor. Sets the duration of SplashScreen.
	 * 
	 * @param duration
	 *          of SplashScreen
	 */
	public CalculatorSplashScreen(int duration) {
		this.duration = duration;
	}

	/**
	 * Method responsible for creating the SplashScreen
	 */
	public void showSplashWindow() {

		JPanel content = new JPanel(new BorderLayout());
		content.setBackground(Color.WHITE);
		// Set size and location of SplashScreen
		int width = 600;
		int height = 350;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

		URL url = CalculatorSplashScreen.class.getResource("/calc.jpg");
		JLabel label = new JLabel(new ImageIcon(url)); // Label for image
		name = new JLabel("Sean Macfarlane 040-779-100", JLabel.CENTER); // Label for Name
		name.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));

		progressBar = new JProgressBar(); // Loading Bar
		progressBar.setMinimum(PB_MINIMUM);
		progressBar.setMaximum(duration); // Duration of Loading Bar

		content.add(progressBar, BorderLayout.CENTER);
		content.add(label, BorderLayout.NORTH);
		content.add(name, BorderLayout.SOUTH);
		content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		setContentPane(content);
		setVisible(true);

		for (int i = PB_MINIMUM; i <= duration; i++) { // Internal Delay for Updating Loading Bar
			final int percent = i;
			try {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						updateProgressBar(percent);
					}
				});
				// make the program inactive for a while so that the GUI thread can do its work
				java.lang.Thread.sleep(25);
			} catch (InterruptedException e) {
				;
			}
		}
		dispose();
	}

	/**
	 * Method responsible for updating Progress Bar
	 * 
	 * @param newValue
	 *          new percentage value for Progress Bar
	 */
	public void updateProgressBar(int newValue) {

		progressBar.setStringPainted(true);
		progressBar.setValue(newValue);
		progressBar.setString("Loading Calculator. Please wait... " + newValue + "%");
	}
}// end CalculatorSplashScreen
