package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Domanda;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.Proposta;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Tipo;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteSkill;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Profilo extends SocialDevelopBaseController {

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
            request.setAttribute("userid", 0);
            if (s != null) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
            }

            request.setAttribute("n", n);
            TemplateResult res = new TemplateResult(getServletContext());
            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(n);
            request.setAttribute("utente", utente);

            List<Progetto> pg = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgettoAdmin(n);

            List<Tipo> tp = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipi();
            List<Skill> sk1 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkill();
            List<Task> tsk = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskByUtente(n);
            List<Task> tskdcom = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskByDomanda(n);
            List<Task> tskpcom = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskByProposta(n);
            List<Domanda> valdom = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getValutazioneByDomanda(n);
            List<Proposta> valpro = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getValutazioneByProposta(n);
            List<UtenteSkill> skillutente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkillByUtente(n);
            // List<Skill> skutente = ((SocialDevelopDataLayer)request.getAttribute("datalayer")).getSkillByUtente(n);
            List<UtenteSkill> valutente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getLivelloByUtente(n);
            request.setAttribute("valutente", valutente);
            //request.setAttribute("skutente", skutente);
            request.setAttribute("valdom", valdom);
            request.setAttribute("valpro", valpro);
            request.setAttribute("tskdcom", tskdcom);
            request.setAttribute("tskpcom", tskpcom);
            request.setAttribute("tsk", tsk);
            request.setAttribute("tipi", tp);
            request.setAttribute("skill1", sk1);
            request.setAttribute("skillutente", skillutente);

            request.setAttribute("pg", pg);
            res.activate("profilo.html", request, response);

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
            request.setAttribute("riferimento", "profilo?n=" + n);
            request.setAttribute("page_title", "Profilo");
            if (request.getParameter("partecipazione") != null) {
                action_partecipazione(request, response, n);
            }
            action_default(request, response, n);

        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Numero non valido");
            action_error(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Profilo servlet";
    }// </editor-fold>

    private void action_partecipazione(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            int id_task = Integer.parseInt(request.getParameter("taskid"));

            Proposta proposta = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getPropostaByUtenteTask(n, id_task);

            if (request.getParameter("partecipazione").equals("accetta")) {
                proposta.setStato("accettato");
            } else {
                proposta.setStato("rifiutato");
            }
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeProposta(proposta);
        } catch (DataLayerException ex) {
            Logger.getLogger(Profilo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
