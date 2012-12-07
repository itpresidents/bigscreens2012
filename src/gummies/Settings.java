package gummies;
import processing.core.PApplet;
import processing.core.PShape;

public class Settings {
	PApplet parent;
	String [] title;
	String names;
	float floodStart, floodEnd, floodRate, waveHeight, launchRate, decayRate;
	boolean isTitleMultiline;
	
	Settings(PApplet p, String[] data) {
		parent = p;
		String delim = ": ";

		title = data[0].split(delim)[1].split("_");
		names = data[1].split(delim)[1];
		floodStart = PApplet.parseFloat(data[2].split(delim)[1]);
		floodEnd = PApplet.parseFloat(data[3].split(delim)[1]);
		floodRate = PApplet.parseFloat(data[4].split(delim)[1]);
		waveHeight = PApplet.parseFloat(data[5].split(delim)[1]);
		launchRate = PApplet.parseFloat(data[6].split(delim)[1]);
		decayRate = PApplet.parseFloat(data[7].split(delim)[1]);
		
		if(title.length > 1)
			isTitleMultiline = true;
	}
}
