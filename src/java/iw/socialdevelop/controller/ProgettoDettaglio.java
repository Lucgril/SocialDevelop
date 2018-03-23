package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Domanda;
import iw.socialdevelop.data.model.Messaggio;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.Proposta;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Tipo;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProgettoDettaglio extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {

        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int n) {

        try {

            HttpSession s = SecurityLayer.checkSession(request);
            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
            }
            request.setAttribute("n", n);

            TemplateResult res = new TemplateResult(getServletContext());
            Progetto progetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetto(n);
            List<Task> tasks = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(progetto);
            List<Messaggio> messaggi = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getMessaggi(progetto);
            request.setAttribute("progetto", progetto);
            request.setAttribute("page_title", progetto.getNome());

            request.setAttribute("messaggi", messaggi);

            for (Task t : tasks) {
                Tipo tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(t.getIdTipo());
                t.setTipo(tipo);

            }
            request.setAttribute("tasks", tasks);
            if (s != null) {

                List<Domanda> domanda = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtentiPartecipantiProggettoDomanda((int) s.getAttribute("userid"), n);
                List<Proposta> proposta = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtentiPartecipantiProggettoProp((int) s.getAttribute("userid"), n);

                if (domanda.size() > 0 || proposta.size() > 0) {
                    request.setAttribute("partecipa", true);
                }
                UtenteProgetto utenteprogetto = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgetto((int) s.getAttribute("userid"), n);
                if (utenteprogetto != null && utenteprogetto.getRuolo().equals("admin")) {
                    request.setAttribute("adminp", true);
                }
            }

            res.activate("progettoDettaglio.html", request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(ProgettoDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ProgettoDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        int n;

        try {

            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            request.setAttribute("riferimento", "progetto?n=" + n);

            if (request.getParameter("tipo") != null) {
                action_messaggio(request, response, n);
            }
            action_default(request, response, n);

        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Numero non valido");
            action_error(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Progetto dettaglio servlet";
    }// </editor-fold>

    private void action_messaggio(HttpServletRequest request, HttpServletResponse response, int n) {
        try {
            HttpSession s = SecurityLayer.checkSession(request);
            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
            }
            int userid = (int) request.getAttribute("userid");
            Messaggio messaggio = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createMessaggio();
            messaggio.setProgetto(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetto(n));
            messaggio.setUtente(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(userid));
            messaggio.setTipo(request.getParameter("tipo"));
            messaggio.setTesto(request.getParameter("messaggio"));
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeMessaggio(messaggio);
        } catch (DataLayerException ex) {
            Logger.getLogger(ProgettoDettaglio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
