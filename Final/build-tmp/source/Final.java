import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Final extends PApplet {

char axiom;
String sentence;
boolean flowerDone;
boolean first = true;
int len;
int generations;
int currentGen;
int genSpeed;
int nbDrops;
float angle;
float thiccNess;
float time;
float n;
ArrayList<Particle> particles = new ArrayList<Particle>();
StateManager stateManager;
Command stopGrowing;
Command grow;
Command snow;
States states;

public void setup() {
	resetMatrix();
	currentGen = 0;
	states = States.DRAWTREE;
	len = height;      
	axiom = 'A';
	sentence += axiom;
	generations = 6; //
	currentGen = 0;
	genSpeed = 1000;
	thiccNess = 4;
	angle = radians(45);
	time = 0;
	nbDrops = 1;
	stateManager = new StateManager();
	stopGrowing = new StopGrowing();
	grow = new Grow();
	snow = new Snow();
    
	frameRate(25);
	
	background(51);
	

	stateManager.renderFirstTwig();
}

public void draw()
{
	stateManager.update();
	System.out.println(time);
}
public interface Command {
   public void execute();
}
public class Grow implements Command {
 public void execute(){
      states = States.DRAWTREE;
   }
}
class Particle {
 	private PVector location;
	private PVector velocity;
	private PVector acceleration;
	private float lifespan;
	
	public Particle(PVector l) {
		location = l.get();
		velocity = new PVector(0,10);
		lifespan = 100;
		acceleration = new PVector(0,0.05f);
	}
	
	public void update() {
		velocity.add(acceleration);
		location.add(velocity);
		lifespan -= 2.0f;
	}

	public void display() {
		noStroke();
		fill(0xffffffff, lifespan);
		ellipse(location.x,location.y,8,8);
	}

	public boolean isDead() {
		if (lifespan < 0.0f) {
			return true;
		} else {
			return false;
		}
	}

	public void run() {
		update();
		display();
	}
}
public class Snow implements Command {
   public void execute(){
      states = States.SNOW;
   }
}
class StateManager {
	private boolean first;

	public StateManager() {
		//grow.execute();
		first = true;
	}

	
private void render() {	
	background(51);
	resetMatrix();
	translate(width / 2, height);
	stroke(58, 209, 103);
	strokeWeight(thiccNess);
	for (int i = 0; i < sentence.length(); i++) {
		char current = sentence.charAt(i);

		if (current == 'A') {
			line(0, 0, 0, -len);
			translate(0, -len);
		} else if (current == 'B') {
			line(0, 0, 0, -len);
			translate(0, -len);
		} else if (current == '[') {
			pushMatrix();
			rotate(-angle);
		} else if (current == ']') {
			popMatrix();
			rotate(angle);
		}
	}
}

private void generate() {
		len *= 0.5f;
		String nextSentence = "";
		for (int i = 0; i < sentence.length(); i++) {
			char current = sentence.charAt(i);
			boolean found = false;
			if	(current == 'B'){
				found = true;
				nextSentence += "BB";
			} else if( current == 'A'){
				found = true;
				nextSentence += "B[A]A";
			}
			if(!found){
				nextSentence += current;
			}
		}
		sentence = nextSentence;
		currentGen++;
		render();
}

	public void update() {
		if(first) {
			grow.execute();
			first = false;
		}

		switch(states) {
			case DRAWTREE:	if(currentGen < generations) {
								delay(1000);	
								generate();
								} else
									stopGrowing.execute();
								break;
				
			
			case WAITING:		delay(3000);
								snow.execute();
								break;
				
			
			case SNOW:		background(51);
								render();
								resetMatrix();

								for(int i = 0; i < nbDrops; i++) {
									n = noise(time);
									float x = map(n,0,1,0,width);
									particles.add(new Particle(new PVector(x,0)));	
								}

								int ok = 0;
								for (Particle item : particles) {
								    item.run();
								}
								break;

			
			default: 			System.out.println("Somthing went terribly wrong...");
								break;
		}
		time++;
	}

	public void renderFirstTwig() {
		render();
	}
}
public enum States {
	DRAWTREE, 
	WAITING, 
	SNOW
}
public class StopGrowing implements Command {
   public void execute(){
      states = States.WAITING;
   }
}
  public void settings() { 	fullScreen(); 	smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Final" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
