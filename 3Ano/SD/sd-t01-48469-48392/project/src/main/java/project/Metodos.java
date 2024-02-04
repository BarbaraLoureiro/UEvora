package project;

import java.util.ArrayList;


public interface Metodos extends java.rmi.Remote {
    public String registo(String nome,String tipoArte,String localizacao,String estaAatuar) throws  java.rmi.RemoteException, Exception;
    public String list_artist() throws java.rmi.RemoteException, Exception;

    public String list_artistAprovado() throws java.rmi.RemoteException, Exception;
    public String list_artistNaoAprovado() throws java.rmi.RemoteException, Exception;

    public String aprovarArtista(int id) throws java.rmi.RemoteException, Exception;

    public String list_artistLocalizacao(String local) throws java.rmi.RemoteException, Exception;

    public String list_artistArte(String arte) throws java.rmi.RemoteException, Exception;

    public String list_locaisAtuacoes() throws java.rmi.RemoteException, Exception;

    public String listPerformances(int id) throws java.rmi.RemoteException, Exception;

    public void enviarDonativo(int id, double valor) throws java.rmi.RemoteException, Exception;

    public String listarDonativos(int artistID) throws java.rmi.RemoteException, Exception;

    public boolean verificaArtista(int idcheck) throws java.rmi.RemoteException, Exception;


    }
