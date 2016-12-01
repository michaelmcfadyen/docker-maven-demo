package sky.uk.resource;

import org.springframework.stereotype.Component;
import sky.uk.data.Person;
import sky.uk.data.PersonDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/")
public class DemoResource {

    private final PersonDao personDao;

    @Inject
    public DemoResource(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GET
    public String status(){
        return "OK";
    }

    @GET
    @Path("/person/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person get(@PathParam("id") int id){
        return personDao.get(id);
    }

    @POST
    @Path("/person")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Person person){
        personDao.insert(person);
        return Response.noContent().build();
    }
}
