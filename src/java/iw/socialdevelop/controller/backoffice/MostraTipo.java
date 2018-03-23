package iw.socialdevelop.controller.backoffice;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResultBackOffice;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.controller.SocialDevelopBaseController;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Tipo;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MostraTipo extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResultBackOffice res = new TemplateResultBackOffice(getServletContext());
            request.setAttribute("page_title", "Tipi");
            List<Tipo> tipi = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipi();
            request.setAttribute("tipi", tipi);
            res.activate("mostraTipo.html", request, response);
        } catch (DataLayerException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
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
                try {
                    action_default(request, response);

                } catch (IOException ex) {
                    request.setAttribute("exception", ex);
                    action_error(request, response);

                } catch (TemplateManagerException ex) {
                    request.setAttribute("exception", ex);
                    action_error(request, response);

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
