package net.foxgenesis.slimesoccer.util;
import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.Slime;
import net.foxgenesis.slimesoccer.ui.Scene;
import net.foxgenesis.slimesoccer.ui.SoccerGame;

/**
 * SlimeAbilityUtil handles the abilities of all slimes
 * @author Brandon
 * @editor Seth
 */
public final class SlimeAbilityUtil {
	
	/**
	 * Handles the abilities of all slimes
	 * @param slime - Slime to handle ability for
	 */
	public static void handleFirstAbility(Slime slime) {
		if(Scene.getCurrentScene() instanceof SoccerGame) {
			final SoccerGame game = (SoccerGame)Scene.getCurrentScene();
			final Ball b = (Ball)game.getObjects()[SoccerGame.BALL];
			final Slime user, target;
			if(!slime.isSecondaryControls()) {
				user = (Slime)game.getObjects()[SoccerGame.PLAYER1];
				target = (Slime)game.getObjects()[SoccerGame.PLAYER2];
			}
			else {
				target = (Slime)game.getObjects()[SoccerGame.PLAYER1];
				user = (Slime)game.getObjects()[SoccerGame.PLAYER2];
			}

			boolean abilityUsed = false; 
			switch(user.getType()) {
			case SPONGE:
				if(!abilityUsed) {
					//ystem.out.println("sponge left");
					user.setSize(300,300);
					user.getLocation().y = 200;
					abilityUsed = true; 
					break;
				}
				user.setSize(100, 100);
				user.getLocation().y = -200; 
				abilityUsed = false; 
				break;
			case DISCO: 
				if(!abilityUsed) {    
					System.out.println("disco left");
					//    p1.getImage().setRotation(90); 
					user.getLocation().x = 200;
					user.setSize(200,user.getHeight());
					abilityUsed = true;
					break;
				}
				user.setSize(100, user.getHeight());
				user.setRotation(0);
				abilityUsed = false; 
				break;
			case INDIAN:
				if(!abilityUsed) {
					System.out.println("indian left");
					target.changeJump(); 
					abilityUsed = true; 
					break;
				}
				target.changeJump();
				abilityUsed = false;
				break;
			case RUNNER:
				if(!abilityUsed) {
					System.out.println("runner left");
					if(user.getDirection() == 0)
						user.getLocation().x = 200;
					else
						user.getLocation().x = 200;
					abilityUsed = true;
					break;
				}
				//abilityUsed = false;
				break;
			case GHOST:
				if(!abilityUsed) {
					System.out.println("ghost right"); 
					target.setOpacity(0f);
					abilityUsed = true;
					break;
				}
				target.setOpacity(1f);
				abilityUsed = false;
				break;
			case TRAFFIC:
				if(!abilityUsed) {
					b.getVelocity().x = b.getVelocity().y = 0f; 
					abilityUsed = true; 
					break;
				}
				b.getVelocity().x = b.getVelocity().y = 0f; 
				abilityUsed = false;
				break;
			case FIRE:
				b.getVelocity().x *= 1.5f; 
				b.getVelocity().y *= 1.5f;
				break;
			case DEFAULT:  
				break;
			default:
				break;
			}
		}
	}
}