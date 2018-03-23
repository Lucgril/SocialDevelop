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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewTask extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_newTask(HttpServletRequest request, HttpServletResponse response, int n) {

        try {

            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createTask();
            task.setNome(request.getParameter("nometask"));
            task.setDescrizione(request.getParameter("descrizione"));

            String a = request.getParameter("datainizio");
            String c = request.getParameter("datafine");
            String b = a.replaceAll("-", "/");
            String f = c.replaceAll("-", "/");
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date d = df.parse(b);
            DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
            Date d2 = df2.parse(f);
            task.setDataInizio(d);
            task.setDataFine(d2);
            task.setCollaboratori(SecurityLayer.checkNumeric(request.getParameter("numcollaboratori")));
            task.setProgetto(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetto(n));
            task.setTipo(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(SecurityLayer.checkNumeric(request.getParameter("tipo"))));

            int idt = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeTask(task);

            response.sendRedirect("newskill?n=" + idt);
        } catch (DataLayerException ex) {
            Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            TemplateResult res = new TemplateResult(getServletContext());

            List<Tipo> tipi = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipi();
            request.setAttribute("tipi", tipi);

            res.activate("newtask.html", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataLayerException ex) {
            Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        int n = SecurityLayer.checkNumeric(request.getParameter("n"));
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {
            try {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));
                request.setAttribute("page_title", "Nuovo Task");
                int userid = (int) request.getAttribute("userid");

                UtenteProgetto ut = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgetto(userid, n);
                if (ut != null && ut.getRuolo().equals("admin")) {
                    if (request.getParameter("crea") != null) {
                        action_newTask(request, response, n);
                    } else {
                        action_default(request, response, n);
                    }
                } else {
                    request.setAttribute("exception", new Exception("Non Sei il coordinatore del progetto!!"));
                    action_error(request, response);
                }
            } catch (DataLayerException ex) {
                Logger.getLogger(NewTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("exception", new Exception("Effettua la login per creare un nuovo task"));
            action_error(request, response);
        }
    }

}
