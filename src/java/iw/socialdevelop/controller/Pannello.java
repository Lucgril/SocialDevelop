package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Domanda;
import iw.socialdevelop.data.model.Proposta;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Pannello extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        int n;
        request.setAttribute("page_title", "Pannello");

        try {
            n = SecurityLayer.checkNumeric(request.getParameter("n"));

            if (request.getParameter("partecipazione") != null) {
                action_partecipazione(request, response, n);
            } else {
                if (request.getParameter("domanda") != null) {
                    action_domanda(request, response, n);
                } else {
                    if (request.getParameter("annulla") != null) {
                        action_annulla(request, response, n);
                    } else {
                        if (request.getParameter("annulladomanda") != null) {
                            action_annulladomanda(request, response, n);
                        } else {
                            action_default(request, response, n);
                        }

                    }
                }
            }

        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Numero non valido");
            action_error(request, response);
        }
    }

    private void action_partecipazione(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            Email email = new Email();

            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(n);
            int id_task = Integer.parseInt(request.getParameter("taskid"));
            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(id_task);
            UtenteProgetto utenteProgetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgettoByProgetto(task.getProgetto().getId());
            Proposta proposta = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getPropostaByUtenteTask(n, id_task);

            if (request.getParameter("partecipazione").equals("accetta")) {

                proposta.setStato("accettato");
                task.setCollaboratoritot((task.getCollaboratoritot() + 1));
                ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeTask(task);

                String textAccettaSviluppatore = email.accettaPropostaSviluppatore(utente.getNome(), utente.getCognome(), utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), task.getNome());
                String textAccettaCoordinatore = email.accettaPropostaCoordinatore(utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), utente.getNome(), utente.getCognome(), task.getNome());
                email.Invia(utente.getEmail(), textAccettaSviluppatore);
                email.Invia(utenteProgetto.getUtente().getEmail(), textAccettaCoordinatore);
            } else {
                String textRifiutaSviluppatore = email.rifiutaPropostaSviluppatore(utente.getNome(), utente.getCognome(), utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), task.getNome());
                String textRifiutaCoordinatore = email.rifiutaPropostaCoordinatore(utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), utente.getNome(), utente.getCognome(), task.getNome());
                email.Invia(utente.getEmail(), textRifiutaSviluppatore);
                email.Invia(utenteProgetto.getUtente().getEmail(), textRifiutaCoordinatore);
                proposta.setStato("rifiutato");
            }

            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeProposta(proposta);
            response.sendRedirect("pannello?n=" + n);

        } catch (DataLayerException ex) {
            Logger.getLogger(Profilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pannello.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void action_domanda(HttpServletRequest request, HttpServletResponse response, int n) {
        //int id_task = Integer.parseInt(request.getParameter("taskid"));
        try {

            Email email = new Email();

            int id_taskdomanda = Integer.parseInt(request.getParameter("taskdomandaid"));
            int domandaid = Integer.parseInt(request.getParameter("utentedomanda"));
            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(domandaid);
            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(id_taskdomanda);
            UtenteProgetto utenteProgetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgettoByProgetto(task.getProgetto().getId());
            Domanda domanda = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getDomandaByUtenteTask(domandaid, id_taskdomanda);

            if (request.getParameter("domanda").equals("accetta")) {
                domanda.setStato("accettato");
                task.setCollaboratoritot((task.getCollaboratoritot() + 1));
                ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeTask(task);
                String textAccettaSviluppatore = email.accettaDomandaSviluppatore(utente.getNome(), utente.getCognome(), utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), task.getNome());
                String textAccettaCoordinatore = email.accettaDomandaCoordinatore(utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), utente.getNome(), utente.getCognome(), task.getNome());
                email.Invia(utente.getEmail(), textAccettaSviluppatore);
                email.Invia(utenteProgetto.getUtente().getEmail(), textAccettaCoordinatore);
            } else {
                domanda.setStato("rifiutato");
                String textRifiutaSviluppatore = email.rifiutaDomandaSviluppatore(utente.getNome(), utente.getCognome(), utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), task.getNome());
                String textRifiutaCoordinatore = email.rifiutaDomandaCoordinatore(utenteProgetto.getUtente().getNome(), utenteProgetto.getUtente().getCognome(), utente.getNome(), utente.getCognome(), task.getNome());
                email.Invia(utente.getEmail(), textRifiutaSviluppatore);
                email.Invia(utenteProgetto.getUtente().getEmail(), textRifiutaCoordinatore);

            }
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeDomanda(domanda);
            response.sendRedirect("pannello?n=" + n);
        } catch (DataLayerException ex) {
            Logger.getLogger(Profilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pannello.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            HttpSession s = SecurityLayer.checkSession(request);

            if (s != null && ((int) s.getAttribute("userid")) == n) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));

                request.setAttribute("n", n);
                TemplateResult res = new TemplateResult(getServletContext());
                Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(n);
                request.setAttribute("utente", utente);

                List<Task> tskatt = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getPropostaInAttesa(n);
                List<Task> tskdom = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getDomandaInAttesa(n);
                List<Domanda> dom = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getStatoDomanda(n);

                List<Utente> utentetsk = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteInvitato(n);
                List<Task> tskinvitato = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskInvitato(n);
                List<Proposta> stinvitato = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getStatoInvitato(n);
                List<Utente> utentedom = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteByDomandaEffettuata(n);
                List<Task> taskdom = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskByDomandaEffettuata(n);
                List<Task> tskliv = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskBySkillLivello(n);
                int size = tskliv.size();
                Boolean controllo = false;
                for (int i = 0; i < size; i++) {
                    controllo = false;
                    for (Task task : tskdom) {
                        if (tskliv.get(i).getId() == task.getId()) {
                            controllo = true;
                        }
                    }
                    if (controllo) {
                        tskliv.remove(i--);
                        size = tskliv.size();
                    }
                }

                request.setAttribute("tskliv", tskliv);
                request.setAttribute("utentedom", utentedom);
                request.setAttribute("taskdom", taskdom);
                request.setAttribute("stinvitato", stinvitato);
                request.setAttribute("tskinvitato", tskinvitato);
                request.setAttribute("utentetsk", utentetsk);

                request.setAttribute("dom", dom);
                request.setAttribute("tskdom", tskdom);
                request.setAttribute("tskatt", tskatt);

                res.activate("pannello.html", request, response);
            } else {
                request.setAttribute("message", "Sezione riservata!");
                action_error(request, response);
            }
        } catch (DataLayerException ex) {
            Logger.getLogger(ProgettoDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ProgettoDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_annulla(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            int id_proposta = Integer.parseInt(request.getParameter("annulla"));
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).deletePropostaById(id_proposta);
            response.sendRedirect("pannello?n=" + n);
        } catch (IOException ex) {
            Logger.getLogger(Pannello.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_annulladomanda(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            int id_proposta = Integer.parseInt(request.getParameter("annulladomanda"));
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).deleteDomandaById(id_proposta);
            response.sendRedirect("pannello?n=" + n);
        } catch (IOException ex) {
            Logger.getLogger(Pannello.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
