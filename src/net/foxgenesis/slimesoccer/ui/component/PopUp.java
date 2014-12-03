package net.foxgenesis.slimesoccer.ui.component;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.ui.component.Button.Action;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class PopUp extends Component {

	private String text;
	private static final Color fadeScreen = new Color(0.7f,0.7f,0.7f,0.5f), body = Color.lightGray, header = Color.gray;
	private final Type type;
	private final Button[] buttons;
	public static final int EXIT = 0, OK = 1, YES = 2, NO = 3;
	private ActionHandler handler;

	public PopUp(String text, Type type) {
		super();
		this.text = text;
		this.type = type;
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

	public Type getType() {
		return type;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void listen(Input input) {
		for(Button a:buttons)
			if(a != null)
				a.listen(input);
	}

	public void mute(Input input) {
		for(Button a:buttons)
			if(a != null)
				a.mute(input);
	}

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
		setSize(g.getFont().getWidth(text)+20,g.getFont().getHeight(text) + 20 + buttons[EXIT].height);
		g.setColor(body);
		g.fillRect(getLocation().x,getLocation().y,width,height);
		g.setColor(Color.black);
		g.drawRect(getLocation().x,getLocation().y,width,height);
		g.setColor(header);
		g.fillRect(getLocation().x+1,getLocation().y+1,width-1,buttons[EXIT].height+buttons[EXIT].getPadding());
		for(Button a:buttons)
			if(a != null)
				a.draw(g);
		g.setColor(Color.black);
		g.drawString(text, getLocation().x+width/2-g.getFont().getWidth(text)/2, getLocation().y+height/2-g.getFont().getHeight(text)/2+buttons[EXIT].height/2);
		g.drawRect(getLocation().x,getLocation().y,width,buttons[EXIT].height+buttons[EXIT].getPadding());
	}

	public static enum Type {
		DIALOG,
		MESSAGE;
	}

	public static interface ActionHandler {
		public void actionEvent(int button);
	}
}
