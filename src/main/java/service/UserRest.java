package service;

import model.User;
import persistence.repositories.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/service")
public class UserRest{

    private UserRepository userRepository;

    @GET
    @Path("/byCuil/{cuil}")
    @Produces("application/json")
    public List<User> findUserByCUIL(@PathParam("cuil") final String cuil){
        return this.userRepository.filterUser(cuil);
    }

}
