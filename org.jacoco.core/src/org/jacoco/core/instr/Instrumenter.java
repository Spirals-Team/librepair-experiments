/*******************************************************************************
 * Copyright (c) 2009, 2018 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    
 *******************************************************************************/
package org.jacoco.core.instr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.jacoco.core.internal.BytecodeVersion;
import org.jacoco.core.internal.ContentTypeDetector;
import org.jacoco.core.internal.InputStreams;
import org.jacoco.core.internal.Pack200Streams;
import org.jacoco.core.internal.data.CRC64;
import org.jacoco.core.internal.flow.ClassProbesAdapter;
import org.jacoco.core.internal.instr.ClassInstrumenter;
import org.jacoco.core.internal.instr.IProbeArrayStrategy;
import org.jacoco.core.internal.instr.InstrSupport;
import org.jacoco.core.internal.instr.ProbeArrayStrategyFactory;
import org.jacoco.core.internal.instr.SignatureRemover;
import org.jacoco.core.runtime.IExecutionDataAccessorGenerator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Several APIs to instrument Java class definitions for coverage tracing.
 */
public class Instrumenter {

	private final IExecutionDataAccessorGenerator accessorGenerator;

	private final SignatureRemover signatureRemover;

	/**
	 * Creates a new instance based on the given runtime.
	 * 
	 * @param runtime
	 *            runtime used by the instrumented classes
	 */
	public Instrumenter(final IExecutionDataAccessorGenerator runtime) {
		this.accessorGenerator = runtime;
		this.signatureRemover = new SignatureRemover();
	}

	/**
	 * Determines whether signatures should be removed from JAR files. This is
	 * typically necessary as instrumentation modifies the class files and
	 * therefore invalidates existing JAR signatures. Default is
	 * <code>true</code>.
	 * 
	 * @param flag
	 *            <code>true</code> if signatures should be removed
	 */
	public void setRemoveSignatures(final boolean flag) {
		signatureRemover.setActive(flag);
	}

	/**
	 * Creates a instrumented version of the given class if possible.
	 * 
	 * @param reader
	 *            definition of the class as ASM reader
	 * @return instrumented definition
	 * 
	 */
	public byte[] instrument(final ClassReader reader) {
		return instrument(reader.b);
	}

