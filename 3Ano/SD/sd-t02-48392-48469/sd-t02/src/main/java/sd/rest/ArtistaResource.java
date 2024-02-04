package sd.rest;

import jakarta.ws.rs.*;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author barbara
 */

@Path(value = "/api")
public class ArtistaResource {
    private DbOperations dbOperations;
    
    public ArtistaResource(){
        this.dbOperations=new DbOperations();
    }
    
    // criar novo artista
    @Path("/artista")
    @POST
    @Consumes({"application/json"})
    public synchronized boolean criarArtista(Artista a) throws SQLException{
        System.out.println("Registando um novo artista");
        
        return this.dbOperations.registarArtista(a);
    }

    // listar todos os artistas 
    @Path("/artistas")
    @GET
    @Produces({"application/json"})
    public synchronized List<Artista> listarArtistas() throws SQLException{
        System.out.println("Listando todos os artistas");
        
        return this.dbOperations.listarArtistas();
    }
    
    // listar artistas aprovados
    @Path("/artistasaprovados")
    @GET
    @Produces({"application/json"})
    public synchronized List<Artista> listarArtistasAprovados() throws SQLException{
        System.out.println("Listando artistas aprovados");
        
        return this.dbOperations.listarArtistasPorEstado("aprovado");
    }
    
    // listar artistas desaprovados
    @Path("/artistasdesaprovados")
    @GET
    @Produces({"application/json"})
    public synchronized List<Artista> listarArtistasDesaprovados() throws SQLException{
        System.out.println("Listando artistas desaprovados");
        
        return this.dbOperations.listarArtistasPorEstado("desaprovado");
    }

    // aprovar artista
    @Path("/aprovarartista")
    @GET
    @Consumes({"application/json"})
    public synchronized boolean aprovarArtista(@QueryParam("aid") String aid) throws SQLException{
        System.out.println("Aprovando o artista");
                
        return this.dbOperations.aprovarArtista(aid);
    }
    
    // procurar artistas
    @Path("procuraartistas")
    @GET
    @Produces({"application/json"})
    public synchronized List<Artista> procurarArtistas(@QueryParam("tipo") String tipo,@QueryParam("latitude") String latitude,@QueryParam("longitude") String longitude) throws SQLException{
        System.out.println("Procurando artistas por filtros");

        return this.dbOperations.procurarArtistas(tipo, latitude, longitude);
    }

    // alterar artista
    @Path("/alterarartista")
    @POST
    @Consumes({"application/json"})
    public synchronized boolean alterarArtista(Artista a) throws SQLException{
        System.out.println("Alterando o artista");
        
        return this.dbOperations.alterarArtista(a);
    }
}
