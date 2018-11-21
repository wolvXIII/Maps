/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.maps.openstreetmapslite;

public class RequestBuffer {

	private final StringBuffer buffer;
	private boolean addAnd;

	public RequestBuffer(StringBuffer buffer) {
		this.buffer = buffer;
		// addAnd = false; VM DONE
	}

	public RequestBuffer(String service) {
		this(new StringBuffer(service).append('?'));
	}

	public RequestBuffer() {
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

	public RequestBuffer append(String key, String value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	public RequestBuffer append(String key, int value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	public RequestBuffer append(String key, float value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	public RequestBuffer append(String key, double value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	public RequestBuffer append(String key, boolean value) {
		append(key).append(value)/* .append('"') */;
		return this;
	}

	@Override
	public String toString() {
		return this.buffer.toString();
	}

}
