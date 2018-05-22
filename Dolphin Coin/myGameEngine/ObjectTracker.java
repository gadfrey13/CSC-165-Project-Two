package myGameEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import a1.GameObject.GameObject;
import ray.rage.scene.SceneManager;
import ray.rml.Vector3f;

public class ObjectTracker {
	private GameObject[] collections;
	private GameObject[] collectionsOfPlayers;
	private GameHub playerOne,playerTwo;
	private FloatController flControl;
	private SceneManager sm;
	private int size;
	private Vector3f ontop = (Vector3f) Vector3f.createFrom(0.0f, 0.0f, 0.0f);
	public ObjectTracker(GameObject[] collectionsOfPlaeyers, GameObject[] collections, GameHub playerOne, GameHub playerTwo,SceneManager sm){
		this.collections = collections;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.sm = sm;
		this.collectionsOfPlayers = collectionsOfPlayers;
	}
	
	public void track(GameObject[] collectionsOfPlaeyers, GameObject[] collections, GameHub playerOne, GameHub playerTwo,SceneManager sm){
		flControl = new FloatController();
		sm.addController(flControl);
		for(int i = 0; i < collectionsOfPlayers.length; i++){
			GameObject player = collectionsOfPlayers[i];
			for(int j = 0; j < collections.length; j++){
				GameObject prize = collections[j];
				if(player.getType().equals("player1")){
					if(player.getNode().getLocalPosition().sub(prize.getNode().getLocalForwardAxis()) == ontop ){
						flControl.addNode(prize.getNode());
						playerOne.add();
					}
				}else{
					if(player.getNode().getLocalPosition().sub(prize.getNode().getLocalForwardAxis()) == ontop ){
						flControl.addNode(prize.getNode());
						playerOne.add();
					}
				}
			}
		}
	  }
	}

