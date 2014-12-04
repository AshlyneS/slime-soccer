package net.foxgenesis.slimesoccer.ui.component;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.ui.component.Button.Action;

import org.newdawn.slick.Color;

public class SoccerGameMenu extends PopUp {

	public static int MAIN_MENU = 1;
	public SoccerGameMenu() {
		super("PAUSED", "Menu", Type.MENU);
		autoSize = false;
		buttons[MAIN_MENU] = new Button("Main Menu"){
			@Override
			public void mouseEntered() {
				setBackground(Color.white);
			}
			@Override
			public void mouseLeft() {
				setBackground(Color.white.darker(0.15f));
			}
		};
		buttons[MAIN_MENU].setBackground(Color.white.darker(0.15f));
		buttons[MAIN_MENU].setDrawHoverScreen(false);
		buttons[MAIN_MENU].drawShadow(false);
		buttons[MAIN_MENU].setForeground(Color.black);
		buttons[MAIN_MENU].setAction(new Action() {
			@Override
			public void act(int button, int x, int y, int clickCount) {
				setVisible(false);
				Button a = buttons[MAIN_MENU];
				a.setBackground(Color.white);
				a.cursorEntered = false;
				a.held = false;
				if(handler != null)
					handler.actionEvent(MAIN_MENU);
			}
		});
		setSize(SlimeSoccer.getWidth()/3*2,SlimeSoccer.getHeight()/3);
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		buttons[MAIN_MENU].setLocation(getLocation().x+width/2,getLocation().y+height-buttons[MAIN_MENU].getHeight());
	}
}
