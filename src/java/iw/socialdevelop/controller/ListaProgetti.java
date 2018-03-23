package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.result.TemplateResultPager;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListaProgetti extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private List<Progetto> getResults(HttpServletRequest request, HttpServletResponse response, int page) throws DataLayerException {

        List<Progetto> resultlist = new ArrayList();
        List<Progetto> progetti = new ArrayList();

        if (((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetti() != null) {
            progetti = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getProgetti();
        }

        int totpage;

        if (progetti.size() % 5 == 0) {
            totpage = progetti.size() / 5;
        } else {
            totpage = (progetti.size() / 5) + 1;
        }

        request.setAttribute("totpages", totpage);
        int x = page * 5;

        if (x > progetti.size()) {
            x = progetti.size();
        }

        for (int i = 5 * (page - 1); i < x; ++i) {
            resultlist.add(progetti.get(i));
        }

        return resultlist;
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession s = SecurityLayer.checkSession(request);

        if (s != null) {

            request.setAttribute("logged", s.getAttribute("logged"));
            request.setAttribute("userid", s.getAttribute("userid"));
        }

        try {
            int page = 1;

            if (request.getParameter("page") != null) {
                page = SecurityLayer.checkNumeric(request.getParameter("page"));
            }

            request.setAttribute("progetti", getResults(request, response, page));
            request.setAttribute("curpage", page);

            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("listaprogetti2.html", request, response);

        } catch (DataLayerException ex) {
            Logger.getLogger(ListaProgetti.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ListaProgetti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void action_json(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int page = 1;

            if (request.getParameter("page") != null) {
                page = SecurityLayer.checkNumeric(request.getParameter("page"));
            }

            request.setAttribute("progetti", getResults(request, response, page));
            request.setAttribute("curpage", page);

            TemplateResultPager res = new TemplateResultPager(getServletContext());
            res.activate("simplepager.json", request, response);

        } catch (DataLayerException ex) {
            Logger.getLogger(ListaProgetti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        request.setAttribute("page_title", "SocialDevelop");

        if (request.getParameter("json") != null) {
            try {
                action_json(request, response);
            } catch (IOException ex) {
                Logger.getLogger(ListaProgetti.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                action_default(request, response);
            } catch (IOException ex) {
                Logger.getLogger(ListaProgetti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
