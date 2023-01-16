package com.github.yeetmanlord.reflection_api;

import com.github.yeetmanlord.reflection_api.exceptions.VersionFormatException;

public class Version implements Comparable<Version> {

	/**
	 * The minimum version supported by this API. I cannot guarantee that this API will work on older versions.
	 * This is the bare minimum for functionality, but I also cannot guarantee that this API will properly function
	 * from 1.8 - 1.8.8. I have not tested this API on those versions, and I don't intend to support it. If possible
	 * use 1.8.8 or higher.
	 */
	public static final Version UNSUPPORTED_MIN = new Version(8, 0);

	/**
	 * The start of the core API support. Below this version support is not actively maintained
	 * but may still work. Above this version support is actively maintained.
	 */
	public static final Version SUPPORTED_MIN = new Version(8, 8);

	/**
	 * The maximum version supported by this API
	 */
	public static final Version MAX = new Version(20, 0);

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
	 * @return Returns true if the current server version is older than the inputted
	 *         version. This does not return true if the version is the same.
	 */
	public boolean isOlder(Version version) {

		int compare = compareTo(version);
		return compare == -1;

	}

	/**
	 * @param version String of version
	 * @return Returns true if the current server version is older than the inputted
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
	 * @return Returns true if the current server version is newer than the inputted
	 *         version.
	 * @apiNote This will also return true if the versions are the same.
	 * @implNote If you are checking for version 1.16 for things like netherite. The
	 *           way to check for 1.16 would be
	 *           version.isNewer({@link Version#Version(int, int) new Version(16, 0)}).
	 */
	public boolean isNewer(Version version) {

		int compare = compareTo(version);
		return compare == 1 || compare == 0;

	}

	/**
	 * @param version String of version
	 * @return Returns true if the current server version is newer than the inputted
	 *         version.
	 * @apiNote This will also return true if the versions are the same.
	 * @implNote If you are checking for version 1.16 for things like netherite. The
	 *           way to check for 1.16 would be version.isNewer("1.16").
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
