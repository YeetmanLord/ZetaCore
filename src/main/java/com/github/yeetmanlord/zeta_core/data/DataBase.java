package com.github.yeetmanlord.zeta_core.data;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import com.github.yeetmanlord.zeta_core.sql.connection.SQLClient;

public class DataBase extends DataStorer {

	public SQLClient client;

	public String ipAddress;

	public String username;

	public String password;

	public boolean initialized;

	public int port;

	public String databaseName;

	public DataBase(ZetaCore instanceIn) {

		super(instanceIn, "database");

	}

	@Override
	public void write() {

		config.set("ip", ipAddress);
		config.set("initialized", initialized);
		config.set("username", username);
		config.set("password", password);
		config.set("port", port);
		config.set("databaseName", databaseName);

		this.save();

	}

	@Override
	public void read() {

		this.initialized = config.getBoolean("initialized");
		this.username = config.getString("username");
		this.ipAddress = config.getString("ip");
		this.password = config.getString("password");
		this.port = config.getInt("port");
		this.databaseName = config.getString("databaseName");

		if (initialized) {
			this.client = new SQLClient(ipAddress, username, password, port, databaseName);
		}

	}

	@Override
	public void setDefaults() {

		if (!this.config.contains("ip")) {
			config.set("ip", "localhost");
		}

		if (!this.config.contains("initialized")) {
			config.set("initialized", false);
		}

		if (!this.config.contains("username")) {
			config.set("username", "root");
		}

		if (!this.config.contains("password")) {
			config.set("password", "");
		}

		if (!this.config.contains("port")) {
			config.set("port", 3306);
		}

		if (!this.config.contains("databaseName")) {
			config.set("databaseName", "bedwars_zeta_data");
		}

	}

}
