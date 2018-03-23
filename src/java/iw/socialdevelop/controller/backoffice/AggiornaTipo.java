package iw.socialdevelop.controller.backoffice;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResultBackOffice;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.controller.SocialDevelopBaseController;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Tipo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AggiornaTipo extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int n) throws DataLayerException, TemplateManagerException {
        TemplateResultBackOffice res = new TemplateResultBackOffice(getServletContext());
        request.setAttribute("page_title", "Aggiorna Tipo");
        Tipo tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(n);
        request.setAttribute("tipo", tipo);
        res.activate("aggiornaTipo.html", request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {
            String a = (String) s.getAttribute("amministratore");
            if (a.equals("T")) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("nome", s.getAttribute("nome"));
                request.setAttribute("cognome", s.getAttribute("cognome"));
                int n;
                try {
                    n = SecurityLayer.checkNumeric(request.getParameter("n"));
                    action_default(request, response, n);
                } catch (NumberFormatException ex) {
                    request.setAttribute("message", "Numero non valido");
                    action_error(request, response);
                } catch (DataLayerException ex) {
                    Logger.getLogger(AggiornaSkill.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TemplateManagerException ex) {
                    Logger.getLogger(AggiornaSkill.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                request.setAttribute("exception", new Exception("Sezione riservata! Non Sei l'amministratore!"));
                action_error(request, response);
            }
        } else {
            request.setAttribute("exception", new Exception("Sezione riservata! Non sei loggato!"));
            action_error(request, response);
        }

    }

}
