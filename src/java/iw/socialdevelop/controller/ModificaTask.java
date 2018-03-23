package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Tipo;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModificaTask extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            TemplateResult res = new TemplateResult(getServletContext());

            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(n);
            Tipo tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(task.getIdTipo());

            task.setTipo(tipo);
            request.setAttribute("task", task);
            Date inizio_task = task.getDataInizio();
            String inizo_task2 = inizio_task.toString();
            request.setAttribute("data_inizio", inizo_task2);
            Date fine_task = task.getDataFine();
            String fine_task2 = fine_task.toString();
            request.setAttribute("data_fine", fine_task2);
            res.activate("modificaTask.html", request, response);

        } catch (DataLayerException ex) {
            Logger.getLogger(ModificaProgetto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ModificaProgetto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_modifica(HttpServletRequest request, HttpServletResponse response, int n) throws IOException {

        try {
            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(n);
            Tipo tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(task.getIdTipo());
            String nome = request.getParameter("nometask");
            String descrizione = request.getParameter("descrizione");
            int collaboratori = SecurityLayer.checkNumeric(request.getParameter("numcollaboratori"));

            if (request.getParameter("datainizio") != null) {
                String data_inizio = request.getParameter("datainizio");
                data_inizio = data_inizio.replaceAll("-", "/");
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date d = df.parse(data_inizio);
                task.setDataInizio(d);
            }
            if (request.getParameter("datafine") != null) {
                String data_fine = request.getParameter("datafine");
                data_fine = data_fine.replaceAll("-", "/");
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                Date d2 = df.parse(data_fine);
                task.setDataFine(d2);
            }
            task.setTipo(tipo);
            task.setNome(nome);
            task.setDescrizione(descrizione);
            task.setCollaboratori(collaboratori);
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeTask(task);
            
            String p=request.getParameter("progetto");
            if (request.getParameter("modificatask") != null) {
                response.sendRedirect("progetto?n="+p);
            } else {
                response.sendRedirect("newskill?n=" + n);
            }

        } catch (DataLayerException ex) {
            Logger.getLogger(ModificaProgetto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ModificaTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //n è il numero del task
        request.setAttribute("page_title", "Modifica task");
        int n = SecurityLayer.checkNumeric(request.getParameter("n"));
        int progetto = SecurityLayer.checkNumeric(request.getParameter("progetto"));
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {
            try {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
                int userid = (int) request.getAttribute("userid");

                UtenteProgetto ut = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgetto(userid, progetto);
                if (ut != null && ut.getRuolo().equals("admin")) {
                    if (request.getParameter("modificatask") != null || request.getParameter("modificaskill") != null) {
                        action_modifica(request, response, n);
                    } else {
                        action_default(request, response, n);
                    }
                } else {
                    request.setAttribute("exception", new Exception("Non Sei il coordinatore del progetto!!"));
                    action_error(request, response);
                }
            } catch (DataLayerException ex) {
                Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ModificaTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("exception", new Exception("Effettua la login per creare un nuovo task"));
            action_error(request, response);
        }

    }

}
