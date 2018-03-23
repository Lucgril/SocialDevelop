package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Domanda;
import iw.socialdevelop.data.model.Proposta;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AggiungiValutazione extends SocialDevelopBaseController {

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
            int taskid = SecurityLayer.checkNumeric(request.getParameter("taskid"));
            int userid = SecurityLayer.checkNumeric(request.getParameter("userid"));
            int valutazione = SecurityLayer.checkNumeric(request.getParameter("valutazione"));

            if (request.getParameter("proposta") != null) {
                Proposta proposta = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getPropostaByUtenteTask(userid, taskid);
                proposta.setValutazione(valutazione);
                ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeProposta(proposta);
            } else {
                Domanda domanda = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getDomandaByUtenteTask(userid, taskid);
                domanda.setValutazione(valutazione);
                ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeDomanda(domanda);
            }

            Email email = new Email();
            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(taskid);
            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(userid);
            UtenteProgetto utenteProgetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgettoByProgetto(task.getProgetto().getId());
            String textValutazione = email.creaRecensione(utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), utente.getNome(), utente.getCognome(), task.getNome(), valutazione);
            email.Invia(utente.getEmail(), textValutazione);

            response.sendRedirect("task?n=" + taskid);
        } catch (DataLayerException ex) {
            Logger.getLogger(EliminaSviluppatore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AggiungiValutazione.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
