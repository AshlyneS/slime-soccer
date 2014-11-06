package net.foxgenesis.slimesoccer.ui.component;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ProgressBar extends Component {
	private double value,max,min;
	private Color foreground,background;
	private Runnable run;
	private boolean pText=true;
	private boolean invert;
	private String text;
	
	public ProgressBar() {
		super();
		foreground = Color.green;
		background = Color.black;
		max = 100;
		min = 0;
		text="Loading...";
	}
	
	public double getMaximumValue() {
		return max;
	}
	
	public void setPrintText(boolean state) {
		this.pText = state;
	}
	
	public String getText() {
		return text;
	}
	
	public void setInvertedPercentage(boolean state) {
		invert = state;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public double getMinimumValue() {
		return min;
	}
	
	public void setMaximumValue(double value) {
		this.max = value;
	}
	
	public void setMinimumValue(double value) {
		this.min = value;
	}
	
	public void setValue(double value) {
		if(value>max)
			value = max;
		else if(value < min)
			value = min;
		this.value = value;
	}
	
	public void setAction(Runnable run) {
		this.run = run;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setForeground(Color color) {
		this.foreground = color;
	}
	
	public void setBackground(Color color) {
		this.background = color;
	}
	
	public Color getBackground() {
		return background;
	}
	
	public Color getForeground() {
		return foreground;
	}
	
	@Override
	public void update(int delta) {
		if(invert?value <= min:value >= max)
			if(run != null) {
				run.run();
				run = null;
			}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(background);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(foreground);
		g.fillRect(getX(), getY(), (int)(getWidth()/max*value), getHeight());
		g.setColor(Color.black);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		if(pText) {
			g.setColor(Color.white);
			g.drawString(text, getX()+getWidth()/2-g.getFont().getWidth(text)/2, getY()+getHeight()+5);
		}
	}

}
