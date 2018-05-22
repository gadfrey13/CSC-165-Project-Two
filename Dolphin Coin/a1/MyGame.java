package a1;

import myGameEngine.MyGameEngine;
import myGameEngine.MyGameEngineTwo;
import ray.rage.game.Game;

public class MyGame extends MyGameEngineTwo {
    public MyGame() {
        super();
		System.out.println("press T to render triangles");
		System.out.println("press L to render lines");
		System.out.println("press P to render points");
		System.out.println("press C to increment counter");
    }

    public static void main(String[] args) {
        Game game = new MyGame();
        try {
            game.startup();
            game.run();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
        	game.setState(Game.State.STOPPING);
            game.exit();
        }
    }
}