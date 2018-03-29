package edu.sdsc.mmtf.spark.mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.rcsb.mmtf.api.StructureDataInterface;
import org.rcsb.mmtf.decoder.DecoderUtils;
import org.rcsb.mmtf.encoder.AdapterToStructureData;

import scala.Tuple2;

/**
 * Maps a structure to its individual polymer chains. Polymer chains
 * include polypeptides, polynucleotides, and linear and branched polysaccharides.
 * For a multi-model structure, only the first model is considered.
 * 
 * @author Peter Rose
 */
public class StructureToPolymerChains implements PairFlatMapFunction<Tuple2<String,StructureDataInterface>,String, StructureDataInterface> {
	private static final long serialVersionUID = -5979145207983266913L;
	private boolean useChainIdInsteadOfChainName = false;
	private boolean excludeDuplicates = false;

	/**
	 * Extracts all polymer chains from a structure. A key is assigned to
	 * each polymer: <PDB ID.Chain Name>, e.g., 4HHB.A. Here Chain 
	 * Name is the name of the chain as found in the corresponding pdb file.
	 */
	public StructureToPolymerChains() {}
	
	/**
	 * Extracts all polymer chains from a structure. If the argument is set to true,
	 * the assigned key is: <PDB ID.Chain ID>, where Chain ID is the unique identifier
	 * assigned to each molecular entity in an mmCIF file. This Chain ID corresponds to
	 * <a href="http://mmcif.wwpdb.org/dictionaries/mmcif_mdb.dic/Items/_atom_site.label_asym_id.html">
	 * _atom_site.label_asym_id</a> field in an mmCIF file.
	 * 
	 * @param useChainIdInsteadOfChainName if true, use the Chain Id in the key assignments
	 * @param excludeDuplicates if true, return only one chain for each unique sequence
	 */
	public StructureToPolymerChains(boolean useChainIdInsteadOfChainName, boolean excludeDuplicates) {
		this.useChainIdInsteadOfChainName = useChainIdInsteadOfChainName;
		this.excludeDuplicates = excludeDuplicates;
	}
	
