package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EliminaSviluppatore extends SocialDevelopBaseController {

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
            int taskid = SecurityLayer.checkNumeric(request.getParameter("taskid"));
            int userid = SecurityLayer.checkNumeric(request.getParameter("userid"));
            Task task = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).getTask(taskid);

            if (request.getParameter("domanda") != null) {
                ((SocialDevelopDataLayer) request.getAttribute("datalayer")).deleteDomanda(taskid, userid);

            } else {
                ((SocialDevelopDataLayer) request.getAttribute("datalayer")).deleteProposta(taskid, userid);
            }

            task.setCollaboratoritot((task.getCollaboratoritot()) - 1);
            ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeTask(task);

            response.sendRedirect("task?n=" + taskid);
        } catch (IOException ex) {
            Logger.getLogger(EliminaSviluppatore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataLayerException ex) {
            Logger.getLogger(EliminaSviluppatore.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