	private byte[] instrument(final byte[] source) {
		final long classId = CRC64.classId(source);
		final int originalVersion = BytecodeVersion.get(source);
		final byte[] b = BytecodeVersion.downgradeIfNeeded(originalVersion,
				source);
		final ClassReader reader = new ClassReader(b);
		final ClassWriter writer = new ClassWriter(reader, 0) {
			@Override
			protected String getCommonSuperClass(final String type1,
					final String type2) {
				throw new IllegalStateException();
			}
		};
		final IProbeArrayStrategy strategy = ProbeArrayStrategyFactory
				.createFor(classId, reader, accessorGenerator);
		final ClassVisitor visitor = new ClassProbesAdapter(
				new ClassInstrumenter(strategy, writer),
				InstrSupport.needsFrames(originalVersion));
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);
		final byte[] instrumented = writer.toByteArray();
		BytecodeVersion.set(instrumented, originalVersion);
		return instrumented;
	}

	/**
	 * Creates a instrumented version of the given class if possible.
	 * 
	 * @param buffer
	 *            definition of the class
	 * @param name
	 *            a name used for exception messages
	 * @return instrumented definition
	 * @throws IOException
	 *             if the class can't be instrumented
	 */
	public byte[] instrument(final byte[] buffer, final String name)
			throws IOException {
		try {
			return instrument(buffer);
		} catch (final RuntimeException e) {
			throw instrumentError(name, e);
		}
	}

	/**
	 * Creates a instrumented version of the given class if possible. The
	 * provided {@link InputStream} is not closed by this method.
	 * 
	 * @param input
	 *            stream to read class definition from
	 * @param name
	 *            a name used for exception messages
	 * @return instrumented definition
	 * @throws IOException
	 *             if reading data from the stream fails or the class can't be
	 *             instrumented
	 */
	public byte[] instrument(final InputStream input, final String name)
			throws IOException {
		final byte[] bytes;
		try {
			bytes = InputStreams.readFully(input);
		} catch (final IOException e) {
			throw instrumentError(name, e);
		}
		return instrument(bytes, name);
	}

	/**
	 * Creates a instrumented version of the given class file. The provided
	 * {@link InputStream} and {@link OutputStream} instances are not closed by
	 * this method.
	 * 
	 * @param input
	 *            stream to read class definition from
	 * @param output
	 *            stream to write the instrumented version of the class to
	 * @param name
	 *            a name used for exception messages
	 * @throws IOException
	 *             if reading data from the stream fails or the class can't be
	 *             instrumented
	 */
	public void instrument(final InputStream input, final OutputStream output,
			final String name) throws IOException {
		output.write(instrument(input, name));
	}

	private IOException instrumentError(final String name,
			final Exception cause) {
		final IOException ex = new IOException(
				String.format("Error while instrumenting %s.", name));
		ex.initCause(cause);
		return ex;
	}

	/**
	 * Creates a instrumented version of the given resource depending on its
	 * type. Class files and the content of archive files are instrumented. All
	 * other files are copied without modification. The provided
	 * {@link InputStream} and {@link OutputStream} instances are not closed by
	 * this method.
	 * 
	 * @param input
	 *            stream to contents from
	 * @param output
	 *            stream to write the instrumented version of the contents
	 * @param name
	 *            a name used for exception messages
	 * @return number of instrumented classes
	 * @throws IOException
	 *             if reading data from the stream fails or a class can't be
	 *             instrumented
	 */
	public int instrumentAll(final InputStream input, final OutputStream output,
			final String name) throws IOException {
		final ContentTypeDetector detector;
		try {
			detector = new ContentTypeDetector(input);
		} catch (final IOException e) {
			throw instrumentError(name, e);
		}
		switch (detector.getType()) {
		case ContentTypeDetector.CLASSFILE:
			instrument(detector.getInputStream(), output, name);
			return 1;
		case ContentTypeDetector.ZIPFILE:
			return instrumentZip(detector.getInputStream(), output, name);
		case ContentTypeDetector.GZFILE:
			return instrumentGzip(detector.getInputStream(), output, name);
		case ContentTypeDetector.PACK200FILE:
			return instrumentPack200(detector.getInputStream(), output, name);
		default:
			copy(detector.getInputStream(), output, name);
			return 0;
		}
	}

	private int instrumentZip(final InputStream input,
			final OutputStream output, final String name) throws IOException {
		final ZipInputStream zipin = new ZipInputStream(input);
		final ZipOutputStream zipout = new ZipOutputStream(output);
		ZipEntry entry;
		int count = 0;
		while ((entry = nextEntry(zipin, name)) != null) {
			final String entryName = entry.getName();
			if (signatureRemover.removeEntry(entryName)) {
				continue;
			}

			zipout.putNextEntry(new ZipEntry(entryName));
			if (!signatureRemover.filterEntry(entryName, zipin, zipout)) {
				count += instrumentAll(zipin, zipout, name + "@" + entryName);
			}
			zipout.closeEntry();
		}
		zipout.finish();
		return count;
	}

	private ZipEntry nextEntry(final ZipInputStream input,
			final String location) throws IOException {
		try {
			return input.getNextEntry();
		} catch (final IOException e) {
			throw instrumentError(location, e);
		}
	}

	private int instrumentGzip(final InputStream input,
			final OutputStream output, final String name) throws IOException {
		final GZIPInputStream gzipInputStream;
		try {
			gzipInputStream = new GZIPInputStream(input);
		} catch (final IOException e) {
			throw instrumentError(name, e);
		}
		final GZIPOutputStream gzout = new GZIPOutputStream(output);
		final int count = instrumentAll(gzipInputStream, gzout, name);
		gzout.finish();
		return count;
	}

	private int instrumentPack200(final InputStream input,
			final OutputStream output, final String name) throws IOException {
		final InputStream unpackedInput;
		try {
			unpackedInput = Pack200Streams.unpack(input);
		} catch (final IOException e) {
			throw instrumentError(name, e);
		}
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		final int count = instrumentAll(unpackedInput, buffer, name);
		Pack200Streams.pack(buffer.toByteArray(), output);
		return count;
	}

	private void copy(final InputStream input, final OutputStream output,
			final String name) throws IOException {
		final byte[] buffer = new byte[1024];
		int len;
		while ((len = read(input, buffer, name)) != -1) {
			output.write(buffer, 0, len);
		}
	}

	private int read(final InputStream input, final byte[] buffer,
			final String name) throws IOException {
		try {
			return input.read(buffer);
		} catch (final IOException e) {
			throw instrumentError(name, e);
		}
	}

}
