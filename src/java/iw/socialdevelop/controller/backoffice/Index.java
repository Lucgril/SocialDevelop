package iw.socialdevelop.controller.backoffice;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResultBackOffice;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.socialdevelop.controller.SocialDevelopBaseController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Index extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {
            String a = (String) s.getAttribute("amministratore");
            if (a.equals("T")) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("nome", s.getAttribute("nome"));
                request.setAttribute("cognome", s.getAttribute("cognome"));
                TemplateResultBackOffice res = new TemplateResultBackOffice(getServletContext());
                try {
                    res.activate("index.html", request, response);
                } catch (TemplateManagerException ex) {
                    Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
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
