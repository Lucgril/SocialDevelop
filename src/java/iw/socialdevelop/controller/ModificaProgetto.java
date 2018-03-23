package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModificaProgetto extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            Progetto progetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetto(n);
            request.setAttribute("progetto", progetto);
            res.activate("modificaProgetto.html", request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(ModificaProgetto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ModificaProgetto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_modifica(HttpServletRequest request, HttpServletResponse response, int n) {
        try {
            String nome = request.getParameter("nomeprogetto");
            String descrizione = request.getParameter("descrizione");

            Progetto progetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetto(n);
            progetto.setNome(nome);
            progetto.setDescrizione(descrizione);

            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeProgetto(progetto);

            response.sendRedirect("progetto?n=" + n);
        } catch (DataLayerException ex) {
            Logger.getLogger(ModificaProgetto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModificaProgetto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        int n = SecurityLayer.checkNumeric(request.getParameter("n"));
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {

            try {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
                int userid = (int) request.getAttribute("userid");

                UtenteProgetto ut = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgetto(userid, n);

                if (ut != null && ut.getRuolo().equals("admin")) {
                    if (request.getParameter("descrizione") != null) {
                        action_modifica(request, response, n);
                    } else {
                        action_default(request, response, n);
                    }
                } else {
                    request.setAttribute("exception", new Exception("Non Sei il coordinatore del progetto!!"));
                    action_error(request, response);
                }
            } catch (DataLayerException ex) {
                Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("exception", new Exception("Effettua la login per creare un nuovo task"));
            action_error(request, response);
        }

    }

}
