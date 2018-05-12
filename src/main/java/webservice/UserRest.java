package webservice;

import model.User;
import service.UserService;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


/**
 * Created by mariano on 05/05/18.
 */

@Path("/user")
public class UserRest {

    public UserService getService() {
        return service;
    }

    public void setService(UserService service) {
        this.service = service;
    }

    UserService service;


    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findUser(@PathParam("id") final String idUser) {
        User user = service.findById(idUser);
        return Response.ok().build();
    }
//
////    @GET
////    @Path("/byAuthor/{id}")
////    @Produces("application/json")
////    public Response findPostsPublishedByAuthorId(@PathParam("id") final String id) {
////        List<Post> posts = postRepository.getPosts(id);
////        if (posts.isEmpty()) {
////            return Response.status(Response.Status.NOT_FOUND).build();
////        }
////        return Response.ok(posts).build();
////    }
////
////    @GET
////    @Path("/count")
////    @Produces("application/json")
////    public Integer countPostsPublishedByBlogId(@DefaultValue(StringUtils.EMPTY) @QueryParam("tag") final String tag) {
////        return postRepository.getcount(tag);
////    }
//
//
}
