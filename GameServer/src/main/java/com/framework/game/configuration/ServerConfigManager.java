package com.framework.game.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerConfigManager {
	@Value("${profile}") private String profile;
	
	public boolean isLocal() {
		return profile.equalsIgnoreCase(ServerConfig.LOCAL.getProfile());
	}
	
	public boolean isDev() {
		return profile.equalsIgnoreCase(ServerConfig.DEV.getProfile());
	}

	public boolean isAlpha() {
		return profile.equalsIgnoreCase(ServerConfig.ALPHA.getProfile());
	}

	public boolean isBeta() {
		return profile.equalsIgnoreCase(ServerConfig.BETA.getProfile());
	}

	public boolean isReal() {
		return profile.equalsIgnoreCase(ServerConfig.REAL.getProfile());
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
}

enum ServerConfig {
	LOCAL("local"),
	DEV("dev"),
	ALPHA("alpha"),
	BETA("beta"),
	REAL("real"),
	;
	
	private String profile;
	
	private ServerConfig(String profile) {
		this.profile = profile;
	}

	public String getProfile() {
		return profile;
	}
}
