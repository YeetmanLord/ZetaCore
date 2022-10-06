package com.github.yeetmanlord.reflection_api.exceptions;

public class VersionFormatException extends Exception {

	private static final long serialVersionUID = 5394422750058896194L;

	public VersionFormatException() {

		super();

	}

	public VersionFormatException(String versionString) {

		super(versionString);

	}

	public VersionFormatException(String versionString, String msg) {

		super(msg + ": " + versionString);

	}

}