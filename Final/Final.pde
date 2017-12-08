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
	fullScreen();
	background(51);
	smooth();

	stateManager.renderFirstTwig();
}

public void draw()
{
	stateManager.update();
}