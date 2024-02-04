package project;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;


public class Cliente_gestao implements java.rmi.Remote {
    
    
      public static void main(String args[]) {
        Scanner s=new Scanner(System.in);
        String regHost = "";
	    String regPort = "";
        
        try (InputStream input = new FileInputStream("recursos/configs.properties")) {

            Properties prop = new Properties();

            // loading properties file
            prop.load(input);
            regHost = prop.getProperty("regHost");
            regPort = prop.getProperty("regPort");

        } catch (Exception e) {
            e.printStackTrace();
        }

	try {
	    // remote object
	    Metodos obj =(Metodos) java.rmi.Naming.lookup("rmi://" 
                      + regHost +":"+regPort +"/metodos");
	
        // connected successfully 
        System.out.println("BEM VINDO:");
        
        while(true){
            String r="";
            int c=0;
            
            //Menu
            System.out.print("\nMENU:\n");
            System.out.println("1-Listar artistas");
            System.out.println("2-Listar artistas aprovados");
            System.out.println("3-Listar artistas não aprovados");
            System.out.println("4-Aprovar artista");
            System.out.println("5-SAIR\n");
            System.out.print("INDIQUE UTILIZANDO O RESPETIVO NUMERO A OPERAÇÃO A REALIZAR: ");
            c=s.nextInt();
            System.out.println();
            
            switch(c){
                
                //listar artistas
                case 1:
                    System.out.println();
                    r=obj.list_artist();
                    System.out.println("Artista: "+r);
                break;

                //listar artistas aprovados
                case 2:
                    System.out.println();
                    r=obj.list_artistAprovado();
                    System.out.println("Artista: "+r);
                    break;

                //listar artistas não aprovados
                case 3:
                    System.out.println();
                    r=obj.list_artistNaoAprovado();
                    System.out.println("Artista: "+r);
                    break;

                //aprovar artista por artistID
                case 4:
                    int id;
                    System.out.println("Introduza artistID do Artista: ");
                    id = s.nextInt();
                    System.out.println();
                    if (obj.verificaArtista(id)) {
                        r=obj.aprovarArtista(id);
                        System.out.println("Artista: "+r);
                    } else {
                        System.out.println("O ID do artista introduzido é inválido.");
                    }
                    break;

                    //sair
                case 5:
                    System.out.println("ADEUS");
                    System.exit(0);
                default:
                    System.out.println("Comando inválido");
                break;
                    
            }
        }
    
        }catch(Exception e){
            e.printStackTrace();
        }
      }
}
