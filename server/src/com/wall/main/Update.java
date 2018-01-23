package com.wall.main;

import java.util.ArrayList;

import com.wall.game.objects.Asteroid;
import com.wall.game.objects.Laser;
import com.wall.game.objects.Player;
import com.wall.game.objects.UpdatePosition;

public class Update {

	// Update the position of all the objects
	public static void update(float delta, GameObjects objects) {
		for(Player p : objects.getPlayers()) {
			p.update(delta);
		}
		for(Asteroid a : objects.getAsteroids()) {
			a.update(delta);
		}
		for(Laser l : objects.getLasers()) {
			l.update(delta);
		}
	}
	
	// Get a list of all the objects to send to the clients
	public static ArrayList<UpdatePosition> updateObjects(GameObjects objects) {
		ArrayList<UpdatePosition> positions = new ArrayList<>();

		for (int i = objects.getPlayers().size() - 1; i >= 0; i--) {
			positions.add(new UpdatePosition(objects.getPlayers().get(i).getX(), objects.getPlayers().get(i).getY(),
					objects.getPlayers().get(i).getDirectionInDegrees(), UpdatePosition.PLAYER, i));
		}
		for (int i = objects.getAsteroids().size() - 1; i >= 0; i--) {
			positions.add(new UpdatePosition(objects.getAsteroids().get(i).getX(), objects.getAsteroids().get(i).getY(),
					objects.getAsteroids().get(i).getDirectionInDegrees(), UpdatePosition.ASTEROID, i));
		}
		for (int i = objects.getLasers().size() - 1; i >= 0; i--) {
			positions.add(new UpdatePosition(objects.getLasers().get(i).getX(), objects.getLasers().get(i).getY(),
					objects.getLasers().get(i).getDirectionInDegrees(), UpdatePosition.LASER, i));
		}

		return positions;
	}

}
