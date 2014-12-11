package net.foxgenesis.slimesoccer.util;
import java.util.Timer;
import java.util.TimerTask;

import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.Slime;
import net.foxgenesis.slimesoccer.objects.Slime.Type;
import net.foxgenesis.slimesoccer.ui.Scene;
import net.foxgenesis.slimesoccer.ui.SoccerGame;

/**
 * SlimeAbilityUtil handles the abilities of all slimes
 * @author Brandon
 * @editor Seth
 */
public final class SlimeAbilityUtil {
	private static final Timer timer = new Timer();
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

			switch(user.getType()) {
			case SPONGE:
				user.setSize(300,300);
				user.getLocation().y = 200;
				undo(new Runnable() {
					@Override
					public void run() {
						user.setSize(100, 100);
						user.getLocation().y = -200; 
					}
				}, user.getType());
				break;
			case DISCO: 
				user.setRotation(90);
				user.setSize(200,user.getHeight());
				undo(new Runnable() {
					@Override
					public void run() {
						user.setSize(100, user.getHeight());
						user.setRotation(0);
					}
				}, user.getType());
			case INDIAN:
				target.changeJump(); 
				undo(new Runnable() {
					@Override
					public void run() {
						target.changeJump();
					}
				}, user.getType());
				break;
			case RUNNER:
				if(user.getDirection() == 0)
					user.getLocation().x = 200;
				else
					user.getLocation().x = 400;
				break;
			case GHOST:
				target.setOpacity(0f);
				undo(new Runnable() {
					@Override
					public void run() {
						target.setOpacity(1f);
					}
				}, user.getType());
				break;
			case TRAFFIC:
				b.getVelocity().x = b.getVelocity().y = 0f; 
				break;
			case FIRE:
				b.getVelocity().x *= 1.5f; 
				b.getVelocity().y *= 1.5f;
				break;
			case ICE:
				b.getVelocity().x *= .3f;
				b.getVelocity().y *= .3f; 
				break;
			case MONK:
				b.getVelocity().x = -b.getVelocity().x; 
				break; 
			case WATER:
				b.getVelocity().y = b.getVelocity().y > 0?-11f:-8f;
				break;
			case BOXER:
				float xD = Math.abs(user.getLocation().x-target.getLocation().x);
				if(xD < 200) {
					target.setPaused(true);
					undo(new Runnable() {
						@Override
						public void run() {
							target.setPaused(false);
						}
					},user.getType());
				}
				break;
			case TEST5:
				game.getObjects()[SoccerGame.BALL2] = new Ball();
				undo(new Runnable() {
					@Override
					public void run() {
						game.getObjects()[5] = null;
					}
				},user.getType());
				break;
			case DEFAULT:  
				break;
			default:
				break;
			}
		}
	}

	private static void undo(final Runnable run, Type type) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				run.run();
			}
		}, type.getDuration());
	}
}