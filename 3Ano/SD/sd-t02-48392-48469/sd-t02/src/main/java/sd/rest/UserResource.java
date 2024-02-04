package sd.rest;

import jakarta.ws.rs.*;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author barbara
 */

@Path(value = "/api")
public class UserResource {
    private DbOperations dbOperations;

    public UserResource() {
        this.dbOperations = new DbOperations();
    }

    // login user  
    @Path("/loginUser")
    @GET
    @Produces({ "application/json" })
    public String loginUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        if (this.dbOperations.authenticateUser(username, password)) {
            return "Login aceite";
        } else {
            return "Inválido username ou password xisdÊ";
        }
    }

    // login admin  
    @Path("/loginAdmin")
    @GET
    @Produces({ "application/json" })
    public String loginAdmin(@QueryParam("username") String username, @QueryParam("password") String password) {
        if (this.dbOperations.authenticateAdmin(username, password)) {
            return "Login aceite";
        } else {
            return "Inválido username ou password lol";
        }
    }

    // criar novo user
    @Path("/user")
    @POST
    @Consumes({ "application/json" })
    public synchronized boolean criarUser(User u) throws SQLException {
        System.out.println("Registando um novo user");

        return this.dbOperations.registarUser(u);
    }

    // listar users role 'USER'
    @Path("/roleuser")
    @GET
    @Produces({"application/json"})
    public synchronized List<User> listarUsersRoleUser() throws SQLException{
        System.out.println("Listando users");
        
        return this.dbOperations.listarUsers("USER");
    }
    
    // listar users role 'ADMIN'
    @Path("/roleadmin")
    @GET
    @Produces({"application/json"})
    public synchronized List<User> listarUsersRoleAdmin() throws SQLException{
        System.out.println("Listando admins");
        
        return this.dbOperations.listarUsers("ADMIN");
    }

    // atribuir admin
    @Path("/atribuiradmin")
    @GET
    @Consumes({"application/json"})
    public synchronized boolean atribuirAdmin(@QueryParam("uid") String uid) throws SQLException{
        System.out.println("Aprovando o artista");
                
        return this.dbOperations.atribuirAdmin(uid);
    }
}
