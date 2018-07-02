package org.apache.flink.streaming.connectors.fs.bucketing_new.writers;

import org.apache.flink.core.fs.FSDataOutputStream;

import java.io.IOException;
import java.io.Serializable;

public interface Writer<IN> extends Serializable {

	/**
	 * Writes one element to the bucket file.
	 */
	void write(IN element, FSDataOutputStream stream)throws IOException;

}
