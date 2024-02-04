package sd.clientes;

import org.json.*;

import sd.rest.Performance;
import sd.rest.PostgresConnector;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.*;
import java.util.*;

/**
 *
 * @author barbara
 */
public class Cliente_geral {

    private static final String URL = "http://localhost:8080/api";

    /**************************************************************************/
    /***************************** USER ***************************************/
    /**************************************************************************/

    public static int menu() {
        int option;

        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("Escolha uma opção:\n" +
                "   1. Registar artista\n" +
                "   2. Listar todos os artistas\n" +
                "   3. Registar performance de artista\n" +
                "   4. Listar localizaçãoes onde estão a atuar artistas\n" +
                "   5. Listar performances passadas de um artista\n" +
                "   6. Próxima performance de um artista\n" +
                "   7. Fazer um donativo a um artista\n" +
                "   8. Listar donativos de um artista\n" +
                "   9. Procurar artistas por localização e/ou arte \n" +
                "   10. Sair");
        try {
            System.out.print("Opção: ");
            option = scan.nextInt();
        } catch (Exception e) {
            option = 11;
        }
        return option;
    }

    public static int AdminMenu() {
        int option;

        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("Escolha uma opção:\n" +
                "   1. Listar artistas aprovados\n" +
                "   2. Listar artistas não aprovados\n" +
                "   3. Aprovar artista por id\n" +
                "   4. Listar users\n" +
                "   5. Listar admins\n" +
                "   6. Atribuir administração a um user por id\n" +
                "   7. Alterar informações de um artista\n" +
                "******* ações comuns a users ************************\n"+
                "   8. Registar artista\n" +
                "   9. Listar todos os artistas\n" +
                "   10. Registar performance de artista\n" +
                "   11. Listar localizaçãoes onde estão a atuar artistas\n" +
                "   12. Listar performances passadas de um artista\n" +
                "   13. Próxima performance de um artista\n" +
                "   14. Fazer um donativo a um artista\n" +
                "   15. Listar donativos de um artista\n" +
                "   16. Procurar artistas por localização e/ou arte \n" +
                "   17. Sair");
        try {
            System.out.print("Opção: ");
            option = scan.nextInt();
        } catch (Exception e) {
            option = 18;
        }
        return option;
    }

