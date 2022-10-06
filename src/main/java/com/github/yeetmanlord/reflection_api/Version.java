package com.github.yeetmanlord.reflection_api;

import com.github.yeetmanlord.reflection_api.exceptions.VersionFormatException;

public class Version implements Comparable<Version> {

	private String version;

	private int majorVersion, minorVersion;

	public Version(String version) throws VersionFormatException {

		if (!(version.matches("\\d.\\d\\d") || version.matches("\\d.\\d\\d.\\d") || version.matches("\\d.\\d\\d.\\d\\d") || version.matches("\\d.\\d") || version.matches("\\d.\\d.\\d") || version.matches("\\d.\\d.\\d\\d"))) {
			throw new VersionFormatException("Invalid version format", version);
		}

		this.version = version;
		String[] split = version.replace(".", "_").split("_");
		this.majorVersion = Integer.valueOf(split[1]);
		this.minorVersion = 0;

		if (split.length == 3) {
			this.minorVersion = Integer.valueOf(split[2]);
		}

	}

	public Version(int major, int minor) {

		if (major < 0) {
			major = 0;
		}

		this.version = "1." + major;

		if (minor > 0) {
			this.version = version + "." + minor;
		}

		this.majorVersion = major;
		this.minorVersion = minor;

	}

	/**
	 * @param object Can be a {@link String} or {@link Version}
	 */
	@Override
	public boolean equals(Object object) {

		if (object instanceof Version) {
			return ((Version) object).getVersion().equals(this.version);
		}
		else return super.equals(object);

	}

	@Override
	public String toString() {

		return version;

	}

	/**
	 * @param version String of version
	 * @return Returns true if the current server version is older than the inputed
	 *         version. This does not return true if the version is the same.
	 */
	public boolean isOlder(Version version) {

		int compare = compareTo(version);
		return compare == -1;

	}

	/**
	 * @param version String of version
	 * @return Returns true if the current server version is older than the inputed
	 *         version. This does not return true if the version is the same.
	 */
	public boolean isOlder(String version) {

		try {
			return isOlder(new Version(version));
		}
		catch (VersionFormatException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * @param version String of version
	 * @return Returns true if the current server version is newer than the inputed
	 *         version.
	 * @apiNote This will also return true if the versions are the same.
	 * @implNote If you are checking for version 1.16 for things like netherite. The
	 *           way to check for 1.16 would be {@link Version#isNewer("1.16")}.
	 */
	public boolean isNewer(Version version) {

		int compare = compareTo(version);
		return compare == 1 || compare == 0;

	}

	/**
	 * @param version String of version
	 * @return Returns true if the current server version is newer than the inputed
	 *         version.
	 * @apiNote This will also return true if the versions are the same.
	 * @implNote If you are checking for version 1.16 for things like netherite. The
	 *           way to check for 1.16 would be {@link Version#isNewer("1.16")}.
	 */
	public boolean isNewer(String version) {

		try {
			return isNewer(new Version(version));
		}
		catch (VersionFormatException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public int compareTo(Version version) {

		return compare(this.majorVersion, version.majorVersion, this.minorVersion, version.minorVersion);

	}

	/**
	 * @return A return value of -1 means the inputed version is older than the
	 *         current server version. A return value of 0 means the versions are
	 *         the same. A return value of 1 means the current server version is
	 *         older than the inputed version
	 */
	public int compare(int major1, int major2, int minor1, int minor2) {

		if (major1 < major2 || (major1 == major2 && minor1 < minor2)) {
			return -1;
		}
		else if (major1 == major2 && minor1 == minor2) {
			return 0;
		}

		return 1;

	}

	public String getVersion() {

		return version;

	}

	public int getMajorVersion() {

		return majorVersion;

	}

	public int getMinorVersion() {

		return minorVersion;

	}

	public boolean equals(String version) {

		try {
			return this.equals(new Version(version));
		}
		catch (VersionFormatException e) {
			e.printStackTrace();
		}

		return false;

	}

}
