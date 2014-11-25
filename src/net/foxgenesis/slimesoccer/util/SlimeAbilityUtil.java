package net.foxgenesis.slimesoccer.util;

import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.Slime;
import net.foxgenesis.slimesoccer.ui.Scene;
import net.foxgenesis.slimesoccer.ui.SoccerGame;

public final class SlimeAbilityUtil {

	public static void handelAbility(Slime slime) {
		if(Scene.getCurrentScene() instanceof SoccerGame) {
			SoccerGame game = (SoccerGame)Scene.getCurrentScene();
			Ball b = (Ball)game.getObjects()[SoccerGame.BALL];
			Slime p1 = (Slime)game.getObjects()[SoccerGame.PLAYER1];
			Slime p2 = (Slime)game.getObjects()[SoccerGame.PLAYER2];
			
		}
	}
}
