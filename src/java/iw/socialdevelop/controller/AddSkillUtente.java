package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteSkill;
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

public class AddSkillUtente extends SocialDevelopBaseController {

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

                int userid = (int) (s.getAttribute("userid"));
                int n = SecurityLayer.checkNumeric(request.getParameter("n"));

                request.setAttribute("logged", s.getAttribute("logged"));
                request.setAttribute("userid", s.getAttribute("userid"));

                Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getUtente(userid);

                Enumeration<String> e = request.getParameterNames();
                List<String> ll = Collections.list(e);
                int size = ll.size();

                for (int i = 0; i < size - 2; i++) {
                    UtenteSkill utenteSkill = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createUtenteSkill();
                    utenteSkill.setUtente(utente);
                    int skillid = SecurityLayer.checkNumeric(request.getParameter((String) ll.get(i)));
                    int livello = SecurityLayer.checkNumeric(request.getParameter((String) ll.get(++i)));
                    Skill skill = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getSkill(skillid);
                    utenteSkill.setSkill(skill);
                    utenteSkill.setLivello(livello);
                    ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeUtenteSkill(utenteSkill);
                    response.sendRedirect("profilo?n=" + userid);
                }
            }
        } catch (DataLayerException ex) {
            Logger.getLogger(AddSkillUtente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddSkillUtente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
