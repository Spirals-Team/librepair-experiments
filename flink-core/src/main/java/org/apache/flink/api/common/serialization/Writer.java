package org.apache.flink.api.common.serialization;

import org.apache.flink.core.fs.FSDataOutputStream;

import java.io.IOException;
import java.io.Serializable;

/**
 * Javadoc.
 */
public interface Writer<IN> extends Serializable {

	/**
	 * Writes one element to the bucket file.
	 */
	void write(IN element, FSDataOutputStream stream)throws IOException;

}
