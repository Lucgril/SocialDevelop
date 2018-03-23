package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.impl.ProgettoImpl;
import iw.socialdevelop.data.impl.UtenteProgettoImpl;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewProgetto extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_new(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            HttpSession s = SecurityLayer.checkSession(request);
            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
            }
            TemplateResult res = new TemplateResult(getServletContext());

            String nome = (String) request.getParameter("nomeprogetto");
            String descrizione = (String) request.getParameter("descrizione");

            Progetto progetto = new ProgettoImpl(((SocialDevelopDataLayer) request.getAttribute("datalayer")));
            UtenteProgetto utenteprogetto = new UtenteProgettoImpl(((SocialDevelopDataLayer) request.getAttribute("datalayer")));

            progetto.setNome(nome);
            progetto.setDescrizione(descrizione);
            int idp = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeProgetto(progetto);
            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente((int) request.getAttribute("userid"));

            utenteprogetto.setUtente(utente);

            utenteprogetto.setProgetto(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetto(idp));
            utenteprogetto.setRuolo("admin");
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeUtenteProgetto(utenteprogetto);

            response.sendRedirect("progetto?n=" + idp);

        } catch (DataLayerException ex) {
            Logger.getLogger(NewProgetto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            HttpSession s = SecurityLayer.checkSession(request);

            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));

                request.setAttribute("page_title", "Crea nuovo progetto");
                TemplateResult res = new TemplateResult(getServletContext());

                res.activate("nuovoprogetto.html", request, response);
            } else {
                request.setAttribute("exception", new Exception("Effettua la login per creare un progetto!!"));
                action_error(request, response);
            }
        } catch (TemplateManagerException ex) {
            Logger.getLogger(NewProgetto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            if (request.getParameter("crea") != null) {
                action_new(request, response);
            } else {
                action_default(request, response);
            }
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

}
