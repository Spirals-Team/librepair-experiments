/*  Copyright (C) 2016  Nicholas Wright
    
    This file is part of similarImage - A similar image finder using pHash
    
    similarImage is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.dozedoff.similarImage.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import at.dhyan.open_imaging.GifDecoder;

public abstract class ImageUtil {
	/**
	 * Create an image from a byte array.
	 * 
	 * @param data
	 *            array containing the image
	 * @return the converted image
	 * @throws IOException
	 *             on error during conversion
	 */
	public static BufferedImage bytesToImage(byte[] data) throws IOException {
		InputStream is = new ByteArrayInputStream(data);
		return ImageIO.read(is);
	}

	/**
	 * Convert a image into a byte array
	 * 
	 * @param image
	 *            to convert
	 * @return array containing the image
	 * @throws IOException
	 *             on error during conversion
	 */
	public static byte[] imageToBytes(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ImageIO.write(image, "png", baos);
		
		return baos.toByteArray();
	}

	/**
	 * Load an image from the given path.
	 * 
	 * @param path
	 *            to the image to load
	 * @return the loaded image
	 * @throws IOException
	 *             if there is an error reading the image
	 */
	public static BufferedImage loadImage(Path path) throws IOException {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			BufferedImage bi;

			try {
				bi = ImageIO.read(is);
			} catch (ArrayIndexOutOfBoundsException e) {
				bi = GifDecoder.read(is).getFrame(0);
			}

			return bi;
		}
	}
}
