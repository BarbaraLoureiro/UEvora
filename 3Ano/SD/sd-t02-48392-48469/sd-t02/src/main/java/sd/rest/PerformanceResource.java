package sd.rest;

import java.util.List;

import jakarta.ws.rs.*;

/**
 *
 * @author barbara
 */

@Path(value = "/api")
public class PerformanceResource {
    private DbOperations dbOperations;
    
    public PerformanceResource(){
        this.dbOperations=new DbOperations();
    }
    
    // criar nova performance
    @Path("/performance")
    @POST
    @Consumes({"application/json"})
    public synchronized boolean registoPerformance(Performance p) throws Exception{
        System.out.println("Registando nova performance de artista");
        
        return this.dbOperations.registoPerformance(p);
    }

    // lista de locais com performances a acontecer
    @Path("/locais")
    @GET
    @Produces({"application/json"})
    public synchronized List<Performance> listarLocalizacoesAatuar() throws Exception{
        System.out.println("Listando locais com peformances a acontecer");
        
        return this.dbOperations.listarLocalizacoesAatuar();
    }

    // Consultar performances passadas por artista
    @Path("/performancespassadas/{aid}")
    @GET
    @Produces({"application/json"})
    public synchronized List<Performance> listarPerformancesPassadas(@PathParam("aid") int aid) throws Exception {
        System.out.println("Listando performances passadas do artista");
                
        return this.dbOperations.listarPerformancesPassadas(aid);
    }

    // Consultar performances futuras por artista
    @Path("/performancesfuturas/{aid}")
    @GET
    @Produces({"application/json"})
    public synchronized List<Performance> listarPerformancesFuturas(@PathParam("aid") int aid) throws Exception {
        System.out.println("Listando performances passadas do artista");
                
        return this.dbOperations.listarPerformancesFuturas(aid);
    }

}
