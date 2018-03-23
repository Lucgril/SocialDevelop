package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.TaskSkill;
import iw.socialdevelop.data.model.Tipo;
import iw.socialdevelop.data.model.TipoSkill;
import iw.socialdevelop.data.model.UtenteProgetto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewSkill extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        int n;

        try {
            //n è l'id del task per chui vogliamo aggiungere skill
            n = SecurityLayer.checkNumeric(request.getParameter("n"));
            request.setAttribute("n", n);
            request.setAttribute("page_title", "New Skill");
            HttpSession s = SecurityLayer.checkSession(request);
            if (s != null) {
                try {
                    request.setAttribute("logged", s.getAttribute("logged"));
                    request.setAttribute("userid", s.getAttribute("userid"));
                    int userid = (int) request.getAttribute("userid");

                    Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(n);
                    s.setAttribute("idtask", n);
                    UtenteProgetto up = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtenteProgetto(userid, task.getProgetto().getId());

                    if (up != null && up.getRuolo().equals("admin")) {
                        if (request.getParameter("store") != null) {
                            action_store(request, response, n);
                        } else {
                            if (request.getParameter("eliminaskill") != null) {
                                action_elimina(request, response);
                            }
                            action_default(request, response, n);
                        }
                    } else {
                        request.setAttribute("exception", new Exception("Non Sei il coordinatore del progetto!!"));
                        action_error(request, response);
                    }
                } catch (DataLayerException ex) {
                    Logger.getLogger(NewSkill.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Numero non valido");
            action_error(request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response, int n) {

        try {
            TemplateResult res = new TemplateResult(getServletContext());

            List<TaskSkill> taskskill = new ArrayList();
            taskskill = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskSkillByTask(n);
            List<Skill> s = new ArrayList();
            request.setAttribute("taskskill", taskskill);

            for (TaskSkill t : taskskill) {
                s.add(t.getSkill());
            }
            request.setAttribute("skill", s);

            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createTask();
            Tipo tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createTipo();
            task.copyFrom(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(n));

            tipo = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipo(task.getIdTipo());
            List<TipoSkill> tiposkill = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipoSkillByTipo(tipo);

            List<Skill> skill = new ArrayList();
            for (TipoSkill t : tiposkill) {
                skill.add(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkill(t.getSkill().getId()));
            }
            int count = 0;
            String url = "search?keywords=&author=admin";
            for (TaskSkill t : taskskill) {
                url = url + "&sk" + count + "=" + t.getSkill().getId() + "&livelloskill" + count + "=" + t.getLivello();
                count++;
            }

            request.setAttribute("url", url);
            request.setAttribute("selectskill", skill);
            request.setAttribute("taskid", n);
            res.activate("newskill.html", request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(NewSkill.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(NewSkill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_store(HttpServletRequest request, HttpServletResponse response, int n) {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            int idt = n;
            int idsk = SecurityLayer.checkNumeric(request.getParameter("listaskill"));
            int livello = SecurityLayer.checkNumeric(request.getParameter("livelloskill"));

            List<TaskSkill> ts = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTaskSkillByTask(idt);
            for (TaskSkill t : ts) {
                if (t.getSkill().getId() == idsk) {
                    request.setAttribute("exception", new Exception("Questa Skill  è già stata inserita!"));
                    action_error(request, response);
                    return;
                }
            }

            TaskSkill taskskill = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createTaskSkill();
            taskskill.setTask(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(idt));
            taskskill.setSkill(((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkill(idsk));
            taskskill.setLivello(livello);
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeTaskSkill(taskskill);
            action_default(request, response, n);
        } catch (DataLayerException ex) {
            Logger.getLogger(NewSkill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_elimina(HttpServletRequest request, HttpServletResponse response) {
        int idtaskskill = SecurityLayer.checkNumeric(request.getParameter("tid"));
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).deleteTaskSkill(idtaskskill);
    }

}
