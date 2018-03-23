package iw.socialdevelop.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import iw.framework.data.DataLayerException;
import iw.socialdevelop.data.model.Files;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Utente;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class registrati extends SocialDevelopBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    protected void action_default(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, TemplateManagerException, DataLayerException, ParseException, NoSuchAlgorithmException {

        try {
            String email = request.getParameter("email");
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Registrato");

            String a = request.getParameter("datan");

            String dd = a.replaceAll("-", "/");

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            Date d = df.parse(dd);

            Utente utenteNew = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createUtente();

            utenteNew.setNome(request.getParameter("nome"));
            utenteNew.setCognome(request.getParameter("cognome"));
            utenteNew.setEmail(email);
            utenteNew.setPassword(request.getParameter("password"));
            utenteNew.setAmministratore("F");
            utenteNew.setTelefono(request.getParameter("telefono"));
            utenteNew.setDataNascita(d);

            int id = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeUtente(utenteNew);

            int fileID = 0;
            Part file_to_upload = request.getPart("filetoupload");

            if (file_to_upload.getSize() > 0) {

                MessageDigest md = MessageDigest.getInstance("SHA-1");

                File uploaded_file = File.createTempFile("curriculum_", "", new File(getServletContext().getInitParameter("uploads.directory")));
                try (InputStream is = file_to_upload.getInputStream();
                        OutputStream os = new FileOutputStream(uploaded_file)) {
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = is.read(buffer)) > 0) {

                        md.update(buffer, 0, read);
                        os.write(buffer, 0, read);
                    }
                }

                byte[] digest = md.digest();
                String sdigest = "";
                for (byte b : digest) {
                    sdigest += String.valueOf(b);
                }

                Files filesNew = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).createFiles();
                filesNew.setNome("curriculum_" + utenteNew.getId());
                filesNew.setTipo(file_to_upload.getContentType());
                filesNew.setSize((int) file_to_upload.getSize());
                filesNew.setLocalFile(uploaded_file.getName());
                filesNew.setDigest(sdigest);
                filesNew.setIdUtente(id);

                ((SocialDevelopDataLayer) request.getAttribute("datalayer")).storeFiles(filesNew);
            }

            Utente utente = ((SocialDevelopDataLayer) request.getAttribute("datalayer")).login(utenteNew.getPassword(), utenteNew.getEmail());
            SecurityLayer.createSession(request, utente.getNome(), utente.getCognome(), utente.getId(), utente.getAmministratore());
            request.setAttribute("page_title", "Benvenuto");
            HttpSession s = request.getSession(true);
            request.setAttribute("logged", s.getAttribute("logged"));
            request.setAttribute("nome", s.getAttribute("nome"));
            request.setAttribute("cognome", s.getAttribute("cognome"));
            response.sendRedirect("profilo?n=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(registrati.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            action_default(request, response);

        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);

        } catch (TemplateManagerException ex) {
            Logger.getLogger(registrati.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataLayerException ex) {
            Logger.getLogger(registrati.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(registrati.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(registrati.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
