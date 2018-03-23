package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RecuperoPassword extends SocialDevelopBaseController {

     private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
     
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.setAttribute("page_title", "SocialDevelop");

        if (request.getParameter("recupero") != null) {
            action_recupero(request, response);
        } else {
            action_default(request, response);
        }
    }

    private void action_recupero(HttpServletRequest request, HttpServletResponse response) {
         try {
             String email = (String) request.getParameter("email");
             Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteByEmail(email);
             if(utente != null) {
             Email recuperoEmail = new Email();
             String testo = recuperoEmail.password(utente.getNome(), utente.getCognome(), utente.getPassword());
             recuperoEmail.Invia(email, testo);
             response.sendRedirect("progetti");
             }
             else {
                 request.setAttribute("message", "Email errata o utente non registrato!");
                 action_error(request,response);
                
             }
         } catch (DataLayerException ex) {
             Logger.getLogger(RecuperoPassword.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(RecuperoPassword.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) {
         try {
             request.setAttribute("page_title", "Recupero Password");
             TemplateResult res = new TemplateResult(getServletContext());
             res.activate("recuperoPassword.html", request, response);
         } catch (TemplateManagerException ex) {
             Logger.getLogger(RecuperoPassword.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
}
