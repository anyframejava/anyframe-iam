package com.sds.emp.security.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

import javax.security.auth.Subject;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import com.sds.emp.common.EmpException;
import com.sds.emp.security.services.AuthenticationService;
import com.sds.emp.security.services.Credential;
import com.tagish.auth.TypedPrincipal;

public class DBAuthenticationService implements AuthenticationService,
        ApplicationContextAware {

    private DataSource dataSource = null;

    private String sqlQuery = null;

    private MessageSource messageSource = null;

    private static Log logger =
        LogFactory.getLog(DBAuthenticationService.class);

    /**
     * set data source
     * @param dataSource
     *        be used when execute query
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * set query statement for authenticate
     * @param sqlQuery
     *        query statement
     */
    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    /**
     * set ApplicationContext
     * @param applicationContext
     *        to be set by container
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.messageSource =
            (MessageSource) applicationContext.getBean("messageSource");
    }

    /**
     * Use this method to return a populated Subject.
     * It is populated with the principals which
     * represent the identity of the user as well as
     * any other principal for which permissions may be
     * associated. For instance, if the configured
     * Realm implementation has
     * <code>GroupSupport</code> then the
     * Authentication Manager may choose to add a
     * Principal for each Group the user is a member
     * of. Additional Principals may be added to
     * represent security related attributes that may
     * be reflected in Policy definition, for Realms
     * with <code>AttributeSupport</code>.
     * @param credential
     *        a collection of credential objects used
     *        provided as proof of identity
     * @return a Subject populated with appropriate
     *         Principals
     * @throws EmpException
     *         thrown if there is a problem during
     *         authentication
     */
    public Subject authenticate(Credential credential) throws EmpException {

        Subject subject = null;
        ResultSet rsu = null;
        PreparedStatement pstmt = null;
        Connection conn = null;

        String userid = credential.getProperty("userid");
        String password = credential.getProperty("password").trim();

        try {

            conn = dataSource.getConnection();

            if (logger.isInfoEnabled())
                logger.info(messageSource.getMessage("info.security.data.sql",
                    new String[] {sqlQuery }, Locale.getDefault()));
            pstmt = conn.prepareStatement(sqlQuery);

            if (logger.isDebugEnabled()) {
                logger.debug(messageSource.getMessage(
                    "debug.security.data.userid", new String[] {userid },
                    Locale.getDefault()));
                logger.debug(messageSource.getMessage(
                    "debug.security.data.userid", new String[] {password },
                    Locale.getDefault()));
            }

            pstmt.setString(1, userid);
            pstmt.setString(2, password);

            rsu = pstmt.executeQuery();

            if (rsu.next()) {
                String username = rsu.getString(2);
                String grade = rsu.getString(5);

                Set principals = new HashSet();
                Set credentials = new HashSet();

                principals
                    .add(new TypedPrincipal(username, TypedPrincipal.USER));

                StringTokenizer tokens = new StringTokenizer(grade, ",");
                while (tokens.hasMoreTokens()) {
                    principals.add(new TypedPrincipal(tokens.nextToken(),
                        TypedPrincipal.GROUP));
                }

                subject =
                    new Subject(false, principals, credentials, credentials);
            } else {
                throw new EmpException(messageSource, "error.security.login");
            }

        } catch (Exception e) {
            if (e instanceof EmpException)
                throw (EmpException) e;
            else
                throw new EmpException(messageSource,
                    "error.security.check.userid", e);
        } finally {
            try {
                if (rsu != null)
                    rsu.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                
            }
        }
        return subject;
    }
}
