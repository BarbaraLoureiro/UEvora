package project;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class MetodosImp extends UnicastRemoteObject implements Metodos {

    // deve existir um construtor
    public MetodosImp() throws java.rmi.RemoteException {
        super();
    }

    Scanner s = new Scanner(System.in);

    public String registo(String nome,String tipoArte,String localizacao,String atuar) throws java.rmi.RemoteException, Exception {

        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Obter ultimo artistID
        int artistID = 0;

        try {
            ResultSet rs = stmt.executeQuery("SELECT MAX(artistID) FROM artistas");
            while (rs.next()) {
                artistID = rs.getInt("max");
            }
            rs.close(); // muito importante depois da consulta!
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problems retrieving data from db...");
        }

        var estado = "false";
        artistID++;
        //Inserir artista na bd
        try {
            stmt.executeUpdate("insert into artistas values('" + artistID + "','" + nome + "','" + tipoArte + "','" + localizacao + "','" + atuar + "','" + estado + "')");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas em inserir informação na base de dados...");
        }
        pc.disconnect();

        //Retornar id do artista
        String id_artist = "ArtistID: " + Integer.toString(artistID);

        return "Artista registado, " + id_artist;
    }


    public String list_artist() throws java.rmi.RemoteException, Exception {

        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Guardar resultados
        String res = "";

        try {

            ResultSet rs = stmt.executeQuery("SELECT * from artistas ORDER BY artistID" );

            while (rs.next()) {
                int artistID = rs.getInt("artistID");
                String nome = rs.getString("nome");
                String tipoArte = rs.getString("tipoArte");
                String localizacao = rs.getString("localizacao");
                String atuar = rs.getString("atuar");
                res += "\nartistID: " + artistID + " nome: " + nome + " tipoArte: " + tipoArte  + " localizacao: " + localizacao + " atuar: " + atuar;

            }

            rs.close(); // muito importante depois da consulta!
        } catch (SQLException e) {
            System.err.println("Problemas em receber informação da base de dados...");
            e.printStackTrace();
        }

        pc.disconnect();

        return res;
    }

    public String list_artistAprovado() throws java.rmi.RemoteException, Exception {

        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Guardar resultados
        String res = "";

        try {

            ResultSet rs = stmt.executeQuery("SELECT * from artistas WHERE estado='true'" );

            while (rs.next()) {
                int artistID = rs.getInt("artistID");
                String nome = rs.getString("nome");
                String tipoArte = rs.getString("tipoArte");
                String localizacao = rs.getString("localizacao");
                String atuar = rs.getString("atuar");
                res += "\nartistID: " + artistID + " nome: " + nome + " tipoArte: " + tipoArte  + " localizacao: " + localizacao + " atuar: " + atuar;

            }

            rs.close(); // muito importante depois da consulta!
        } catch (SQLException e) {
            System.err.println("Problemas em receber informação da base de dados...");
            e.printStackTrace();
        }

        pc.disconnect();

        return res;
    }

    public String list_artistNaoAprovado() throws java.rmi.RemoteException, Exception {

        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Guardar resultados
        String res = "";

        try {

            ResultSet rs = stmt.executeQuery("SELECT * from artistas WHERE estado='false'" );

            while (rs.next()) {
                int artistID = rs.getInt("artistID");
                String nome = rs.getString("nome");
                String tipoArte = rs.getString("tipoArte");
                String localizacao = rs.getString("localizacao");
                String atuar = rs.getString("atuar");
                res += "\nartistID: " + artistID + " nome: " + nome + " tipoArte: " + tipoArte  + " localizacao: " + localizacao + " atuar: " + atuar;

            }

            rs.close(); // muito importante depois da consulta!
        } catch (SQLException e) {
            System.err.println("Problemas em receber informação da base de dados...");
            e.printStackTrace();
        }

        pc.disconnect();

        return res;
    }

    public String aprovarArtista(int id) throws java.rmi.RemoteException, Exception{
        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        try {
        ResultSet rs = stmt.executeQuery("UPDATE artistas SET estado='true' WHERE artistID='"+id+"'");
        rs.close(); // muito importante depois da consulta!
        }
        catch (SQLException e) {
            System.err.println("Problemas em inserir informação na base de dados...");
        }

        return "Artista aprovado!";
    }

    public String list_artistLocalizacao(String local) throws java.rmi.RemoteException, Exception {

        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Guardar resultados
        String res = "";

        try {

            ResultSet rs = stmt.executeQuery("SELECT * from artistas WHERE localizacao='"+local+"'" );

            while (rs.next()) {
                int artistID = rs.getInt("artistID");
                String nome = rs.getString("nome");
                String tipoArte = rs.getString("tipoArte");
                String localizacao = rs.getString("localizacao");
                String atuar = rs.getString("atuar");
                res += "\nartistID: " + artistID + " nome: " + nome + " tipoArte: " + tipoArte  + " localizacao: " + localizacao + " atuar: " + atuar;

            }

            rs.close(); // muito importante depois da consulta!
        } catch (SQLException e) {
            System.err.println("Problemas em receber informação da base de dados...");
            e.printStackTrace();
        }

        pc.disconnect();

        return res;
    }

    public String list_artistArte(String arte) throws java.rmi.RemoteException, Exception {

        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Guardar resultados
        String res = "";

        try {

            ResultSet rs = stmt.executeQuery("SELECT * from artistas WHERE tipoArte='"+arte+"'" );

            while (rs.next()) {
                int artistID = rs.getInt("artistID");
                String nome = rs.getString("nome");
                String tipoArte = rs.getString("tipoArte");
                String localizacao = rs.getString("localizacao");
                String atuar = rs.getString("atuar");
                res += "\nartistID: " + artistID + " nome: " + nome + " tipoArte: " + tipoArte  + " localizacao: " + localizacao + " atuar: " + atuar;

            }

            rs.close(); // muito importante depois da consulta!
        } catch (SQLException e) {
            System.err.println("Problemas em receber informação da base de dados...");
            e.printStackTrace();
        }

        pc.disconnect();

        return res;
    }

    public String list_locaisAtuacoes() throws java.rmi.RemoteException, Exception {

        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Guardar resultados
        String res = "";

        try {

            ResultSet rs = stmt.executeQuery("SELECT DISTINCT localizacao from artistas WHERE atuar='"+true+"' " );

            while (rs.next()) {
                String localizacao = rs.getString("localizacao");
                res += "\n localizacao: " + localizacao ;

            }

            rs.close(); // muito importante depois da consulta!
        } catch (SQLException e) {
            System.err.println("Problemas em receber informação da base de dados...");
            e.printStackTrace();
        }

        pc.disconnect();

        return res;
    }

    public String listPerformances(int id) throws java.rmi.RemoteException, Exception {
        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Guardar resultados
        String res = "";

        try {
            ResultSet rs = stmt.executeQuery("SELECT data, localizacao FROM performances WHERE artistID = '"+ id+"'");

            while (rs.next()) {
                String data = rs.getString("data");
                String localizacao = rs.getString("localizacao");
                res += "\nData: " + data + " Localização: " + localizacao;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas em receber informação da base de dados...");
        }

        pc.disconnect();

        return res;
    }

    public void enviarDonativo(int id1, double valor) throws java.rmi.RemoteException, Exception {
        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        try {
            stmt.executeUpdate("INSERT INTO donativos (artistid, valor, data) VALUES (" + id1 + ", " + valor + ", CURRENT_DATE)");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas em inserir informação na base de dados...");
        }

        pc.disconnect();
    }

    public String listarDonativos(int id2) throws java.rmi.RemoteException, Exception {
        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        //Guardar resultados
        String res = "";

        try {
            ResultSet rs = stmt.executeQuery("SELECT donativoid, valor, data FROM donativos WHERE artistid = " + id2);

            while (rs.next()) {
                int donativoid = rs.getInt("donativoid");
                double valor = rs.getDouble("valor");
                Date data = rs.getDate("data");
                res += "\nDonativo ID: " + donativoid + " Valor: " + valor + " Data: " + data;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas em receber informação da base de dados...");
        }

        pc.disconnect();

        return res;
    }

    public boolean verificaArtista(int idcheck) throws java.rmi.RemoteException, Exception {

        String host = "", name = "", user = "", password = "";

        //Obter properties para ligar a bd
        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Criar instancia de PostgresConnector
        PostgresConnector pc = new PostgresConnector(host, name, user, password);

        // estabelecer a ligacao à bd
        pc.connect();
        Statement stmt = pc.getStatement();

        boolean isValid = false;

        //Verificar se o id do artista existe
        try {
            ResultSet rs = stmt.executeQuery("SELECT artistid FROM artistas WHERE artistid = " + idcheck);
            if (rs.next()) {
                isValid = true;
            }
            rs.close(); // muito importante depois da consulta!
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problemas em receber informação da base de dados...");
        }

        pc.disconnect();

        return isValid;
    }
}
