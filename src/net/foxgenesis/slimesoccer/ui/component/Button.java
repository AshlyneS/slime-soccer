package net.foxgenesis.slimesoccer.ui.component;

import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

/**
 * Button component
 *
 * @author Seth
 */
public class Button extends Component {

    private Action action;
    private String text;
    private Color foreground = Color.black, background = Color.white;
    private Animation ani;
    private boolean cursorEntered = false;
    private boolean held = false;
    private boolean drawShadow = true;
    private boolean visible = true;
    private boolean enabled = true;
    private boolean drawBorder = true;
    private boolean hover = false;
    private Timer timer;
    private String tooltip = "";

    /**
     * Creates a new button with given text and background
     * @param text - text to be displayed
     * @param ani - animation for background
     */
    public Button(String text, Animation ani) {
    	this.text = text;
        timer = new Timer();
        this.ani = ani;
    }
    /**
     * Creates a new button with a given text
     *
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
     *
     * @return boolean
     */
    public boolean doesDrawBorder() {
        return drawBorder;
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
     * Sets the tooltip to be shown when the cursor hovers over the button
     *
     * @param text
     */
    public void setToolTipText(String text) {
        tooltip = text;
    }

    /**
     * Sets weather the button should draw it's borders or not
     *
     * @param state
     */
    public void setDrawBorder(boolean state) {
        drawBorder = state;
    }

    /**
     * Returns weather the button draws a shadow behind it
     *
     * @return boolean
     */
    public boolean doesDrawShadow() {
        return drawShadow;
    }

    /**
     * Returns weather the button is click-able or not
     *
     * @return boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets weather the button should be click-able or not
     *
     * @param state
     */
    public void setEnabled(boolean state) {
        enabled = state;
    }

    /**
     * Sets weather the button should be drawn or not
     *
     * @param state
     */
    public void setVisible(boolean state) {
        visible = state;
    }

    /**
     * Returns weather the button is visible on the screen
     *
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

    /**
     * Draws the button with a given Graphics
     *
     * @param g Graphics to draw with
     */
    public void draw(Graphics g) {
        if (visible) {
            if (drawShadow) {
                g.setColor(new Color(.5f, .5f, .5f, .5f));
                g.fillRect((int) this.getX() + 3, (int) this.getY() + 3, getWidth(), getHeight());
            }
           try{
                ani.draw((int) this.getX(), (int) this.getY(), getWidth(), getHeight());
            }catch(NullPointerException e)
            {
            	g.setColor(background);
            	g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            }

            if (drawShadow) {
                g.setColor(Color.black);
                g.drawRect((int) this.getX(), (int) this.getY(), getWidth(), getHeight());
            }
            if (!text.equalsIgnoreCase("")) {
                int height = g.getFont().getHeight(text);
                int width = g.getFont().getWidth(text);
                this.setSize(width + 10, height + 10);
                int x = (int) this.getX() + ((this.getWidth() - width) / 2);
                int y = (int) this.getY() + ((this.getHeight() - height) / 2);
                g.setColor(foreground);
                g.drawString(text, x, y);
            }

            if (cursorEntered && !held && enabled) {
                g.setColor(new Color(1f, 1f, 1f, 0.5f));
                g.fillRect((int) this.getX(), (int) this.getY(), getWidth(), getHeight());
            } else if (cursorEntered && held && enabled) {
                g.setColor(new Color(.5f, .5f, .5f, .5f));
                g.fillRect((int) this.getX(), (int) this.getY(), getWidth(), getHeight());
            } else if (!enabled) {
                g.setColor(new Color(0f, 0f, 0f, .5f));
                g.fillRect((int) this.getX(), (int) this.getY(), getWidth(), getHeight());
            }
            if (!tooltip.equalsIgnoreCase("") && hover) {
                int height = g.getFont().getHeight(tooltip);
                int width = g.getFont().getWidth(tooltip);
                int x = (int) getX() + getWidth() + (((width + 5) - width) / 2);
                int y = (int) getY() - 20 + (((height + 5) - height) / 2);
                g.setColor(Color.white);
                g.fillRect((int) getX() + getWidth(), (int) getY() - 20, g.getFont().getWidth(tooltip + 5), g.getFont().getHeight(tooltip + 5));
                g.setColor(Color.black);
                g.drawRect((int) getX() + getWidth(), (int) getY() - 20, g.getFont().getWidth(tooltip + 5), g.getFont().getHeight(tooltip + 5));
                g.drawString(tooltip, x, y);
            }
        }
    }

    /**
     * Draws the button at a given location
     *
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
     *
     * @param action
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Sets the input that the button should listen to
     *
     * @param input Input to listen to
     */
    public void listen(final Input input) {
        input.addMouseListener(new MouseListener() {
            @Override
            public void inputEnded() {
            }

            @Override
            public void inputStarted() {
            }

            @Override
            public boolean isAcceptingInput() {
                return isVisible() && isEnabled();
            }

            @Override
            public void setInput(Input input) {
            }

            @Override
            public void mouseClicked(int button, int x, int y, int clickCount) {
                if (button == Input.MOUSE_LEFT_BUTTON) {
                    if (contains(x, y)) {
                        if (action != null && enabled && visible) {
                            action.act(input);
                        } else;
                    } else;
                } else if (button == Input.MOUSE_RIGHT_BUTTON) {
                    System.out.println(input.getMouseX() + "\t" + input.getMouseY());
                }
            }

            @Override
            public void mouseDragged(int oldx, int oldy, int newx, int newy) {
            }

            @Override
            public void mouseMoved(int oldx, int oldy, int newx, int newy) {
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
                            if (cursorEntered) {
                                hover = true;
                            }
                        }
                    }, 2000, 2000);
                }
            }

            @Override
            public void mousePressed(int button, int x, int y) {
                if (cursorEntered) {
                    if (button == Input.MOUSE_LEFT_BUTTON) {
                        held = true;
                        mousePressedAndHeld();
                    }
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
            public void mouseWheelMoved(int change) {
            }
        });
    }

    /**
     * Sets the text to be displayed on the button
     *
     * @param text text to show
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the text shown on the button
     *
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
     *
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

        /**
         * runs a specific action
         *
         * @param input
         */
        public void act(Input input);
    }
}