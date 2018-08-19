package ua.epam.spring.hometask.web.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ua.epam.spring.hometask.service.UserService;

@Endpoint
public class UserEndPoint {

    public static final String NAMESPACE_URI = "http://movie/soap-service";

    @Autowired
    private UserService userService;

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
//    @ResponsePayload
//    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest request){
//        GetUserByIdResponse getUserByIdResponse = new GetUserByIdResponse();
//        response
//    }



}
