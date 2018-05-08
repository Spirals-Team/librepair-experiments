package edu.unq.desapp.grupo_a.backend.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class ResponseGenerator  {

	private static final Logger LOGGER = Logger.getLogger("edu.unq.desapp.grupo_a.backend.error");
	
	public Response responseOK(String message){
		return buildResponse(new Message(message), Status.OK);
	}

	public Response responseBadRequest(String message){
		return buildResponse(new Message(message), Status.BAD_REQUEST);
	}
	
	public <T> Response buildSuccessResponse(T entity) {
		return buildResponse(entity, Status.OK);
	}

	public <T> Response buildResponse(Status status) {
		return Response.status(status)
				.build();
	}
	
	public <T> Response buildResponse(T entity, Status status) {
		return Response.status(status)
				.entity(entity)
				.build();
	}
	
	public Response buildErrorResponse(Exception exception) {
		LOGGER.error("Exception", exception);
		if (exception instanceof RuntimeException) {
			return buildResponse(new Message(exception.getMessage()), Status.BAD_REQUEST);
		}
		return buildResponse(new Message("A problem has ocurred!"), Status.INTERNAL_SERVER_ERROR);
	}

    public Response buildCustomErrorResponse(String mensaje, Status status) {
        return Response
            .status(status)
            .entity(new Message(mensaje))
            .build();
    }
    
    
    

}
