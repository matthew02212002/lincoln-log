/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Instantiates the {@link org.hibernate.SessionFactory} and provides an accessor
 * method for it
 * @author sethschwiethale
 */
public class HibernateUtil {

    private static Configuration config;
    private static final SessionFactory sessionFactory = buildSessionFactory();
    /*
     - logger for this class
     */
    private static Logger logger = Logger.getLogger(HibernateUtil.class);
    /**
     * builds a {@link org.hibernate.SessionFactory} from the
     * {@link org.hibernate.cfg.Configuration} at the root of this project
     * NOTE: I found it much more simplified to generate the schema here whenever
     * I wanted to by uncommenting the {@link #generateSchema() } line
     * @return
     */
    private static SessionFactory buildSessionFactory() {
        try {
// Create the SessionFactory from hibernate.cfg.xml
            config = new Configuration();
            config.configure();

//            generateSchema();

            return config.buildSessionFactory();

        } catch (Throwable ex) {
// Make sure you log the exception, as it might be swallowed
            logger.error("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex.fillInStackTrace());
        }
    }
    /**
     * returns the single instance of {@link org.hibernate.SessionFactory} for
     * this application
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    /**
     * uses Hibernates hbm2ddl tool to generate the schema from the
     * {@link org.hibernate.cfg.Configuration}
     */
    private static void generateSchema() {
        SchemaExport exporter;
        exporter = new SchemaExport(config);
        exporter.create(true, true);
    }

}
