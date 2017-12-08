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
		len *= 0.5;
		String nextSentence = "";
		for (int i = 0; i < sentence.length(); i++) {
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
			case DRAWTREE:	if(currentGen < generations) {
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