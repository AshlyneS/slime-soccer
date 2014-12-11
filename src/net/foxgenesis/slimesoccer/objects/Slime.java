package net.foxgenesis.slimesoccer.objects;

import java.util.Timer;
import java.util.TimerTask;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.io.KeyboardInput;
import net.foxgenesis.slimesoccer.ui.component.ProgressBar;
import net.foxgenesis.slimesoccer.util.SlimeAbilityUtil;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Slime is the player object
 * @author Seth
 */
public class Slime extends GameObject {
	private final Image img;
	private Image flipped;
	private final Type type;
	private static final float MAX_SPEED = 5f, SPEED = 0.3f, JUMP_VELOCITY = -5f;
	protected final boolean controlled, secondary;
	private boolean canJump = true,abilityCheck = true, indianJump = true;
	private int direction = 0;
	private Font font;
	private double cooldown = 0.5;
	private ProgressBar bar;
	private Timer timer;
	private int goals = 0;
        protected float opacity = 1f;
	protected static boolean paused;

	/**
	 * Create a new Slime with a type and controlled parameters
	 * @param type -  type of slime to make
	 * @param controlled - is this player controlled
	 */
	public Slime(Type type, boolean controlled) {
		this(type,controlled,false);
	}

	/**
	 * Create a new Slime with a type and controlled parameters
	 * @param type -  type of slime to make
	 * @param controlled - is this player controlled
	 * @param secondaryInput - if to use secondary input controls
	 */
	public Slime(Type type, boolean controlled, boolean secondaryInput) {
		super(100, 100);
		img = Textures.get(type.img).getScaledCopy((int)width,(int)height);
		flipped = img.getFlippedCopy(true,false);
                img.rotate(90);
		this.type = type;
		font = Fonts.get("hiero");
		this.controlled = controlled;
		this.secondary = secondaryInput;
		timer = new Timer();
		bar = new ProgressBar();
		bar.setPrintText(false);
		bar.setSize(SlimeSoccer.getWidth()/2-60, 20);
		bar.setLocation(secondary?SlimeSoccer.getWidth()-bar.getWidth()-10f:10f, 50f);
		bar.setBackground(secondary?Color.black:Color.green);
		bar.setForeground(secondary?Color.green:Color.black);
		bar.setValue(50);
		if(!secondary)
			bar.setInvertedPercentage(true);
	}

	public Image[] getImages() {
		return new Image[]{img,flipped};
	}

	public int getGoalCount() {
		return goals;
	}

	public void setGoalCount(int count) {
		goals = count;
	}

	public void changeJump() {
		if(indianJump)
			indianJump = false; 
		else
			indianJump = true; 
	}

	/**
	 * Gets the type of slime
	 * @return type of slime
	 */
	public Type getType() {
		return type;
	}

	@Override
	public void render(Graphics g) {
		bar.draw(g);
		switch(direction) {
		case 0:
			if(img != null)
				img.draw(location.x,location.y, width, height, new Color(1f,1f,1f,opacity));
			break;
		case 1:
			if(flipped != null)
				flipped.draw(location.x,location.y, width, height, new Color(1f,1f,1f,opacity));
			break;
		}
		font.drawString(secondary?SlimeSoccer.getWidth()-font.getWidth(type.name())-5:15, 55, type.name(), Color.black);
		font.drawString(secondary?SlimeSoccer.getWidth()-font.getWidth(type.name())-10:10, 50, type.name());

		font.drawString(SlimeSoccer.getWidth()/2 - font.getWidth(""+goals)/2 - (secondary?-40:35), 55, ""+goals, Color.black);
		font.drawString(SlimeSoccer.getWidth()/2 - font.getWidth(""+goals)/2 - (secondary?-35:40), 50, ""+goals);
	}
        
        public boolean facingRight()
        {
            return direction == 0;
        }

	@Override
	public void update(int delta) {
		super.update(delta);
		bar.update(delta);
		bar.setValue(bar.getValue()+(secondary?cooldown:-cooldown));
		img.setRotation(this.getRotation());
		flipped = img.getFlippedCopy(true, false);
		flipped.rotate(-img.getRotation());
                
		if(controlled) {
			if(KeyboardInput.keys[secondary?Keyboard.KEY_RIGHT:Keyboard.KEY_D]) {
				velocity.x = velocity.x + SPEED < MAX_SPEED?velocity.x+=SPEED:MAX_SPEED;
				direction = 1;
			}
			else if(KeyboardInput.keys[secondary?Keyboard.KEY_LEFT:Keyboard.KEY_A]) {
				velocity.x = velocity.x - SPEED > -MAX_SPEED?velocity.x-=SPEED:-MAX_SPEED;
				direction = 0;
			}
			else
                           // if(indianJump)
                            //{
				if(canJump)
					if(velocity.x > 0)
						if(velocity.x - SPEED/2 < 0)
							velocity.x -= velocity.x;
						else
							velocity.x-=SPEED/2;
					else if(velocity.x < 0)
						if(velocity.x + SPEED/2 > 0)
							velocity.x -= velocity.x;
						else
							velocity.x+=SPEED/2;
                                if(indianJump)
                                {
			if(KeyboardInput.keys[secondary?Keyboard.KEY_UP:Keyboard.KEY_W] && canJump) {
				velocity.y = JUMP_VELOCITY;
				canJump = false;
			}
                            }

			if(abilityCheck && canUseAbility() && KeyboardInput.keys[secondary?Keyboard.KEY_SPACE:Keyboard.KEY_1]) {
				abilityCheck = false;
				bar.setValue(bar.getValue()+(secondary?-50:50));
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						abilityCheck = true;
					}
				}, 1000);
				if(secondary)
					type.secondAbilityActivated(this);
				else
					type.firstAbilityActivated(this);
			}
			if(outOfBounds(location.x,location.y + velocity.y,false,true))
				canJump = true;
		}
	}

	@Override
	public boolean isSolid() {
		return paused;
	}

	/**
	 * Checks if the player can use their ability
	 * @return if the player can use their ability
	 */
	public boolean canUseAbility() {
		return secondary?bar.getValue()>bar.getMaximumValue()/2:bar.getValue()<bar.getMaximumValue()/2;
	}

	/**
	 * Type contains the list of Slime types
	 * @author Seth
	 */
	public static enum Type {
		DEFAULT("defaultslime",1000),
		GOAL("goal",1000),
		SPONGE("spongeslime",1000),
		DISCO("discoslime",1000),
		INDIAN("indianslime",1000),
		NATURE("natureslime", 1000),
                RUNNER("runnerslime", 1000),
                GHOST("ghostslime", 1000),
                FIRE("fireslime", 1000),
                TRAFFIC("trafficslime", 1000),
		TEST5("svetty",1000);

		private String img;
		private long duration;
		/**
		 * Create a new Type with given Image path
		 * @param img - Image path
		 */
		Type(String img, long duration) {
			this.img = img;
			this.duration = duration;
		}

		private void firstAbilityActivated(Slime slime) {
			SlimeAbilityUtil.handleFirstAbility(slime);
		}

		private void secondAbilityActivated(Slime slime) {
			SlimeAbilityUtil.handleSecondAbility(slime);
		}



		public String getTextureName() {
			return img;
		}

		public long getDuration() {
			return duration;
		}
	}

}
