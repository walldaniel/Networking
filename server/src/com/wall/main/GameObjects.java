package com.wall.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.wall.game.Enemy;
import com.wall.game.Laser;
import com.wall.game.Player;

public class GameObjects {

	private Map<Integer, Player> players;
	private ArrayList<Enemy> enemies;
	private ArrayList<Laser> lasers;
	
	public GameObjects() {
		players = new HashMap<Integer, Player>();
		enemies = new ArrayList<Enemy>();
		lasers = new ArrayList<Laser>();
	}
	
	public void addPlayer(Player p, Integer num) {
		players.put(num, p);
	}
	public void addEnemy(Enemy e) {
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
