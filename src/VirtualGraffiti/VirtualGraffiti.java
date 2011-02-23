package VirtualGraffiti;

import java.io.FileInputStream;
import java.io.IOException;
import processing.core.PApplet;
import processing.core.PFont;

/*
 * TODO
 * 
 * + GML
 * + calibration of cans
 * + add all this to github
 * - add camera blob tracker
 * - prefs
 * - calibration of tracker
 * - calibration save and load
 * - load and saving images
 */


//public class test extends PApplet {
public class VirtualGraffiti extends PApplet{

	//import ddf.minim.*;

	//	import fullscreen.*; 
	//import codeanticode.gsvideo.*;
	//import s373.flob.*;


	//P5Properties props;


	/*FullScreen fs; 
	AudioSample rattle;
	AudioPlayer spray;
	Minim minim;
	 */

	//where the config file is
	String rootPath = "/Users/stuchilds/Downloads/VGRAF/";
//	String rootPath = "/home/matthew/work/processingSketches/virtualGraffiti/data";
	String confFile = rootPath  + "/virtualPainting.properties";
	//stuff we get from the config file

	//TODO
	int maxBrushSize;
	int minBrushSize;
	int minOpacity;
	int maxOpacity;
	String canType;
	String trackerType;

	int w;
	int h;

	String imagePath;
	String backgroundsPath;

	boolean debug = false;
	double frameTime, oldFrameTime;
	
	PFont fontA;
	Thing thing;

	public static void main(String args[]) {
		PApplet.main(new String[] {"VirtualGraffiti.VirtualGraffiti" });
	}


	public void setup() 
	{
		frameRate( 60 );
		try {
			P5Properties props=new P5Properties();
			// load a configuration from a file inside the data folder
			props.load(new FileInputStream(confFile));

			w=props.getIntProperty("width",1024);
			h=props.getIntProperty("height",768);
			imagePath = props.getStringProperty( "imagePath", "./images/" ); 
			backgroundsPath = props.getStringProperty( "backgroundsPath",sketchPath + "/data/" ); 
			maxBrushSize = props.getIntProperty("brush.maxBrushSize", 70 );
			minBrushSize = props.getIntProperty("brush.minBrushSize", 20 );
			minOpacity = props.getIntProperty("brush.minOpacity", 70 );
			maxOpacity = props.getIntProperty("brush.maxOpacity", 255 );
			canType = props.getStringProperty( "canType", "Mouse" );
			trackerType = props.getStringProperty( "trackerType","Mouse" );
			// useWii = false;
			//	    useCan = props.getBooleanProperty( "useCan", true );
			//    calibrationDelay = props.getIntProperty("calibration.delayLength", 10 );
			//   calibrationPause = props.getIntProperty("calibration.pauseLength", 30 );
			
			System.out.println( "can type:'" + canType + "'");
			System.out.println( "tracker type:" + trackerType );
			
			System.out.println( "image save path:" + imagePath );
			System.out.println( "image load path:" + backgroundsPath );
		}

		catch(IOException e) {
			println("couldn't read config file...");
		}
		size(w,h);
		
		//fontA = loadFont( rootPath + "/data/Ziggurat-HTF-Black-32.vlw");

		// Create the fullscreen object
		//	  fs = new FullScreen(this); 

		// enter fullscreen mode
		//  fs.enter(); 
		//load the sounds 
		
		/*
			  minim = new Minim(this);
		  rattle = minim.loadSample("rattle.wav", 2048);
		  if ( rattle == null ) println("Didn't get rattle!");
		  spray = minim.loadFile("spraylong.wav", 2048);
		  if( spray == null ) println( "didn't get spray!" );
	  */
		
		//can types
		thing = new Thing( this, canType, trackerType );
	}

	public void stop()
	{
		// always close Minim audio classes when you are done with them
		/*
		rattle.close();
	  spray.close();
	  minim.stop();
		 */
	}

	public void draw()
	{
		double count = 0;

		//first update the can tracker
		thing.update();
		if( thing.calibrated() )
		{
			oldFrameTime = frameTime;
			thing.paint();
			frameTime = millis();
			if( debug )
			{
			int fpsInt = (int)( 1000 / ( frameTime - oldFrameTime ));
			if( count ++ % 1000 == 0 )
				System.out.println( "fps: " + fpsInt );
			}
		}
		else
		{
			thing.calibrate();
		}
	}	

	public void keyPressed()
	{
	  if( key == 'c' )
	  {
		  thing.wipeCalibration();
	  }
	  if( key == 'l' )
	  {
		  thing.loadCalibration();
	  }
	  if( key == 's' )
	  {
		  thing.storeCalibration();
	  }
	  if( key == 'd' )
	  {
		  if( debug ) 
			  debug = false;
		  else
			  debug = true;
	  }
	}

}
