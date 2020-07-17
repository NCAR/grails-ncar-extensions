package edu.ucar.hibernate;

import org.hibernate.dialect.*;

/**
 * A Hibernate/GORM dialect for MySQL 5.7, additionally specifying the MyISAM engine and the UTF8 charset.
 *
 * In Grails/GORM configuration, specify the dialect as a string containing the name of this class
 * with key <code>hibernate.dialect</code> or <code>dataSource.dialect</code>
 * in <code>application.yml</code> or <code>application.groovy</code>
 * (or external config files).
 * (Direct class reference in groovy also works, but is converted to a String)
 *
 * @see <a href="http://grails.1312388.n4.nabble.com/findBy-method-is-case-insensitive-td1379303.html#a1379308">http://grails.1312388.n4.nabble.com/findBy-method-is-case-insensitive-td1379303.html#a1379308</a>
 */
public class MySQL57MyISAMUtf8Dialect extends MySQL57Dialect {
    // MySQLMyISAMDialect is deprecated! It tells us to set hibernate.dialect.storage_engine = myisam
    // but we can't set that and hibernate.dialect in Grails/GORM due to hierarchical property maps.

    @Override
    public String getTableTypeString() {
        return super.getTableTypeString() + " DEFAULT CHARSET=utf8";
        }

    @Override
    protected MySQLStorageEngine getDefaultMySQLStorageEngine() {
        return MyISAMStorageEngine.INSTANCE;
    }

}
