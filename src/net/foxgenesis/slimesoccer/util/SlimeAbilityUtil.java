package net.foxgenesis.slimesoccer.util;
import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.Slime;
import net.foxgenesis.slimesoccer.ui.Scene;
import net.foxgenesis.slimesoccer.ui.SoccerGame;

public final class SlimeAbilityUtil {
    public static void handleFirstAbility(Slime slime) {
		if(Scene.getCurrentScene() instanceof SoccerGame) {
			SoccerGame game = (SoccerGame)Scene.getCurrentScene();
			Ball b = (Ball)game.getObjects()[SoccerGame.BALL];
			Slime p1 = (Slime)game.getObjects()[SoccerGame.PLAYER1];
			Slime p2 = (Slime)game.getObjects()[SoccerGame.PLAYER2];
                        
                        boolean abilityUsed = false; 
			switch(p1.getType()) {
                            case SPONGE:
                                if(!abilityUsed)
                                {
                                   
                                    //ystem.out.println("sponge left");
                                    
                                    p1.modifyHeight(300);
                                    p1.modifyWidth(300);
                                        p1.moveY(200);
                                    abilityUsed = true; 
                                }
                                else
                                {
                                    p1.modifyHeight(100);
                                    p1.modifyWidth(100); 
                                    p1.moveY(-200); 
                                    abilityUsed = false; 
                                }
                                    
                                break;
                            case DISCO: 
                                if(!abilityUsed)
                                {    
                                    System.out.println("disco left");
                                    
                                
                                    //    p1.getImage().setRotation(90); 
                                    p1.moveX(200);
                                    p1.modifyWidth(200);
                                    abilityUsed = true;
                                }
                                else
                                {
                                    p1.modifyWidth(100);
                                    p1.setRotation(0);
                                    abilityUsed = false; 
                                }
                                break;
                            case INDIAN:
                                if(!abilityUsed)
                                {
                                    System.out.println("indian left");
                                    p2.changeJump(); 
                                    abilityUsed = true; 
                                }
                                else
                                {
                                    p2.changeJump();
                                    abilityUsed = false;
                                
                                }
                                break;
                            case RUNNER:
                                if(!abilityUsed)
                                {
                                    System.out.println("runner left");
                                    if(p1.facingRight())
                                        p1.moveX(200);
                                    else
                                        p1.moveX(200);
                                    
                                    abilityUsed = true;
                                }
                                else
                                {
                                    //abilityUsed = false;
                                }
                            break;
                                case GHOST:
                              if(!abilityUsed)
                                {
                                    System.out.println("ghost right"); 
                                    
                                        p2.makeInvisible();
                                   
                                    abilityUsed = true;
                                 }
                                else
                                {
                                    p2.makeInvisible();
                                    abilityUsed = false;
                                }
                                break;
                            case DEFAULT:  
                                break;
                            }
                        
                    
                        }
	}
    public static void handleSecondAbility(Slime slime) {
		if(Scene.getCurrentScene() instanceof SoccerGame) {
			SoccerGame game = (SoccerGame)Scene.getCurrentScene();
			Ball b = (Ball)game.getObjects()[SoccerGame.BALL];
			Slime p1 = (Slime)game.getObjects()[SoccerGame.PLAYER1];
			Slime p2 = (Slime)game.getObjects()[SoccerGame.PLAYER2];
			boolean abilityUsed = false; 
			switch(p2.getType()) {
                            case SPONGE:
                               if(!abilityUsed)
                                {
                                    System.out.println("sponge left");
                                    p2.modifyHeight(300);
                                    p2.modifyWidth(300);
                                        
                                    abilityUsed = true; 
                                }
                                else
                                {
                                    p2.modifyHeight(100);
                                    p2.modifyWidth(100); 
                                    
                                    abilityUsed = false; 
                                }
                                    
                                break;
                            case DISCO: 
                                if(!abilityUsed)
                                {    
                                    System.out.println("disco right");
                                    p2.moveX(200);
                                    p2.modifyWidth(200);
                                    
                                //    p2.discoRotation(90);
                                    abilityUsed = true;
                                }
                                else
                                {
                                    p2.modifyWidth(100);
                                    
                                    p2.setRotation(0);
                                    abilityUsed = false; 
                                }
                                break;
                            case INDIAN:
                                if(!abilityUsed)
                                {
                                    System.out.println("indian right");
                                    p1.changeJump(); 
                                    abilityUsed = true; 
                                }
                                else
                                {
                                    p1.changeJump();
                                    abilityUsed = false;
                                
                                }
                                break;
                          case RUNNER:
                                if(!abilityUsed)
                                {
                                    System.out.println("runner right"); 
                                    if(p2.facingRight())
                                        p2.moveX(200);
                                    else
                                        p2.moveX(200);
                                    
                                    abilityUsed = true;
                                }
                                else
                                {
                                    //abilityUsed = false;
                                }
                                break;
                          case GHOST:
                              if(!abilityUsed)
                                {
                                    System.out.println("ghost right"); 
                                    
                                        p1.makeInvisible(); 
                                   
                                    abilityUsed = true;
                                 }
                                else
                                {
                                    p1.makeInvisible();
                                    abilityUsed = false;
                                }
                                break;
                            case DEFAULT:  
                                break;
                            }
                        
                    
                        }
	}
                        
                    
       }