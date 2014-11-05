package net.foxgenesis.slimesoccer.util;

import java.util.ArrayList;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author michael_daley
 *
 * Class which takes a message, location and bounce height and then is able to render that 
 * message with a SIN bounce running through it.
 */
public class TextBounce {

   private ArrayList<Character> characters;
   
   public TextBounce(String message, int x, int y, AngelCodeFont font, float speed, int bounceHeight) throws SlickException{
      /* Create character objects for all characters in the message */
      characters = new ArrayList<Character>();
      int mx = x;
      for (int i=0;i<message.length();i++) {
             Character c = new Character(i, message.substring(i, i+1),font, bounceHeight, speed,  mx, y);
             characters.add(c);
             mx += font.getWidth(message.substring(i, i+1)) + 1;
      }
   }
   
   /**
    * Update the individual characters which make up this message
    * 
    * @param delta milliseconds since last update passed from the main Update method
    */
   public void update(int delta) {
      for (int i=0;i<characters.size();i++) {
         Character c = characters.get(i);
         c.update(delta);
      }
   }
   
   /**
    * Render the individual characters to the graphics context
    * 
    * @param g Graphics context to which items should be rendered
    */
   public void render(Graphics g) {
      for (int i=0;i<characters.size();i++) {
         Character c = characters.get(i);
         c.render(g);
      }      
   }

   /**
    * @author michael_daley
    * 
    * Inner Class which is used to control each individual character in the bouncing message
    */
   public class Character {

      private int id;
      private String c;
      private int x;
      private int y;
      private int bounceHeight;
      private float speed;
      private float counter;
      private AngelCodeFont font;
      
      public Character(int id, String c, AngelCodeFont font, int boundHeight, float speed, int x, int y) {
         this.id = id;
         this.c = c;
         this.font = font;
         this.x = x;
         this.y = y;
         this.bounceHeight = boundHeight;
         this.speed = speed;
      }
      
      public void update(int delta) {
         /* Normally you would update this counter, which controls the speed of the bounce
          * using the delta passed in.  I've been having problems with Delta and vSync being
          * used together.  For this reason I restrict the FPS to 60 and set the vSync as well.
          * I then just upate the counter without using delta and I get smooth playback.
          */
         counter += speed;
      }
      
      public void render(Graphics g) {
         /* Sin calculation borrowed from Cute Planet Puzzle by Kev Glass */
         font.drawString(x, y + (float)(-30+(Math.sin(counter+(id*100)) * bounceHeight)), c);
      }   
   }
}
