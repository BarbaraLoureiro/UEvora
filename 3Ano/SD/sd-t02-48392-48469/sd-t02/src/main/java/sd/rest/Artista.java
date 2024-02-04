package sd.rest;

import javax.xml.bind.annotation.*;

/**
 *
 * @author barbara
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "artista")
public class Artista {
    
    @XmlElement(required = false)
    protected String aid;
    
    @XmlElement(required = true)
    protected String nome;
    
    @XmlElement(required = true)
    protected String tipo;
    
    @XmlElement(required = true)
    protected String estado;

    @XmlElement(required = false)
    protected String latitude;

    @XmlElement(required = false)
    protected String longitude;
    
    public Artista(){
        this.nome="";
        this.tipo="";
        this.estado="";
    }
    
    public Artista(String nome, String tipo, String estado){
        this.nome=nome;
        this.tipo=tipo;
        this.estado=estado;
    }
    
    public Artista(String aid, String nome, String tipo, String estado){
        this.aid=aid;
        this.nome=nome;
        this.tipo=tipo;
        this.estado=estado;
    }

    public Artista(String aid, String nome, String tipo, String latitude, String longitude){
        this.nome=nome;
        this.tipo=tipo;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    
    public String getAid(){
        return aid;
    }
    
    public void setAid(String aid){
        this.aid=aid;
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome=nome;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void setTipo(String tipo){
        this.tipo=tipo;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public void setEstado(String estado){
        this.estado=estado;
    }   
    
    public String getLatitude(){
        return latitude;
    }
    
    public void setLatitude(String latitude){
        this.latitude=latitude;
    }
    
    public String getLongitude(){
        return longitude;
    }
    
    public void setLongitude(String longitude){
        this.longitude=longitude;
    }

}
