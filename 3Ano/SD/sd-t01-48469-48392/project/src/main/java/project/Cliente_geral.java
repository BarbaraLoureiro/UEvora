package project;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;


public class Cliente_geral implements java.rmi.Remote {

    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        String regHost = "";
        String regPort = "";

        try ( InputStream input = new FileInputStream("recursos/configs.properties")) {

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
            Metodos obj = (Metodos) java.rmi.Naming.lookup("rmi://"
                    + regHost + ":" + regPort + "/metodos");

            // connected successfully 
            System.out.println("BEM VINDO:");

            while (true) {
                String r = "";
                int c = 0;

                //Menu
                System.out.print("\nMENU:\n");
                System.out.println("1-Registar novo artista");
                System.out.println("2-Listar artistas");
                System.out.println("3-Listar artistas por localização");
                System.out.println("4-Listar artistas por arte");
                System.out.println("5-Listar locais de atuações atuais");
                System.out.println("6-Listar locais e datas de atuações passadas por artista");
                System.out.println("7-Fazer donativo a artista");
                System.out.println("8-Listar donativos por artista");
                System.out.println("9- SAIR\n");
                System.out.print("INDIQUE UTILIZANDO O RESPETIVO NUMERO A OPERAÇÃO A REALIZAR: ");
                c = s.nextInt();
                System.out.println();

                switch (c) {
                    //registo artista
                    case 1:
                        String nome,
                         tipoArte,
                         localizacao,
                         atuar;
                        
                        System.out.println("DETALHES DO ARTISTA: ");
                        System.out.print("Nome: ");
                        nome = s.next();
                        System.out.print("Tipo de arte: ");
                        tipoArte = s.next();
                        System.out.print("Localizacão: ");
                        localizacao = s.next();
                        System.out.print("Está a atuar? (true/false): ");
                        atuar = s.next();

                        r = obj.registo(nome, tipoArte, localizacao, atuar);
                        System.out.println(r + "\n");
                        break;

                    //listar artistas
                    case 2:
                        System.out.println();
                        r = obj.list_artist();
                        System.out.println("Artista: " + r);
                        break;

                    //listar artistas por localização
                    case 3:
                        System.out.print("Localizacao: ");
                        String local = s.next();
                        System.out.println();
                        r = obj.list_artistLocalizacao(local);
                        System.out.println("Artista: " + r);
                        break;

                    //listar artistas por arte
                    case 4:
                        System.out.print("Tipo de Arte: ");
                        String arte = s.next();
                        System.out.println();
                        r = obj.list_artistArte(arte);
                        System.out.println("Artista: " + r);
                        break;

                    //listar locais de atuações atuais
                    case 5:
                        r = obj.list_locaisAtuacoes();
                        System.out.println("Locais de atuações:" +r);
                        break;

                    //listar locais de atuações
                    case 6:
                        System.out.print("Introduza id artista: ");
                        int id = s.nextInt();
                        System.out.println();

                        if (obj.verificaArtista(id)) {
                            r = obj.listPerformances(id);
                            System.out.println("Atuações passadas: " + r);
                        } else {
                            System.out.println("O ID do artista introduzido é inválido.");
                        }
                        break;

                    //fazer donativo
                    case 7:
                        System.out.print("Introduza id artista: ");
                        int id1 = s.nextInt();
                        if (obj.verificaArtista(id1)) {
                            System.out.print("Introduza valor do donativo(euros): ");
                            double valor= s.nextDouble();
                            System.out.println();
                            obj.enviarDonativo(id1,valor);
                            System.out.println("Donativo recebido com sucesso!");
                        } else {
                            System.out.println("O ID do artista introduzido é inválido.");
                        }
                        break;

                    //listar donativos do artista
                    case 8:
                        System.out.print("Introduza id artista: ");
                        int id2 = s.nextInt();
                        System.out.println();
                        if (obj.verificaArtista(id2)) {
                            r = obj.listarDonativos(id2);
                            System.out.println("Donativos: " + r);
                        } else {
                            System.out.println("O ID do artista introduzido é inválido.");
                        }

                        break;

                    //sair
                    case 9:
                        System.out.println("ADEUS");
                        System.exit(0);
                    default:
                        System.out.println("Comando inválido");
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
