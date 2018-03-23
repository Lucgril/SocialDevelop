package iw.framework.data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataLayerMysqlImpl implements DataLayer {

    public DataSource datasource;
    public Connection connection;

    public DataLayerMysqlImpl(DataSource datasource) throws SQLException, NamingException {
        super();
        this.datasource = datasource;
        this.connection = null;
    }

    @Override
    public void init() throws DataLayerException {
        try {
           
            connection = datasource.getConnection();
        } catch (SQLException ex) {
            throw new DataLayerException("Error initializing data layer", ex);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException ex) {
            
        }
    }

    @Override

    public void close() throws Exception {
        destroy();
    }
}
