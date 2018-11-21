/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Request {

	private final StringBuffer buffer;
	private boolean addAnd;

	public Request(StringBuffer buffer) {
		this.buffer = buffer;
		// addAnd = false; VM DONE
	}

	public Request(String service) {
		this(new StringBuffer(service).append('?'));
	}

	public Request() {
		this(new StringBuffer());
	}

	private StringBuffer append(String key) {
		if (this.addAnd) {
			this.buffer.append('&');
		} else {
			this.addAnd = true;
		}
		return this.buffer.append(key).append('=')/* .append('"') */;
	}

	public Request append(String key, String value) {
		append(key).append(value.replace(' ', '+'))/* .append('"') */;
		return this;
	}

	public Request append(String key, int value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	public Request append(String key, float value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	public Request append(String key, double value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	public Request append(String key, boolean value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	public byte[] send() throws MalformedURLException, IOException, ProtocolException {
		URL url = new URL(this.buffer.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		// if (DEBUG) {
		byte[] result = StreamUtilities.readFully(inputStream);
		return result;
	}

	@Override
	public String toString() {
		return this.buffer.toString();
	}

}
