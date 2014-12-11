package net.foxgenesis.slimesoccer.ui.component;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.ui.component.Button.Action;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
/**
 * Popup is a popup window that displays text and or a menu
 * @author Seth
 */
public class PopUp extends Component {

	protected String text, title;
	protected static final Color fadeScreen = new Color(0.7f,0.7f,0.7f,0.5f), body = Color.lightGray, header = Color.gray;
	private final Type type;
	protected final Button[] buttons;
	public static final int EXIT = 0, OK = 1, YES = 2, NO = 3;
	protected ActionHandler handler;
	protected boolean autoSize = true;
	
	/**
	 * Create a new PopUp with given text, title and type
	 * @param text - text to display
	 * @param title - title of window
	 * @param type - type of dialog
	 */
	public PopUp(String text, String title, Type type) {
		super();
		this.text = text;
		this.type = type;
		this.title = title;
		buttons = new Button[2];
		buttons[EXIT] = new Button("X"){
			@Override
			public void mouseEntered() {
				setBackground(Color.red);
			}
			@Override
			public void mouseLeft() {
				setBackground(Color.red.darker(0.15f));
			}
		};
		buttons[EXIT].setBackground(Color.red.darker(0.15f));
		buttons[EXIT].setRaisedBorder(false);
		buttons[EXIT].setDrawHoverScreen(false);
		buttons[EXIT].drawShadow(false);
		buttons[EXIT].setForeground(Color.white);
		buttons[EXIT].setAction(new Action() {
			@Override
			public void act(int button, int x, int y, int clickCount) {
				setVisible(false);
				Button a = buttons[EXIT];
				a.setBackground(Color.red);
				a.cursorEntered = false;
				a.held = false;
				if(handler != null)
					handler.actionEvent(EXIT);
			}
		});
		for(Button a:buttons)
			if(a != null)
				a.setSmoothMoving(false);
		setSmoothMoving(false);
		setVisible(false);
	}

	/**
	 * Create a new PopUp with a given text and type
	 * @param text - text to display
	 * @param type - type of dialog
	 */
	public PopUp(String text, Type type) {
		this(text,"",type);
	}

	/**
	 * Get the current text of the dialog
	 * @return current text
	 */
	public String getText() {
		return text;
	}

	@Override
	public void setVisible(boolean state) {
		super.setVisible(state);
		for(Button a: buttons)
			if(a != null)
				a.setVisible(state);
	}

	/**
	 * Get the type of dialog
	 * @return type of dialog
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Set the current text of the dialog
	 * @param text - current text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Listen to an input
	 * @param input - input to listen to
	 */
	public void listen(Input input) {
		for(Button a:buttons)
			if(a != null)
				a.listen(input);
	}

	/**
	 * Stop listening to an input
	 * @param input - input to stop listening to
	 */
	public void mute(Input input) {
		for(Button a:buttons)
			if(a != null)
				a.mute(input);
	}

	/**
	 * Sets the action handler that will handle actions from this component
	 * @param handler
	 */
	public void setActionHandler(ActionHandler handler) {
		this.handler = handler;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		//set button locations
		for(Button a:buttons)
			if(a != null)
				a.update(delta);
		buttons[EXIT].setLocation(getLocation().x+width-buttons[EXIT].width/2, getLocation().y+buttons[EXIT].height/2+2);
		setLocation(SlimeSoccer.getWidth()/2-width/2,SlimeSoccer.getHeight()/2-height/2);
	}

	@Override
	public void draw(Graphics g) {
		if(!isVisible())
			return;
		g.setColor(fadeScreen);
		g.fillRect(0, 0, SlimeSoccer.getWidth(), SlimeSoccer.getHeight());
		if(autoSize)
			setSize(g.getFont().getWidth(text)+20,g.getFont().getHeight(text) + 20 + buttons[EXIT].height);
		g.setColor(body);
		g.fillRect(getLocation().x,getLocation().y,width,height);
		g.setColor(Color.black);
		g.drawRect(getLocation().x,getLocation().y,width,height);
		g.setColor(header);
		g.fillRect(getLocation().x+1,getLocation().y+1,width-1,buttons[EXIT].height+buttons[EXIT].getPadding());
		g.setColor(Color.white);
		g.drawString(title, getLocation().x+5, getLocation().y+3);
		g.setColor(Color.black);
		g.drawString(text, getLocation().x+width/2-g.getFont().getWidth(text)/2, getLocation().y+height/2-g.getFont().getHeight(text)/2+buttons[EXIT].height/2);
		g.drawRect(getLocation().x,getLocation().y,width,buttons[EXIT].height+buttons[EXIT].getPadding());
		for(Button a:buttons)
			if(a != null)
				a.draw(g);
	}

	public static enum Type {
		DIALOG,
		MENU;
	}

	public static interface ActionHandler {
		/**
		 * Called on button events from PopUp
		 * @param button - button number
		 */
		public void actionEvent(int button);
	}
}
