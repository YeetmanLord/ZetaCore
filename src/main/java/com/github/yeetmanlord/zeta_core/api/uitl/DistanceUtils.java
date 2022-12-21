package com.github.yeetmanlord.zeta_core.api.uitl;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Utility class for some distance math.
 * 
 * @author YeetManLord
 *
 */
public class DistanceUtils {

	public static boolean withinDistance(double dist, Location vec1, Location vec2) {

		double distance = getDistance(vec1, vec2);
		return distance < dist;

	}

	public static double getDistance(Location vec1, Location vec2) {

		double[] deltas = getDelta(vec1, vec2);
		double deltaX = deltas[0];
		double deltaY = deltas[1];
		double deltaZ = deltas[2];

		double a = pythagoream(deltaY, deltaZ);
		return pythagoream(deltaX, a);

	}

	public static double pythagoream(double a, double b) {

		a = a * a;
		b = b * b;

		double c = a + b;

		c = Math.sqrt(c);

		return c;

	}

	public static double pythagoream3D(double x, double y, double z) {
		double a = pythagoream(x, y);
		double c = Math.sqrt(a*a + z*z);

		return Math.sqrt(c);
	}

	public static double[] getDelta(Location vec1, Location vec2) {

		double aX = vec1.getX();
		double aY = vec1.getY();
		double aZ = vec1.getZ();

		double bX = vec2.getX();
		double bY = vec2.getY();
		double bZ = vec2.getZ();

		return new double[] { Math.abs(aX - bX), Math.abs(aY - bY), Math.abs(aZ - bZ) };

	}

	public static Location randomLocation(Entity entity, int planarBound, int verticalBound, Location location) {

		Random rand = new Random();
		int finalX = 0;
		int finalY = 0;
		int finalZ = 0;

		for (int i = 0; i < 10; ++i) {
			int x = rand.nextInt(2 * planarBound + 1) - planarBound;
			int y = rand.nextInt(2 * verticalBound + 1) - verticalBound;
			int z = rand.nextInt(2 * planarBound + 1) - planarBound;

			if (location == null || x * location.getX() + z * location.getZ() >= 0.0D) {
				Location newLocation;
				x += Math.floor(entity.getLocation().getX());
				y += Math.floor(entity.getLocation().getY());
				z += Math.floor(entity.getLocation().getZ());
				newLocation = new Location(entity.getWorld(), x, y, z);

				if (planarBound > 1) {

					if (entity.getLocation().getX() > newLocation.getX()) {
						x -= rand.nextInt(planarBound / 2);
					}
					else {
						x += rand.nextInt(planarBound / 2);
					}

					if (entity.getLocation().getZ() > newLocation.getZ()) {
						z -= rand.nextInt(planarBound / 2);
					}
					else {
						z += rand.nextInt(planarBound / 2);
					}

				}

				finalX = x;
				finalY = y;
				finalZ = z;

			}
			else if (location != null) {
				x += Math.floor(location.getX());
				y += Math.floor(location.getY());
				z += Math.floor(location.getZ());

				finalX = x;
				finalY = y;
				finalZ = z;
			}

		}

		return new Location(entity.getWorld(), finalX, finalY, finalZ);

	}

}
