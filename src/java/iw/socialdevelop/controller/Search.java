package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Search extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_skill(HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException, SQLException {

        List<String> skill = new ArrayList();
        List<Utente> result = new ArrayList();
        List<Utente> result2 = new ArrayList();
        int i = 0;
        Enumeration<String> e = request.getParameterNames();
        List<String> ll = Collections.list(e);
        List<Utente> utenti = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenti();

        int size = utenti.size();

        Boolean controllo = false;

        for (i = 2; i <= ll.size() - 2; i++) {
            int livello = 0;
            if (request.getParameter((String) ll.get(i + 1)).isEmpty()) {
                livello = 1;
            } else {
                livello = Integer.parseInt(request.getParameter((String) ll.get(i + 1)));
            }
            result = (((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteBySkillLivello(Integer.parseInt(request.getParameter((String) ll.get(i))), livello));
            i++;
            for (int j = 0; j < size; j++) {

                controllo = false;
                for (int k = 0; k < result.size(); k++) {
                    if (utenti.get(j).getId() == result.get(k).getId()) {
                        controllo = true;
                    }
                }
                if (!controllo) {
                    utenti.remove(j--);
                    size = utenti.size();
                }

            }

        }

        TemplateResult res = new TemplateResult(getServletContext());

        if (request.getParameter("author").equals("admin")) {
            HttpSession s = SecurityLayer.checkSession(request);

            int idtask = (int) (s.getAttribute("idtask"));
            List<Utente> domande_utenti = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getDomandaUtenti(idtask);
            List<Utente> proposte_utenti = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getPropostaUtenti(idtask);
            size = utenti.size();
            controllo = false;

            for (i = 0; i < size; i++) {
                controllo = false;
                for (int k = 0; k < domande_utenti.size(); k++) {
                    if (utenti.get(i).getId() == domande_utenti.get(k).getId()) {
                        controllo = true;
                    }
                }
                if (controllo) {
                    utenti.remove(i--);
                    size = utenti.size();
                }
            }
            for (i = 0; i < size; i++) {
                controllo = false;
                for (int k = 0; k < proposte_utenti.size(); k++) {
                    if (utenti.get(i).getId() == proposte_utenti.get(k).getId()) {
                        controllo = true;
                    }
                }
                if (controllo) {
                    utenti.remove(i--);
                    size = utenti.size();
                }
            }

            request.setAttribute("coordinatore", true);

        }

        request.setAttribute("utenti", utenti);

        try {
            res.activate("utentiFiltrati.html", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void action_keywords(HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException, SQLException {

        List<Progetto> progetti = new ArrayList();
        String contenuto[] = null;
        TemplateResult res = new TemplateResult(getServletContext());
        String keywords = request.getParameter("keywords");
        contenuto = keywords.split(" ");

        progetti = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgettiByParole(contenuto);
        request.setAttribute("progetti", progetti);

        try {
            res.activate("listaProgetti.html", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            TemplateResult res = new TemplateResult(getServletContext());
            List<Skill> skill = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkill();
            request.setAttribute("skills", skill);
            res.activate("search.html", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataLayerException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            request.setAttribute("riferimento", "search");
            request.setAttribute("page_title", "Cerca");
            HttpSession s = SecurityLayer.checkSession(request);
            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));

            }

            if (request.getParameter("keywords") != null) {
                String key = request.getParameter("keywords");
                if (key.isEmpty()) {
                    action_skill(request, response);
                } else {
                    action_keywords(request, response);
                }

            } else {
                action_default(request, response);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
