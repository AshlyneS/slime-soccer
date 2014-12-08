package net.foxgenesis.slimesoccer.ui.component;

import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 * Button component
 * @author Seth
 */
public class Button extends Component {

	private Action action;
	private String text, tooltip = "";
	private Color foreground = Color.black, background = Color.white;
	private Animation ani, icon;
	private int roundedArc = 8,mX,mY,padding=2;
	protected boolean cursorEntered = false;
	protected boolean held = false;
	private boolean drawShadow = true;
	private boolean visible = true;
	private boolean enabled = true;
	private boolean drawBorder = true;
	private boolean raised = true;
	private boolean hover = false;
	private boolean drawHoverScreen = true;
	private boolean round = false;
	private boolean toolFont = false;
	private boolean updateOnHover = true;
	private MouseListener listener;
	private Font font;
	private Timer timer;
	private RaisedBoarder boarder = new RaisedBoarder(this);
	
	/**
	 * Creates a new button with given text and background
	 * @param text - text to be displayed
	 * @param ani - animation for background
	 */
	public Button(String text, Animation ani, Animation icon) {
		this.text = text;
		timer = new Timer();
		this.ani = ani;
		this.icon = icon;
		listener = new MouseListener() {
			@Override
			public void inputEnded() {}

			@Override
			public void inputStarted() {}

			@Override
			public boolean isAcceptingInput() {
				return isVisible() && isEnabled();
			}

			@Override
			public void setInput(Input input) {}

			@Override
			public void mouseClicked(int button, int x, int y, int clickCount) {
				if (button == Input.MOUSE_LEFT_BUTTON)
					if (contains(x, y))
						if (action != null && enabled && visible)
							action.act(button,x,y,clickCount);
			}

			@Override
			public void mouseDragged(int oldx, int oldy, int newx, int newy) {}

			@Override
			public void mouseMoved(int oldx, int oldy, int newx, int newy) {
				mX = newx;
				mY = newy;
				if (cursorEntered && !contains(newx, newy)) {
					cursorEntered = false;
					hover = false;
					mouseLeft();
				} else if (contains(newx, newy) && cursorEntered == false) {
					cursorEntered = true;
					mouseEntered();
					timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							if (cursorEntered)
								hover = true;
						}
					}, 2000);
				}
			}

			@Override
			public void mousePressed(int button, int x, int y) {
				if (cursorEntered)
					if (button == Input.MOUSE_LEFT_BUTTON) {
						held = true;
						mousePressedAndHeld();
					}
			}

			@Override
			public void mouseReleased(int button, int x, int y) {
				if (cursorEntered) {
					if (button == Input.MOUSE_LEFT_BUTTON) {
						held = false;
						mouseLetGo();
					}
				}
			}

			@Override
			public void mouseWheelMoved(int change) {}
		};
	}

	public Button(String text, Animation ani) {
		this(text,ani,null);
	}

	/**
	 * Creates a new button with a given text
	 * @param text text to be shown on the button
	 */
	public Button(String text) {
		this(text,null);
	}

	/**
	 * Creates a new button with no text
	 */
	public Button() {
		this("",null);
	}

	/**
	 * Returns weather the button draws its boarders
	 * @return boolean
	 */
	public boolean doesDrawBorder() {
		return drawBorder;
	}

	public void setRaisedBorder(boolean state) {
		raised = state;
	}

	public boolean isBorderRaised() {
		return raised;
	}

	public void setIcon(Animation icon) {
		this.icon = icon;
	}

	public Animation getIcon() {
		return icon;
	}

	/**
	 * Gets the current padding for the button
	 * @return button padding
	 */
	public int getPadding() {
		return padding;
	}

	/**
	 * Sets the current padding for the button
	 * @param padding - how much padding
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}

	/**
	 * Returns whether the button draws a screen on hover
	 * @return draw screen on hover
	 */
	public boolean doesDrawHoverScreen() {
		return drawHoverScreen;
	}

	/**
	 * Sets whether the button draws a screen on hover
	 * @param state - should draw screen
	 */
	public void setDrawHoverScreen(boolean state) {
		drawHoverScreen = state;
	}

	/**
	 * Sets whether the button should only update the animation when hovered over
	 * @param state - update animation on hover
	 */
	public void setUpdateAnimationOnHover(boolean state) {
		updateOnHover = state;
	}

	/**
	 * Gets whether the animation should only update when hovered over
	 * @return should animation only update on hover
	 */
	public boolean getUpdateAnimationOnHover() {
		return updateOnHover;
	}

	/**
	 * Gets the animation of the button
	 * @return button animation;
	 */
	public Animation getAnimation() {
		return ani;
	}

	/**
	 * Get the arc amount
	 * @return arc amount
	 */
	public int getRoundedArc() {
		return roundedArc;
	}

	/**
	 * Sets the arc amount
	 * @param arc - arc amount
	 */
	public void setRoundedArc(int arc) {
		this.roundedArc = arc;
	}

	/**
	 * Checks if the tooltip uses the same font as button text
	 * @return does tooltip use font
	 */
	public boolean doesToolTipUseFont() {
		return toolFont;
	}

	/**
	 * Sets the tooltip to use button font based on state
	 * @param state - should tooltip use same font
	 */
	public void setToolTipUsesFont(boolean state) {
		toolFont = state;
	}

	/**
	 * Gets the font in use
	 * @return font in use
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Sets the font to use for button and or tooltip text
	 * @param font - font to use
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Checks if the button is rounded
	 * @return button is rounded
	 */
	public boolean isRounded() {
		return round;
	}

	/**
	 * Sets whether the button should be rounded
	 * @param state - should button be rounded
	 */
	public void setRounded(boolean state) {
		round = state;
	}

	/**
	 * Sets the background of the button
	 * NOTE: background is only displayed if animation is null
	 * @param color - Color of background
	 */
	public void setBackground(Color color) {
		this.background = color;
	}

	/**
	 * Gets the current color of the background
	 * @return background color
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Sets the text's color
	 * @param color - text color
	 */
	public void setForeground(Color color) {
		this.foreground = color;
	}

	/**
	 * Gets the text's color
	 * @return text color
	 */
	public Color getForeground() {
		return foreground;
	}

	/**
	 * Sets the tooltip to be shown when the cursor hovers over the button
	 * @param text
	 */
	public void setToolTipText(String text) {
		tooltip = text;
	}

	/**
	 * Sets weather the button should draw it's borders or not
	 * @param state
	 */
	public void setDrawBorder(boolean state) {
		drawBorder = state;
	}

	/**
	 * Returns weather the button draws a shadow behind it
	 * @return boolean
	 */
	public boolean doesDrawShadow() {
		return drawShadow;
	}

	/**
	 * Returns weather the button is click-able or not
	 * @return boolean
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets weather the button should be click-able or not
	 * @param state
	 */
	public void setEnabled(boolean state) {
		enabled = state;
	}

	/**
	 * Sets weather the button should be drawn or not
	 * @param state
	 */
	public void setVisible(boolean state) {
		visible = state;
	}

	/**
	 * Returns weather the button is visible on the screen
	 * @return boolean
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Sets weather the button should draw a shadow or not
	 */
	public void drawShadow(boolean state) {
		this.drawShadow = state;
	}

	@Override
	public boolean contains(float x, float y) {
		if(x > getLocation().x+width/2 || x < getLocation().x-width/2)
			return false;
		if(y > getLocation().y+height/2 || y < getLocation().y-height/2)
			return false;
		return true;
	}

	@Override
	public void update(int i) {
		super.update(i);
		if(updateOnHover && cursorEntered || !updateOnHover) {
			if(ani != null)
				ani.update(i);
			if(icon != null)
				icon.update(i);
		}
		boarder.setFixedLocation(getLocation().x-width/2-padding, getLocation().y-height/2-padding);
		boarder.setFixedSize(width+padding, height+padding);
		boarder.setInvertedColors(!cursorEntered);
	}

	/**
	 * Draws the button with a given Graphics
	 * @param g Graphics to draw with
	 */
	public void draw(Graphics g) {
		if (visible) {
			Font original = g.getFont();
			if(font != null)
				g.setFont(font);
			if (!text.equalsIgnoreCase("")) {
				int height = g.getFont().getHeight(text);
				int width = g.getFont().getWidth(text);
				this.setSize(width + 5 + roundedArc + padding, height + 5 + padding);
			}
			if (drawShadow) {
				g.setColor(new Color(.5f, .5f, .5f, .5f));
				g.fillRoundRect(getLocation().x-width/2 + 3-padding, getLocation().y-height/2 + 3-padding, width+padding, height+padding, round?roundedArc:0);
				g.setColor(Color.black);
				g.drawRoundRect(getLocation().x-width/2-padding, getLocation().y-height/2-padding, width+padding, height+padding, round?roundedArc:0);
			}
			if(background != null) {
				g.setColor(background);
				g.fillRoundRect(getLocation().x-width/2-padding, getLocation().y-height/2-padding, width+padding, height+padding,round?roundedArc:0);
			}
			if(ani != null)
				g.texture(new RoundedRectangle(getLocation().x-width/2-padding,getLocation().y-height/2-padding,width+padding,height+padding,round?roundedArc:0), 
						ani.getCurrentFrame(), 1, 1, true);
			if(icon != null)
				icon.draw(getLocation().x-width-(32/2)-padding, getLocation().y-(32/2)-padding,32,32);
			if(drawBorder)
				if(raised && !round)
					boarder.draw(g);
				else {
					g.setColor(Color.black);
					g.drawRoundRect(getLocation().x-width/2-padding, getLocation().y-height/2-padding, width+padding, height+padding,round?roundedArc:0);
				}
			if (!text.equalsIgnoreCase("")) {
				float x = getLocation().x - width/2;
				float y = getLocation().y - height/2;
				g.setColor(foreground);
				g.drawString(text, x+roundedArc-padding, y);
			}
			if (cursorEntered && !held && enabled && drawHoverScreen) {
				g.setColor(new Color(1f, 1f, 1f, 0.5f));
				g.fillRoundRect(getLocation().x-width/2-padding, getLocation().y-height/2-padding, width+padding, height+padding,round?roundedArc:0);
			} else if (cursorEntered && held && enabled) {
				g.setColor(new Color(.5f, .5f, .5f, .5f));
				g.fillRoundRect(getLocation().x-width/2-padding, getLocation().y-height/2-padding, width+padding, height+padding,round?roundedArc:0);
			} else if (!enabled) {
				g.setColor(new Color(0f, 0f, 0f, .5f));
				g.fillRoundRect(getLocation().x-width/2-padding, getLocation().y-height/2-padding, width+padding, height+padding,round?roundedArc:0);
			}
			if (!tooltip.equalsIgnoreCase("") && hover) {
				if(!toolFont)
					g.setFont(original);
				int height = g.getFont().getHeight(tooltip);
				int width = g.getFont().getWidth(tooltip)+roundedArc;
				float x = mX-width/2;
				float y = mY-height - 20 + (((height + 5) - height) / 2);
				g.setColor(Color.white);
				g.fillRoundRect(x, y, width + 5, height+ 5,round?roundedArc:0);
				g.setColor(Color.black);
				g.drawRoundRect(x, y, width + 5, height + 5,round?roundedArc:0);
				g.drawString(tooltip, x+roundedArc/2, y+roundedArc/2-2);
			}
			g.setFont(original);
		}
	}

	/**
	 * Draws the button at a given location
	 * @param g Graphics to draw with
	 * @param x x location on the screen
	 * @param y y location on the screen
	 */
	public void draw(Graphics g, int x, int y) {
		setLocation(x, y);
		draw(g);
	}

	/**
	 * Sets the action to occur when the button is clicked
	 * @param action
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * Sets the input that the button should listen to
	 * @param input Input to listen to
	 */
	public void listen(Input input) {
		input.addMouseListener(listener);
	}

	public void mute(Input input) {
		input.removeMouseListener(listener);
	}

	/**
	 * Sets the text to be displayed on the button
	 * @param text text to show
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the text shown on the button
	 * @return String
	 */
	public String getText() {
		return text;
	}

	/**
	 * Called when the cursor enters the button
	 */
	public void mouseEntered() {
	}

	/**
	 * Called when mouse leaves the button
	 */
	public void mouseLeft() {
	}

	/**
	 * Called when mouse presses and holds the button
	 */
	public void mousePressedAndHeld() {
	}

	/**
	 * Called when the mouse lets go of the button
	 */
	public void mouseLetGo() {
	}

	/**
	 * Sets the animation of the button
	 * @param animation Animation to use
	 */
	public void setAnimation(Animation animation) {
		this.ani = animation;
	}

	@Override
	public String toString() {
		return "Button{text=" + text + "; hasAction=" + (action != null) + "; border=" + doesDrawBorder() + "; visible=" + visible + "; enabled=" + enabled + "; tooltip=" + tooltip + "}";
	}

	public static interface Action {
		public void act(int button, int x, int y, int clickCount);
	}
}