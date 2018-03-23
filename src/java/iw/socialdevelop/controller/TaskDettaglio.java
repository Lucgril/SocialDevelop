package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Domanda;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Tipo;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TaskDettaglio extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int n) throws IOException, ServletException, TemplateManagerException, DataLayerException, ParseException {

        HttpSession s = SecurityLayer.checkSession(request);

        request.setAttribute("n", n);
        int userid = 0;
        boolean trovato = false;

        if (s != null) {
            request.setAttribute("logged", s.getAttribute("logged"));
            request.setAttribute("userid", s.getAttribute("userid"));
            userid = (int) s.getAttribute("userid");
            s.setAttribute("idtask", n);
        }

        List<Task> tskliv = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskBySkillLivello(userid);

        for (Task taskliv : tskliv) {
            if (taskliv.getId() == n) {
                trovato = true;
            }
        }

        if (trovato) {
            request.setAttribute("adatto", true);
        }

        List<Utente> partecipanti = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getDomandaUtentiAccettatoConValutazione(n);
        List<Utente> partecipanti3 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getDomandaUtentiAccettatoSenzaValutazione(n);
        List<Utente> partecipanti2 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getPropostaUtentiAccettatoConValutazione(n);
        List<Utente> partecipanti4 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getPropostaUtentiAccettatoSenzaValutazione(n);
        request.setAttribute("partecipanti", partecipanti);
        request.setAttribute("partecipanti2", partecipanti2);
        request.setAttribute("partecipanti3", partecipanti3);
        request.setAttribute("partecipanti4", partecipanti4);
        List<Utente> domande_utenti = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getDomandaUtenti(n);

        for (Utente u : domande_utenti) {
            if (u.getId() == userid) {
                request.setAttribute("domandato", true);
            }
        }

        TemplateResult res = new TemplateResult(getServletContext());

        Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(n);
        Tipo tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(task.getIdTipo());
        task.setTipo(tipo);
        Progetto p = task.getProgetto();
        UtenteProgetto ut = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgettoByProgetto(p.getId());
        Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(ut.getUtente().getId());

        if (task.getCollaboratori() == task.getCollaboratoritot()) {
            request.setAttribute("pieno", true);
        } else {
            request.setAttribute("pieno", false);
        }

        if (utente.getId() == userid) {
            request.setAttribute("adminp", true);
        }

        SimpleDateFormat sdf = new SimpleDateFormat();
        String datastring = sdf.format(new Date());

        datastring = datastring.substring(0, 8);
        Date data = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        data = df.parse(datastring);

        if (data.after(task.getDataFine())) {
            request.setAttribute("taskfinito", true);
        }

        request.setAttribute("utente", utente);
        request.setAttribute("task", task);
        request.setAttribute("page_title", task.getNome());

        res.activate("taskDettaglio2.html", request, response);

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        int n;

        try {
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            request.setAttribute("riferimento", "task?n=" + n);
            if (request.getParameter("p") != null) {
                action_store(request, response, n);
            } else {
                action_default(request, response, n);
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Numero non valido");
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (DataLayerException ex) {
            Logger.getLogger(ProgettoDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TaskDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_store(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            HttpSession s = SecurityLayer.checkSession(request);

            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
            }

            int userid = (int) (s.getAttribute("userid"));
            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(n);
            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(userid);

            Domanda domanda = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createDomanda();
            domanda.setTask(task);
            domanda.setUtente(utente);
            domanda.setStato("Adesione richiesta");

            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeDomanda(domanda);
            Email email = new Email();
            UtenteProgetto utenteProgetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgettoByProgetto(task.getProgetto().getId());
            String textOfferta = email.creaOffertaSviluppatore(utente.getNome(), utente.getCognome(), utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), task.getNome());
            String textDomanda = email.creaDomandaCoordinatore(utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), utente.getNome(), utente.getCognome(), task.getNome());
            email.Invia(utente.getEmail(), textOfferta);
            email.Invia(utenteProgetto.getUtente().getEmail(), textDomanda);

            // action_default(request, response, n);
            response.sendRedirect("task?n=" + n);
        } catch (DataLayerException ex) {
            Logger.getLogger(TaskDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TaskDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