	@Override
	public Iterator<Tuple2<String, StructureDataInterface>> call(Tuple2<String, StructureDataInterface> t) throws Exception {
		StructureDataInterface structure = t._2;

		// precalculate indices
		int numChains = structure.getChainsPerModel()[0];
		int[] chainToEntityIndex = getChainToEntityIndex(structure);
		int[] atomsPerChain = new int[numChains];
		int[] bondsPerChain = new int[numChains];
		getNumAtomsAndBonds(structure, atomsPerChain, bondsPerChain);
		
		List<Tuple2<String, StructureDataInterface>> chainList = new ArrayList<>();
		Set<String> seqSet = new HashSet<>();
		
		for (int i = 0, atomCounter = 0, groupCounter = 0; i < numChains; i++){	
			AdapterToStructureData polymerChain = new AdapterToStructureData();
			
			int entityToChainIndex = chainToEntityIndex[i];
			boolean polymer = structure.getEntityType(entityToChainIndex).equals("polymer");
			int polymerAtomCount = 0;

			Map<Integer, Integer> atomMap = new HashMap<>();

			if (polymer) {
		        // to avoid of information loss, add chainName/IDs and entity id
				// this required by some queries
				String structureId = structure.getStructureId() + "." + structure.getChainNames()[i] +
						"." + structure.getChainIds()[i] + "." + (entityToChainIndex+1);
				
				// set header
				polymerChain.initStructure(bondsPerChain[i], atomsPerChain[i], 
						structure.getGroupsPerChain()[i], 1, 1, structureId);
				DecoderUtils.addXtalographicInfo(structure, polymerChain);
				DecoderUtils.addHeaderInfo(structure, polymerChain);	

				// set model info (only one model: 0)
				polymerChain.setModelInfo(0, 1);

				// set entity and chain info
				polymerChain.setEntityInfo(new int[]{0}, structure.getEntitySequence(entityToChainIndex), 
						structure.getEntityDescription(entityToChainIndex), structure.getEntityType(entityToChainIndex));
				polymerChain.setChainInfo(structure.getChainIds()[i], structure.getChainNames()[i], structure.getGroupsPerChain()[i]);
			}

			for (int j = 0; j < structure.getGroupsPerChain()[i]; j++, groupCounter++){
				int groupIndex = structure.getGroupTypeIndices()[groupCounter];
				if (polymer) {
					// set group info
					polymerChain.setGroupInfo(structure.getGroupName(groupIndex), structure.getGroupIds()[groupCounter], 
							structure.getInsCodes()[groupCounter], structure.getGroupChemCompType(groupIndex), structure.getNumAtomsInGroup(groupIndex),
							structure.getGroupBondOrders(groupIndex).length, structure.getGroupSingleLetterCode(groupIndex), structure.getGroupSequenceIndices()[groupCounter], 
							structure.getSecStructList()[groupCounter]);
				}

				for (int k = 0; k < structure.getNumAtomsInGroup(groupIndex); k++, atomCounter++){
					if (polymer) {
						// set atom info
						atomMap.put(atomCounter, polymerAtomCount);
						polymerAtomCount++;
						
						polymerChain.setAtomInfo(structure.getGroupAtomNames(groupIndex)[k], structure.getAtomIds()[atomCounter], structure.getAltLocIds()[atomCounter], 
								structure.getxCoords()[atomCounter], structure.getyCoords()[atomCounter], structure.getzCoords()[atomCounter], 
								structure.getOccupancies()[atomCounter], structure.getbFactors()[atomCounter], structure.getGroupElementNames(groupIndex)[k], structure.getGroupAtomCharges(groupIndex)[k]);
					}
				}

				if (polymer) {
					// add intra-group bond info
					for (int l = 0; l < structure.getGroupBondOrders(groupIndex).length; l++) {
						int bondIndOne = structure.getGroupBondIndices(groupIndex)[l*2];
						int bondIndTwo = structure.getGroupBondIndices(groupIndex)[l*2+1];
						int bondOrder = structure.getGroupBondOrders(groupIndex)[l];
						polymerChain.setGroupBond(bondIndOne, bondIndTwo, bondOrder);
					}
				}
			}

			if (polymer) {
				// Add inter-group bond info
				for(int ii = 0; ii < structure.getInterGroupBondOrders().length; ii++){
					int bondIndOne = structure.getInterGroupBondIndices()[ii*2];
					int bondIndTwo = structure.getInterGroupBondIndices()[ii*2+1];
					int bondOrder = structure.getInterGroupBondOrders()[ii];
					Integer indexOne = atomMap.get(bondIndOne);
					if (indexOne != null) {
						Integer indexTwo = atomMap.get(bondIndTwo);
						if (indexTwo != null) {
							polymerChain.setInterGroupBond(indexOne, indexTwo, bondOrder);
						}
					}
				}

				polymerChain.finalizeStructure();
				
				String chId = structure.getChainNames()[i];
				if (useChainIdInsteadOfChainName) {
					chId = structure.getChainIds()[i];
				}

				if (excludeDuplicates) {
					if (seqSet.contains(structure.getEntitySequence(chainToEntityIndex[i]))) {
						continue;
					}
	                seqSet.add(structure.getEntitySequence(chainToEntityIndex[i]));
				}
				
				chainList.add(new Tuple2<String, StructureDataInterface>(structure.getStructureId() + "." + chId, polymerChain));
			}
		}

		return chainList.iterator();
	}

	/**
	 * Gets the number of atoms and bonds per chain.
	 */
	private static void getNumAtomsAndBonds(StructureDataInterface structure, int[] atomsPerChain, int[] bondsPerChain) {
		int numChains = structure.getChainsPerModel()[0];

		for (int i = 0, groupCounter = 0; i < numChains; i++){	
			for (int j = 0; j < structure.getGroupsPerChain()[i]; j++, groupCounter++){
				int groupIndex = structure.getGroupTypeIndices()[groupCounter];
				atomsPerChain[i] += structure.getNumAtomsInGroup(groupIndex);
				bondsPerChain[i] += structure.getGroupBondOrders(groupIndex).length;
			}
		}
	}

	/**
	 * Returns an array that maps a chain index to an entity index.
	 * @param structureDataInterface
	 * @return
	 */
	private static int[] getChainToEntityIndex(StructureDataInterface structure) {
		int[] entityChainIndex = new int[structure.getNumChains()];

		for (int i = 0; i < structure.getNumEntities(); i++) {
			for (int j: structure.getEntityChainIndexList(i)) {
				entityChainIndex[j] = i;
			}
		}
		return entityChainIndex;
	}
}