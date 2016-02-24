import java.applet.Applet;


public class CalculatorApplet extends Applet {

	@Override
	public void init(){
		super.init();
		CalculatorView calc = new CalculatorView();
		add(calc);
	}
}
