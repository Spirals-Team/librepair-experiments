package com.prussia.play.spring.util;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.Collection;


public class ZipUtil {

	/**
	 * Add all files from the source directory to the destination zip file
	 *
	 * @param source
	 *            the directory with files to add
	 * @param destination
	 *            the zip file that should contain the files
	 * @throws IOException
	 *             if the io fails
	 * @throws ArchiveException
	 *             if creating or adding to the archive fails
	 */
	public static void addFilesToZip(File source, File destination) throws IOException, ArchiveException {
		try (OutputStream archiveStream = new FileOutputStream(destination);
				ArchiveOutputStream archive = new ArchiveStreamFactory()
						.createArchiveOutputStream(ArchiveStreamFactory.ZIP, archiveStream);) {

			if (source.isDirectory()) {
				Collection<File> fileList = FileUtils.listFiles(source, null, true);

				for (File file : fileList) {
					String entryName = getEntryName(source, file);
					zipSingleFile(file, archive, entryName);
				}
			} else if (source.isFile()) {
				String entryName = FilenameUtils.getName(source.getPath());
				zipSingleFile(source, archive, entryName);
			}

			archive.finish();
		}
	}

	private static void zipSingleFile(File source, ArchiveOutputStream archive, String entryName)
			throws IOException, FileNotFoundException {
		ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
		archive.putArchiveEntry(entry);

		try(BufferedInputStream input = new BufferedInputStream(new FileInputStream(source.getPath()));){

			IOUtils.copy(input, archive);
			
		}
		archive.closeArchiveEntry();
	}

//	private void zipFile(ArchiveOutputStream archive, File file, String entryName)
//			throws IOException, FileNotFoundException {
//		ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
//		archive.putArchiveEntry(entry);
//
//		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
//
//		IOUtils.copy(input, archive);
//		input.close();
//		archive.closeArchiveEntry();
//	}

	/**
	 * Remove the leading part of each entry that contains the source directory name
	 *
	 * @param source
	 *            the directory where the file entry is found
	 * @param file
	 *            the file that is about to be added
	 * @return the name of an archive entry
	 * @throws IOException
	 *             if the io fails
	 */
	private static String getEntryName(File source, File file) throws IOException {
		int index = source.getAbsolutePath().length() + 1;
		String path = file.getCanonicalPath();

		return path.substring(index);
	}

}
