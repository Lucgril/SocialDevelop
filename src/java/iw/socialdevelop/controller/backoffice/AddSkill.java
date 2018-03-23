package iw.socialdevelop.controller.backoffice;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResultBackOffice;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.controller.SocialDevelopBaseController;
import iw.socialdevelop.data.impl.TipoSkillImpl;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Tipo;
import iw.socialdevelop.data.model.TipoSkill;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddSkill extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_add(HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException {
        TemplateResultBackOffice res = new TemplateResultBackOffice(getServletContext());
        String tipo = (String) request.getParameter("tipo");
        String skill = (String) request.getParameter("skill");
        Skill skill2 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createSkill();
        skill2.setNome(skill);
        int id_skill = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeSkill(skill2);
        Skill skill3 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkill(id_skill);

        Tipo tipo2 = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipoByNome(tipo);
        TipoSkill tipoSkill = new TipoSkillImpl(((SocialDevelopDataLayer) request.getAttribute("datalayer")));
        tipoSkill.setSkill(skill3);
        tipoSkill.setTipo(tipo2);
        ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeTipoSkill(tipoSkill);
        response.sendRedirect("index");

    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, DataLayerException {
        TemplateResultBackOffice res = new TemplateResultBackOffice(getServletContext());
        request.setAttribute("page_title", "Aggiungi Skill");
        List<Tipo> tipi = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTipi();
        request.setAttribute("tipi", tipi);
        try {
            res.activate("addSkill.html", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(AddSkill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {
            String a = (String) s.getAttribute("amministratore");
            if (a.equals("T")) {
                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("nome", s.getAttribute("nome"));
                request.setAttribute("cognome", s.getAttribute("cognome"));
                try {
                    if (request.getParameter("skill") != null) {
                        action_add(request, response);
                    } else {
                        action_default(request, response);
                    }
                } catch (IOException ex) {
                    request.setAttribute("exception", ex);
                    action_error(request, response);
                } catch (DataLayerException ex) {
                    Logger.getLogger(AddSkill.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                request.setAttribute("exception", new Exception("Sezione riservata! Non Sei l'amministratore!"));
                action_error(request, response);
            }
        } else {
            request.setAttribute("exception", new Exception("Sezione riservata! Non sei loggato!"));
            action_error(request, response);
        }

    }

}
