/**
 * 
 */
package org.tch.ste.infra.repository;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tch.ste.domain.constant.MivConstants;

/**
 * Proxy Datasource which delegates to actual data source.
 * 
 * @author Karthik.
 * 
 */
public class SteDataSource implements DataSource {

    private Map<String, DataSource> dataSources;

    private String defaultDataSourceName;

    /**
     * Constructor.
     * 
     * @param dataSources
     *            Map<String,DataSource> - The datasources.
     * @param defaultDataSourceName
     *            String - The name of the default datasource.
     */
    public SteDataSource(Map<String, DataSource> dataSources, String defaultDataSourceName) {
        this.dataSources = dataSources;
        this.defaultDataSourceName = defaultDataSourceName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.sql.CommonDataSource#getLogWriter()
     */
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getCurrentDataSource().getLogWriter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.sql.CommonDataSource#getLoginTimeout()
     */
    @Override
    public int getLoginTimeout() throws SQLException {
        return getCurrentDataSource().getLoginTimeout();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.sql.CommonDataSource#getParentLogger()
     */
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getCurrentDataSource().getParentLogger();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
     */
    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getCurrentDataSource().setLogWriter(out);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.sql.CommonDataSource#setLoginTimeout(int)
     */
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getCurrentDataSource().setLoginTimeout(seconds);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getCurrentDataSource().isWrapperFor(iface);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getCurrentDataSource().unwrap(iface);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.sql.DataSource#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        return getCurrentDataSource().getConnection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.sql.DataSource#getConnection(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getCurrentDataSource().getConnection(username, password);
    }

    /**
     * Fetches the current data source for the batch.
     * 
     * 
     * @return DataSource - The datasource chosen.
     */
    private DataSource getCurrentDataSource() {
        DataSource retVal = null;
        String currPersistenceUnit = (String) TransactionSynchronizationManager
                .getResource(MivConstants.CURRENT_PERSISTENCE_UNIT);
        if (currPersistenceUnit != null) {
            retVal = this.dataSources.get(currPersistenceUnit);
        }
        if (retVal == null) {
            retVal = this.dataSources.get(this.defaultDataSourceName);
        }
        return retVal;
    }

}
