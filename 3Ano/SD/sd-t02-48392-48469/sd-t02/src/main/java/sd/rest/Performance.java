package sd.rest;

import javax.xml.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author barbara
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "performance")
public class Performance {
    
    @XmlElement(required = true)
    protected int aid;
    
    @XmlElement(required = true)
    protected String pdata;
    @XmlElement(required = true)
    protected String latitude;

    @XmlElement(required = true)
    protected String longitude;

    @XmlElement(required = false)
    protected String nome;

    @XmlElement(required = false)
    protected String tipo;
    
    public Performance(){
        this.aid=0;
        this.pdata="";
        this.latitude="";
        this.longitude="";
    }
    
    public Performance(int aid, String pdata, String latitude, String longitude){
        this.aid=aid;
        this.pdata=pdata;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public Performance(String nome, String tipo, String latitude, String longitude){
        this.nome=nome;
        this.tipo=tipo;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public int getAid(){
        return aid;
    }

    public void setAid(int aid){
        this.aid=aid;
    }

    public void setPdata(String pdata) {
        this.pdata = pdata;
    }

    public String getPdata() {
        return pdata;
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
    
}
