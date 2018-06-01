package com.economizate.servicios.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import com.economizate.servicios.DataSource;

public class CompressionDecorator extends DataSourceDecorator {

	private int compLevel = 6;

	// private static final Logger LOG =
	// Logger.getLogger(CompressionDecorator.class);

	public CompressionDecorator(DataSource source) {
		super(source);
	}

	public int getCompressionLevel() {
		return compLevel;
	}

	public void setCompressionLevel(int value) {
		compLevel = value;
	}

	@Override
	public void writeData(String data) {
		super.writeData(compress(data));
	}

	@Override
	public String readData() {
		return decompress(super.readData());
	}

	public String compress(String stringData) {
		byte[] data = stringData.getBytes();
		try {

			Deflater deflater = new Deflater();
			deflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
					data.length);
			deflater.finish();
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer); // returns the generated
														// code... index
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
			byte[] output = outputStream.toByteArray();

			return Base64.getEncoder().encodeToString(output);

		} catch (IOException ex) {
			return null;
		}
	}

	public String decompress(String stringData) {
		byte[] data = Base64.getDecoder().decode(stringData);
		try {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
					data.length);
			byte[] buffer = new byte[1024];
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
			byte[] output = outputStream.toByteArray();

			return new String(output);
		} catch (IOException ex) {
			return null;
		} catch (DataFormatException e) {
			return null;
		}
	}
}
