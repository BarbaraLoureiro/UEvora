package sd.rest;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author barbara
 */

public class DbOperations {

    // Iniciar a bd com as configurações
    PostgresConnector setDbProperties() throws Exception {
        String host = "", db = "", user = "", pw = "";
        try (InputStream input = new FileInputStream("src/main/resources/configs.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            host = prop.getProperty("host");
            db = prop.getProperty("db");
            user = prop.getProperty("user");
            pw = prop.getProperty("password");
        }
        return new PostgresConnector(host, db, user, pw);

    }

    /**************************************************************************/
    /***************************** USER ***************************************/
    /**************************************************************************/

    // Autenticar user
    public boolean authenticateUser(String username, String password) {
        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();

            String sql = "SELECT password FROM users WHERE username = ? AND usertype = 'USER'";
            PreparedStatement stmt = pc.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, storedPassword)) {
                    // As credenciais são válidas
                    return true; // Retorna o tipo de usuário se as credenciais forem válidas
                }
            }

            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Autenticar admin
    public boolean authenticateAdmin(String username, String password) {
        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();

            String sql = "SELECT password FROM users WHERE username = ? AND usertype = 'ADMIN'";
            PreparedStatement stmt = pc.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(password, storedPassword)) {
                    // As credenciais são válidas
                    return true;
                }
            }

            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // As credenciais não são válidas
        return false;
    }

    // Encriptar a password
    public String hashPassword(String passwordToHash) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(passwordToHash);
    }

    // Verificar se o user já existe
    protected boolean userExists(User u) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ? OR email = ?";

        PostgresConnector pc = setDbProperties();

        try {
            pc.connect();

            PreparedStatement stmt = pc.prepareStatement(sql);

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());

            return stmt.executeQuery().next();
        } finally {
            pc.disconnect();
        }
    }

    // Inserir user na bd
    protected boolean registarUser(User u) throws SQLException {
        try {
            if (userExists(u)) {
                System.out.println("User já existe");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO users (username,email,password,usertype) VALUES (?,?,?,?)";

        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();

            PreparedStatement stmt = pc.prepareStatement(sql);

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, hashPassword(u.getPassword()));
            stmt.setString(4, u.getUsertype());
            stmt.executeUpdate();

            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Consultar todos os users tendo em conta o usertype
    protected List<User> listarUsers(String usertype) throws SQLException {
        String sql = "SELECT * FROM users WHERE usertype=?";

        List<User> users = new LinkedList<>();

        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();

            PreparedStatement stmt = pc.prepareStatement(sql);
            stmt.setString(1, usertype);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getString("uid"), rs.getString("username"), rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("usertype")));
            }
            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // Atribuir admin
    protected boolean atribuirAdmin(String uid) throws SQLException {
        String sql = "UPDATE users SET usertype=? WHERE uid=?";

        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();

            PreparedStatement stmt = pc.prepareStatement(sql);

            int uid1 = Integer.parseInt(uid);

            stmt.setString(1, "ADMIN");
            stmt.setInt(2, uid1);

            stmt.executeUpdate();

            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**************************************************************************/
    /***************************** ARTISTA ************************************/
    /**************************************************************************/

    // Verificar se o artista já existe
    protected boolean artistaExists(Artista a) throws Exception {
        String sql = "SELECT * FROM artistas WHERE nome = ?";

        PostgresConnector pc = setDbProperties();

        try {
            pc.connect();

            PreparedStatement stmt = pc.prepareStatement(sql);

            stmt.setString(1, a.getNome());

            return stmt.executeQuery().next();
        } finally {
            pc.disconnect();
        }
    }

    // Inserir artista na bd
    protected boolean registarArtista(Artista a) throws SQLException {
        try {
            if (artistaExists(a)) {
                System.out.println("Artista já existe");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO artistas (nome,tipo,estado) VALUES (?,?,?)";

        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();

            PreparedStatement stmt = pc.prepareStatement(sql);

            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getTipo());
            stmt.setString(3, a.getEstado());
            stmt.executeUpdate();

            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Aprovar artista
    protected boolean aprovarArtista(String aid) throws SQLException {
        String sql = "UPDATE artistas SET estado=? WHERE aid=?";

        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();

            PreparedStatement stmt = pc.prepareStatement(sql);

            int aid1 = Integer.parseInt(aid);

            stmt.setString(1, "aprovado");
            stmt.setInt(2, aid1);

            stmt.executeUpdate();

            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    // Consultar todos os artistas
    protected List<Artista> listarArtistas() throws SQLException {
        String sql = "SELECT * FROM artistas";

        List<Artista> artistas = new LinkedList<>();

        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();


            PreparedStatement stmt = pc.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                artistas.add(new Artista(rs.getString("aid"), rs.getString("nome"), rs.getString("tipo"),
                        rs.getString("estado")));
            }
            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artistas;
    }

    // Consultar todos os artistas tendo em conta um estado
    protected List<Artista> listarArtistasPorEstado(String estado) throws SQLException {
        String sql = "SELECT * FROM artistas WHERE estado=?";

        List<Artista> artistas = new LinkedList<>();

        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();


            PreparedStatement stmt = pc.prepareStatement(sql);
            stmt.setString(1, estado);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                artistas.add(new Artista(rs.getString("aid"), rs.getString("nome"), rs.getString("tipo"),
                        rs.getString("estado")));
            }
            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artistas;
    }

    // Procurar artistas
    protected List<Artista> procurarArtistas(String tipo, String latitude, String longitude) throws SQLException {
        String sql = "SELECT a.nome, a.tipo, p.latitude, p.longitude FROM artistas a INNER JOIN performances p ON p.aid = a.aid WHERE a.estado = 'aprovado'";
    
        if (!tipo.isEmpty()) {
            sql += " AND a.tipo = ?";
        }
        if (!latitude.isEmpty() && !longitude.isEmpty()) {
            sql += " AND p.latitude = ? AND p.longitude = ?";
        }
    
        List<Artista> artistas = new LinkedList<>();
    
        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();
    
            PreparedStatement stmt = pc.prepareStatement(sql);
    
            int parameterIndex = 1;
            if (!tipo.isEmpty()) {
                stmt.setString(parameterIndex++,  tipo);
            }
            if (!latitude.isEmpty() && !longitude.isEmpty()) {
                stmt.setString(parameterIndex++, latitude);
                stmt.setString(parameterIndex++, longitude);
            }
    
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                artistas.add(new Artista(rs.getString("nome"), rs.getString("tipo"), rs.getString("latitude"), rs.getString("longitude")));
            }
            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artistas;
    }    

    // Alterar artista
    protected boolean alterarArtista(Artista a) throws SQLException {
        String sql = "UPDATE artistas SET nome=?, tipo=? WHERE aid=?";

        try {
            PostgresConnector pc = setDbProperties();
            pc.connect();

            PreparedStatement stmt = pc.prepareStatement(sql);

            int aid1 = Integer.parseInt(a.getAid());

            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getTipo());
            stmt.setInt(3, aid1);

            stmt.executeUpdate();

            pc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    

    /***************************************************************************/
    /***************************** PERFORMANCE *********************************/
    /***************************************************************************/

    // Inserir performance na bd
    protected boolean registoPerformance(Performance p) throws Exception {
        // Primeiro, verificar se o artista existe e está aprovado
        String checkSql = "SELECT aid FROM artistas WHERE aid = ? AND estado = 'aprovado'";

        PostgresConnector pc = setDbProperties();
        pc.connect();

        try {
            PreparedStatement checkStmt = pc.prepareStatement(checkSql);
            checkStmt.setInt(1, p.getAid());
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Artista não existe ou não está aprovado");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Se o artista estiver aprovado, inserir a performance
        String sql = "INSERT INTO performances (aid, pdata, latitude, longitude) VALUES (?,?,?,?)";

        try {
            PreparedStatement stmt = pc.prepareStatement(sql);

            stmt.setInt(1, p.getAid());

            // Convertendo a string para java.sql.Date
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsed = format.parse(p.getPdata());
            java.sql.Date pdata = new java.sql.Date(parsed.getTime());

            // Agora você pode usar pdata na sua consulta
            stmt.setDate(2, pdata);

            stmt.setString(3, p.getLatitude());
            stmt.setString(4, p.getLongitude());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pc.disconnect();
        }
        return true;
    }

    // Consultar todas as performances
    protected List<Performance> listarLocalizacoesAatuar() throws Exception {
        List<Performance> locais = new ArrayList<>();

        String sql = "SELECT a.nome, a.tipo, p.latitude, p.longitude FROM performances p INNER JOIN artistas a ON p.aid = a.aid WHERE a.estado = 'aprovado' AND p.pdata = CURRENT_DATE";

        PostgresConnector pc = setDbProperties();
        pc.connect();

        try {
            PreparedStatement stmt = pc.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Performance local = new Performance();
                local.setNome(rs.getString("nome"));
                local.setTipo(rs.getString("tipo"));
                local.setLatitude(rs.getString("latitude"));
                local.setLongitude(rs.getString("longitude"));
                locais.add(local);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pc.disconnect();
        }

        return locais;
    }

    // Consultar todas as performances passadas de um aid
    protected List<Performance> listarPerformancesPassadas(int aid) throws Exception {
        List<Performance> performances = new ArrayList<>();

        String sql = "SELECT  p.pdata, p.latitude, p.longitude FROM performances p INNER JOIN artistas a ON p.aid = a.aid WHERE a.aid = ? AND a.estado = 'aprovado' AND p.pdata < CURRENT_DATE";

        PostgresConnector pc = setDbProperties();
        pc.connect();

        try {
            PreparedStatement stmt = pc.prepareStatement(sql);
            stmt.setInt(1, aid); // Define o aid do artista

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Performance performance = new Performance();
                performance.setPdata(rs.getString("pdata")); 
                performance.setLatitude(rs.getString("latitude"));
                performance.setLongitude(rs.getString("longitude"));
                performances.add(performance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pc.disconnect();
        }

        return performances;
    }

    // Consultar todas as performances futuras de um aid
    protected List<Performance> listarPerformancesFuturas(int aid) throws Exception {
        List<Performance> performances = new ArrayList<>();

        String sql = "SELECT  p.pdata, p.latitude, p.longitude FROM performances p INNER JOIN artistas a ON p.aid = a.aid WHERE a.aid = ? AND a.estado = 'aprovado' AND p.pdata > CURRENT_DATE ORDER BY p.pdata ASC LIMIT 1";

        PostgresConnector pc = setDbProperties();
        pc.connect();

        try {
            PreparedStatement stmt = pc.prepareStatement(sql);
            stmt.setInt(1, aid); // Define o aid do artista

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Performance performance = new Performance();
                performance.setPdata(rs.getString("pdata")); 
                performance.setLatitude(rs.getString("latitude"));
                performance.setLongitude(rs.getString("longitude"));
                performances.add(performance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pc.disconnect();
        }

        return performances;
    }

    /***************************************************************************/
    /***************************** DONATIVO ************************************/
    /***************************************************************************/
    

    // Inserir donativo na bd
    protected boolean registoDonativo(Donativo d) throws Exception {
        // Primeiro, verificar se o artista existe e está aprovado
        String checkSql = "SELECT aid FROM artistas WHERE aid = ? AND estado = 'aprovado'";

        PostgresConnector pc = setDbProperties();
        pc.connect();

        try {
            PreparedStatement checkStmt = pc.prepareStatement(checkSql);
            checkStmt.setInt(1, d.getAid()); // Supondo que getAid() retorna o ID do artista
            ResultSet rs = checkStmt.executeQuery();

            // Se não houver artistas aprovados com o ID fornecido, retorna false
            if (!rs.next()) {
                System.out.println("Artista não existe ou não está aprovado");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Se o artista estiver aprovado, inserir o donativo
        String sql = "INSERT INTO donativos (aid, valor, ddata) VALUES (?,?,?)";

        try {
            PreparedStatement stmt = pc.prepareStatement(sql);

            stmt.setInt(1, d.getAid());
            stmt.setString(2, d.getValor());

            java.sql.Date ddata = java.sql.Date.valueOf(LocalDate.now());
            stmt.setDate(3, ddata);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            pc.disconnect();
        }
        return true;
    }

    // Consultar todos os donativos de um aid
    protected List<Donativo> listarDonativos(int aid) throws Exception {
        List<Donativo> donativos = new ArrayList<>();

        String sql = "SELECT  d.valor, d.ddata FROM donativos d INNER JOIN artistas a ON d.aid = a.aid WHERE a.aid = ? AND a.estado = 'aprovado'";

        PostgresConnector pc = setDbProperties();
        pc.connect();

        try {
            PreparedStatement stmt = pc.prepareStatement(sql);
            stmt.setInt(1, aid); // Define o aid do artista

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Donativo donativo = new Donativo();
                donativo.setValor(rs.getString("valor"));
                donativo.setDdata(rs.getString("ddata"));
                donativos.add(donativo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pc.disconnect();
        }

        return donativos;
    }

}
