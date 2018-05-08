package edu.unq.desapp.grupo_a.backend.webservice;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import edu.unq.desapp.grupo_a.backend.model.PersistenceEntity;
import edu.unq.desapp.grupo_a.backend.service.GenericService;
import edu.unq.desapp.grupo_a.backend.utils.ResponseGenerator;

public abstract class GenericRest<T extends PersistenceEntity> {

	@Autowired
	private ResponseGenerator responseGenerator;

	public abstract GenericService<T> getService();

	public Response find(Long id) {
		try {
			return responseGenerator.buildSuccessResponse(this.getService().find(id));
		} catch (Exception e) {
			return responseGenerator.buildErrorResponse(e);
		}
	}

	public Response findAll() {
		try {
			return responseGenerator.buildSuccessResponse(this.getService().findAll());
		} catch (Exception e) {
			return responseGenerator.buildErrorResponse(e);
		}
	}

	public Response create(T entity) {
		try {
			return responseGenerator.buildSuccessResponse(this.getService().save(entity));
		} catch (Exception e) {
			return responseGenerator.buildErrorResponse(e);
		}
	}

	public Response update(T entity) {
		try {
			return responseGenerator.buildSuccessResponse(this.getService().update(entity));
		} catch (Exception e) {
			return responseGenerator.buildErrorResponse(e);
		}
	}

	public Response delete(Long id) {
		try {
			this.getService().delete(id);
			return responseGenerator.buildResponse(Status.OK);
		} catch (Exception e) {
			return responseGenerator.buildErrorResponse(e);
		}
	}

	public ResponseGenerator getResponseGenerator() {
		return responseGenerator;
	}

}
