package iw.socialdevelop.controller;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    public void Invia(String email_destinatario, String text) {

        final String username = "socialdevelop17@gmail.com";
        final String password = "davideunivaq";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lucag.8595@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email_destinatario));
            message.setSubject("SocialDevelop");
            message.setText(text
                    + "\n\n Cordiali saluti,\n lo staff di SocialDevelop!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String creaPropostaSviluppatore(String nome, String cognome, String nome_coordinatore, String cognome_coordinatore, String task) {
        String text = "Salve" + " " + nome + " " + cognome + ",\n Il coordinatore" + " " + nome_coordinatore + " "
                + cognome_coordinatore + " " + "ti ha invitato a far parte del suo task" + " " + task;

        return text;
    }

    public String creaInvitoCoordinatore(String nome_coordinatore, String cognome_coordinatore, String nome, String cognome, String task) {
        String text = "Salve" + " " + nome_coordinatore + " " + cognome_coordinatore + ",\n Hai appena mandato un invito a" + " " + nome + " "
                + cognome + " " + "per far parte del tuo task" + " " + task;

        return text;
    }

    public String creaOffertaSviluppatore(String nome, String cognome, String nome_coordinatore, String cognome_coordinatore, String task) {
        String text = "Salve" + " " + nome + " " + cognome + ",\n Hai appena mandato una domanda a" + " " + nome_coordinatore + " "
                + cognome_coordinatore + " " + "per far parte del suo task" + " " + task;

        return text;
    }

    public String creaDomandaCoordinatore(String nome_coordinatore, String cognome_coordinatore, String nome, String cognome, String task) {
        String text = "Salve" + " " + nome_coordinatore + " " + cognome_coordinatore + ",\n Hai appena ricevuto una domanda da" + " " + nome + " "
                + cognome + " " + "per far parte del tuo task" + " " + task;

        return text;
    }

    public String creaRecensione(String nome_coordinatore, String cognome_coordinatore, String nome, String cognome, String task, int valutazione) {
        String text = "Salve" + " " + nome + " " + cognome + ",\n Hai appena ricevuto una valutazione di" + " " + valutazione + "/5" + " " + "da parte di" + " " + nome_coordinatore + " "
                + cognome_coordinatore + " " + "sul task" + " " + task;

        return text;
    }

    public String accettaPropostaSviluppatore(String nome, String cognome, String nome_coordinatore, String cognome_coordinatore, String task) {
        String text = "Salve" + " " + nome + " " + cognome + ",\n Hai appena accettato una proposta di" + " " + nome_coordinatore + " "
                + cognome_coordinatore + " " + "per far parte del tuo task" + " " + task;

        return text;
    }

    public String accettaPropostaCoordinatore(String nome_coordinatore, String cognome_coordinatore, String nome, String cognome, String task) {
        String text = "Salve" + " " + nome_coordinatore + " " + cognome_coordinatore + ",\n" + nome + " " + cognome + " " + "ha appena accettato una tua proposta per far parte del tuo task" + " " + task;

        return text;
    }

    public String accettaDomandaCoordinatore(String nome_coordinatore, String cognome_coordinatore, String nome, String cognome, String task) {
        String text = "Salve" + " " + nome_coordinatore + " " + cognome_coordinatore + ",\n Hai appena accettato una domanda di" + " " + nome + " "
                + cognome + " " + "per far parte del tuo task" + " " + task;

        return text;
    }

    public String accettaDomandaSviluppatore(String nome, String cognome, String nome_coordinatore, String cognome_coordinatore, String task) {
        String text = "Salve" + " " + nome + " " + cognome + ",\n" + nome_coordinatore + " " + cognome_coordinatore + " " + "ha appena accettato una tua domanda per far parte del suo task" + " " + task;

        return text;
    }

    public String rifiutaPropostaSviluppatore(String nome, String cognome, String nome_coordinatore, String cognome_coordinatore, String task) {
        String text = "Salve" + " " + nome + " " + cognome + ",\n Hai appena rifiutato una proposta di" + " " + nome_coordinatore + " "
                + cognome_coordinatore + " " + "per far parte del tuo task" + " " + task;

        return text;
    }

    public String rifiutaPropostaCoordinatore(String nome_coordinatore, String cognome_coordinatore, String nome, String cognome, String task) {
        String text = "Salve" + " " + nome_coordinatore + " " + cognome_coordinatore + ",\n" + nome + " " + cognome + " " + "ha appena rifiutato una tua proposta per far parte del tuo task" + " " + task;

        return text;
    }

    public String rifiutaDomandaCoordinatore(String nome_coordinatore, String cognome_coordinatore, String nome, String cognome, String task) {
        String text = "Salve" + " " + nome_coordinatore + " " + cognome_coordinatore + ",\n Hai appena rifiutato una domanda di" + " " + nome + " "
                + cognome + " " + "per far parte del tuo task" + " " + task;

        return text;
    }

    public String rifiutaDomandaSviluppatore(String nome, String cognome, String nome_coordinatore, String cognome_coordinatore, String task) {
        String text = "Salve" + " " + nome + " " + cognome + ",\n" + nome_coordinatore + " " + cognome_coordinatore + " " + "ha appena rifiutato una tua domanda per far parte del suo task" + " " + task;

        return text;
    }
    
     public String password(String nome, String cognome, String password) {
        String text = "Salve" + " " + nome + " " + cognome + ",\n La sua  password è" + " " + password + ".";

        return text;
    }
}
