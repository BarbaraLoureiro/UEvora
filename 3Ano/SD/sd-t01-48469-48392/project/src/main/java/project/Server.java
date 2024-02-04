package project;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author b4rbara
 */
public class Server {
    /*
    private static String host;
    private static String name;
    private static String user;
    private static String password;*/
    
    public static void main(String args[]) {
        
        
	int regPort= 1099; // default RMIRegistry port

        try (InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();
            // loading properties file
            prop.load(input);

           /* // buscar cada linha do config
            host = prop.getProperty("host");
            name = prop.getProperty("name");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
            System.out.println(host + " " + name + " " + user);*/

            regPort = Integer.parseInt(prop.getProperty("regPortServer"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /*PostgresConnector basedados = new PostgresConnector(host, name, user, password);*/
	
	try {

	    // remote object creation
	    Metodos obj= new MetodosImp();

	    java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);

	    registry.rebind("metodos", obj);

            // server is working and expecting connections
            System.out.println("Servidor Ok");
            
            //basedados.connect();
	} 
	catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
}
