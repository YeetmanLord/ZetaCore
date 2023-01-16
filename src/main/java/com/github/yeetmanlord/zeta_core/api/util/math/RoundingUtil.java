package com.github.yeetmanlord.zeta_core.api.util.math;

import org.bukkit.Location;

/**
 * 
 * Utility class for dealing with rounding values to specified place values
 * rather than nearest whole number. As well as centering locations.
 * 
 * @author YeetManLord
 *
 */
public class RoundingUtil {

	/**
	 * 
	 * @param value Input value to round
	 * @param place The decimal place to round to. A value greater than zero will
	 *              round after the decimal so {@link #roundNearestPlace(float, int)
	 *              roundNearestPlace(10.06F, 1)} will round to 10.1F. While a place
	 *              in the negatives will round before the decimal
	 *              {@link #roundNearestPlace(float, int) roundNearestPlace(12.5F,
	 *              -1)} will round to 10F.
	 * @return Returns rounded value to the specified nearest place
	 */
	public static float roundNearestPlace(float value, int place) {

		if (place > 0) {
			float newValue = value;
			int divisor = (int) Math.pow(10, place);

			newValue = Math.round(newValue * divisor);
			newValue /= divisor;

			return newValue;
		}
		else if (place < 0) {
			float newValue = value;
			int multiplier = (int) Math.pow(10, -place);

			newValue = Math.round(newValue / multiplier);
			newValue *= multiplier;

			return newValue;
		}
		else return Math.round(value);

	}

	/**
	 * 
	 * @param value Input value to round
	 * @param place The decimal place to round to. A value greater than zero will
	 *              round after the decimal so {@link #roundNearestPlace(double, int)
	 *              roundNearestPlace(10.06D, 1)} will round to 10.1D. While a place
	 *              in the negatives will round before the decimal
	 *              {@link #roundNearestPlace(double, int) roundNearestPlace(12.5D,
	 *              -1)} will round to 10D.
	 * @return Returns rounded value to the specified nearest place
	 */
	public static double roundNearestPlace(double value, int place) {

		if (place > 0) {
			double newValue = value;
			int divisor = (int) Math.pow(10, place);

			newValue = Math.round(newValue * divisor);
			newValue /= divisor;

			return newValue;
		}
		else if (place < 0) {
			double newValue = value;
			int multiplier = (int) Math.pow(10, -place);

			newValue = Math.round(newValue / multiplier);
			newValue *= multiplier;

			return newValue;
		}
		else return Math.round(value);

	}

	/**
	 * Centers a location on the block it is in. Depending on location it will either be centered to the center or one of the corners.
	 * @param loc The location to center.
	 * @return The centered location.
	 */
	public static Location center(Location loc) {

		double x = loc.getX();
		double z = loc.getZ();

		double xDeci = x - Math.floor(x);

		double zDeci = z - Math.floor(z);

		if (xDeci >= 0.75D) {
			xDeci = 1D;
		}
		else if (xDeci >= 0.25D) {
			xDeci = 0.5D;
		}
		else {
			xDeci = 0D;
		}

		x = Math.floor(x) + xDeci;

		if (zDeci >= 0.75D) {
			zDeci = 1D;
		}
		else if (zDeci >= 0.25D) {
			zDeci = 0.5D;
		}
		else {
			zDeci = 0D;
		}

		z = Math.floor(z) + zDeci;

		loc.setX(x);
		loc.setZ(z);
		loc.setY(roundNearestPlace(loc.getY(), 1));

		return loc;

	}

}
