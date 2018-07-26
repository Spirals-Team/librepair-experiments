package com.dnastack.dos.server.service;

import com.dnastack.dos.server.exception.EntityNotFoundException;
import com.dnastack.dos.server.model.DataBundle;
import com.dnastack.dos.server.model.Ga4ghDataBundle;
import com.dnastack.dos.server.repository.Ga4ghDataBundleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class Ga4ghDataBundleService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Ga4ghDataBundleRepository ga4ghDataBundleRepository;

	// Helper - converts list of DataBundle objects to a paginated list
	public Page<DataBundle> paginateList(List<DataBundle> objectList, Pageable pageable)
			throws InvalidParameterException {
		int start = pageable.getOffset();
		int end = (start + pageable.getPageSize()) > objectList.size() ? objectList.size()
				: (start + pageable.getPageSize());
		
		if (!(objectList.isEmpty() && pageable.getPageNumber() == 0)) {
			if (start >= end) {
				throw new InvalidParameterException("Page does not exist, page number is too large.");
			}
		}
		
		return new PageImpl<DataBundle>(objectList.subList(start, end),
				new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()),
				objectList.size());
	}

	// Helper - compare version Strings of the form x.x.x
	public boolean isVersionCorrectForm(String v) {
		return v.matches("\\d+|\\d+(\\.\\d+)|\\d+(\\.\\d+(\\.\\d+))");
	}

	public boolean isFirstVersionGreaterOrEqual(String v1, String v2) throws InvalidParameterException {

		if (!isVersionCorrectForm(v1) || !isVersionCorrectForm(v2)) {
			throw new InvalidParameterException("Invalid version form. Version numbers must be of the form x.x.x");
		}

		String[] v1Parts = v1.split("\\.");
		String[] v2Parts = v2.split("\\.");

		int length = Math.max(v1Parts.length, v2Parts.length);
		for (int i = 0; i < length; i++) {
			int v1Part = i < v1Parts.length ? Integer.parseInt(v1Parts[i]) : 0;
			int v2Part = i < v2Parts.length ? Integer.parseInt(v2Parts[i]) : 0;

			if (v1Part < v2Part)
				return false;
			if (v1Part > v2Part)
				return true;
		}
		return true;
	}

	// GET Specific Object
	public Ga4ghDataBundle getObjectByIdAndVersion(String id, String version) throws EntityNotFoundException {
		Ga4ghDataBundle ga4gh = ga4ghDataBundleRepository.findByIdAndVersion(id, version);
		if (ga4gh == null) {
			throw new EntityNotFoundException(Ga4ghDataBundle.class, "id and version", id + 'v' + version);
		}
		return ga4gh;
	}

	public Ga4ghDataBundle getObjectByIdWithHighestVersion(String id) throws EntityNotFoundException {
		List<Ga4ghDataBundle> objects = new ArrayList<>();
		ga4ghDataBundleRepository.findByIdEquals(id).forEach(o -> {
			if (o.isHighest() == true) {
				objects.add(o);
			}
		});
		if (objects.isEmpty()) {
			throw new EntityNotFoundException(Ga4ghDataBundle.class, "id", id);
		}
		return objects.get(0);
	}

	// GET List of Objects by some criteria
	public Page<DataBundle> getObjectByIdAndAllVersions(String id, Pageable pageable)
			throws EntityNotFoundException, Exception {
		List<DataBundle> objects = new ArrayList<>();
		ga4ghDataBundleRepository.findByIdEquals(id).forEach(o -> objects.add(new DataBundle(o)));
		if (objects.isEmpty()) {
			throw new EntityNotFoundException(Ga4ghDataBundle.class, "id", id);
		}
		return paginateList(objects, pageable);
	}

	public Page<DataBundle> getAllObjectsWithHighestVersions(Pageable pageable) throws Exception {
		List<DataBundle> objects = new ArrayList<>();
		ga4ghDataBundleRepository.findAll().forEach(o -> {
			if (o.isHighest() == true) {
				objects.add(new DataBundle(o));
			}
		});
		return paginateList(objects, pageable);
	}

	public Page<DataBundle> getAllObjectsAndAllVersions(Pageable pageable) throws Exception {
		List<DataBundle> objects = new ArrayList<>();
		ga4ghDataBundleRepository.findAll().forEach(o -> objects.add(new DataBundle(o)));
		return paginateList(objects, pageable);
	}

	public List<Ga4ghDataBundle> getAllObjectsAndAllVersionsRaw() {
		List<Ga4ghDataBundle> objects = new ArrayList<>();
		ga4ghDataBundleRepository.findAll().forEach(o -> objects.add(o));
		return objects;
	}

	public Page<DataBundle> getObjectsByAlias(String alias, Pageable pageable) throws Exception {
		List<DataBundle> objects = new ArrayList<>();
		ga4ghDataBundleRepository.findAll().forEach(o -> {
			if (o.getAliases().contains(alias)) {
				objects.add(new DataBundle(o));
			}
		});
		return paginateList(objects, pageable);
	}

	public Page<DataBundle> getObjectsByAliasWithHighestVersion(String alias, Pageable pageable) throws Exception {
		List<DataBundle> objects = new ArrayList<>();
		ga4ghDataBundleRepository.findAll().forEach(o -> {
			if (o.isHighest() && o.getAliases().contains(alias)) {
				objects.add(new DataBundle(o));
			}
		});
		return paginateList(objects, pageable);
	}

	// POST
	public void addObject(Ga4ghDataBundle object) throws Exception {
		List<Ga4ghDataBundle> ga4ghList = ga4ghDataBundleRepository.findByIdEquals(object.getId());
		if (!ga4ghList.isEmpty()) {
			throw new Exception(
					"Data Bundle with that id already exists in the database. Use a PUT Request in order to update.");
		}
		if (!isVersionCorrectForm(object.getVersion())) {
			throw new InvalidParameterException("Invalid version form. Version numbers must be of the form x.x.x");
		}
		ga4ghDataBundleRepository.save(object);
	}

	// PUT
	public void updateObject(String data_bundle_id, Ga4ghDataBundle object) throws EntityNotFoundException, Exception {
		// TODO can turn findByIdEquals into a boolean function, might make things cleaner
		List<Ga4ghDataBundle> objects_DataBundleId = ga4ghDataBundleRepository.findByIdEquals(data_bundle_id);
		if (objects_DataBundleId.isEmpty()) {
			throw new EntityNotFoundException(Ga4ghDataBundle.class, "data_bundle_id", data_bundle_id);
		}

		if (!data_bundle_id.equals(object.getId())) {
			List<Ga4ghDataBundle> objects_Id = ga4ghDataBundleRepository.findByIdEquals(object.getId());
			if (!objects_Id.isEmpty()) {
				throw new Exception(
						"Data Bundle with that id already exists in the database. Overriding this Data Bundle's id would conflict with the id of another Data Bundle.");
			}

			// Updating id of all versions of the data bundle
			objects_DataBundleId.forEach(o -> ga4ghDataBundleRepository.save(new Ga4ghDataBundle(o.isHighest(), object.getId(),
					new ArrayList<>(o.getData_object_ids()), o.getCreated(), o.getUpdated(), o.getVersion(),
					new ArrayList<>(o.getChecksums()), o.getDescription(), new ArrayList<>(o.getAliases()),
					new HashMap<>(o.getSystem_metadata()), new HashMap<>(o.getUser_metadata()))));
			
			// Deleting objects with old id
			deleteObject(data_bundle_id);
		}
		
		// Updating 'highest' variable
		Ga4ghDataBundle ga4ghHighest = ga4ghDataBundleRepository.findByIdAndHighest(object.getId(), true);

		// Comparing version numbers and setting correct object to highest version
		if (isFirstVersionGreaterOrEqual(object.getVersion(), ga4ghHighest.getVersion())) {
			ga4ghHighest.setHighest(false);
		} else {
			object.setHighest(false);
		}
		
		// Updating previously (and possibly still currently) highest version object
		ga4ghDataBundleRepository.save(ga4ghHighest);

		// Saving new object
		ga4ghDataBundleRepository.save(object);

	}

	// DELETE
	public void deleteAllObjects() {
		ga4ghDataBundleRepository.deleteAll();
	}

	public void deleteObject(String id) throws EntityNotFoundException {
		List<Ga4ghDataBundle> objects = ga4ghDataBundleRepository.findByIdEquals(id);
		if (objects.isEmpty()) {
			throw new EntityNotFoundException(Ga4ghDataBundle.class, "id", id);
		}
		objects.forEach(o -> ga4ghDataBundleRepository.delete(o.getId() + 'v' + o.getVersion()));
	}

}
