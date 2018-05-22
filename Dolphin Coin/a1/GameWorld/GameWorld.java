package a1.GameWorld;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import a1.GameObject.GameObject;
import a1.GameObject.ManualMadeGameObjects;
import myGameEngine.ObjectTracker;
import ray.rage.Engine;
import ray.rage.scene.Light;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rage.scene.controllers.RotationController;
import ray.rage.scene.controllers.ScalingController;
import ray.rage.scene.controllers.WaypointController;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class GameWorld {
	private Engine eng;
	private SceneManager sm;
	private GameObject dolphinOne,dolphinTwo;
	private int sizeofCollection = 1;
	private GameObject[] collections = new GameObject[100];
	private int size = 40;
	private GameObject[] prize = new GameObject[size];
	private GameObject[] stars = new GameObject[size];
	private GameObject[] collectionsOfPlayers = new GameObject[2];
	private int sizeRemover = 4;
	private GameObject[] scoreRemover = new GameObject[sizeRemover];
	GameObject ultimate;
	private Vector3f rotX = (Vector3f) Vector3f.createFrom(1.0f, 0.0f, 0.0f);
	private Vector3f rotY = (Vector3f) Vector3f.createFrom(0.0f, 1.0f, 0.0f);
	private Vector3f rotZ = (Vector3f) Vector3f.createFrom(0.0f, 0.0f, 1.0f);
	private RotationController rot;
	private GameObject parent;
	public GameWorld(Engine eng, SceneManager sm){
		this.eng = eng;
		this.sm = sm;
	}
	
	public void init() throws IOException{
		//Manual Made Game Object Square
		ManualMadeGameObjects square = new ManualMadeGameObjects();
		square.createSquare(eng, sm, "square");
		square.getSquare().setLocalPosition(0.0f, -0.3f, 0.0f);
		square.getSquare().rotate(Degreef.createFrom(180), Vector3f.createFrom(1.0f,  0.0f, 1.0f));
		square.getSquare().scale(30.0f, 0.1f, 30.0f);
		
		//Manual Made Game Object Lines
		ManualMadeGameObjects lines = new ManualMadeGameObjects();
		lines.createAxes(eng, sm);
		
		//Dolphin Player One
		dolphinOne = new GameObject(sm,"dolphinPlayerOne","dolphinHighPoly.obj","player1");
		dolphinOne.create();
		dolphinOne.getNode().setLocalPosition(0.0f, 0.0f, 1.0f);
		dolphinOne.addTexture("Dolphin_HighPolyUV.png");
		dolphinOne.addMaterial("dolphin.mtl");
		dolphinOne.getNode().rotate(Degreef.createFrom(45), Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		
		//Dolphin Player Two
		dolphinTwo = new GameObject(sm,"dolphinPlayerTwo","dolphinHighPoly.obj","player2");
		dolphinTwo.create();
		dolphinTwo.getNode().setLocalPosition(3.0f, 0.0f, 4.0f);
		dolphinTwo.addTexture("Dolphin_HighPolyUV.png");
		dolphinTwo.addMaterial("dolphin.mtl");
		
		//Create Light
		sm.getAmbientLight().setIntensity(new Color(.4f, .4f, .4f));

		Light plight = sm.createLight("testLamp1", Light.Type.POINT);
		plight.setAmbient(new Color(.4f, .4f, .4f));
		plight.setDiffuse(new Color(.7f, .7f, .7f));
		plight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
		plight.setRange(50f);

		SceneNode plightNode = sm.getRootSceneNode().createChildSceneNode("plightNode");
		plightNode.attachObject(plight);
		
		//Parent
		parent = new GameObject(sm,"blank","sphere.obj","parent");
		parent.create();
		parent.addTexture("stars.png");
		parent.getNode().setLocalPosition(Vector3f.createFrom(0.0f, 4.0f, 0.0f));
		parent.addMaterial("sphere.mtl");
		/*
		Light sunLight = sm.createLight("sunlight", Light.Type.POINT);
		sunLight.setAmbient(new Color(.4f, .4f, .4f));
		sunLight.setDiffuse(new Color(1.0f, 1.0f, 0.0f));
		sunLight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
		sunLight.setRange(10f);

		SceneNode sunNode = sm.getRootSceneNode().createChildSceneNode("sunNode");
		sunNode.attachObject(sunLight);
		sunNode.setLocalPosition(Vector3f.createFrom(0.0f, 4.0f, 0.0f));
		*/
		RotationController coin = new RotationController(rotY,0.5f);
		sm.addController(coin);
		
		for(int i = 0; i < size; i++){
			String str = "prize" + i;
			prize[i] = new GameObject(sm,str,"dogecoin.obj","prize");
			prize[i].create();
			prize[i].getNode().setLocalPosition(2.0f+rand(), 0.0f, 1.0f+rand());
			prize[i].getNode().scale(0.3f, 0.3f, 0.3f);
			prize[i].addTexture("300coin.png");
			prize[i].getNode().scale(0.005f,0.005f,0.005f);
			//prize[i].addMaterial("cube.mtl");
			coin.addNode(prize[i].getNode());
		}
		
		for(int i = 0; i < size; i++){
			String str = "star" + i;
			stars[i] = new GameObject(sm,str,"sphere.obj","stars");
			stars[i].create();
			stars[i].getNode().setLocalPosition(2.0f+rand(), -2.0f, 1.0f+rand());
			stars[i].getNode().scale(0.2f, 0.2f, 0.2f);
			stars[i].addTexture("stars.png");
			stars[i].addMaterial("sphere.mtl");
		}
		
		//Create a hierachy
		for(int i = 0; i < size; i++){
			parent.getNode().attachChild(stars[i].getNode());
		}
			
		//Players
		collectionsOfPlayers[0] = dolphinOne;
		collectionsOfPlayers[1] = dolphinTwo;
		/*
		for(int i = 0; i < 1; i++){
			String str = "remover" + i;
			scoreRemover[i] = new GameObject(sm,str,"sphere.obj","remover");
			scoreRemover[i].create();
			scoreRemover[i].addMaterial("sphere.mtl");
			scoreRemover[i].addTexture("red.jpeg");
		}
		*/
		
	
		
		//Add rotaion to the prizes
		rot = new RotationController(rotY,0.01f);
		rot.addNode(parent.getNode());
		sm.addController(rot);
		
		GameObject moon = new GameObject(sm,"ultimate","earth.obj","ultimate");
		moon.create();
		moon.getNode().setLocalPosition(-30.0f, 5.0f, 20.0f);
		moon.addMaterial("moon.mtl");
		moon.addTexture("moon.jpeg");
		
		GameObject shuttle = new GameObject(sm,"shuttle","shuttle.obj","shuttle");
		shuttle.create();
		shuttle.addTexture("spstob_1.jpg");
		shuttle.getNode().scale(2.0f, 2.0f, 2.0f);
		shuttle.getNode().rotate(Degreef.createFrom(250), rotY);

		WaypointController way = new WaypointController(1000);
		way.addWaypoint(Vector3f.createFrom(-15.0f, 1.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(-10.0f, 1.5f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(-5.0f, 2.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(0.0f, 2.5f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(5.0f, 3.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(10.0f, 4.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(15.0f, 5.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(20.0f, 6.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(30.0f, 8.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(35.0f, 9.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(40.0f, 10.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(50.0f, 11.0f, 20.0f));
		way.addWaypoint(Vector3f.createFrom(-15.0f,100.0f, 20.0f));
		way.addNode(shuttle.getNode());
		sm.addController(way);
		
	}
	
	public RotationController getRotation(){
		return rot;
	}
	
	
	public GameObject playerOne(){
		return dolphinOne;
	}
	
	public GameObject playerTwo(){
		return dolphinTwo;
	}
	
	public GameObject[] getCollections(){
		return prize;
	}
	
	public GameObject[] getPlayerCollection(){
		return collectionsOfPlayers;
	}
	
	public GameObject getUltimatePrize(){
		return ultimate;
	}
	
	public int rand(){
		return (int) (Math.random() * 60) - 30;
	}
	
	public GameObject[] getStars(){
		return stars;
	}
	
	public GameObject getParent(){
		return parent;
	}
}
