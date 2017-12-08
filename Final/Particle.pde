class Particle {
 	private PVector location;
	private PVector velocity;
	private PVector acceleration;
	private float lifespan;
	
	public Particle(PVector l) {
		location = l.get();
		velocity = new PVector(0,10);
		lifespan = 100;
		acceleration = new PVector(0,0.05);
	}
	
	public void update() {
		velocity.add(acceleration);
		location.add(velocity);
		lifespan -= 2.0;
	}

	public void display() {
		noStroke();
		fill(#ffffff, lifespan);
		ellipse(location.x,location.y,8,8);
	}

	public boolean isDead() {
		if (lifespan < 0.0) {
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