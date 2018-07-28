package cn.edu.tsinghua.tsfile.file.metadata.statistics;

import cn.edu.tsinghua.tsfile.common.utils.ByteBufferUtil;
import cn.edu.tsinghua.tsfile.common.utils.BytesUtils;
import cn.edu.tsinghua.tsfile.common.utils.ReadWriteIOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Statistics for float type
 *
 * @author kangrong
 */
public class FloatStatistics extends Statistics<Float> {
	private float max;
	private float min;
	private float first;
	private double sum;
	private float last;

	@Override
	public void setMinMaxFromBytes(byte[] minBytes, byte[] maxBytes) {
		max = BytesUtils.bytesToFloat(maxBytes);
		min = BytesUtils.bytesToFloat(minBytes);
	}

	@Override
	public void updateStats(float value) {
		if (this.isEmpty) {
			initializeStats(value, value, value, value,value);
			isEmpty = false;
		} else {
			updateStats(value, value, value, value,value);
		}
	}

	private void updateStats(float minValue, float maxValue, float firstValue, double sumValue,float last) {
		if (minValue < min) {
			min = minValue;
		}
		if (maxValue > max) {
			max = maxValue;
		}
		sum += sumValue;
		this.last = last;
	}

	@Override
	public Float getMax() {
		return max;
	}

	@Override
	public Float getMin() {
		return min;
	}

	@Override
	public Float getFirst() {
		return first;
	}

	@Override
	public double getSum() {
		return sum;
	}
	
	@Override
	public Float getLast(){
		return last;
	}

	@Override
	protected void mergeStatisticsValue(Statistics<?> stats) {
		FloatStatistics floatStats = (FloatStatistics) stats;
		if (isEmpty) {
			initializeStats(floatStats.getMin(), floatStats.getMax(), floatStats.getFirst(), floatStats.getSum(),floatStats.getLast());
			isEmpty = false;
		} else {
			updateStats(floatStats.getMin(), floatStats.getMax(), floatStats.getFirst(), floatStats.getSum(),floatStats.getLast());
		}

	}

	public void initializeStats(float min, float max, float first, double sum,float last) {
		this.min = min;
		this.max = max;
		this.first = first;
		this.sum = sum;
		this.last = last;
	}

	@Override
	public byte[] getMaxBytes() {
		return BytesUtils.floatToBytes(max);
	}

	@Override
	public byte[] getMinBytes() {
		return BytesUtils.floatToBytes(min);
	}

	@Override
	public byte[] getFirstBytes() {
		return BytesUtils.floatToBytes(first);
	}

	@Override
	public byte[] getSumBytes() {
		return BytesUtils.doubleToBytes(sum);
	}
	
	@Override
	public byte[] getLastBytes(){
		return BytesUtils.floatToBytes(last);
	}

	@Override
	public ByteBuffer getMaxBytebuffer() {
		return ByteBufferUtil.bytes(max);
	}

	@Override
	public ByteBuffer getMinBytebuffer() { return ByteBufferUtil.bytes(min); }

	@Override
	public ByteBuffer getFirstBytebuffer() {
		return ByteBufferUtil.bytes(first);
	}

	@Override
	public ByteBuffer getSumBytebuffer() {
		return ByteBufferUtil.bytes(sum);
	}

	@Override
	public ByteBuffer getLastBytebuffer() {
		return ByteBufferUtil.bytes(last);
	}


	@Override
	public int sizeOfDatum() {
		return 4;
	}

	@Override
	public String toString() {
		return "[max:" + max + ",min:" + min + ",first:" + first + ",sum:" + sum + ",last:" + last + "]";
	}

	@Override
	void fill(InputStream inputStream) throws IOException {
		this.min = ReadWriteIOUtils.readFloat(inputStream);
		this.max = ReadWriteIOUtils.readFloat(inputStream);
		this.first = ReadWriteIOUtils.readFloat(inputStream);
		this.last = ReadWriteIOUtils.readFloat(inputStream);
		this.sum = ReadWriteIOUtils.readDouble(inputStream);
	}
}
