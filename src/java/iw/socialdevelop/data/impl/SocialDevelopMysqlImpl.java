package iw.socialdevelop.data.impl;

import it.univaq.f4i.iw.framework.result.StreamResult;
import iw.framework.data.DataLayerException;
import iw.framework.data.DataLayerMysqlImpl;
import iw.socialdevelop.data.model.Domanda;
import iw.socialdevelop.data.model.Files;
import iw.socialdevelop.data.model.Messaggio;
import iw.socialdevelop.data.model.Progetto;
import iw.socialdevelop.data.model.Proposta;
import iw.socialdevelop.data.model.Skill;
import iw.socialdevelop.data.model.SocialDevelopDataLayer;
import iw.socialdevelop.data.model.Task;
import iw.socialdevelop.data.model.Tipo;
import iw.socialdevelop.data.model.Utente;
import iw.socialdevelop.data.model.UtenteProgetto;
import iw.socialdevelop.data.model.UtenteSkill;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;
import iw.socialdevelop.data.model.TaskSkill;
import iw.socialdevelop.data.model.TipoSkill;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SocialDevelopMysqlImpl extends DataLayerMysqlImpl implements SocialDevelopDataLayer {

    private PreparedStatement iUtente, dUtente, uUtente, sUtenti;
    private PreparedStatement iProgetto, uProgetto, dProgetto;
    private PreparedStatement iSkill, uSkill, dSkill;
    private PreparedStatement iTipo, uTipo, dTipo, sTipoByNome;
    private PreparedStatement iTipoSkill, uTipoSkill, dTipoSkillByTipo, dTipoSkillBySkill;
    private PreparedStatement iTask, dTask, uTask;
    private PreparedStatement iDomanda, uDomanda, dDomanda, dDomandaById;
    private PreparedStatement iProposta, uProposta, dProposta, dPropostaById;
    private PreparedStatement iMessaggio, uMessaggio, dMessaggio;
    private PreparedStatement iUtenteProgetto, uUtenteProgetto, dUtenteProgetto, sUtenteProgettoByUeP, sUtenteProgettoByProgetto;
    private PreparedStatement iTaskSkill, dTaskSkill, uTaskSkill, sTaskTipoSkillByTask;
    private PreparedStatement iUtenteSkill, uUtenteSkill;
    private PreparedStatement sTaskById, sTaskApertiByProgetto, sProgettoById, sUtenteById, sPropostaById, sDomandaById, sSkillById, sTipoById, sMessaggioById, sUtenteProgettoById, sUtenteSkillById, sUtenteByEmail, sTipoSkillById;
    private PreparedStatement sProgetti, sTask, sMessaggi, sMessaggiPubblici, sUtenteProgetto, sTaskAperti, sSkill, sTipi, sSkillByTipo;
    private PreparedStatement sUtenteBySkill, sUtenteBySkillLivello, sTaskTipoSkill, sTipoSkillByTipo;
    private PreparedStatement sDomandeByUtente, sProposteByUtente, sDomandaByUtenteTask;
    private PreparedStatement sFilesByUtente, iFiles, uFiles, sFilesById;
    private PreparedStatement sProgettoByAdmin, sTaskByUtente, sPropostaInAttesa, sDomandaInAttesa, sDomandaUtenti, sPropostaUtenti;
    private PreparedStatement sStatoDomanda, sTaskByDomanda, sTaskByProposta, sValutazioneByDomanda, sValutazioneByProposta, sPropostaByUtenteTask;
    private PreparedStatement sDomandaUtentiAccettateConValutazione, sDomandaUtentiAccettateSenzaValutazione, sPropostaUtentiAccettateConValutazione, sPropostaUtentiAccettateSenzaValutazione, sLivelloByUtente, sSkillByUtente, sTaskBySkillLivello;
    private PreparedStatement sUtenteByDomandaEffettuata, sTaskByDomandaEffettuata, sUtenteInvitato, sTaskInvitato, sStatoInvitato;
    private PreparedStatement sUtentiPartecipantiProgettoProp, sUtentiPartecipantiProgettoDomanda;

    public SocialDevelopMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super(datasource);
    }

    @Override
    public void init() throws DataLayerException {
        try {
            super.init();

            iUtente = connection.prepareStatement("INSERT INTO utente (nome,cognome,email,password,telefono,data_nascita,amministratore) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            dUtente = connection.prepareStatement("DELETE FROM utente WHERE id=?");
            sUtenti = connection.prepareStatement("SELECT * FROM utente");
            uUtente = connection.prepareStatement("UPDATE utente SET nome=?, cognome=?,  email=?, password=?, telefono=?, dataNascita=?, amministratore=? WHERE id=?");

            iProgetto = connection.prepareStatement("INSERT INTO progetto (nome,descrizione) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uProgetto = connection.prepareStatement("UPDATE progetto SET nome=?, descrizione=? WHERE id=?");
            dProgetto = connection.prepareStatement("DELETE FROM progetto WHERE id=?");

            iTask = connection.prepareStatement("INSERT INTO task (nome,descrizione,collaboratori,data_inizio,data_fine,progetto,tipo, collaboratoritot) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            dTask = connection.prepareStatement("DELETE FROM task WHERE id=?");
            uTask = connection.prepareStatement("UPDATE task SET nome=?, descrizione=?,collaboratori=?,data_inizio=?,data_fine=?,progetto=?, collaboratoritot=?  WHERE id=?");

            iTipo = connection.prepareStatement("INSERT INTO tipo (nome) VALUE(?)", Statement.RETURN_GENERATED_KEYS);
            uTipo = connection.prepareStatement("UPDATE tipo SET nome=? WHERE id=?");
            dTipo = connection.prepareStatement("DELETE FROM tipo WHERE id=?");
            sTipoById = connection.prepareStatement("SELECT * FROM tipo WHERE id=?");
            sTipoByNome = connection.prepareStatement("SELECT * FROM tipo WHERE nome=?");

            iTipoSkill = connection.prepareStatement("INSERT INTO tipo_skill(tipo,skill) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            uTipoSkill = connection.prepareStatement("UPDATE tipo_skill set tipo=?, skill=? WHERE id=?");
            dTipoSkillByTipo = connection.prepareStatement("DELETE FROM tipo_skill WHERE tipo=?");
            dTipoSkillBySkill = connection.prepareStatement("DELETE FROM tipo_skill WHERE skill=?");

            iSkill = connection.prepareStatement("INSERT INTO skill (nome) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            uSkill = connection.prepareStatement("UPDATE skill SET nome=? WHERE id=?");
            dSkill = connection.prepareStatement("DELETE FROM skill WHERE id=?");

            iDomanda = connection.prepareStatement("INSERT INTO domanda (utente,task,data,stato) VALUES(?,?,CURDATE(),?)", Statement.RETURN_GENERATED_KEYS);
            uDomanda = connection.prepareStatement("UPDATE domanda SET valutazione=?, stato=? WHERE id=?");
            dDomanda = connection.prepareStatement("DELETE FROM domanda WHERE task=? AND utente=?");
            dDomandaById = connection.prepareStatement("DELETE FROM domanda WHERE id=?");

            iProposta = connection.prepareStatement("INSERT INTO proposta (utente,task,data,stato) VALUES(?,?,CURDATE(),?)", Statement.RETURN_GENERATED_KEYS);
            uProposta = connection.prepareStatement("UPDATE proposta SET valutazione=?, stato=? WHERE id=?");
            dProposta = connection.prepareStatement("DELETE FROM proposta WHERE task=? AND utente=?");
            dPropostaById = connection.prepareStatement("DELETE FROM proposta WHERE id=?");

            iMessaggio = connection.prepareStatement("INSERT INTO messaggio (utente,progetto,testo,data,tipo) VALUES(?,?,?,CURDATE(),?)", Statement.RETURN_GENERATED_KEYS);
            uMessaggio = connection.prepareStatement("UPDATE messaggio SET id_utente=?,id_progetto=?, testo=?, data=?, tipo=? WHERE id=? ");
            dMessaggio = connection.prepareStatement("DELETE FROM messaggio WHERE id=?");

            iUtenteProgetto = connection.prepareStatement("INSERT INTO utente_progetto (utente,progetto,ruolo) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtenteProgetto = connection.prepareStatement("UPDATE utente_progetto SET ruolo=? WHERE utente=? AND progetto=?");
            dUtenteProgetto = connection.prepareStatement("DELETE FROM utente_progetto WHERE utente=? AND progetto=?");
            sUtenteProgettoByUeP = connection.prepareStatement("SELECT * FROM utente_progetto WHERE utente=? AND progetto=?");
            sUtenteProgettoByProgetto = connection.prepareStatement("SELECT * FROM utente_progetto WHERE progetto=? ANd ruolo='admin'");

            iUtenteSkill = connection.prepareStatement("INSERT INTO utente_skill (utente,skill,livello) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uUtenteSkill = connection.prepareStatement("UPDATE utente_skill SET livello=? WHERE id_skill=? AND id_utente=?");

            iTaskSkill = connection.prepareStatement("INSERT INTO task_skill (task,skill,livello) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            dTaskSkill = connection.prepareStatement("DELETE FROM task_skill WHERE id=?");
            uTaskSkill = connection.prepareStatement("UPDATE task_skill SET livello=? WHERE task=?, skill=?");
            sTaskTipoSkillByTask = connection.prepareStatement("SELECT * FROM task_skill WHERE task=?");

            sProgetti = connection.prepareStatement("SELECT * FROM progetto ORDER BY progetto.id DESC");
            sTask = connection.prepareStatement("SELECT * FROM task WHERE progetto=?");
            sTaskById = connection.prepareStatement("SELECT * FROM  task WHERE id=?");
            sSkill = connection.prepareStatement("SELECT * FROM skill");
            sTipi = connection.prepareStatement("SELECT * FROM tipo");
            sUtenteProgetto = connection.prepareStatement("SELECT * FROM utente_progetto WHERE  progetto=?");
            sMessaggi = connection.prepareStatement("SELECT * FROM messaggio WHERE progetto=?");
            sMessaggiPubblici = connection.prepareStatement("SELECT * FROM messaggio WHERE progetto=? AND tipo='pubblico'");
            sTaskAperti = connection.prepareStatement("SELECT * FROM task WHERE collaboratori>0");

            sUtenteBySkill = connection.prepareStatement("SELECT u1.nome,u1.cognome FROM SKILL s JOIN utente_skill u ON s.id=u.skill JOIN utente u1 ON u.utente=u1.id WHERE s.id=?");
            sUtenteBySkillLivello = connection.prepareStatement("SELECT u1.id,u1.nome,u1.cognome, u1.email, u1.password,u1.telefono, u1.data_nascita,u1.amministratore FROM utente_skill u JOIN utente u1 ON u.utente=u1.id WHERE u.skill=? AND u.livello>=?");

            sUtenteById = connection.prepareStatement("SELECT * FROM utente WHERE id=?");
            sUtenteByEmail = connection.prepareStatement("SELECT * FROM utente WHERE email=?");
            sTaskById = connection.prepareStatement("SELECT * FROM task WHERE id=?");
            sTaskApertiByProgetto = connection.prepareStatement("SELECT * FROM task WHERE id_progetto=? AND collaboratori>0");
            sProgettoById = connection.prepareStatement("SELECT * FROM progetto WHERE id=?");
            sPropostaById = connection.prepareStatement("SELECT * FROM proposta WHERE id=?");
            sProposteByUtente = connection.prepareStatement("SELECT * FROM proposta WHERE utente=?");
            sDomandaById = connection.prepareStatement("SELECT * FROM domanda WHERE id=?");
            sDomandeByUtente = connection.prepareStatement("SELECT * FROM domanda WHERE utente=?");
            sSkillById = connection.prepareStatement("SELECT * FROM skill WHERE id=?");
            sTaskById = connection.prepareStatement("SELECT * FROM task WHERE id=?");
            sTaskTipoSkill = connection.prepareStatement("SELECT * FROM task_skill WHERE id=?");
            sMessaggioById = connection.prepareStatement("SELECT * FROM messaggio WHERE id=?");
            sUtenteSkillById = connection.prepareStatement("SELECT * FROM utente_skill WHERE id=?");
            sUtenteProgettoById = connection.prepareStatement("SELECT * FROM utente_progetto WHERE id=?");
            sSkillByTipo = connection.prepareStatement("SELECT * FROM skill WHERE tipo=?");
            sTipoSkillById = connection.prepareStatement("SELECT * FROM tipo_skill WHERE id=?");
            sTipoSkillByTipo = connection.prepareStatement("SELECT * FROM tipo_skill WHERE tipo=?");

            sFilesByUtente = connection.prepareStatement("SELECT * FROM files WHERE utente=?");
            iFiles = connection.prepareStatement("INSERT INTO files(name,type,size,localfile,digest,utente) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            uFiles = connection.prepareStatement("UPDATE files SET name=?,type=?,size=?,localfile=?,digest=?, utente=? WHERE ID=?");
            sFilesById = connection.prepareStatement("SELECT * FROM files WHERE id=?");

            sProgettoByAdmin = connection.prepareStatement("SELECT * FROM progetto JOIN utente_progetto WHERE utente_progetto.utente=? and utente_progetto.ruolo ='admin' AND progetto.id = utente_progetto.progetto");
            sTaskByUtente = connection.prepareStatement("SELECT * FROM task JOIN proposta,domanda WHERE (task.id = proposta.task OR task.id = domanda.task)  AND (proposta.stato='accettato' OR domanda.stato='accettato') AND (proposta.utente=? OR domanda.utente=?) AND task.data_fine > CURRENT_DATE GROUP BY task.id");
            sPropostaInAttesa = connection.prepareStatement("SELECT * FROM task JOIN proposta WHERE task.id = proposta.task AND proposta.stato = 'Adesione richiesta' and proposta.utente=?");
            sDomandaInAttesa = connection.prepareStatement("SELECT * FROM task JOIN domanda WHERE task.id = domanda.task AND domanda.utente=?");
            sStatoDomanda = connection.prepareStatement("SELECT * FROM domanda JOIN task WHERE domanda.task = task.id AND domanda.utente=?");
            sTaskByDomanda = connection.prepareStatement("SELECT * FROM task JOIN domanda WHERE task.id = domanda.task AND domanda.stato = 'accettato' AND domanda.utente=? AND task.data_fine < CURRENT_DATE");
            sTaskByProposta = connection.prepareStatement("SELECT * FROM task JOIN proposta WHERE task.id = proposta.task AND proposta.stato = 'accettato' AND proposta.utente=? AND task.data_fine < CURRENT_DATE");
            sValutazioneByDomanda = connection.prepareStatement("SELECT * FROM domanda JOIN task WHERE domanda.task = task.id AND domanda.stato='accettato' AND domanda.utente=? AND task.data_fine < CURRENT_DATE");
            sValutazioneByProposta = connection.prepareStatement("SELECT * FROM proposta JOIN task WHERE proposta.task = task.id AND proposta.stato='accettato' AND proposta.utente=? AND task.data_fine < CURRENT_DATE");
            sPropostaByUtenteTask = connection.prepareStatement("SELECT * FROM proposta WHERE utente=? AND task=?");
            sDomandaUtenti = connection.prepareStatement("SELECT utente.id, utente.nome, utente.cognome, utente.password, utente.email, utente.telefono, utente.data_nascita, utente.amministratore FROM utente JOIN task , domanda WHERE task.id = ? AND domanda.task = task.id AND utente.id = domanda.utente");
            sPropostaUtenti = connection.prepareStatement("SELECT utente.id, utente.nome, utente.cognome, utente.password, utente.email, utente.telefono, utente.data_nascita, utente.amministratore FROM utente JOIN task , proposta WHERE task.id = ? AND proposta.task = task.id AND utente.id = proposta.utente");

            sDomandaUtentiAccettateConValutazione = connection.prepareStatement("SELECT utente.id, utente.nome, utente.cognome, utente.password, utente.email, utente.telefono, utente.data_nascita, utente.amministratore FROM utente JOIN task , domanda WHERE task.id = ? AND domanda.task = task.id AND utente.id = domanda.utente AND domanda.stato='accettato' AND domanda.valutazione>0");
            sDomandaUtentiAccettateSenzaValutazione = connection.prepareStatement("SELECT utente.id, utente.nome, utente.cognome, utente.password, utente.email, utente.telefono, utente.data_nascita, utente.amministratore FROM utente JOIN task , domanda WHERE task.id = ? AND domanda.task = task.id AND utente.id = domanda.utente AND domanda.stato='accettato' AND domanda.valutazione=0");

            sPropostaUtentiAccettateConValutazione = connection.prepareStatement("SELECT utente.id, utente.nome, utente.cognome, utente.password, utente.email, utente.telefono, utente.data_nascita, utente.amministratore FROM utente JOIN task , proposta WHERE task.id = ? AND proposta.task = task.id AND utente.id = proposta.utente AND proposta.stato='accettato' AND proposta.valutazione>0");
            sPropostaUtentiAccettateSenzaValutazione = connection.prepareStatement("SELECT utente.id, utente.nome, utente.cognome, utente.password, utente.email, utente.telefono, utente.data_nascita, utente.amministratore FROM utente JOIN task , proposta WHERE task.id = ? AND proposta.task = task.id AND utente.id = proposta.utente AND proposta.stato='accettato' AND proposta.valutazione=0");
            sUtenteInvitato = connection.prepareStatement("SELECT * FROM utente JOIN task,utente_progetto,proposta WHERE utente_progetto.utente = ? AND task.progetto = utente_progetto.progetto AND proposta.task = task.id AND utente.id = proposta.utente");
            sTaskInvitato = connection.prepareStatement("SELECT * FROM task JOIN utente,utente_progetto,proposta WHERE utente_progetto.utente = ? AND task.progetto = utente_progetto.progetto AND proposta.task = task.id AND utente.id = proposta.utente");
            sStatoInvitato = connection.prepareStatement("SELECT * FROM proposta JOIN utente,utente_progetto,task WHERE utente_progetto.utente = ? AND task.progetto = utente_progetto.progetto AND proposta.task = task.id AND utente.id = proposta.utente");
            sUtenteByDomandaEffettuata = connection.prepareStatement("SELECT * FROM utente JOIN utente_progetto,task, domanda WHERE utente_progetto.utente = ? AND task.progetto = utente_progetto.progetto AND utente.id = domanda.utente AND domanda.task = task.id AND domanda.stato='Adesione richiesta' ");
            sTaskByDomandaEffettuata = connection.prepareStatement("SELECT * FROM task JOIN utente_progetto,utente, domanda WHERE utente_progetto.utente = ? AND task.progetto = utente_progetto.progetto AND utente.id = domanda.utente AND domanda.task = task.id AND domanda.stato='Adesione richiesta' ");
            sDomandaByUtenteTask = connection.prepareStatement("SELECT * FROM domanda WHERE utente=? AND task=?");
            sSkillByUtente = connection.prepareStatement("SELECT * FROM utente_skill WHERE utente=?");
            //sSkillByUtente = connection.prepareStatement("SELECT * FROM skill JOIN utente_skill, utente WHERE utente_skill.utente = utente.id AND utente.id = ? AND utente_skill.skill = skill.id ");
            sLivelloByUtente = connection.prepareStatement("SELECT * FROM utente_skill JOIN skill, utente WHERE utente_skill.utente = utente.id AND utente.id = ? AND utente_skill.skill = skill.id ");
            sTaskBySkillLivello = connection.prepareStatement("SELECT * FROM task JOIN utente_skill,task_skill, domanda , proposta WHERE utente_skill.livello >= task_skill.livello AND task_skill.task = task.id AND utente_skill.utente = ? and task_skill.skill = utente_skill.skill AND domanda.utente != utente_skill.utente AND domanda.task != task.id AND proposta.utente != utente_skill.utente AND proposta.task != task.id and task.data_fine >= CURRENT_DATE AND task.collaboratori > task.collaboratoritot GROUP BY task.nome");

            sUtentiPartecipantiProgettoProp = connection.prepareStatement("SELECT * FROM proposta JOIN task WHERE proposta.utente = ? AND proposta.task = task.id AND task.progetto = ? and proposta.stato = 'accettato'");
            sUtentiPartecipantiProgettoDomanda = connection.prepareStatement("SELECT * FROM domanda JOIN task WHERE domanda.utente = ? AND domanda.task = task.id AND task.progetto = ? and domanda.stato = 'accettato'");

        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inizializzazione del SocialDevelop data layer ", ex);

        }
    }

    @Override
    public Progetto createProgetto() {
        return new ProgettoImpl(this);
    }

    public Progetto createProgetto(ResultSet rs) throws DataLayerException {
        try {
            ProgettoImpl a = new ProgettoImpl(this);
            a.setId(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setDescrizione(rs.getString("descrizione"));

            return a;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto progetto da ResultSet", ex);
        }
    }

    @Override
    public Task createTask() {
        return new TaskImpl(this);
    }

    public Task createTask(ResultSet rs) throws DataLayerException {
        try {
            TaskImpl t = new TaskImpl(this);
            t.setId(rs.getInt("id"));
            t.setNome(rs.getString("nome"));
            t.setDescrizione(rs.getString("descrizione"));
            t.setCollaboratori(rs.getInt("collaboratori"));
            t.setDataInizio(rs.getDate("data_inizio"));
            t.setDataFine(rs.getDate("data_fine"));
            t.setIdProgetto(rs.getInt("progetto"));
            t.setIdTipo(rs.getInt("tipo"));
            t.setCollaboratoritot(rs.getInt("collaboratoritot"));
            return t;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto task da ResultSet", ex);
        }
    }

    @Override
    public Domanda createDomanda() {
        return new DomandaImpl(this);
    }

    public Domanda createDomanda(ResultSet rs) throws DataLayerException {
        try {
            DomandaImpl d = new DomandaImpl(this);
            d.setId(rs.getInt("id"));
            d.setIdUtente(rs.getInt("utente"));
            d.setIdTask(rs.getInt("task"));
            d.setData(rs.getDate("data"));
            d.setStato(rs.getString("stato"));
            d.setValutazione(rs.getInt("valutazione"));
            return d;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto task da ResultSet", ex);
        }
    }

    @Override
    public Proposta createProposta() {
        return new PropostaImpl(this);
    }

    public Proposta createProposta(ResultSet rs) throws DataLayerException {
        try {
            PropostaImpl p = new PropostaImpl(this);
            p.setId(rs.getInt("id"));
            p.setIdUtente(rs.getInt("utente"));
            p.setIdTask(rs.getInt("task"));
            p.setData(rs.getDate("data"));
            p.setStato(rs.getString("stato"));
            p.setValutazione(rs.getInt("valutazione"));
            return p;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto task da ResultSet", ex);
        }
    }

    @Override
    public Messaggio createMessaggio() {
        return new MessaggioImpl(this);
    }

    public Messaggio createMessaggio(ResultSet rs) throws DataLayerException {
        try {
            MessaggioImpl m = new MessaggioImpl(this);
            m.setId(rs.getInt("id"));
            m.setIdUtente(rs.getInt("utente"));
            m.setIdProgetto(rs.getInt("progetto"));
            m.setTesto(rs.getString("testo"));
            m.setTipo(rs.getString("tipo"));
            m.setData(rs.getDate("data"));
            return m;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto messaggio da ResultSet", ex);
        }
    }

    @Override
    public Skill createSkill() {
        return new SkillImpl(this);
    }

    public Skill createSkill(ResultSet rs) throws DataLayerException {
        try {
            SkillImpl s = new SkillImpl(this);
            s.setId(rs.getInt("id"));
            s.setNome(rs.getString("nome"));
            return s;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto skill da ResultSet", ex);
        }
    }

    @Override
    public Tipo createTipo() {
        return new TipoImpl(this);
    }

    public Tipo createTipo(ResultSet rs) throws DataLayerException {
        try {
            TipoImpl t = new TipoImpl(this);
            t.setId(rs.getInt("id"));
            t.setNome(rs.getString("nome"));
            return t;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto tipo da ResultSet", ex);
        }
    }

    @Override
    public TipoSkill createTipoSkill() {
        return new TipoSkillImpl(this);
    }

    public TipoSkill createTipoSkill(ResultSet rs) throws DataLayerException {
        try {
            TipoSkillImpl t = new TipoSkillImpl(this);
            t.setId(rs.getInt("id"));
            t.setIdTipo(rs.getInt("tipo"));
            t.setIdSkill(rs.getInt("skill"));
            return t;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto TipoSkill da ResultSet", ex);
        }
    }

    @Override
    public UtenteProgetto createUtenteProgetto() {
        return new UtenteProgettoImpl(this);
    }

    public UtenteProgetto createUtenteProgetto(ResultSet rs) throws DataLayerException {
        try {
            UtenteProgettoImpl u = new UtenteProgettoImpl(this);
            u.setId(rs.getInt("id"));
            u.setIdUtente(rs.getInt("utente"));
            u.setUtente(getUtente(rs.getInt("utente")));
            u.setIdProgetto(rs.getInt("progetto"));
            u.setRuolo(rs.getString("ruolo"));
            return u;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto utenteProgetto da ResultSet", ex);
        }
    }

    @Override
    public TaskSkill createTaskSkill() {
        return new TaskSkillImpl(this);
    }

    public TaskSkill createTaskSkill(ResultSet rs) throws DataLayerException {
        try {
            TaskSkillImpl t = new TaskSkillImpl(this);
            t.setId(rs.getInt("id"));
            t.setIdTask(rs.getInt("task"));
            t.setIdSkill(rs.getInt("skill"));
            t.setLivello(rs.getInt("livello"));
            return t;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto taskTipoSkill da ResultSet", ex);
        }
    }

    @Override
    public UtenteSkill createUtenteSkill() {
        return new UtenteSkillImpl(this);
    }

    public UtenteSkill createUtenteSkill(ResultSet rs) throws DataLayerException {
        try {
            UtenteSkillImpl u = new UtenteSkillImpl(this);
            u.setId(rs.getInt("id"));
            u.setIdUtente(rs.getInt("utente"));
            u.setSkill(getSkill(rs.getInt("skill")));
            u.setIdSkill(rs.getInt("skill"));
            u.setLivello(rs.getInt("livello"));
            return u;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare un oggetto utenteSkill da ResultSet", ex);
        }
    }

    @Override
    public List<Progetto> getProgetti() throws DataLayerException {
        List<Progetto> result = new ArrayList();

        try (ResultSet rs = sProgetti.executeQuery()) {
            while (rs.next()) {
                result.add(getProgetto(rs.getInt("id")));

            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i Progetti", ex);
        }
        return result;
    }

    @Override
    public Progetto getProgetto(int id_progetto) throws DataLayerException {
        try {
            sProgettoById.setInt(1, id_progetto);
            try (ResultSet rs = sProgettoById.executeQuery()) {
                if (rs.next()) {

                    return createProgetto(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare progetto by id", ex);
        }
        return null;
    }

    @Override
    public List<Task> getTask(Progetto progetto) throws DataLayerException {

        List<Task> result = new ArrayList();
        try {
            sTask.setInt(1, progetto.getId());
            try (ResultSet rs = sTask.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i messaggi del progetto", ex);
        }
        return result;
    }

    @Override
    public Task getTask(int id_task) throws DataLayerException {
        try {
            sTaskById.setInt(1, id_task);
            try (ResultSet rs = sTaskById.executeQuery()) {
                if (rs.next()) {

                    return createTask(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare task by id", ex);
        }
        return null;
    }

    @Override
    public List<Messaggio> getMessaggi(Progetto progetto) throws DataLayerException {
        List<Messaggio> result = new ArrayList();
        try {
            sMessaggi.setInt(1, progetto.getId());
            try (ResultSet rs = sMessaggi.executeQuery()) {
                while (rs.next()) {
                    result.add(getMessaggioById(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i messaggi del progetto", ex);
        }
        return result;

    }

    @Override
    public List<Messaggio> getMessaggiPubblici(Progetto progetto) throws DataLayerException {
        List<Messaggio> result = new ArrayList();
        try {
            sMessaggiPubblici.setInt(1, progetto.getId());
            try (ResultSet rs = sMessaggiPubblici.executeQuery()) {
                while (rs.next()) {
                    result.add(getMessaggioById(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i messaggi del progetto", ex);
        }
        return result;
    }

    @Override
    public Utente getUtente(int id_utente) throws DataLayerException {
        try {
            sUtenteById.setInt(1, id_utente);
            try (ResultSet rs = sUtenteById.executeQuery()) {
                if (rs.next()) {

                    return createUtente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare task by id", ex);
        }
        return null;
    }

    @Override
    public List<Utente> getUtenteBySkill(int id_skill) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sUtenteBySkill.setInt(1, id_skill);
            try (ResultSet rs = sUtenteBySkill.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti con questa skill", ex);
        }
        return result;
    }

    @Override
    public Files getFiles(int id_files) throws DataLayerException {
        //SELECT * FROM files WHERE id=?
        try {
            sFilesById.setInt(1, id_files);
            try (ResultSet rs = sFilesById.executeQuery()) {
                if (rs.next()) {

                    return createFiles(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare domanda by id", ex);
        }
        return null;
    }

    @Override
    public List<Utente> getUtenti() throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {

            try (ResultSet rs = sUtenti.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti con questa skill", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getUtenteBySkillLivello(int id_skill, int livello) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            //SELECT u1.id,u1.nome,u1.cognome, u1.email, u1.password,u1.telefono, u1.data_nascita,u1.amministratore FROM utente_skill u JOIN utente u1 ON u.utente=u1.id WHERE u.skill=? AND u.livello>=?
            sUtenteBySkillLivello.setInt(1, id_skill);
            sUtenteBySkillLivello.setInt(2, livello);
            try (ResultSet rs = sUtenteBySkillLivello.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti", ex);
        }
        return result;
    }

    @Override
    public Domanda getDomanda(int id_domanda) throws DataLayerException {
        try {
            sDomandaById.setInt(1, id_domanda);
            try (ResultSet rs = sDomandaById.executeQuery()) {
                if (rs.next()) {

                    return createDomanda(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare domanda by id", ex);
        }
        return null;
    }

    @Override
    public List<Domanda> getDomande(int id_utente) throws DataLayerException {
        List<Domanda> result = new ArrayList();

        try {
            sDomandeByUtente.setInt(1, id_utente);
            try (ResultSet rs = sDomandeByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add(getDomanda(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare domanda by id", ex);
        }
        return result;
    }

    @Override
    public Proposta getProposta(int id_proposta) throws DataLayerException {
        try {
            sPropostaById.setInt(1, id_proposta);
            try (ResultSet rs = sPropostaById.executeQuery()) {
                if (rs.next()) {

                    return createProposta(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare proposta by id", ex);
        }
        return null;
    }

    @Override
    public List<Proposta> getProposte(int id_utente) throws DataLayerException {
        List<Proposta> result = new ArrayList();

        try {
            sProposteByUtente.setInt(1, id_utente);
            try (ResultSet rs = sProposteByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add(getProposta(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare proposta by id", ex);
        }
        return result;
    }

    @Override
    public List<Task> getTaskAperti() throws DataLayerException {
        List<Task> result = new ArrayList();

        try (ResultSet rs = sTaskAperti.executeQuery()) {
            while (rs.next()) {
                result.add(getTask(rs.getInt("id")));

            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile Caricare i Task aperti", ex);
        }
        return result;
    }

    @Override
    public List<Task> getTaskAperti(int id_progetto) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTaskApertiByProgetto.setInt(1, id_progetto);
            try (ResultSet rs = sTaskApertiByProgetto.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task aperti del progetto", ex);
        }
        return result;
    }

    @Override
    public List<Skill> getSkill() throws DataLayerException {
        List<Skill> result = new ArrayList();

        try (ResultSet rs = sSkill.executeQuery()) {
            while (rs.next()) {
                result.add(getSkill(rs.getInt("id")));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare skill by id", ex);
        }
        return result;
    }

    @Override
    public List<Tipo> getTipi() throws DataLayerException {
        List<Tipo> result = new ArrayList();

        try (ResultSet rs = sTipi.executeQuery()) {
            while (rs.next()) {
                result.add(getTipo(rs.getInt("id")));

            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile Caricare i Tipi", ex);
        }
        return result;
    }

    @Override
    public Tipo getTipo(int id_tipo) throws DataLayerException {
        try {
            sTipoById.setInt(1, id_tipo);
            try (ResultSet rs = sTipoById.executeQuery()) {
                if (rs.next()) {

                    return createTipo(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare tipo by id", ex);
        }
        return null;
    }

    @Override
    public TipoSkill getTipoSkill(int id_tipo_skill) throws DataLayerException {
        try {
            sTipoSkillById.setInt(1, id_tipo_skill);
            try (ResultSet rs = sTipoSkillById.executeQuery()) {
                if (rs.next()) {

                    return createTipoSkill(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare tipoSkill by id", ex);
        }
        return null;
    }

    @Override
    public List<TipoSkill> getTipoSkillByTipo(Tipo tipo) throws DataLayerException {
        List<TipoSkill> result = new ArrayList();
        try {

            sTipoSkillByTipo.setInt(1, tipo.getId());
            try (ResultSet rs = sTipoSkillByTipo.executeQuery()) {
                while (rs.next()) {
                    result.add(getTipoSkill(rs.getInt("id")));

                }
            } catch (SQLException ex) {
                throw new DataLayerException("Impossibile Caricare i Tipi", ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Skill getSkill(int id_skill) throws DataLayerException {
        try {
            sSkillById.setInt(1, id_skill);
            try (ResultSet rs = sSkillById.executeQuery()) {
                if (rs.next()) {

                    return createSkill(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare skill by id", ex);
        }
        return null;
    }

    @Override
    public Utente createUtente() {
        return new UtenteImpl(this);
    }

    public Utente createUtente(ResultSet rs) throws DataLayerException {
        try {
            UtenteImpl u = new UtenteImpl(this);
            u.setId(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setCognome(rs.getString("cognome"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setTelefono(rs.getString("telefono"));
            u.setAmministratore(rs.getString("amministratore"));
            return u;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto utente da ResultSet", ex);
        }
    }

    @Override
    public List<Utente> getUtenteByProgetto(int id_progetto) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sUtenteProgetto.setInt(1, id_progetto);
            try (ResultSet rs = sUtenteProgetto.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti del progetto", ex);
        }
        return result;
    }

    @Override
    public Messaggio getMessaggioById(int id_messaggio) throws DataLayerException {
        try {
            sMessaggioById.setInt(1, id_messaggio);
            try (ResultSet rs = sMessaggioById.executeQuery()) {
                if (rs.next()) {

                    return createMessaggio(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare messaggio by id", ex);
        }
        return null;
    }

    @Override
    public int storeTask(Task task) throws DataLayerException {
        int id = task.getId();

        try {
            if (id > 0) {
                if (!task.isDirty()) {
                    return id;
                }
                //UPDATE task SET nome=?, descrizione=?,collaboratori=?,data_inizio=?,data_fine=?,progetto=?,tipo=?  WHERE id=?
                uTask.setString(1, task.getNome());
                uTask.setString(2, task.getDescrizione());
                uTask.setInt(3, task.getCollaboratori());
                java.sql.Date sqlDate = new java.sql.Date(task.getDataInizio().getTime());
                uTask.setDate(4, sqlDate);
                java.sql.Date sqlDate2 = new java.sql.Date(task.getDataFine().getTime());
                uTask.setDate(5, sqlDate2);
                uTask.setInt(6, task.getProgetto().getId());

                uTask.setInt(7, task.getCollaboratoritot());
                uTask.setInt(8, task.getId());

                uTask.executeUpdate();
            } else {
                //INSERT INTO task (nome,descrizione,collaboratori,data_inizio,data_fine,progetto,tipo) VALUES(?,?,?,?,?,?,?)
                iTask.setString(1, task.getNome());
                iTask.setString(2, task.getDescrizione());
                iTask.setInt(3, task.getCollaboratori());
                java.sql.Date sqlDate = new java.sql.Date(task.getDataInizio().getTime());
                iTask.setDate(4, sqlDate);
                java.sql.Date sqlDate2 = new java.sql.Date(task.getDataFine().getTime());
                iTask.setDate(5, sqlDate2);
                iTask.setInt(6, task.getProgetto().getId());
                iTask.setInt(7, task.getTipo().getId());
                iTask.setInt(8, task.getCollaboratoritot());
                if (iTask.executeUpdate() == 1) {

                    try (ResultSet keys = iTask.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                task.copyFrom(getTask(id));
            }
            task.setDirty(false);
            return id;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare il task", ex);
        }
    }

    @Override
    public void storeMessaggio(Messaggio messaggio) throws DataLayerException {
        int id = messaggio.getId();
        try {
            if (id > 0) { //UPDATE messaggio SET id_utente=?,id_progetto=?, testo=?, data=?, tipo=? WHERE id=? 
                if (!messaggio.isDirty()) {
                    return;
                }
                uMessaggio.setInt(1, messaggio.getUtente().getId());
                uMessaggio.setInt(2, messaggio.getProgetto().getId());
                if (messaggio.getTesto() != null) {
                    uMessaggio.setString(3, messaggio.getTesto());
                } else {
                    uMessaggio.setNull(3, java.sql.Types.INTEGER);
                }

                uMessaggio.setDate(4, (Date) messaggio.getData());
                uMessaggio.setString(5, messaggio.getTipo());
                uMessaggio.setInt(6, messaggio.getId());
                uMessaggio.executeUpdate();
            } else {

                if (messaggio.getUtente() != null) {
                    iMessaggio.setInt(1, messaggio.getUtente().getId());
                } else {
                    iMessaggio.setNull(1, java.sql.Types.INTEGER);
                }
                if (messaggio.getProgetto() != null) {
                    iMessaggio.setInt(2, messaggio.getProgetto().getId());
                } else {
                    iMessaggio.setNull(2, java.sql.Types.INTEGER);
                }
                if (messaggio.getTesto() != null) {
                    iMessaggio.setString(3, messaggio.getTesto());
                } else {
                    iMessaggio.setNull(3, java.sql.Types.INTEGER);
                }

                iMessaggio.setString(4, messaggio.getTipo());

                if (iMessaggio.executeUpdate() == 1) {

                    try (ResultSet keys = iMessaggio.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                messaggio.copyFrom(getMessaggioById(id));
            }
            messaggio.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire articolo", ex);
        }
    }

    @Override
    public void storeProposta(Proposta proposta) throws DataLayerException {
        int id = proposta.getId();
        try {
            if (id > 0) { //update
                if (!proposta.isDirty()) {
                    return;
                }
                uProposta.setInt(1, proposta.getValutazione());
                uProposta.setString(2, proposta.getStato());
                uProposta.setInt(3, proposta.getId());
                uProposta.executeUpdate();
            } else { //insert

                if (proposta.getUtente() != null) {
                    iProposta.setInt(1, proposta.getUtente().getId());
                } else {
                    iProposta.setNull(1, java.sql.Types.INTEGER);
                }
                if (proposta.getTask() != null) {
                    iProposta.setInt(2, proposta.getTask().getId());
                } else {
                    iProposta.setNull(2, java.sql.Types.INTEGER);
                }
                iProposta.setString(3, proposta.getStato());

                if (iProposta.executeUpdate() == 1) {

                    try (ResultSet keys = iProposta.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                proposta.copyFrom(getProposta(id));
            }
            proposta.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire proposta", ex);
        }
    }

    @Override
    public void storeDomanda(Domanda domanda) throws DataLayerException {
        int id = domanda.getId();
        try {
            if (id > 0) { //
                if (!domanda.isDirty()) {
                    return;
                }
                uDomanda.setInt(1, domanda.getValutazione());
                uDomanda.setString(2, domanda.getStato());
                uDomanda.setInt(3, domanda.getId());
                uDomanda.executeUpdate();
            } else { //insert

                if (domanda.getUtente() != null) {
                    iDomanda.setInt(1, domanda.getUtente().getId());
                } else {
                    iDomanda.setNull(1, java.sql.Types.INTEGER);
                }
                if (domanda.getTask() != null) {
                    iDomanda.setInt(2, domanda.getTask().getId());
                } else {
                    iDomanda.setNull(2, java.sql.Types.INTEGER);
                }

                iDomanda.setString(3, domanda.getStato());

                if (iDomanda.executeUpdate() == 1) {

                    try (ResultSet keys = iDomanda.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                domanda.copyFrom(getDomanda(id));
            }
            domanda.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire domanda", ex);
        }
    }

    @Override
    public void storeUtenteProgetto(UtenteProgetto utenteProgetto) throws DataLayerException {
        int id = utenteProgetto.getId();
        try {
            if (id > 0) { //update 
                if (!utenteProgetto.isDirty()) {
                    return;
                }
                uUtenteProgetto.setString(1, utenteProgetto.getRuolo());
                uUtenteProgetto.setInt(2, utenteProgetto.getUtente().getId());
                uUtenteProgetto.setInt(3, utenteProgetto.getProgetto().getId());
                uUtenteProgetto.executeUpdate();
            } else { //insert

                if (utenteProgetto.getUtente() != null) {
                    iUtenteProgetto.setInt(1, utenteProgetto.getUtente().getId());
                } else {
                    iUtenteProgetto.setNull(1, java.sql.Types.INTEGER);
                }
                if (utenteProgetto.getProgetto() != null) {
                    iUtenteProgetto.setInt(2, utenteProgetto.getProgetto().getId());
                } else {
                    iUtenteProgetto.setNull(2, java.sql.Types.INTEGER);
                }
                iUtenteProgetto.setString(3, utenteProgetto.getRuolo());

                if (iUtenteProgetto.executeUpdate() == 1) {

                    try (ResultSet keys = iUtenteProgetto.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                utenteProgetto.copyFrom(getUtenteProgettoById(id));
            }

            utenteProgetto.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire utente-progetto", ex);
        }
    }

    @Override
    public void storeUtenteSkill(UtenteSkill utenteSkill) throws DataLayerException {
        int id = utenteSkill.getId();
        try {
            if (id > 0) { //update UPDATE utenteSkill SET livello=? WHERE id_skill=? AND id_utente=?
                if (!utenteSkill.isDirty()) {
                    return;
                }
                uUtenteSkill.setInt(1, utenteSkill.getLivello());
                uUtenteSkill.setInt(2, utenteSkill.getUtente().getId());
                uUtenteSkill.setInt(3, utenteSkill.getSkill().getId());
                uUtenteSkill.executeUpdate();
            } else { //insert

                if (utenteSkill.getUtente() != null) {
                    iUtenteSkill.setInt(1, utenteSkill.getUtente().getId());
                } else {
                    iUtenteSkill.setNull(1, java.sql.Types.INTEGER);
                }
                if (utenteSkill.getSkill() != null) {
                    iUtenteSkill.setInt(2, utenteSkill.getSkill().getId());
                } else {
                    iUtenteSkill.setNull(2, java.sql.Types.INTEGER);
                }
                iUtenteSkill.setInt(3, utenteSkill.getLivello());

                if (iUtenteSkill.executeUpdate() == 1) {

                    try (ResultSet keys = iUtenteSkill.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                utenteSkill.copyFrom(getUtenteSkillById(id));
            }
            utenteSkill.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire utente-skill", ex);
        }
    }

    @Override
    public void storeTaskSkill(TaskSkill taskSkill) throws DataLayerException {
        int id = taskSkill.getId();

        try {
            if (id > 0) { //update UPDATE TaskSkill SET livello=? WHERE  id_task=?, id_skill=?, id_tipo=?
                //non facciamo nulla se l'oggetto non ha subito modifiche
                if (!taskSkill.isDirty()) {
                    return;
                }
                uTaskSkill.setInt(1, taskSkill.getLivello());
                uTaskSkill.setInt(2, taskSkill.getTask().getId());
                uTaskSkill.setInt(3, taskSkill.getSkill().getId());

                uTaskSkill.executeUpdate();
            } else { //insert INSERT INTO task_tipo_skill (task,tipo,skill,livello) VALUES(?,?,?,?)

                iTaskSkill.setInt(1, taskSkill.getTask().getId());
                iTaskSkill.setInt(2, taskSkill.getSkill().getId());
                iTaskSkill.setInt(3, taskSkill.getLivello());

                if (iTaskSkill.executeUpdate() == 1) {

                    try (ResultSet keys = iTaskSkill.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                taskSkill.copyFrom(getTaskSkill(id));
            }
            taskSkill.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire TaskSkill", ex);
        }
    }

    @Override
    public void storeTipoSkill(TipoSkill tipoSkill) throws DataLayerException {
        int id = tipoSkill.getId();

        try {
            if (id > 0) {
                //non facciamo nulla se l'oggetto non ha subito modifiche
                if (!tipoSkill.isDirty()) {
                    return;
                }
                uTipoSkill.setInt(1, tipoSkill.getTipo().getId());
                uTipoSkill.setInt(2, tipoSkill.getSkill().getId());
                uTipoSkill.setInt(3, tipoSkill.getId());

                uTipoSkill.executeUpdate();
            } else { //insert INSERT INTO task_tipo_skill (task,tipo,skill,livello) VALUES(?,?,?,?)

                iTipoSkill.setInt(1, tipoSkill.getTipo().getId());
                iTipoSkill.setInt(2, tipoSkill.getSkill().getId());

                if (iTipoSkill.executeUpdate() == 1) {

                    try (ResultSet keys = iTipoSkill.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                tipoSkill.copyFrom(getTipoSkill(id));
            }
            tipoSkill.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire TipoSkill", ex);
        }
    }

    @Override
    public int storeSkill(Skill skill) throws DataLayerException {
        int id = skill.getId();
        try {
            if (id > 0) { //update UPDATE skill SET nome=? WHERE id=? 
                if (!skill.isDirty()) {
                    return id;
                }
                uSkill.setString(1, skill.getNome());
                uSkill.setInt(2, skill.getId());

                uSkill.executeUpdate();
            } else { //insert INSERT INTO skill (nome) VALUE(?)

                iSkill.setString(1, skill.getNome());

                if (iSkill.executeUpdate() == 1) {

                    try (ResultSet keys = iSkill.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                skill.copyFrom(getSkill(id));
            }
            skill.setDirty(false);
            return id;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire una skill", ex);
        }
    }

    @Override
    public int storeUtente(Utente utente) throws DataLayerException {
        int id = utente.getId();

        try {
            if (id > 0) { //update UPDATE utente SET nome=?, cognome=?, cf=?, residenza=?, email=?, password=?, telefono=?, dataNascita=? WHERE id=?
                //non facciamo nulla se l'oggetto non ha subito modifiche
                if (!utente.isDirty()) {
                    return id;
                }
                uUtente.setString(1, utente.getNome());
                uUtente.setString(2, utente.getCognome());

                uUtente.setString(3, utente.getEmail());
                uUtente.setString(4, utente.getPassword());
                if (utente.getTelefono() != null) {
                    uUtente.setString(5, utente.getTelefono());
                } else {
                    uUtente.setNull(5, java.sql.Types.INTEGER);
                }

                uUtente.setDate(6, (Date) utente.getDataNascita());
                uUtente.setString(7, utente.getAmministratore());
                uUtente.setInt(8, utente.getId());
                uUtente.executeUpdate();
                return utente.getId();

            } else { //insert
                //INSERT INTO utente (nome,cognome,email,password,telefono,data_nascita) VALUES(?,?,?,?,?,?)
                iUtente.setString(1, utente.getNome());
                iUtente.setString(2, utente.getCognome());
                iUtente.setString(3, utente.getEmail());
                iUtente.setString(4, utente.getPassword());
                if (utente.getTelefono() != null) {
                    iUtente.setString(5, utente.getTelefono());
                } else {
                    iUtente.setNull(5, java.sql.Types.INTEGER);
                }
                java.sql.Date sqlDate = new java.sql.Date(utente.getDataNascita().getTime());
                iUtente.setDate(6, sqlDate);
                iUtente.setString(7, utente.getAmministratore());

                if (iUtente.executeUpdate() == 1) {

                    try (ResultSet keys = iUtente.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                utente.copyFrom(getUtente(id));
            }
            utente.setDirty(false);
            return id;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire l'utente", ex);
        }
    }

    @Override
    public void storeTipo(Tipo tipo) throws DataLayerException {

        int id = tipo.getId();
        try {
            if (id > 0) { //update

                if (!tipo.isDirty()) {
                    return;
                }
                uTipo.setString(1, tipo.getNome());
                uTipo.setInt(2, ((int) tipo.getId()));

                uTipo.executeUpdate();
            } else { //insert
                iTipo.setString(1, tipo.getNome());
                if (iTipo.executeUpdate() == 1) {

                    try (ResultSet keys = iTipo.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                tipo.copyFrom(getTipo(id));
            }
            tipo.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire il tipo", ex);
        }
    }

    @Override
    public int storeProgetto(Progetto progetto) throws DataLayerException {
        int id = progetto.getId();
        try {
            if (id > 0) { //update

                if (!progetto.isDirty()) {
                    return id;
                }
                uProgetto.setString(1, progetto.getNome());
                if (progetto.getDescrizione() != null) {
                    uProgetto.setString(2, progetto.getDescrizione());
                } else {
                    uProgetto.setNull(2, java.sql.Types.INTEGER);
                }

                uProgetto.setInt(3, progetto.getId());

                uProgetto.executeUpdate();
            } else { //insert

                iProgetto.setString(1, progetto.getNome());

                iProgetto.setString(2, progetto.getDescrizione());

                if (iProgetto.executeUpdate() == 1) {

                    try (ResultSet keys = iProgetto.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                progetto.copyFrom(getProgetto(id));
            }
            progetto.setDirty(false);
            return id;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile inserire il progetto", ex);
        }
    }

    @Override
    public TaskSkill getTaskSkill(int id_taskSkill) throws DataLayerException {
        try {
            sTaskTipoSkill.setInt(1, id_taskSkill);
            try (ResultSet rs = sTaskTipoSkill.executeQuery()) {
                if (rs.next()) {
                    return createTaskSkill(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare TaskSkill", ex);
        }

        return null;
    }

    @Override
    public List<TaskSkill> getTaskSkillByTask(int id_task) throws DataLayerException {
        List<TaskSkill> result = new ArrayList();
        try {
            sTaskTipoSkillByTask.setInt(1, id_task);
            try (ResultSet rs = sTaskTipoSkillByTask.executeQuery()) {
                while (rs.next()) {
                    result.add(getTaskSkill(rs.getInt("id")));

                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare TaskSkill", ex);
        }

        return result;
    }

    @Override
    public UtenteProgetto getUtenteProgettoById(int id_utente_progetto) throws DataLayerException {
        try {

            sUtenteProgettoById.setInt(1, id_utente_progetto);
            try (ResultSet rs = sUtenteProgettoById.executeQuery()) {
                if (rs.next()) {

                    return createUtenteProgetto(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare utente progetto by id", ex);
        }
        return null;
    }

    @Override
    public UtenteProgetto getUtenteProgettoByProgetto(int id_progetto) throws DataLayerException {
        try {

            sUtenteProgettoByProgetto.setInt(1, id_progetto);
            try (ResultSet rs = sUtenteProgettoByProgetto.executeQuery()) {
                if (rs.next()) {

                    return createUtenteProgetto(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare utente progetto by id", ex);
        }
        return null;
    }

    @Override
    public UtenteProgetto getUtenteProgetto(int id_utente, int id_progetto) throws DataLayerException {
        //SELECT * FROM utente_progetto WHERE utente=? AND progetto=?
        try {

            sUtenteProgettoByUeP.setInt(1, id_utente);
            sUtenteProgettoByUeP.setInt(2, id_progetto);
            try (ResultSet rs = sUtenteProgettoByUeP.executeQuery()) {
                if (rs.next()) {

                    return createUtenteProgetto(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare utente progetto by id", ex);
        }
        return null;
    }

    @Override
    public UtenteSkill getUtenteSkillById(int id_utente_skill) throws DataLayerException {
        try {
            sUtenteSkillById.setInt(1, id_utente_skill);
            try (ResultSet rs = sUtenteSkillById.executeQuery()) {
                if (rs.next()) {

                    return createUtenteSkill(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare utente skill by id", ex);
        }
        return null;
    }

    @Override
    public List<Task> getTask() throws DataLayerException {
        List<Task> result = new ArrayList();

        try (ResultSet rs = sTask.executeQuery()) {
            while (rs.next()) {
                result.add(getTask(rs.getInt("id")));

            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile Caricare i Task", ex);
        }
        return result;
    }

    @Override
    public Files createFiles() {
        return new FilesImpl(this);
    }

    public Files createFiles(ResultSet rs) throws DataLayerException {
        try {
            FilesImpl f = new FilesImpl(this);
            f.setId(rs.getInt("ID"));
            f.setNome(rs.getString("name"));
            f.setTipo(rs.getString("type"));
            f.setSize(rs.getInt("size"));
            f.setLocalFile(rs.getString("localfile"));
            f.setDigest(rs.getString("digest"));
            f.setIdUtente(rs.getInt("utente"));
            return f;
        } catch (SQLException ex) {
            throw new DataLayerException("Incapace di creare un oggetto files da ResultSet", ex);
        }
    }

    @Override
    public Files getFiles(int id_utente, HttpServletRequest request, HttpServletResponse response) throws DataLayerException {
        //SELECT * FROM files WHERE id=?
        StreamResult result = new StreamResult(request.getServletContext());
        try {
            sFilesByUtente.setInt(1, id_utente);
            try (ResultSet rs = sFilesByUtente.executeQuery()) {
                if (rs.next()) {
                    try (InputStream is = new FileInputStream(
                            request.getServletContext().getInitParameter("uploads.directory") + File.separatorChar + rs.getString("localfile"))) {
                        request.setAttribute("contentType", rs.getString("type"));
                        result.activate(is, rs.getLong("size"), rs.getString("name"), request, response);
                    } catch (IOException ex) {
                        Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare domanda by id", ex);
        }
        return null;
    }

    @Override
    public void storeFiles(Files files) throws DataLayerException {
        int id = files.getId();

        try {
            if (id > 0) { //update UPDATE files SET name=?,type=?,size=?,localfile=?,digest=?, utente=? WHERE ID=?
                //non facciamo nulla se l'oggetto non ha subito modifiche
                if (!files.isDirty()) {
                    return;
                }
                uFiles.setString(1, files.getNome());
                uFiles.setString(2, files.getTipo());
                uFiles.setInt(3, files.getSize());
                uFiles.setString(4, files.getLocalFile());
                uFiles.setString(5, files.getDigest());
                uFiles.setInt(6, files.getUtente().getId());
                uFiles.setInt(7, files.getId());

                uFiles.executeUpdate();
            } else { //insert "INSERT INTO files(name,type,size,localfile,digest, utente) VALUES(?,?,?,?,?,?)"

                iFiles.setString(1, files.getNome());
                iFiles.setString(2, files.getTipo());
                iFiles.setInt(3, files.getSize());
                iFiles.setString(4, files.getLocalFile());
                iFiles.setString(5, files.getDigest());
                iFiles.setInt(6, files.getIdUtente());

                if (iFiles.executeUpdate() == 1) {

                    try (ResultSet keys = iFiles.getGeneratedKeys()) {

                        if (keys.next()) {

                            id = keys.getInt(1);
                        }
                    }
                }
            }

            if (id > 0) {
                files.copyFrom(getFiles(id));
            }
            files.setDirty(false);
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile 'store' Files", ex);
        }
    }

    @Override
    public Utente getUtenteByEmail(String email) throws DataLayerException {
        //SELECT * FROM utente WHERE email=?
        try {
            sUtenteByEmail.setString(1, email);
            try (ResultSet rs = sUtenteByEmail.executeQuery()) {
                if (rs.next()) {

                    return createUtente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare Utente by Email", ex);
        }
        return null;
    }

    @Override
    public List<Skill> getSkillByTipo(Tipo tipo) throws DataLayerException {
        List<Skill> skills = new ArrayList();
        try {
            sSkillByTipo.setInt(1, tipo.getId());
            try (ResultSet rs = sSkillByTipo.executeQuery()) {
                while (rs.next()) {
                    skills.add(getSkill(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile Caricare le skill", ex);
        }
        return skills;
    }

    @Override
    public Utente login(String password, String email) throws DataLayerException, SQLException {
        Utente utente = null;
        utente = getUtenteByEmail(email);

        if (utente != null && utente.getPassword().equals(password)) {

            return utente;
        }
        return null;
    }

    public static String generateToken() {
        SecureRandom random = new SecureRandom();

        long longToken = Math.abs(random.nextLong());
        return Long.toString(longToken, 200);
    }

    @Override
    public List<Progetto> getProgettiByParole(String keywords[]) throws DataLayerException, SQLException {
        List<Progetto> result = new ArrayList();
        List<Progetto> result2 = new ArrayList();
        /*
        String query = "SELECT * FROM progetto WHERE nome LIKE (";
        //sProgettiByParole = connection.prepareStatement("SELECT * FROM progetto WHERE nome IN");
        for(int i = 0; i < keywords.length-1; i++) {
            query += "?,";
        }
        query +="?) OR descrizione LIKE(";
        for(int i = 0; i < keywords.length-1; i++) {
            query += "?,";
        }
        query +="?)";
        sProgettiByParole = connection.prepareStatement(query);
        System.out.println(query);
        System.out.println(keywords[0]);
        for(int i = 0; i < keywords.length; i++) {
            sProgettiByParole.setString(i+1, "%"+keywords[i]+"%");
        }
        for(int i = 0; i < keywords.length; i++) {
            sProgettiByParole.setString(keywords.length+1+i, "%"+keywords[i]+"%");
        }
        System.out.println(sProgettiByParole);
        try (ResultSet rs = sProgettiByParole.executeQuery()) {
                while (rs.next()) {
                result.add(getProgetto(rs.getInt("id")));
                }
            }
        keywords[0].
        System.out.println(result.size());
        return result;
        } 
         */
        result = getProgetti();
        int size = result.size();

        for (int j = 0; j < keywords.length; j++) {
            for (int i = 0; i < size; i++) {
                if ((result.get(i).getNome().contains(keywords[j])) || result.get(i).getDescrizione().contains(keywords[j])) {
                    if (!result2.contains(result.get(i))) {
                        result2.add(result.get(i));
                    }
                }
            }
        }
        return result2;
    }

    @Override
    public List<Utente> getUtentiByParole(String keywords[]) throws DataLayerException, SQLException {
        List<Utente> result = new ArrayList();
        List<Utente> result2 = new ArrayList();

        result = getUtenti();
        int size = result.size();
        for (int j = 0; j < keywords.length; j++) {
            for (int i = 0; i < size; i++) {
                if ((result.get(i).getNome().contains(keywords[j])) || result.get(i).getCognome().contains(keywords[j])) {
                    if (!result2.contains(result.get(i))) {
                        result2.add(result.get(i));
                    }
                }
            }
        }
        return result2;
    }

    @Override
    public Tipo getTipoByNome(String nome) throws DataLayerException {
        try {
            sTipoByNome.setString(1, nome);
            try (ResultSet rs = sTipoByNome.executeQuery()) {
                if (rs.next()) {

                    return createTipo(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossbile caricare Skill by Nome", ex);
        }
        return null;
    }

    @Override
    public void deleteTipo(int id_tipo) {
        try {
            dTipo.setInt(1, id_tipo);
            dTipo.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteSkill(int id_skill) {
        try {
            dSkill.setInt(1, id_skill);
            dSkill.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteTipoSkillByTipo(int id_tipo) {
        try {
            dTipoSkillByTipo.setInt(1, id_tipo);
            dTipoSkillByTipo.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteTipoSkillBySkill(int id_skill) {
        try {
            dTipoSkillBySkill.setInt(1, id_skill);
            dTipoSkillBySkill.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteTaskSkill(int id_task_skill) {
        try {
            dTaskSkill.setInt(1, id_task_skill);
            dTaskSkill.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Progetto> getProgettoAdmin(int id_utente) throws DataLayerException {
        List<Progetto> result = new ArrayList();
        try {
            sProgettoByAdmin.setInt(1, id_utente);
            try (ResultSet rs = sProgettoByAdmin.executeQuery()) {
                while (rs.next()) {
                    result.add(getProgetto(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i progetti", ex);
        }
        return result;
    }

    @Override
    public List<Task> getTaskByUtente(int id_utente) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTaskByUtente.setInt(1, id_utente);
            sTaskByUtente.setInt(2, id_utente);
            try (ResultSet rs = sTaskByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Task> getPropostaInAttesa(int id_utente) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sPropostaInAttesa.setInt(1, id_utente);

            try (ResultSet rs = sPropostaInAttesa.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Task> getDomandaInAttesa(int id_utente) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sDomandaInAttesa.setInt(1, id_utente);

            try (ResultSet rs = sDomandaInAttesa.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Domanda> getStatoDomanda(int id_utente) throws DataLayerException {
        List<Domanda> result = new ArrayList();
        try {
            sStatoDomanda.setInt(1, id_utente);

            try (ResultSet rs = sStatoDomanda.executeQuery()) {
                while (rs.next()) {
                    result.add(getDomanda(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Task> getTaskByDomanda(int id_utente) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTaskByDomanda.setInt(1, id_utente);

            try (ResultSet rs = sTaskByDomanda.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Task> getTaskByProposta(int id_utente) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTaskByProposta.setInt(1, id_utente);

            try (ResultSet rs = sTaskByProposta.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Domanda> getValutazioneByDomanda(int id_utente) throws DataLayerException {
        List<Domanda> result = new ArrayList();
        try {
            sValutazioneByDomanda.setInt(1, id_utente);

            try (ResultSet rs = sValutazioneByDomanda.executeQuery()) {
                while (rs.next()) {
                    result.add(getDomanda(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Proposta> getValutazioneByProposta(int id_utente) throws DataLayerException {
        List<Proposta> result = new ArrayList();
        try {
            sValutazioneByProposta.setInt(1, id_utente);

            try (ResultSet rs = sValutazioneByProposta.executeQuery()) {
                while (rs.next()) {
                    result.add(getProposta(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public Proposta getPropostaByUtenteTask(int id_utente, int id_task) throws DataLayerException {

        try {
            sPropostaByUtenteTask.setInt(1, id_utente);
            sPropostaByUtenteTask.setInt(2, id_task);

            try (ResultSet rs = sPropostaByUtenteTask.executeQuery()) {

                if (rs.next()) {

                    return createProposta(rs);
                }

            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }

        return null;

    }

    @Override
    public Domanda getDomandaByUtenteTask(int id_utente, int id_task) throws DataLayerException {

        try {
            sDomandaByUtenteTask.setInt(1, id_utente);
            sDomandaByUtenteTask.setInt(2, id_task);

            try (ResultSet rs = sDomandaByUtenteTask.executeQuery()) {

                if (rs.next()) {

                    return createDomanda(rs);
                }

            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }

        return null;

    }

    @Override
    public List<Utente> getDomandaUtenti(int id_task) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sDomandaUtenti.setInt(1, id_task);
            try (ResultSet rs = sDomandaUtenti.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti del progetto", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getPropostaUtenti(int id_task) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sPropostaUtenti.setInt(1, id_task);
            try (ResultSet rs = sPropostaUtenti.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti del progetto", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getPropostaUtentiAccettatoConValutazione(int id_task) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sPropostaUtentiAccettateConValutazione.setInt(1, id_task);
            try (ResultSet rs = sPropostaUtentiAccettateConValutazione.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti del progetto", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getPropostaUtentiAccettatoSenzaValutazione(int id_task) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sPropostaUtentiAccettateSenzaValutazione.setInt(1, id_task);
            try (ResultSet rs = sPropostaUtentiAccettateSenzaValutazione.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti del progetto", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getDomandaUtentiAccettatoConValutazione(int id_task) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sDomandaUtentiAccettateConValutazione.setInt(1, id_task);
            try (ResultSet rs = sDomandaUtentiAccettateConValutazione.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti del progetto", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getDomandaUtentiAccettatoSenzaValutazione(int id_task) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sDomandaUtentiAccettateSenzaValutazione.setInt(1, id_task);
            try (ResultSet rs = sDomandaUtentiAccettateSenzaValutazione.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare gli utenti del progetto", ex);
        }
        return result;
    }

    @Override
    public void deleteProposta(int taskid, int userid) {
        try {
            dProposta.setInt(1, taskid);
            dProposta.setInt(2, userid);
            dProposta.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteDomanda(int taskid, int userid) {
        try {
            dDomanda.setInt(1, taskid);
            dDomanda.setInt(2, userid);
            dDomanda.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteDomandaById(int iddomanda) {
        try {
            dDomandaById.setInt(1, iddomanda);
            dDomandaById.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Utente> getUtenteInvitato(int id_utente) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sUtenteInvitato.setInt(1, id_utente);

            try (ResultSet rs = sUtenteInvitato.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Task> getTaskInvitato(int id_utente) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTaskInvitato.setInt(1, id_utente);

            try (ResultSet rs = sTaskInvitato.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Proposta> getStatoInvitato(int id_utente) throws DataLayerException {
        List<Proposta> result = new ArrayList();
        try {
            sStatoInvitato.setInt(1, id_utente);

            try (ResultSet rs = sStatoInvitato.executeQuery()) {
                while (rs.next()) {
                    result.add(getProposta(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Utente> getUtenteByDomandaEffettuata(int id_utente) throws DataLayerException {
        List<Utente> result = new ArrayList();
        try {
            sUtenteByDomandaEffettuata.setInt(1, id_utente);

            try (ResultSet rs = sUtenteByDomandaEffettuata.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtente(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Task> getTaskByDomandaEffettuata(int id_utente) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTaskByDomandaEffettuata.setInt(1, id_utente);

            try (ResultSet rs = sTaskByDomandaEffettuata.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<UtenteSkill> getSkillByUtente(int id_utente) throws DataLayerException {
        List<UtenteSkill> result = new ArrayList();
        try {
            sSkillByUtente.setInt(1, id_utente);

            try (ResultSet rs = sSkillByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtenteSkillById(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<UtenteSkill> getLivelloByUtente(int id_utente) throws DataLayerException {
        List<UtenteSkill> result = new ArrayList();
        try {
            sLivelloByUtente.setInt(1, id_utente);

            try (ResultSet rs = sLivelloByUtente.executeQuery()) {
                while (rs.next()) {
                    result.add(getUtenteSkillById(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Task> getTaskBySkillLivello(int id_utente) throws DataLayerException {
        List<Task> result = new ArrayList();
        try {
            sTaskBySkillLivello.setInt(1, id_utente);

            try (ResultSet rs = sTaskBySkillLivello.executeQuery()) {
                while (rs.next()) {
                    result.add(getTask(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Proposta> getUtentiPartecipantiProggettoProp(int id_utente, int id_progetto) throws DataLayerException {
        List<Proposta> result = new ArrayList();
        try {
            sUtentiPartecipantiProgettoProp.setInt(1, id_utente);
            sUtentiPartecipantiProgettoProp.setInt(2, id_progetto);

            try (ResultSet rs = sUtentiPartecipantiProgettoProp.executeQuery()) {
                while (rs.next()) {
                    result.add(getProposta(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public List<Domanda> getUtentiPartecipantiProggettoDomanda(int id_utente, int id_progetto) throws DataLayerException {
        List<Domanda> result = new ArrayList();
        try {
            sUtentiPartecipantiProgettoDomanda.setInt(1, id_utente);
            sUtentiPartecipantiProgettoDomanda.setInt(2, id_progetto);

            try (ResultSet rs = sUtentiPartecipantiProgettoDomanda.executeQuery()) {
                while (rs.next()) {
                    result.add(getDomanda(rs.getInt("id")));
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile caricare i task", ex);
        }
        return result;
    }

    @Override
    public void deletePropostaById(int id_proposta) {
        try {
            dPropostaById.setInt(1, id_proposta);
            dPropostaById.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(SocialDevelopMysqlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {

        try {
            iUtente.close();
            dUtente.close();
            uUtente.close();
            iProgetto.close();
            uProgetto.close();
            dProgetto.close();
            iSkill.close();
            uSkill.close();
            dSkill.close();
            iTipo.close();
            uTipo.close();
            dTipo.close();
            iTask.close();
            dTask.close();
            uTask.close();
            iDomanda.close();
            uDomanda.close();
            dDomanda.close();
            iProposta.close();
            uProposta.close();
            dProposta.close();
            iMessaggio.close();
            uMessaggio.close();
            dMessaggio.close();
            iUtenteProgetto.close();
            uUtenteProgetto.close();
            dUtenteProgetto.close();
            iTaskSkill.close();
            uTaskSkill.close();
            iTipoSkill.close();
            uTipoSkill.close();
            dTipoSkillByTipo.close();
            dTipoSkillBySkill.close();
            iUtenteSkill.close();
            uUtenteSkill.close();
            sTaskById.close();
            sTaskApertiByProgetto.close();
            sProgettoById.close();
            sUtenteById.close();
            sPropostaById.close();
            sDomandaById.close();
            sSkillById.close();
            sTipoById.close();
            sTipoSkillById.close();
            sMessaggioById.close();
            sUtenteProgettoById.close();
            sUtenteSkillById.close();
            sProgetti.close();
            sTask.close();
            sMessaggi.close();
            sMessaggiPubblici.close();
            sUtenteProgetto.close();
            sTaskAperti.close();
            sSkill.close();
            sTipi.close();
            sUtenteBySkill.close();
            sUtenteBySkillLivello.close();
            sTaskTipoSkill.close();
            sDomandeByUtente.close();
            sProposteByUtente.close();
            sFilesByUtente.close();
            iFiles.close();
            uFiles.close();
            sUtenteByEmail.close();
            sSkillByTipo.close();
            sUtenteProgettoByUeP.close();
            sTaskTipoSkillByTask.close();
            sTipoSkillByTipo.close();
            sTipoByNome.close();
            sUtenteProgettoByProgetto.close();
            sProgettoByAdmin.close();
            sTaskByUtente.close();
            sPropostaInAttesa.close();
            sDomandaInAttesa.close();
            sStatoDomanda.close();
            sTaskByDomanda.close();
            sTaskByProposta.close();
            sValutazioneByDomanda.close();
            sValutazioneByProposta.close();
            sPropostaByUtenteTask.close();
            sDomandaUtenti.close();
            sPropostaUtenti.close();
            dTaskSkill.close();
            sTaskInvitato.close();
            sUtenteInvitato.close();
            sStatoInvitato.close();
            sUtenteByDomandaEffettuata.close();
            sTaskByDomandaEffettuata.close();
            sDomandaByUtenteTask.close();
            sSkillByUtente.close();
            sLivelloByUtente.close();
            sTaskBySkillLivello.close();
            sUtentiPartecipantiProgettoProp.close();
            sUtentiPartecipantiProgettoDomanda.close();

        } catch (SQLException ex) {

        }
        super.destroy();
    }

}
