package com.garamsoft.bubble.weather.network.week.week;

import com.garamsoft.bubble.weather.network.NetworkRequest;

public class WeekWetherRequest extends NetworkRequest {

	public WeekWetherRequest(String url) {
		this.parser = new WeekWetherParser();
		this.urlString = url;
	}
}
