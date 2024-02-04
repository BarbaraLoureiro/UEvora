package sd.rest;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author barbara
 */

public class Donativo {
    @XmlElement(required = true)
    protected int aid;
    
    @XmlElement(required = true)
    protected String valor;
    
    @XmlElement(required = true)
    protected String ddata;
    
    public Donativo(){
        this.aid=0;
        this.valor="";
        this.ddata="";
    }
    
    public Donativo(String valor, String ddata){
        this.valor="";
        this.ddata="";
    }
    
    public int getAid(){
        return aid;
    }

    public void setAid(int aid){
        this.aid=aid;
    }

    public String getValor(){
        return valor;
    }
    
    public void setValor(String valor){
        this.valor=valor;
    }
    
    public String getDdata(){
        return ddata;
    }
    
    public void setDdata(String ddata){
        this.ddata=ddata;
    }
   
}