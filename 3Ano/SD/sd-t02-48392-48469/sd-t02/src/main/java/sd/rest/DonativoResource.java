package sd.rest;

import java.util.List;
import jakarta.ws.rs.*;

/**
 *
 * @author barbara
 */

@Path(value = "/api")
public class DonativoResource {
    private DbOperations dbOperations;

    public DonativoResource() {
        this.dbOperations = new DbOperations();
    }

    // enviar donativo a artista
    @Path("/donativo")
    @POST
    @Consumes({"application/json"})
    public synchronized boolean registoDonativo(Donativo d) throws Exception{
        System.out.println("Registando nova performance de artista");
        
        return this.dbOperations.registoDonativo(d);
    }

    // listar donativos por artista
    @Path("/listdonativos/{aid}")
    @GET
    @Produces({"application/json"})
    public synchronized List<Donativo> listarDonativos(@PathParam("aid") int aid) throws Exception {
        System.out.println("Listando donativos do artista");

        return this.dbOperations.listarDonativos(aid);
    }

}
