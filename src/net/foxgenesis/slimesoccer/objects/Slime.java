@@ -20,16 +20,18 @@ import org.newdawn.slick.Image;
 * @author Seth
 */
public class Slime extends GameObject {
	private final Image img;
	private Image flipped;
	private final Image img; 
        private Image flipped;
	private final Type type;
	private static final float MAX_SPEED = 5f, SPEED = 0.3f, JUMP_VELOCITY = -5f;
	private final boolean controlled, secondary;
	private boolean canJump = true,abilityCheck = true;
	private int direction = 0;
        private int rotationDegrees = 0; 
	private double cooldown = 0.5;
	private ProgressBar bar;
	private Timer timer;
        private boolean indianJump = true; 

	/**
	 * Create a new Slime with a type and controlled parameters
@@ -50,6 +52,7 @@ public class Slime extends GameObject {
		super(100, 100);
		img = Textures.get(type.img).getScaledCopy((int)width,(int)height);
		flipped = img.getFlippedCopy(true,false);
                img.rotate(90);
		this.type = type;
		this.controlled = controlled;
		this.secondary = secondaryInput;
@@ -63,10 +66,11 @@ public class Slime extends GameObject {
		if(!secondary)
			bar.setInvertedPercentage(true);
	}
	
	public Image[] getImages() {
		return new Image[]{img,flipped};
	}
        
        public Image[] getImage()
        {
            return new Image[]{img, flipped}; 
        }
	
	/**
	 * Gets the type of slime
@@ -95,13 +99,13 @@ public class Slime extends GameObject {

	@Override
	public void update(int delta) {
		super.update(delta);
		bar.setLocation(secondary?SlimeSoccer.getWidth()-bar.getWidth()-10f:10f, 50f);
		bar.setLocation(secondary?SlimeSoccer.getWidth()-bar.getWidth()-10:10, 50);
		bar.update(delta);
		bar.setValue(bar.getValue()+(secondary?cooldown:-cooldown));
		img.setRotation(this.getRotation());
		flipped = img.getFlippedCopy(true, false);
		flipped.rotate(-img.getRotation());
                this.setRotation(90);
                flipped = img.getFlippedCopy(true, false);
                flipped.rotate(-img.getRotation()); 
		if(controlled) {
			if(KeyboardInput.keys[secondary?Keyboard.KEY_RIGHT:Keyboard.KEY_D]) {
				velocity.x = velocity.x + SPEED < MAX_SPEED?velocity.x+=SPEED:MAX_SPEED;
@@ -112,6 +116,7 @@ public class Slime extends GameObject {
				direction = 0;
			}
			else
                        if(indianJump) {
				if(canJump)
					if(velocity.x > 0)
						if(velocity.x - SPEED/2 < 0)
@@ -127,6 +132,7 @@ public class Slime extends GameObject {
				velocity.y = JUMP_VELOCITY;
				canJump = false;
			}
                        }
			
			if(abilityCheck && canUseAbility() && KeyboardInput.keys[secondary?Keyboard.KEY_SPACE:Keyboard.KEY_1]) {
				abilityCheck = false;
@@ -137,17 +143,36 @@ public class Slime extends GameObject {
						abilityCheck = true;
					}
				}, 1000);
				type.abilityActivated(this);
                                if(!secondary)
                                    type.firstAbilityActivated(this);
                                else
                                    type.secondAbilityActivated(this);
			}
			if(outOfBounds(location.x,location.y + velocity.y,false,true))
				canJump = true;
		}
	}
        
        public void moveY(float y) {
            location.y -= y; 
        }

	@Override
	public boolean isSolid() {
		return false;
	}
        
        public void changeJump() {
            if(indianJump)
                indianJump = false; 
            else
                indianJump = true; 
        }
        
        public void discoRotation(int r)
        {
            rotationDegrees = r; 
        }
	
	/**
	 * Checks if the player can use their ability
@@ -162,7 +187,11 @@ public class Slime extends GameObject {
	 * @author Seth
	 */
	public static enum Type {
		DEFAULT("svetty",1000);
		DEFAULT("defaultslime",0), 
                SPONGE("spongeslime", 10000), 
                DISCO("discoslime", 10000),
                INDIAN("indianslime", 10000) ;
                
		
		private String img;
		private long duration;
@@ -175,8 +204,12 @@ public class Slime extends GameObject {
			this.duration = duration;
		}
		
		private void abilityActivated(Slime slime) {
			SlimeAbilityUtil.handelAbility(slime);
		private void firstAbilityActivated(Slime slime) {
			SlimeAbilityUtil.handleFirstAbility(slime);
		}
                
                private void secondAbilityActivated(Slime slime) {
			SlimeAbilityUtil.handleSecondAbility(slime);
		}
		
		public long getDuration() {
