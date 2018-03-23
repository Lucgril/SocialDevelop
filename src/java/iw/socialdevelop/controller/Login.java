package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        request.setAttribute("riferimento", request.getParameter("riferimento"));

        try {

            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).login(password, email);

            if (utente == null) {
                request.setAttribute("exception", new Exception("Login failed!"));
                action_error(request, response);
            } else {

                if ("T".equals(utente.getAmministratore())) {
                    SecurityLayer.createSession(request, utente.getNome(), utente.getCognome(), utente.getId(), utente.getAmministratore());
                    request.setAttribute("page_title", "Benvenuto");
                    HttpSession s = request.getSession(true);
                    request.setAttribute("logged", s.getAttribute("logged"));
                    request.setAttribute("nome", s.getAttribute("nome"));
                    request.setAttribute("cognome", s.getAttribute("cognome"));
                    response.sendRedirect("index");

                } else {
                    SecurityLayer.createSession(request, utente.getNome(), utente.getCognome(), utente.getId(), utente.getAmministratore());
                    request.setAttribute("page_title", "Benvenuto");
                    HttpSession s = request.getSession(true);
                    request.setAttribute("logged", s.getAttribute("logged"));
                    request.setAttribute("nome", s.getAttribute("nome"));
                    request.setAttribute("cognome", s.getAttribute("cognome"));

                    response.sendRedirect(request.getParameter("riferimento"));
                }
            }
        } catch (DataLayerException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
