/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on Jun 16, 2010
 * Author: ap3
 *
 */

package org.biojava.nbio.structure.io;

import java.io.Serializable;

import org.biojava.nbio.structure.AminoAcid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that configures parameters that can be sent to the PDB file parsers
 *
 * <ul>
 * <li> {@link #setParseCAOnly(boolean)} - parse only the Atom records for C-alpha atoms</li>
 * <li> {@link #setParseSecStruc(boolean)} - a flag if the secondary structure information from the PDB file (author's assignment) should be parsed.
 *      If true the assignment can be accessed through {@link AminoAcid}.getSecStruc(); </li>
 * <li> {@link #setAlignSeqRes(boolean)} - should the AminoAcid sequences from the SEQRES
 *      and ATOM records of a PDB file be aligned? (default:yes)</li>
 * <li> {@link #setHeaderOnly(boolean)} - parse only the PDB/mmCIF file header, ignoring coordinates
 * </li>
 * <li> {@link #setCreateAtomBonds(boolean)} - create atom bonds from parsed bonds in PDB/mmCIF files and chemical component files
 * </li>
 * </ul>
 *
 * @author Andreas Prlic
 *
 */
public class FileParsingParameters implements Serializable
{

	private static final Logger logger = LoggerFactory.getLogger(FileParsingParameters.class);


	private static final long serialVersionUID = 5878292315163939027L;



	/**
	 * Flag to detect if the secondary structure info should be read
	 *
	 */
	private boolean parseSecStruc;

	/**
	 * Flag to control if SEQRES and ATOM records should be aligned
	 */
	private boolean alignSeqRes;

	/**
	 * Flag to control reading in only Calpha atoms - this is useful for parsing large structures like 1htq.
	 */
	private boolean parseCAOnly;

	/**
	 * Flag to parse header only
	 */
	private boolean headerOnly;


	/**
	 * Update locally cached files to the latest version of remediated files
	 */
	private boolean updateRemediatedFiles;

	/**
	 * The maximum number of atoms that will be parsed before the parser switches to a CA-only
	 * representation of the PDB file. If this limit is exceeded also the SEQRES groups will be
	 * ignored.
	 */
	public static final int ATOM_CA_THRESHOLD = 500000;

	private int atomCaThreshold;


	/**
	 * Should we parse the biological assembly information from a file?
	 */
	private boolean parseBioAssembly;

	/**
	 * Should we create bonds between atoms when parsing a file?
	 */
	private boolean createAtomBonds;

	/**
	 * Should we create charges on atoms when parsing a file?
	 */
	private boolean createAtomCharges;

	/**
	 * Should we use internal (asym_id) or public facing (author) chain ids
	 */
	private boolean useInternalChainId;
	/**
	 * The maximum number of atoms we will add to a structure,
	 * this protects from memory overflows in the few really big protein structures.
	 */
	public static final int MAX_ATOMS = Integer.MAX_VALUE; // no limit, we don't want to truncate molecules, but the user should make sure there is more memory available
	//public static final int MAX_ATOMS = 700000; // tested with java -Xmx300M

	int maxAtoms ;

	String[] fullAtomNames;

	public FileParsingParameters(){
		setDefault();
	}

	public void setDefault(){

		parseSecStruc = false;
		// Default is to align / when false the unaligned SEQRES is stored.
		alignSeqRes   = true;
		parseCAOnly = false;

		headerOnly = false;

		updateRemediatedFiles = false;
		fullAtomNames = null;

		maxAtoms = MAX_ATOMS;

		atomCaThreshold = ATOM_CA_THRESHOLD;

		parseBioAssembly = false;

		createAtomBonds = false;

		createAtomCharges = true;

		useInternalChainId = false;
	}

	/**
	 * Is secondary structure assignment being parsed from the file?
	 * default is null
	 * @return boolean if HELIX STRAND and TURN fields are being parsed
	 */
	public boolean isParseSecStruc() {
		return parseSecStruc;
	}

	/**
	 * A flag to tell the parser to parse the Author's secondary structure assignment from the file
	 * default is set to false, i.e. do NOT parse.
	 * @param parseSecStruc if HELIX STRAND and TURN fields are being parsed
	 */
	public void setParseSecStruc(boolean parseSecStruc) {
		this.parseSecStruc = parseSecStruc;
	}

	/** Parse only the PDB file header out of the files
	 *
	 * @return flag
	 */
	public boolean isHeaderOnly()
	{
		return headerOnly;
	}

	/** Parse only the PDB file header out of the files
	 *
	 * @param headerOnly flag
	 */
	public void setHeaderOnly(boolean headerOnly)
	{
		this.headerOnly = headerOnly;
	}

	/**
	 * The flag if only the C-alpha atoms of the structure should be parsed.
	 *
	 * @return the flag
	 */
	public boolean isParseCAOnly() {
		return parseCAOnly;
	}
	/**
	 * Flag if only the C-alpha atoms of the structure should be parsed.
	 *
	 * @param parseCAOnly boolean flag to enable or disable C-alpha only parsing
	 */
	public void setParseCAOnly(boolean parseCAOnly) {
		this.parseCAOnly = parseCAOnly;
	}



	/** Flag if the SEQRES amino acids should be aligned with the ATOM amino acids.
	 *
	 * @return flag if SEQRES - ATOM amino acids alignment is enabled
	 */
	public boolean isAlignSeqRes() {
		return alignSeqRes;
	}



	/**
	 * Define if the SEQRES in the structure should be aligned with the ATOM records
	 * if yes, the AminoAcids in structure.getSeqRes will have the coordinates set.
	 * @param alignSeqRes
	 */
	public void setAlignSeqRes(boolean alignSeqRes) {
		this.alignSeqRes = alignSeqRes;
	}

	/** A flag if local files should be replaced with the latest version of remediated PDB files. Default: false
	 *
	 * @returns updateRemediatedFiles flag
	 * @deprecated Properties which impact downloading and caching behavior
	 *  have been moved to the {@link StructureIOFile} implementations.
	 *  See {@link LocalPDBDirectory#getFetchBehavior(LocalPDBDirectory.FetchBehavior)}
	 */
	@Deprecated
	public boolean isUpdateRemediatedFiles() {
		return updateRemediatedFiles;
	}

	/** A flag if local files should be replaced with the latest version of remediated PDB files. Default: false
	 *
	 * @param updateRemediatedFiles
	 * @deprecated Properties which impact downloading and caching behavior
	 *  have been moved to the {@link StructureIOFile} implementations.
	 *  See {@link LocalPDBDirectory#setFetchBehavior(LocalPDBDirectory.FetchBehavior)}
	 */
	@Deprecated
	public void setUpdateRemediatedFiles(boolean updateRemediatedFiles) {
		logger.warn("FileParsingParameters.setUpdateRemediatedFiles() is deprecated, please use LocalPDBDirectory.setFetchBehavior() instead. The option will be removed in upcoming releases");
		this.updateRemediatedFiles = updateRemediatedFiles;
	}

	/**
	 * By default the parser will read in all atoms (unless using the CAonly switch). This allows to specify a set of atoms to be read. e.g.
	 * {"CA", "CB" }. Returns null if all atoms are accepted.
	 * @return accepted atom names, or null if all atoms are accepted. default null
	 */
	public String[] getAcceptedAtomNames() {
		return fullAtomNames;
	}


	/**
	 * By default the parser will read in all atoms (unless using the CAonly switch). This allows to specify a set of atoms to be read. e.g.
	 * {"CA", "CB" }. Returns null if all atoms are accepted.
	 * @param accepted atom names, or null if all atoms are accepted. default null
	 */

	public void setAcceptedAtomNames(String[] fullAtomNames) {
		this.fullAtomNames = fullAtomNames;
	}


	/**
	 * The maximum numbers of atoms to load in a protein structure (prevents memory overflows)
	 *
	 * @return maximum nr of atoms to load, default Integer.MAX_VALUE;
	 */
	public int getMaxAtoms() {
		return maxAtoms;
	}

	/**
	 * The maximum numbers of atoms to load in a protein structure (prevents memory overflows)
	 *
	 * @param maxAtoms maximun nr of atoms to load
	 */
	public void setMaxAtoms(int maxAtoms) {
		this.maxAtoms = maxAtoms;
	}


	/**
	 * The maximum number of atoms that will be parsed before the parser switches to a CA-only
	 * representation of the PDB file. If this limit is exceeded also the SEQRES groups will be
	 * ignored.
	 *
	 *
	 * @return atomCaThreshold.
	 */
	public int getAtomCaThreshold() {
		return atomCaThreshold;
	}


	/**
	 * The maximum number of atoms that will be parsed before the parser switches to a CA-only
	 * representation of the PDB file. If this limit is exceeded also the SEQRES groups will be
	 * ignored.
	 * @param atomCaThreshold maximum number of atoms for all atom representation.
	 */
	public void setAtomCaThreshold(int atomCaThreshold) {
		this.atomCaThreshold = atomCaThreshold;
	}


	/** Should the biological assembly info (REMARK 350) be parsed from the PDB file?
	 *
	 * @return boolean flag yes/no
	 */
	public boolean isParseBioAssembly() {
		return parseBioAssembly;
	}

	/** Should the biological assembly info (REMARK 350) be parsed from the PDB file?
	 *
	 * @param parseBioAssembly  boolean flag yes/no
	 */

	public void setParseBioAssembly(boolean parseBioAssembly) {
		this.parseBioAssembly = parseBioAssembly;
	}

	/**
	 * Should we create bonds between atoms when parsing a file?
	 *
	 * @return true if we should create the bonds, false if not
	 */
	public boolean shouldCreateAtomBonds() {
		return createAtomBonds;
	}

	/**
	 * Should we create bonds between atoms when parsing a file.
	 * Will create intra-group bonds from information available in chemical component files and
	 * some other bonds from struc_conn category in mmCIF file.
	 *
	 * @param createAtomBonds
	 *            true if we should create the bonds, false if not
	 * @see BondMaker
	 */
	public void setCreateAtomBonds(boolean createAtomBonds) {
		this.createAtomBonds = createAtomBonds;
	}

	/**
	 * Should we create charges on atoms when parsing a file?
	 *
	 * @return true if we should create the charges, false if not
	 */
	public boolean shouldCreateAtomCharges() {
		return createAtomCharges;
	}

	/**
	 * Should we create charges on atoms when parsing a file?
	 *
	 * @param createAtomCharges
	 *            true if we should create the charges, false if not
	 */
	public void setCreateAtomCharges(boolean createAtomCharges) {
		this.createAtomCharges = createAtomCharges;
	}

	/**
	 * Should we use internal (asym_id) or public facing (author) chain ids
	 * @return
	 * @since 4.2
	 */
	public boolean isUseInternalChainId() {
		return useInternalChainId;
	}

	/**
	 * Set the useInternalChainId parsing mode. This is an experimental
	 * parsing mode that applies only to the mmCIF parser. It will create chains
	 * following the model specified in the mmCIF dictionary where both polymer and
	 * non-polymer entities are assigned separate chains. The chain identifiers
	 * used are the asym_ids specified in mmCIF file. Some BioJava features might not
	 * work properly in this parsing mode.
	 * @param useInternalChainId
	 * @since 4.2
	 */
	public void setUseInternalChainId(boolean useInternalChainId) {
		this.useInternalChainId = useInternalChainId;
	}
}
