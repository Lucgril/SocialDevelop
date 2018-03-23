package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TerminiDelServizio extends SocialDevelopBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("TerminiServizio.html", request, response);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(TerminiDelServizio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
