package nc.noumea.mairie.bilan.energie.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import nc.noumea.mairie.bilan.energie.core.exception.FileException;

/**
 * Class utilitaire des fichiers
 * 
 * @author Greg Dujardin
 * 
 */
public final class FileUtils {

	/**
	 * Constructeur privé conseillé par Sonar
	 */
	private FileUtils() {

	}

	/**
	 * Lecture d'un répertoire pour lister les fichiers répondant à certains
	 * critères
	 * 
	 * @param repertoire Répertoire à lister
	 * @param prefix Critère préfixe
	 * @param extension Critère extension
	 * @return Liste des fichiers
	 * @throws FileException Exception sur la manipulation des fichiers
	 */
	public final static List<File> listFichier(String repertoire,
			String prefix, String extension) throws FileException {

		List<File> listeFichier = new ArrayList<File>();

		File dir = new File(repertoire);

		// Contrôle que le chemin est un répertoire
		if (dir.isDirectory() == false) {
			throw new FileException("Le chemin " + repertoire
					+ " n'est pas un répertoire");
		}

		// Liste du contenu
		File[] list = dir.listFiles();

		for (File file : list) {
			if (file.getName().toUpperCase().startsWith(prefix.toUpperCase())
					&& file.getName().toUpperCase()
							.endsWith(extension.toUpperCase()))

				listeFichier.add(file);
		}
		return listeFichier;
	}

	/**
	 * Décompression d'un fichier Zip
	 *
	 * @param zipFile Fichier à dézipper
	 * @param repertoireTravail Répertoire de travail
	 * @throws FileException Exception de manipulation de fichier
	 */
	public final static void unZipIt(File zipFile, String repertoireTravail)
			throws FileException {

		byte[] buffer = new byte[1024];

		try {

			File dir = new File(repertoireTravail);

			// Contrôle que le chemin est un répertoire
			if (dir.isDirectory() == false) {
				throw new FileException("Le chemin " + repertoireTravail
						+ " n'est pas un répertoire");
			}

			// Création du fichier Zip
			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(zipFile));

			// Lecture du contenu du zip
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(repertoireTravail + File.separator
						+ fileName);

				// Création des répertoires inexistant
				new File(newFile.getParent()).mkdirs();

				// Ecriture du fichier
				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException ex) {
			throw new FileException("Erreur lors du dézippage du fichier "
					+ zipFile.getName());
		}
	}

	/**
	 * Suppression d'un fichier ou d'un répertoire de manière récursive
	 * 
	 * @param repertoire Répertoire à supprimer
	 */
	public final static void delete(File repertoire) {

		if (repertoire.isDirectory()) {

			// Le répertoire est vide, on le supprime
			if (repertoire.list().length == 0) {

				repertoire.delete();
			} else {

				// Balayer le contenu du répertoire
				File files[] = repertoire.listFiles();

				for (File file2 : files) {

					// Appel récursif
					delete(file2);
				}

				// Le répertoire est vide, on le supprime
				if (repertoire.list().length == 0) {
					repertoire.delete();
				}
			}

		} else {
			// C'est un fichier, on le supprime
			repertoire.delete();
		}
	}
}
