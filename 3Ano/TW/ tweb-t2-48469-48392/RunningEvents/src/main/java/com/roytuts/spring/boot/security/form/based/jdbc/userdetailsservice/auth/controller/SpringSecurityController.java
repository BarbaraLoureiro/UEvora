package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.controller;

import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.EventoDao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.InscricaoDao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.dao.UserDao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Evento;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.Inscricao;
import com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model.User;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

@Controller
public class SpringSecurityController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private EventoDao eventoDao;
    @Autowired
    private InscricaoDao inscricaoDao;

    @GetMapping("/index")
    public String defaultPage(Model model, HttpServletRequest req) {
        String user_name = req.getRemoteUser();
        model.addAttribute("user_name", user_name);
        model.addAttribute("msg", "Welcome to Spring Security");

        List<Evento> eventosAtuais = eventoDao.getEventosAtivos();
        List<Evento> eventosAntigos = eventoDao.getEventosPassados();
        List<Evento> eventosFuturos = eventoDao.getEventosFuturos();
        model.addAttribute("eventosAtuais", eventosAtuais);
        model.addAttribute("eventosAntigos", eventosAntigos);
        model.addAttribute("eventosFuturos", eventosFuturos);

        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Credenciais inválidas, tente novamente.");
        }
        if (logout != null) {
            model.addAttribute("msg", "Foi desconectado com sucesso.");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model, HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/staff")
    public String staff(Model model) {
        model.addAttribute("title", "Administrator Control Panel");
        model.addAttribute("msg", "Welcome to Staff Page");
        return "staff";
    }

    @GetMapping("/newuser")
    public String newuser(Model model) {
        model.addAttribute("title", "New User");
        model.addAttribute("message", "fill new user's details");

        return "newuser";
    }

    @PostMapping("/register")
    public String register(@RequestParam String user_name,
            @RequestParam String user_pass,
            @RequestParam String nome,
            @RequestParam String genero,
            @RequestParam String escalao,
            Model model) {

        model.addAttribute("title", "registration page");
        model.addAttribute("message", "registration is OK");

        String encodedPassword = new BCryptPasswordEncoder().encode(user_pass);
        User u = new User(user_name, encodedPassword, nome, genero, escalao, "ROLE_ATLETA");
        userDao.saveUser(u);
        System.out.println("GRAVAR na BD: " + u.toString());
        model.addAttribute("user", u);

        return "register";
    }

    @PostMapping("/registarevento")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String registaroferta(@RequestParam String evento_name,
            @RequestParam String descricao,
            @RequestParam Date data, @RequestParam Integer preco) {

        Evento e = new Evento(evento_name, descricao, data, preco);
        eventoDao.saveEvento(e);
        System.out.println("GRAVAR na BD: " + e.toString());
        System.out.println("");

        return "redirect:/staff";
    }

    @GetMapping("/atleta")
    public String atletaPage(Principal principal, Model model) {
        List<Evento> eventos = eventoDao.getAllEventos();
        model.addAttribute("eventos", eventos);

        String userName = principal.getName();

        List<Inscricao> inscricoes = inscricaoDao.getInscricoesByAtleta(userName);

        model.addAttribute("inscricoes", inscricoes);

        return "atleta";
    }

    @GetMapping("/newinscricao")
    public String newinscricao(Model model) {
        model.addAttribute("title", "New Inscription");
        model.addAttribute("message", "fill new inscription's details");

        return "newinscricao";
    }

    @PostMapping("/inscrever")
    public String inscrever(@RequestParam("eventoName") String eventoName, Principal principal, Model model) {
        String userName = principal.getName();

        model.addAttribute("title", "registration page");
        model.addAttribute("message", "registration is OK");

        inscricaoDao.inscreverAtleta(userName, eventoName);
        System.out.println("GRAVAR na BD: " + userName + " " + eventoName);
        model.addAttribute("user_name", userName);
        model.addAttribute("evento_name", eventoName);

        Evento evento = eventoDao.getEventoByName(eventoName);
        if (evento == null) {
            model.addAttribute("errorMessage", "Evento not found");
            return "error";

        }

        // Gerar referência MB para pagamento
        RestTemplate restTemplate = new RestTemplate();
        String amount = String.valueOf(evento.getPreco());
        String mbRefUrl = "https://magno.di.uevora.pt/tweb/t2/mbref4payment?amount=" + amount;
        String mbRef = restTemplate.getForObject(mbRefUrl, String.class);

        return "atleta";
    }

    @PostMapping("/registarTempo")
    public String registarTempo(@RequestParam("eventoName") String eventoName, @RequestParam Integer dorsal,
            @RequestParam String sectionID, @RequestParam LocalDateTime elapsed_time) {
        System.out.println("GRAVAR na BD: " + eventoName + " " + dorsal + " " + sectionID + " " + elapsed_time);

        return "redirect:/staff";
    }

    @GetMapping("/procurar")
    public String procurar(Model model) {
        List<Evento> eventos = eventoDao.getAllEventos();
        model.addAttribute("eventos", eventos);
        model.addAttribute("msg", "Welcome to Search Page");
        return "procurar";
    }

}
