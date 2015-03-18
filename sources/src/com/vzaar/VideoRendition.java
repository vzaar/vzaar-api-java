package com.vzaar;

import org.codehaus.jackson.annotate.JsonProperty;

public class VideoRendition {

	@JsonProperty("status_id")
	public int statusId;
	@JsonProperty("status")
	public String status;
	@JsonProperty("type")
	public String type;

	@Override
	public String toString() {
		return "VideoRendition{" +
				"statusId=" + statusId  +
				", status='" + status + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
