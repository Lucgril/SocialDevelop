package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CercaSviluppatori extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession s = SecurityLayer.checkSession(request);

            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
            }

            List<Utente> utenti = new ArrayList();
            String contenuto[] = null;
            String keywords = request.getParameter("keywords");
            contenuto = keywords.split(" ");

            utenti = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtentiByParole(contenuto);

            request.setAttribute("coordinatore", true);
            request.setAttribute("utenti", utenti);

            try {
                res.activate("utentiFiltrati.html", request, response);
            } catch (TemplateManagerException ex) {
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DataLayerException ex) {
            Logger.getLogger(CercaSviluppatori.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CercaSviluppatori.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
