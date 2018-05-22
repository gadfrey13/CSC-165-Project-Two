package myGameEngine;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;


import a1.GameObject.GameObject;
import a1.GameObject.ManualMadeGameObjects;
import a1.GameWorld.GameWorld;
import myGameEngine.CameraControl.CameraControl;
import myGameEngine.CameraControl.ObjectControl.Camera3Pcontroller;
import myGameEngine.CameraControl.ObjectControl.MoveForwardAction;
import myGameEngine.CameraControl.ObjectControl.ObjectControl;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.rage.Engine;
import ray.rage.game.VariableFrameRateGame;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.rendersystem.Viewport;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.rendersystem.gl4.GL4RenderSystem;
import ray.rage.scene.Camera;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rage.scene.controllers.TranslationController;
import ray.rage.scene.controllers.WaypointController;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class MyGameEngineTwo extends VariableFrameRateGame{
	//Camera
	private MyCamera cameraOne;
	private MyCamera cameraTwo;
	//MyCamera Controller
	private CameraControl controlOne;
	private CameraControl controlTwo;
	
	//MyGameObject Controller
	private ObjectControl dolphinOne,dolphinTwo;
	//InputManager
	private InputManager im;
	
	//GameWorld
	private GameWorld gw;
	//Time
	float elapseTime = 0.0f;
	
	//Test Object
	GameObject test,testTwo;
	
	//Orbit Controller
	private Camera3Pcontroller orbitController,orbitControllerTwo;
	
	//Boolean
	private boolean bol = true;
	
	//Object Tracker
	private ObjectTracker tracker;
	
	//GameHub
	private GameHub playerOneHub, playerTwoHub;
	//
	private FloatController flControl;
	//
	private Vector3f ontop = (Vector3f) Vector3f.createFrom(1.0f, 1.0f, 1.0f);
	//
	private SceneManager mySm;
	//Once
	int count = 0;
	//Switch
	private int swi = 0;
	
	//Private rs
	private GL4RenderSystem rs;
	//Translate
	private TranslationController translate;
	@Override
	protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge)
	{ 
		rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
	}
	
	@Override
	protected void setupCameras(SceneManager sm, RenderWindow rw) {
		//SetUp Camera One
		cameraOne = new MyCamera(sm,rw);
		cameraOne.setUpCamera("CameraOne", 0,'n');
		//SetUp Camera Two
		cameraTwo = new MyCamera(sm,rw);
		cameraTwo.setUpCamera("CameraTwo", 1,'n');
	}

	@Override
	protected void setupScene(Engine eng, SceneManager sm) throws IOException {
		//Game World
		gw = new GameWorld(eng,sm);
		//Contain all the game objects
		gw.init();
		//Input Manager
		im = new GenericInputManager();
		try{
		String gpName = im.getFirstGamepadName();
		String kbName = im.getKeyboardName();
		//Dolphin Controller
		dolphinTwo = new ObjectControl(cameraOne,gw.playerTwo(),im,kbName,gpName);
		dolphinTwo.setUpKeyBoard();
			
		//Dolphin Controller
		dolphinOne = new ObjectControl(cameraTwo,gw.playerOne(),im,kbName,gpName);
		dolphinOne.setUpGamePad();
		
		setupOrbitCamera(eng,sm,kbName,gpName);
		}catch(Exception e){
			System.out.println("Cant find keyboard or gamepad");
		}
		//Game Hubs
		playerOneHub = new GameHub(eng);
		playerTwoHub = new GameHub(eng);
		
		//Keep track of objects
		tracker = new ObjectTracker(gw.getPlayerCollection(),gw.getCollections(),playerOneHub,playerTwoHub,sm);
		//Get Name
		
		
		//Controller for camera One
		//controlOne = new CameraControl(cameraOne,im);
		//controlOne.setUpKeyBoard();
		//Controller for camera two
		//controlTwo = new CameraControl(cameraTwo,im);
		//controlTwo.setUpGamePad();
				
		//Dolphin Controller
		
		this.mySm = sm;
		
	}

	@Override
	protected void update(Engine eng) {
		rs = (GL4RenderSystem) eng.getRenderSystem();
		//SetUP Hub
		playerOneHub.setHub(15, 350);
		playerTwoHub.setHub2(15, 15);
		//Update the input manager
		im.update(elapseTime);
		//Update the location of the camera
		orbitController.updateCameraPosition();
		orbitControllerTwo.updateCameraPosition();
		//Keep track of the objects
		track(gw.getPlayerCollection(),gw.getCollections(),playerOneHub,playerTwoHub,mySm,gw.getStars(),gw.getParent(),eng);
		if(playerOneHub.getScore() > 20){
			String gameOver = "Player 1 Wins";
			rs.setHUD(gameOver, rs.getCanvas().getWidth()/2, rs.getCanvas().getHeight()/2 + 50);
		}
		
		if(playerTwoHub.getScore() > 20){
			String gameOver = "Player 2 Wins";
			rs.setHUD(gameOver, rs.getCanvas().getWidth()/2, rs.getCanvas().getHeight()/2 - 50);
		}
		
	}
	
	protected void setupOrbitCamera(Engine eng, SceneManager sm,String kbName, String gpName) {
		//Setup Camera For Player Two
		SceneNode dolphinN = sm.getSceneNode(gw.playerTwo().getNode().getName());
		SceneNode cameraN = sm.getSceneNode(cameraTwo.getCameraNode().getName());
		Camera camera = sm.getCamera(cameraTwo.getCamera().getName());
		orbitController = new Camera3Pcontroller(camera, cameraN, dolphinN, kbName, im,0);
	
		//Setup Camera For Player One
		SceneNode dolphinTwoN = sm.getSceneNode(gw.playerOne().getNode().getName());
		SceneNode cameraTwoN = sm.getSceneNode(cameraOne.getCameraNode().getName());
		Camera cameraTwo = sm.getCamera(cameraOne.getCamera().getName());
		orbitControllerTwo = new Camera3Pcontroller(cameraTwo,cameraTwoN,dolphinTwoN,gpName,im,1);
	}
	
	
	@Override
	protected void  setupWindowViewports(RenderWindow rw){//Passing an object will allow that object to have reference to that object
		rw.addKeyListener(this); //Updates the render window
		
		//Top Part Of the Screen
		Viewport topViewport = rw.getViewport(0);
		topViewport.setDimensions(0.51f, .01f, .99f, 0.49f);//bottom, left, width, height
		//topViewport.setClearColor((new Color(1.0f,.7f,.7f)));
		
		Viewport botViewport = rw.createViewport(.01f, .01f, .99f, .49f);
		//botViewport.setClearColor(new Color(.5f,1.0f,.5f));
	}
	
	public void track(GameObject[] collectionsOfPlayers, GameObject[] collections, GameHub playerOne, GameHub playerTwo,SceneManager sm,GameObject[] stars, GameObject parent,Engine eng){
		
		float time = eng.getElapsedTimeMillis();
		int timeSec = Math.round((time/1000.0f));
		flControl = new FloatController();
		sm.addController(flControl);
		int points = 0;
		for(int i = 0; i < collections.length; i++){
			points += collections[i].getPoints();
		}
		for(int i = 0; i < collectionsOfPlayers.length; i++){
			GameObject player = collectionsOfPlayers[i];
			for(int j = 0; j < collections.length; j++){
				GameObject prize = collections[j];
				GameObject star = stars[j];
				Vector3f distance = (Vector3f) player.getNode().getLocalPosition().sub(prize.getNode().getLocalPosition());
				if(player.getType().equals("player1")){
					float distanceToPrize = distance.length();
						  distanceToPrize = Math.abs(distanceToPrize);
						 // System.out.println("Player 1 Distance " + " " + distanceToPrize);
					if(distanceToPrize < 1){
						float value = j * 0.02f;
						flControl.addNode(prize.getNode());
						gw.getRotation().setSpeed(value);
						prize.minusPoint();
						playerOne.add();
		
					}
					playerOne.sendSize(points);
				}
				
				if(player.getType().equals("player2")){
					float distanceToPrize = distance.length();
						  distanceToPrize = Math.abs(distanceToPrize);
						 // System.out.println("Player 2 Distance " + " " + distanceToPrize);
					if(distanceToPrize  < 1 ){
						float value = j * 0.02f;
						flControl.addNode(prize.getNode());
						playerTwo.add();
						prize.minusPoint();
						gw.getRotation().setSpeed(value);
					
					}
					playerTwo.sendSize(points);
				}
			}
		}
		
	
	  
	}

}
