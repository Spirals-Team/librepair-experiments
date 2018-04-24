package service;

import model.User;
import persistence.services.UserService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/services")
public class UserRest{

    private UserService userService;

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public UserService getUserService(){
        return this.userService;
    }

    @GET
    @Path("/byCuil/{cuil}")
    @Produces("application/json")
    public List<User> findUserByCUIL(@PathParam("cuil") final String cuil){
        return this.userService.filterUser(cuil);
    }

}
