package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Proposta;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreaProposta extends SocialDevelopBaseController {

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
            HttpSession s = SecurityLayer.checkSession(request);
            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
            }

            Enumeration<String> e = request.getParameterNames();
            List<String> lista = Collections.list(e);

            int taskid = (int) (s.getAttribute("idtask"));
            int id_coordinatore = (int) s.getAttribute("userid");
            Utente coordinatore = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(id_coordinatore);
            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(taskid);

            for (int i = 0; i < lista.size(); i++) {
                int userid = SecurityLayer.checkNumeric(request.getParameter(lista.get(i)));
                Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(userid);

                Proposta proposta = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createProposta();
                proposta.setTask(task);
                proposta.setUtente(utente);
                proposta.setStato("Adesione richiesta");

                ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeProposta(proposta);

                Email email = new Email();
                Email email2 = new Email();
                UtenteProgetto utenteProgetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgettoByProgetto(task.getProgetto().getId());
                String textInvito = email2.creaInvitoCoordinatore(coordinatore.getNome(), coordinatore.getCognome(), utente.getNome(), utente.getCognome(), task.getNome());
                String textProposta = email.creaPropostaSviluppatore(utente.getNome(), utente.getCognome(), coordinatore.getNome(), coordinatore.getCognome(), task.getNome());
                email.Invia(utente.getEmail(), textProposta);
                email2.Invia(coordinatore.getEmail(), textInvito);
            }

            response.sendRedirect("progetti");
        } catch (DataLayerException ex) {
            Logger.getLogger(TaskDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TaskDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
