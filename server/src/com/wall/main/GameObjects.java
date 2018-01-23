package com.wall.main;

import java.util.ArrayList;

import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;

public class GameObjects {

	private ArrayList<Player> players;
	private ArrayList<Laser> lasers;
	private ArrayList<Asteroid> asteroids;

	public GameObjects() {
		// Create the list of objects
		players = new ArrayList<Player>();
		lasers = new ArrayList<Laser>();
		asteroids = new ArrayList<Asteroid>();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	public ArrayList<Laser> getLasers() {
		return lasers;
	}
	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	public void addLaser(Laser laser) {
		lasers.add(laser);
	}
	public void addAsteroid(Asteroid asteroid) {
		asteroids.add(asteroid);
	}
	
	public ArrayList<Object> getEverything() {
		ArrayList<Object> objects = new ArrayList<>();
		
		objects.addAll(players);
		objects.addAll(lasers);
		objects.addAll(asteroids);
		
		return objects;
	}
}
