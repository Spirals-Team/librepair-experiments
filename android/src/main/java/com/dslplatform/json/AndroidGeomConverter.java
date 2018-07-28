package com.dslplatform.json;

import android.graphics.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AndroidGeomConverter {

	public static final JsonReader.ReadObject<PointF> LOCATION_READER = new JsonReader.ReadObject<PointF>() {
		@Nullable
		@Override
		public PointF read(JsonReader reader) throws IOException {
			return reader.wasNull() ? null : deserializeLocation(reader);
		}
	};
	public static final JsonWriter.WriteObject<PointF> LOCATION_WRITER = new JsonWriter.WriteObject<PointF>() {
		@Override
		public void write(JsonWriter writer, @Nullable PointF value) {
			serializeLocationNullable(value, writer);
		}
	};
	public static final JsonReader.ReadObject<Point> POINT_READER = new JsonReader.ReadObject<Point>() {
		@Nullable
		@Override
		public Point read(JsonReader reader) throws IOException {
			return reader.wasNull() ? null : deserializePoint(reader);
		}
	};
	public static final JsonWriter.WriteObject<Point> POINT_WRITER = new JsonWriter.WriteObject<Point>() {
		@Override
		public void write(JsonWriter writer, @Nullable Point value) {
			serializePointNullable(value, writer);
		}
	};
	public static final JsonReader.ReadObject<Rect> RECTANGLE_READER = new JsonReader.ReadObject<Rect>() {
		@Nullable
		@Override
		public Rect read(JsonReader reader) throws IOException {
			return reader.wasNull() ? null : deserializeRectangle(reader);
		}
	};
	public static final JsonWriter.WriteObject<Rect> RECTANGLE_WRITER = new JsonWriter.WriteObject<Rect>() {
		@Override
		public void write(JsonWriter writer, @Nullable Rect value) {
			serializeRectangleNullable(value, writer);
		}
	};
	public static final JsonReader.ReadObject<Bitmap> IMAGE_READER = new JsonReader.ReadObject<Bitmap>() {
		@Nullable
		@Override
		public Bitmap read(JsonReader reader) throws IOException {
			return reader.wasNull() ? null : deserializeImage(reader);
		}
	};
	public static final JsonWriter.WriteObject<Bitmap> IMAGE_WRITER = new JsonWriter.WriteObject<Bitmap>() {
		@Override
		public void write(JsonWriter writer, @Nullable Bitmap value) {
			serialize(value, writer);
		}
	};

	public static void serializeLocationNullable(@Nullable final PointF value, final JsonWriter sw) {
		if (value == null) {
			sw.writeNull();
		} else {
			serializeLocation(value, sw);
		}
	}

	public static void serializeLocation(final PointF value, final JsonWriter sw) {
		sw.writeAscii("{\"X\":");
		NumberConverter.serialize(value.x, sw);
		sw.writeAscii(",\"Y\":");
		NumberConverter.serialize(value.y, sw);
		sw.writeByte(JsonWriter.OBJECT_END);
	}

	public static PointF deserializeLocation(final JsonReader reader) throws IOException {
		if (reader.last() != '{') throw new IOException("Expecting '{' " + reader.positionDescription() + ". Found " + (char)reader.last());
		byte nextToken = reader.getNextToken();
		if (nextToken == '}') return new PointF();
		float x = 0;
		float y = 0;
		String name = StringConverter.deserialize(reader);
		nextToken = reader.getNextToken();
		if (nextToken != ':') throw new IOException("Expecting ':' " + reader.positionDescription() + ". Found " + (char)nextToken);
		reader.getNextToken();
		float value = NumberConverter.deserializeFloat(reader);
		if ("X".equalsIgnoreCase(name)) {
			x = value;
		} else if ("Y".equalsIgnoreCase(name)) {
			y = value;
		}
		while ((nextToken = reader.getNextToken()) == ',') {
			reader.getNextToken();
			name = StringConverter.deserialize(reader);
			nextToken = reader.getNextToken();
			if (nextToken != ':') throw new IOException("Expecting ':' " + reader.positionDescription() + ". Found " + (char)nextToken);
			reader.getNextToken();
			value = NumberConverter.deserializeFloat(reader);
			if ("X".equalsIgnoreCase(name)) {
				x = value;
			} else if ("Y".equalsIgnoreCase(name)) {
				y = value;
			}
		}
		if (nextToken != '}') throw new IOException("Expecting '}' " + reader.positionDescription() + ". Found " + (char)nextToken);
		return new PointF(x, y);
	}

	public static ArrayList<PointF> deserializeLocationCollection(final JsonReader reader) throws IOException {
		return reader.deserializeCollection(LOCATION_READER);
	}

	public static void deserializeLocationCollection(final JsonReader reader, final Collection<PointF> res) throws IOException {
		reader.deserializeCollection(LOCATION_READER, res);
	}

	public static ArrayList<PointF> deserializeLocationNullableCollection(final JsonReader reader) throws IOException {
		return reader.deserializeNullableCollection(LOCATION_READER);
	}

	public static void deserializeLocationNullableCollection(final JsonReader reader, final Collection<PointF> res) throws IOException {
		reader.deserializeNullableCollection(LOCATION_READER, res);
	}

	public static void serializePointNullable(@Nullable final Point value, final JsonWriter sw) {
		if (value == null) {
			sw.writeNull();
		} else {
			serializePoint(value, sw);
		}
	}

	public static void serializePoint(final Point value, final JsonWriter sw) {
		sw.writeAscii("{\"X\":");
		NumberConverter.serialize(value.x, sw);
		sw.writeAscii(",\"Y\":");
		NumberConverter.serialize(value.y, sw);
		sw.writeByte(JsonWriter.OBJECT_END);
	}

	public static Point deserializePoint(final JsonReader reader) throws IOException {
		if (reader.last() != '{') throw new IOException("Expecting '{' " + reader.positionDescription() + ". Found " + (char)reader.last());
		byte nextToken = reader.getNextToken();
		if (nextToken == '}') return new Point();
		int x = 0;
		int y = 0;
		String name = StringConverter.deserialize(reader);
		nextToken = reader.getNextToken();
		if (nextToken != ':') throw new IOException("Expecting ':' " + reader.positionDescription() + ". Found " + (char)nextToken);
		reader.getNextToken();
		int value = NumberConverter.deserializeInt(reader);
		if ("X".equalsIgnoreCase(name)) {
			x = value;
		} else if ("Y".equalsIgnoreCase(name)) {
			y = value;
		}
		while ((nextToken = reader.getNextToken()) == ',') {
			reader.getNextToken();
			name = StringConverter.deserialize(reader);
			nextToken = reader.getNextToken();
			if (nextToken != ':') throw new IOException("Expecting ':' " + reader.positionDescription() + ". Found " + (char)nextToken);
			reader.getNextToken();
			value = NumberConverter.deserializeInt(reader);
			if ("X".equalsIgnoreCase(name)) {
				x = value;
			} else if ("Y".equalsIgnoreCase(name)) {
				y = value;
			}
		}
		if (nextToken != '}') throw new IOException("Expecting '}' " + reader.positionDescription() + ". Found " + (char)nextToken);
		return new Point(x, y);

	}

	public static ArrayList<Point> deserializePointCollection(final JsonReader reader) throws IOException {
		return reader.deserializeCollection(POINT_READER);
	}

	public static void deserializePointCollection(final JsonReader reader, final Collection<Point> res) throws IOException {
		reader.deserializeCollection(POINT_READER, res);
	}

	public static ArrayList<Point> deserializePointNullableCollection(final JsonReader reader) throws IOException {
		return reader.deserializeNullableCollection(POINT_READER);
	}

	public static void deserializePointNullableCollection(final JsonReader reader, final Collection<Point> res) throws IOException {
		reader.deserializeNullableCollection(POINT_READER, res);
	}

	public static void serializeRectangleNullable(@Nullable final Rect value, final JsonWriter sw) {
		if (value == null) {
			sw.writeNull();
		} else {
			serializeRectangle(value, sw);
		}
	}

	public static void serializeRectangle(final Rect value, final JsonWriter sw) {
		sw.writeAscii("{\"X\":");
		NumberConverter.serialize(value.left, sw);
		sw.writeAscii(",\"Y\":");
		NumberConverter.serialize(value.top, sw);
		sw.writeAscii(",\"Width\":");
		NumberConverter.serialize(value.width(), sw);
		sw.writeAscii(",\"Height\":");
		NumberConverter.serialize(value.height(), sw);
		sw.writeByte(JsonWriter.OBJECT_END);
	}

	public static Rect deserializeRectangle(final JsonReader reader) throws IOException {
		if (reader.last() != '{') throw new IOException("Expecting '{' " + reader.positionDescription() + ". Found " + (char)reader.last());
		byte nextToken = reader.getNextToken();
		if (nextToken == '}') return new Rect();
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		String name = StringConverter.deserialize(reader);
		nextToken = reader.getNextToken();
		if (nextToken != ':') throw new IOException("Expecting ':' " + reader.positionDescription() + ". Found " + (char)nextToken);
		reader.getNextToken();
		int value = NumberConverter.deserializeInt(reader);
		if ("X".equalsIgnoreCase(name)) {
			x = value;
		} else if ("Y".equalsIgnoreCase(name)) {
			y = value;
		} else if ("Width".equalsIgnoreCase(name)) {
			width = value;
		} else if ("Height".equalsIgnoreCase(name)) {
			height = value;
		}
		while ((nextToken = reader.getNextToken()) == ',') {
			reader.getNextToken();
			name = StringConverter.deserialize(reader);
			nextToken = reader.getNextToken();
			if (nextToken != ':') throw new IOException("Expecting ':' " + reader.positionDescription() + ". Found " + (char)nextToken);
			reader.getNextToken();
			value = NumberConverter.deserializeInt(reader);
			if ("X".equalsIgnoreCase(name)) {
				x = value;
			} else if ("Y".equalsIgnoreCase(name)) {
				y = value;
			} else if ("Width".equalsIgnoreCase(name)) {
				width = value;
			} else if ("Height".equalsIgnoreCase(name)) {
				height = value;
			}
		}
		if (nextToken != '}') throw new IOException("Expecting '}' " + reader.positionDescription() + ". Found " + (char)nextToken);
		return new Rect(x, y, x + width, y + height);
	}

	public static ArrayList<Rect> deserializeRectangleCollection(final JsonReader reader) throws IOException {
		return reader.deserializeCollection(RECTANGLE_READER);
	}

	public static void deserializeRectangleCollection(final JsonReader reader, final Collection<Rect> res) throws IOException {
		reader.deserializeCollection(RECTANGLE_READER, res);
	}

	public static ArrayList<Rect> deserializeRectangleNullableCollection(final JsonReader reader) throws IOException {
		return reader.deserializeNullableCollection(RECTANGLE_READER);
	}

	public static void deserializeRectangleNullableCollection(final JsonReader reader, final Collection<Rect> res) throws IOException {
		reader.deserializeNullableCollection(RECTANGLE_READER, res);
	}

	public static void serialize(@Nullable final Bitmap value, final JsonWriter sw) {
		if (value == null) {
			sw.writeNull();
		} else {
			final ByteArrayOutputStream stream = new ByteArrayOutputStream(value.getByteCount());
			value.compress(Bitmap.CompressFormat.PNG, 100, stream);
			BinaryConverter.serialize(stream.toByteArray(), sw);
		}
	}

	public static Bitmap deserializeImage(final JsonReader reader) throws IOException {
		final byte[] content = BinaryConverter.deserialize(reader);
		return BitmapFactory.decodeByteArray(content, 0, content.length);
	}

	public static ArrayList<Bitmap> deserializeImageCollection(final JsonReader reader) throws IOException {
		return reader.deserializeCollection(IMAGE_READER);
	}

	public static void deserializeImageCollection(final JsonReader reader, final Collection<Bitmap> res) throws IOException {
		reader.deserializeCollection(IMAGE_READER, res);
	}

	public static ArrayList<Bitmap> deserializeImageNullableCollection(final JsonReader reader) throws IOException {
		return reader.deserializeNullableCollection(IMAGE_READER);
	}

	public static void deserializeImageNullableCollection(final JsonReader reader, final Collection<Bitmap> res) throws IOException {
		reader.deserializeNullableCollection(IMAGE_READER, res);
	}
}
