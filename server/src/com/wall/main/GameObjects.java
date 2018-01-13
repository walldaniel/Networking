package com.wall.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;

/*
 * Name: Daniel Wall
 * Date: 2018-01-10
 * Purpose: Used to store all the objects needed for a game
 */
public class GameObjects {

	private Map<Integer, Player> players;
	private ArrayList<Asteroid> enemies;
	private ArrayList<Laser> lasers;
	
	/*
	 * Creates a new game object
	 * Initializes all the data objects empty
	 */
	public GameObjects() {
		players = new HashMap<Integer, Player>();
		enemies = new ArrayList<Asteroid>();
		lasers = new ArrayList<Laser>();
	}
	
	public void addPlayer(Player p, Integer num) throws Exception {
		if(players.containsKey(num))
			throw new Exception();
		
		players.put(num, p);
	}
	public void addEnemy(Asteroid e) {
		enemies.add(e);
	}
	public void addLaser(Laser l) {
		lasers.add(l);
	}
	
	public void removePlayer(Integer i) {
		players.remove(i);
	}
	public void removeEnemy(int i) {
		enemies.remove(i);
	}
	public void removeLaser(int i) {
		lasers.remove(i);
	}
}
