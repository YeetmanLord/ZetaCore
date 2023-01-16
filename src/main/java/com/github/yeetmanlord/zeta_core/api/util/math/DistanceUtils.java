package com.github.yeetmanlord.zeta_core.api.util.math;

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

	/**
	 * Checks if a given location is within a given radius of another location.
	 * @param dist The distance to check.
	 * @param vec1 The first and starting location.
	 * @param vec2 The second and ending location.
	 * @return True if the distance is within the radius, false otherwise.
	 */
	public static boolean withinDistance(double dist, Location vec1, Location vec2) {

		double distance = getDistanceSquared(vec1, vec2);
		return distance < dist * dist;

	}

	/**
	 * Gets the distance between two entities (squared, saves performance)
	 *
	 * @param vec1 The first location
	 * @param vec2 The second location
	 * @return The distance between the two locations squared
	 */
	public static double getDistanceSquared(Location vec1, Location vec2) {
		double[] deltas = getDelta(vec1, vec2);
		double deltaX = deltas[0];
		double deltaY = deltas[1];
		double deltaZ = deltas[2];

		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
	}

	/**
	 * Gets the distance between two locations.
	 * @param vec1 The first location.
	 * @param vec2 The second location.
	 * @return
	 */
	public static double getDistance(Location vec1, Location vec2) {

		double[] deltas = getDelta(vec1, vec2);
		double deltaX = deltas[0];
		double deltaY = deltas[1];
		double deltaZ = deltas[2];

		return pythagoream3D(deltaX, deltaY, deltaZ);

	}

	/**
	 * Pythagorean theorem for 2D
	 * @param a Length of side a
	 * @param b Length of side b
	 * @return The length of the hypotenuse
	 */
	public static double pythagoream(double a, double b) {

		a = a * a;
		b = b * b;

		double c = a + b;

		c = Math.sqrt(c);

		return c;

	}

	/**
	 * Pythagorean theorem for 3D
	 * @param x X length
	 * @param y Y length
	 * @param z Z length
	 * @return The distance
	 */
	public static double pythagoream3D(double x, double y, double z) {
		double a = pythagoream(x, y);
		double c = Math.sqrt(a*a + z*z);

		return Math.sqrt(c);
	}

	/**
	 * Gets the delta (change in x, y, and z) between two locations.
	 * @param vec1 The first location.
	 * @param vec2 The second location.
	 * @return An array of doubles, where the first element is the change in x, the second is the change in y, and the third is the change in z.
	 */
	public static double[] getDelta(Location vec1, Location vec2) {

		double aX = vec1.getX();
		double aY = vec1.getY();
		double aZ = vec1.getZ();

		double bX = vec2.getX();
		double bY = vec2.getY();
		double bZ = vec2.getZ();

		return new double[] { Math.abs(aX - bX), Math.abs(aY - bY), Math.abs(aZ - bZ) };

	}

	/**
	 * Gets a random location within a radius of a location.
	 * @param entity The entity to get the location of.
	 * @param planarBound The radius of the cylinder.
	 * @param verticalBound The height of the cylinder.
	 * @param location The location to get the random location from. If null, the entity's location will be used.
	 * @return A random location within the radius of the entity.
	 */
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