    // Autenticar user
    public static boolean authenticateUser(String username, String password) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    URL + "/loginUser?username=" + username + "&password=" + password).openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response.toString().equals("Login aceite");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Autenticar admin
    public static boolean authenticateAdmin(String username, String password) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    URL + "/loginAdmin?username=" + username + "&password=" + password).openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("GET");

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response.toString().equals("Login aceite");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Registar user
    public static boolean postUser(String username, String email, String password, String usertype) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/user").openConnection();

            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String jsonObject = "{\"username\":\"" + username + "\",\"email\":\"" + email + "\", \"password\":\""
                    + password + "\", \"usertype\":\"" + usertype + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonObject.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response.toString().equals("true");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obter lista de user role 'USER'
    public static List<String> getUsersRoleUser() {
        List<String> users = new LinkedList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/roleuser").openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listUsers = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listUsers);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String user = "  id: " + object.getString("uid") +
                        "  Username: " + object.getString("username") +
                        "  Email: " + object.getString("email");
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // Obter lista de user role 'ADMIN'
    public static List<String> getUsersRoleAdmin() {
        List<String> users = new LinkedList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/roleadmin").openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listUsers = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listUsers);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String user = "  id: " + object.getString("uid") +
                        "  Username: " + object.getString("username") +
                        "  Email: " + object.getString("email");
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // Atribuir Admin
    public static boolean atribuirAdmin(String uid) {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/atribuiradmin?uid=" + uid)
                    .openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response.toString().equals("true");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**************************************************************************/
    /***************************** ARTISTA ************************************/
    /**************************************************************************/

    // Registar artista
    public static boolean postArtista(String nome, String tipo, String estado) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/artista").openConnection();

            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String jsonObject = "{\"nome\":\"" + nome + "\",\"tipo\":\"" + tipo + "\", \"estado\":\"" + estado + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonObject.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response.toString().equals("true");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obter lista de todos os artistas
    public static List<String> getArtistas() {
        List<String> artistas = new LinkedList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/artistas").openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listArtistas = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listArtistas);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String artista = "  id: " + object.getString("aid") +
                        "  Nome: " + object.getString("nome") +
                        "  Tipo: " + object.getString("tipo") +
                        "  Estado: " + object.getString("estado");
                artistas.add(artista);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return artistas;
    }

    // Obter lista de artistas aprovados
    public static List<String> getArtistasAprovados() {
        List<String> artistas = new LinkedList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/artistasaprovados").openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listArtistas = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listArtistas);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String artista = "  id: " + object.getString("aid") +
                        "  Nome: " + object.getString("nome") +
                        "  Tipo: " + object.getString("tipo");
                artistas.add(artista);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return artistas;
    }

    // Obter lista de artistas aprovados
    public static List<String> getArtistasDesaprovados() {
        List<String> artistas = new LinkedList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/artistasdesaprovados").openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listArtistas = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listArtistas);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String artista = "  id: " + object.getString("aid") +
                        "  Nome: " + object.getString("nome") +
                        "  Tipo: " + object.getString("tipo");
                artistas.add(artista);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return artistas;
    }

    // Aprovar artista
    public static boolean aprovarArtista(String aid) {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/aprovarartista?aid=" + aid)
                    .openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response.toString().equals("true");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Procurar artistas por localização e/ou arte
    public static List<String> procurarArtistas(String tipo, String latitude, String longitude) {
        List<String> artistas = new LinkedList<>();
    
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    URL + "/procuraartistas?tipo=" + URLEncoder.encode(tipo, "UTF-8") + 
                    "&latitude=" + URLEncoder.encode(latitude, "UTF-8") + 
                    "&longitude=" + URLEncoder.encode(longitude, "UTF-8"))
                    .openConnection();
    
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
    
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
    
            JSONArray objects = new JSONArray(response.toString());

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String nomeA = object.optString("aid", "Nome não disponível");
                String tipoArtista = object.optString("nome", "Tipo não disponível");
                String lat = object.optString("tipo", "Latitude não disponível");
                String lon = object.optString("estado", "Longitude não disponível");
    
                String artista = "Nome: " + nomeA + ", Tipo: " + tipoArtista + ", Latitude: " + lat + ", Longitude: " + lon;
                artistas.add(artista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artistas;
    }
    
    // Alterar informações de um artista
    public static boolean alterarArtista(String aid, String novoNome, String novoTipo) {
        try {
            URL url = new URL(URL + "/alterarartista");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
    
            JSONObject json = new JSONObject();
            json.put("aid", aid);
            json.put("nome", novoNome);
            json.put("tipo", novoTipo);
    
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
    
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            return response.toString().equals("true");
    
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }    


    /**************************************************************************/
    /*************************** PERFORMANCE **********************************/
    /**************************************************************************/

    // registar nova performance
    public static boolean postPerformance(int aid, String pdata, String latitude, String longitude) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/performance").openConnection();

            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String jsonObject = "{\"aid\":\"" + aid + "\",\"pdata\":\"" + pdata + "\", \"latitude\":\"" + latitude
                    + "\", \"longitude\":\"" + longitude + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonObject.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response.toString().equals("true");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Consultar todas as performances
    public static List<String> getLocalizacoesAatuar() {
        List<String> locais = new LinkedList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/locais").openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listLocais = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listLocais);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String local = "  artista: " + object.getString("nome") +
                        "  tipo de arte: " + object.getString("tipo") +
                        "  latitude: " + object.getString("latitude") +
                        "  longitude: " + object.getString("longitude");
                locais.add(local);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return locais;
    }

    // Consultar performances passadas por artista
    public static List<String> getPerformancesPassadas(int aid) {

        List<String> performances = new ArrayList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/performancespassadas/" + aid)
                    .openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listPerformances = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listPerformances);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String performance = "  data: " + object.getString("pdata") +
                        "  latitude: " + object.getString("latitude") +
                        "  longitude: " + object.getString("longitude");
                performances.add(performance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return performances;

    }

    // Consultar performances futuras por artista
    public static List<String> getPerformancesFuturas(int aid) {

        List<String> performances = new ArrayList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/performancesfuturas/" + aid)
                    .openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listPerformances = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listPerformances);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String performance = "  data: " + object.getString("pdata") +
                        "  latitude: " + object.getString("latitude") +
                        "  longitude: " + object.getString("longitude");
                performances.add(performance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return performances;

    }

    /**************************************************************************/
    /*************************** DONATIVO *************************************/
    /**************************************************************************/

    // registar novo donativo
    public static boolean postDonativo(int aid, String valor) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/donativo").openConnection();

            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String jsonObject = "{\"aid\":\"" + aid + "\",\"valor\":\"" + valor + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonObject.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response.toString().equals("true");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getDonativos(int aid) {

        List<String> donativos = new ArrayList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/listdonativos/" + aid)
                    .openConnection();

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            BufferedReader data = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String listPerformances = data.readLine();

            data.close();

            JSONArray objects = new JSONArray(listPerformances);

            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                String donativo = "  valor: " + object.getString("valor") +
                        "  data: " + object.getString("ddata");
                donativos.add(donativo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return donativos;

    }

    /**************************************************************************/
    /***************************** MAIN ***************************************/
    /**************************************************************************/

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String nome, tipo, estado, novoNome, novoTipo; // artista
        String username, email, password, usertype; // user
        String pdata, latitude, longitude; // performance
        String valor; // donativo
        int aid, uid, aaid, paid;
        String aidm, uidm;
        boolean controller;

        while (true) {
            System.out.println("Bem-vindo ao SeekArtist 2.0 – Sistema de Gestão de Artistas de Rua!");
            System.out.println("1. Criar um novo user");
            System.out.println("2. Login");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int option1 = scanner.nextInt();
            scanner.nextLine();

            if (option1 == 1) {
                System.out.println();
                System.out.println("Username: ");
                username = scanner.nextLine();

                System.out.println("Email: ");
                email = scanner.nextLine();

                System.out.println("Password: ");
                password = scanner.nextLine();

                usertype = "USER";

                controller = postUser(username, email, password, usertype);
                if (controller) {
                    System.out.println("User registado com sucesso");
                } else {
                    System.out.println("Ocorreu um erro ao registar o user");
                }

            } else if (option1 == 2) {

                System.out.println();

                System.out.print("Username: ");
                username = scanner.nextLine();

                System.out.print("Password: ");
                password = scanner.nextLine();

                if (authenticateAdmin(username, password) == true) {
                    // Exibir menu de administrador
                    while (true) {
                        int optionA = AdminMenu();

                        if (optionA == 17) {
                            System.out.println("Desligando o Admin...");
                            Thread.sleep(1000);
                            break;
                        }

                        switch (optionA) {

                            case 1:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados = getArtistasAprovados();

                                if (artistasaprovados.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados) {
                                        System.out.println(artista);
                                    }
                                }
                                break;

                            case 2:
                                System.out.println();
                                System.out.println("Lista de artistas desaprovados: ");
                                List<String> artistasdesaprovados = getArtistasDesaprovados();

                                if (artistasdesaprovados.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasdesaprovados) {
                                        System.out.println(artista);
                                    }
                                }
                                break;

                            case 3:
                                System.out.println();
                                System.out.println("Lista de artistas desaprovados: ");
                                List<String> artistasdesaprovados2 = getArtistasDesaprovados();

                                if (artistasdesaprovados2.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasdesaprovados2) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println("Indique o id do artista que pretende aprovar: ");
                                aidm = scanner.nextLine();

                                if (aprovarArtista(aidm)) {
                                    System.out.println("Artista aprovado!");
                                } else {
                                    System.out.println("Ocorreu um erro ao aprovar o artista!");
                                }
                                break;

                            case 4:
                                System.out.println();
                                System.out.println("Lista de users: ");
                                List<String> usersUser = getUsersRoleUser();

                                if (usersUser.size() == 0) {
                                    System.out.println("Não existem users no sistema");
                                } else {
                                    for (String user : usersUser) {
                                        System.out.println(user);
                                    }
                                }
                                break;

                            case 5:
                                System.out.println();
                                System.out.println("Lista de admins: ");
                                List<String> usersAdmin = getUsersRoleAdmin();

                                if (usersAdmin.size() == 0) {
                                    System.out.println("Não existem mais admins no sistema");
                                } else {
                                    for (String user : usersAdmin) {
                                        System.out.println(user);
                                    }
                                }
                                break;

                            case 6:
                                System.out.println();
                                System.out.println("Lista de users: ");
                                List<String> usersUser2 = getUsersRoleUser();

                                if (usersUser2.size() == 0) {
                                    System.out.println("Não existem users no sistema");
                                } else {
                                    for (String user : usersUser2) {
                                        System.out.println(user);
                                    }
                                }

                                System.out.println("Indique o id do user, a que pretende atribuir administração: ");
                                uidm = scanner.nextLine();

                                if (atribuirAdmin(uidm)) {
                                    System.out.println("Admin atribuído!");
                                } else {
                                    System.out.println("Ocorreu um erro ao atribuir admin!");
                                }
                                break;

                            case 7:
                                System.out.println();
                                System.out.println("Lista de artistas: ");
                                List<String> artistas = getArtistas();

                                if (artistas.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistas) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println("Indique o id do artista, a que pretende alterar as informações: ");
                                aidm = scanner.nextLine();

                                System.out.println("Nome: ");
                                novoNome = scanner.nextLine();

                                System.out.println("Tipo:(Dança/Música/Teatro/Pintura) ");
                                novoTipo = scanner.nextLine();


                                if (alterarArtista(aidm, novoNome, novoTipo)) {
                                    System.out.println("Informações alteradas!");
                                } else {
                                    System.out.println("Ocorreu um erro ao alterar as informações!");
                                }
                                break;

                            case 8:
                                estado = "desaprovado";

                                System.out.println();
                                System.out.println("Nome: ");
                                nome = scanner.nextLine();

                                System.out.println("Tipo:(Dança/Música/Teatro/Pintura) ");
                                tipo = scanner.nextLine();

                                controller = postArtista(nome, tipo, estado);
                                if (controller) {
                                    System.out.println("Artista registado com sucesso");
                                } else {
                                    System.out.println("Ocorreu um erro ao registar o artista");
                                }
                                break;

                            case 9:
                                System.out.println();
                                System.out.println("Lista de todos os artistas: ");
                                List<String> artistas3 = getArtistas();

                                if (artistas3.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistas3) {
                                        System.out.println(artista);
                                    }
                                }
                                break;

                            case 10:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados6 = getArtistasAprovados();

                                if (artistasaprovados6.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados6) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out
                                        .println("Indique o id do artista, a que pretende registar nova performance: ");
                                aaid = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Data(AAAA-MM-DD): ");
                                pdata = scanner.nextLine();

                                System.out.println("Latitude: ");
                                latitude = scanner.nextLine();

                                System.out.println("Longitude: ");
                                longitude = scanner.nextLine();

                                if (postPerformance(aaid, pdata, latitude, longitude)) {
                                    System.out.println("Performance registada!");
                                } else {
                                    System.out.println("Ocorreu um erro ao registar performance!");
                                }
                                break;

                            case 11:
                                System.out.println();
                                System.out.println("Localizações onde estão a acontecer performances: ");
                                List<String> locais = getLocalizacoesAatuar();

                                if (locais.size() == 0) {
                                    System.out.println("Não existem performances a acontecer");
                                } else {
                                    for (String local : locais) {
                                        System.out.println(local);
                                    }
                                }
                                break;

                            case 12:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados2 = getArtistasAprovados();

                                if (artistasaprovados2.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados2) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println(
                                        "Indique o id do artista, do qual pretende listar as suas performances passadas: ");
                                paid = scanner.nextInt();
                                scanner.nextLine();

                                List<String> performancesPassadas = getPerformancesPassadas(paid);

                                if (performancesPassadas.size() == 0) {
                                    System.out.println("O artista não tem performances passadas!");
                                } else {
                                    for (String performance : performancesPassadas) {
                                        System.out.println(performance);
                                    }
                                }
                                break;

                            case 13:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados3 = getArtistasAprovados();

                                if (artistasaprovados3.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados3) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println(
                                        "Indique o id do artista, do qual pretende listar as suas performances futuras: ");
                                paid = scanner.nextInt();
                                scanner.nextLine();

                                List<String> performancesFuturas = getPerformancesFuturas(paid);

                                if (performancesFuturas.size() == 0) {
                                    System.out.println("O artista não tem performances futuras!");
                                } else {
                                    for (String performance : performancesFuturas) {
                                        System.out.println(performance);
                                    }
                                }
                                break;

                            case 14:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados4 = getArtistasAprovados();

                                if (artistasaprovados4.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados4) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println("Indique o id do artista, a que pretende fazer donativo: ");
                                aaid = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Valor(euros): ");
                                valor = scanner.nextLine();

                                if (postDonativo(aaid, valor)) {
                                    System.out.println("Donativo registado!");
                                } else {
                                    System.out.println("Ocorreu um erro ao registar donativo!");
                                }
                                break;
                            case 15:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados5 = getArtistasAprovados();

                                if (artistasaprovados5.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados5) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println(
                                        "Indique o id do artista, do qual pretende listar os seus donativos: ");
                                paid = scanner.nextInt();
                                scanner.nextLine();

                                List<String> donativos = getDonativos(paid);

                                if (donativos.size() == 0) {
                                    System.out.println("O artista não tem performances passadas!");
                                } else {
                                    for (String donativo : donativos) {
                                        System.out.println(donativo);
                                    }
                                }
                                break;

                            case 16:
                                System.out.println();
                                System.out.println("Arte: ");
                                tipo = scanner.nextLine();

                                System.out.println("Latitude: ");
                                latitude = scanner.nextLine();

                                System.out.println("Longitude: ");
                                longitude = scanner.nextLine();

                                List<String> listArtistas = procurarArtistas(tipo, latitude, longitude);

                                if (listArtistas.size() == 0) {
                                    System.out.println("Não foram encontrados artistas no sistema");
                                } else {
                                    for (String artista : listArtistas) {
                                        System.out.println(artista);

                                    }
                                }
                                break;
                        }
                    }
                } else if (authenticateUser(username, password) == true) {
                    // Exibir menu de user
                    while (true) {
                        int option = menu();

                        if (option == 10) {
                            System.out.println("Desligando o user...");
                            Thread.sleep(1000);
                            break;
                        }

                        switch (option) {

                            case 1:
                                estado = "desaprovado";

                                System.out.println();
                                System.out.println("Nome: ");
                                nome = scanner.nextLine();

                                System.out.println("Tipo:(Dança/Música/Teatro/Pintura) ");
                                tipo = scanner.nextLine();

                                controller = postArtista(nome, tipo, estado);
                                if (controller) {
                                    System.out.println("Artista registado com sucesso");
                                } else {
                                    System.out.println("Ocorreu um erro ao registar o artista");
                                }
                                break;

                            case 2:
                                System.out.println();
                                System.out.println("Lista de todos os artistas: ");
                                List<String> artistas = getArtistas();

                                if (artistas.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistas) {
                                        System.out.println(artista);
                                    }
                                }
                                break;

                            case 3:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados = getArtistasAprovados();

                                if (artistasaprovados.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out
                                        .println("Indique o id do artista, a que pretende registar nova performance: ");
                                aaid = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Data(AAAA-MM-DD): ");
                                pdata = scanner.nextLine();

                                System.out.println("Latitude: ");
                                latitude = scanner.nextLine();

                                System.out.println("Longitude: ");
                                longitude = scanner.nextLine();

                                if (postPerformance(aaid, pdata, latitude, longitude)) {
                                    System.out.println("Performance registada!");
                                } else {
                                    System.out.println("Ocorreu um erro ao registar performance!");
                                }
                                break;

                            case 4:
                                System.out.println();
                                System.out.println("Localizações onde estão a acontecer performances: ");
                                List<String> locais = getLocalizacoesAatuar();

                                if (locais.size() == 0) {
                                    System.out.println("Não existem performances a acontecer");
                                } else {
                                    for (String local : locais) {
                                        System.out.println(local);
                                    }
                                }
                                break;

                            case 5:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados2 = getArtistasAprovados();

                                if (artistasaprovados2.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados2) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println(
                                        "Indique o id do artista, do qual pretende listar as suas performances passadas: ");
                                paid = scanner.nextInt();
                                scanner.nextLine();

                                List<String> performancesPassadas = getPerformancesPassadas(paid);

                                if (performancesPassadas.size() == 0) {
                                    System.out.println("O artista não tem performances passadas!");
                                } else {
                                    for (String performance : performancesPassadas) {
                                        System.out.println(performance);
                                    }
                                }
                                break;

                            case 6:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados3 = getArtistasAprovados();

                                if (artistasaprovados3.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados3) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println(
                                        "Indique o id do artista, do qual pretende listar as suas performances futuras: ");
                                paid = scanner.nextInt();
                                scanner.nextLine();

                                List<String> performancesFuturas = getPerformancesFuturas(paid);

                                if (performancesFuturas.size() == 0) {
                                    System.out.println("O artista não tem performances futuras!");
                                } else {
                                    for (String performance : performancesFuturas) {
                                        System.out.println(performance);
                                    }
                                }
                                break;

                            case 7:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados4 = getArtistasAprovados();

                                if (artistasaprovados4.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados4) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println("Indique o id do artista, a que pretende fazer donativo: ");
                                aaid = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("Valor(euros): ");
                                valor = scanner.nextLine();

                                if (postDonativo(aaid, valor)) {
                                    System.out.println("Donativo registado!");
                                } else {
                                    System.out.println("Ocorreu um erro ao registar donativo!");
                                }
                                break;
                            case 8:
                                System.out.println();
                                System.out.println("Lista de artistas aprovados: ");
                                List<String> artistasaprovados5 = getArtistasAprovados();

                                if (artistasaprovados5.size() == 0) {
                                    System.out.println("Não existem artistas no sistema");
                                } else {
                                    for (String artista : artistasaprovados5) {
                                        System.out.println(artista);
                                    }
                                }

                                System.out.println(
                                        "Indique o id do artista, do qual pretende listar os seus donativos: ");
                                paid = scanner.nextInt();
                                scanner.nextLine();

                                List<String> donativos = getDonativos(paid);

                                if (donativos.size() == 0) {
                                    System.out.println("O artista não tem performances passadas!");
                                } else {
                                    for (String donativo : donativos) {
                                        System.out.println(donativo);
                                    }
                                }
                                break;

                            case 9:
                                System.out.println();
                                System.out.println("Arte: ");
                                tipo = scanner.nextLine();

                                System.out.println("Latitude: ");
                                latitude = scanner.nextLine();

                                System.out.println("Longitude: ");
                                longitude = scanner.nextLine();

                                List<String> listArtistas = procurarArtistas(tipo, latitude, longitude);

                                if (listArtistas.size() == 0) {
                                    System.out.println("Não foram encontrados artistas no sistema");
                                } else {
                                    for (String artista : listArtistas) {
                                        System.out.println(artista);

                                    }
                                }
                                break;
                        }
                    }
                } else {
                    System.out.println("Inválido username ou password.");
                }
            } else if (option1 == 3) {
                System.out.println("Desligando o cliente...");
                Thread.sleep(1000);
                break;
            } else {
                System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }
    }
}
