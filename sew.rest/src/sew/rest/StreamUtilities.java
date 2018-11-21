/*
 * Sébastien Eon 2013 / CC0-1.0
 */
package sew.rest;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtilities {

	public static byte[] readFully(InputStream input) throws IOException {
		DataInputStream dataInputStream = new DataInputStream(input);
		int length = 0;
		byte[] responseBytes = new byte[0];
		int b;
		while (true) {
			try {
				b = dataInputStream.read();
			} catch (IOException e) {
				e.printStackTrace();
				// FIXME end of stream?!
				break;
			}
			if (b == -1) {
				// end of stream
				break;
			}
			int available = input.available();
			int currentOffset = length;
			length += available + 1;
			System.arraycopy(responseBytes, 0, responseBytes = new byte[length], 0, currentOffset);
			responseBytes[currentOffset++] = (byte) b;
			dataInputStream.readFully(responseBytes, currentOffset, available);
			// while (available > 0) {
			// int nbRead = input.read(responseBytes, currentOffset, available);
			// if (nbRead == -1) {
			// break;
			// }
			// available -= nbRead;
			// }
		}

		input.close();
		return responseBytes;
	}

}
