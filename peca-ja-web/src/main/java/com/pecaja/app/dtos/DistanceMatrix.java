package com.pecaja.app.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrix {

	@SerializedName("destination_addresses")
	@Expose
	@SuppressWarnings("serial")
	private List<String> destinationAddresses = null;
	@SerializedName("origin_addresses")
	@Expose
	@SuppressWarnings("serial")
	private List<String> originAddresses = null;
	@SerializedName("rows")
	@Expose
	private List<Row> rows = null;
	@SerializedName("status")
	@Expose
	private String status;

	public List<String> getDestinationAddresses() {
		return destinationAddresses;
	}

	public void setDestinationAddresses(List<String> destinationAddresses) {
		this.destinationAddresses = destinationAddresses;
	}

	public List<String> getOriginAddresses() {
		return originAddresses;
	}

	public void setOriginAddresses(List<String> originAddresses) {
		this.originAddresses = originAddresses;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}