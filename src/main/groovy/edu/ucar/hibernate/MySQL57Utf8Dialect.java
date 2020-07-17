package edu.ucar.hibernate;

/**
 * A Hibernate/GORM dialect for MySQL 5.7, additionally specifying the UTF8 charset.
 * <b>This is usually the class one should use for a Grails app.</b>
 *
 * Note that the MySQL 5.5/5.7 dialects default to the InnoDB storage engine.
 *
 * In Grails/GORM configuration, specify the dialect as a string containing the name of this class
 * with key <code>hibernate.dialect</code> or <code>dataSource.dialect</code>
 * in <code>application.yml</code> or <code>application.groovy</code>
 * (or external config files).
 * (Direct class reference in groovy also works, but is converted to a String)
 *
 * @see <a href="http://grails.1312388.n4.nabble.com/findBy-method-is-case-insensitive-td1379303.html#a1379308">http://grails.1312388.n4.nabble.com/findBy-method-is-case-insensitive-td1379303.html#a1379308</a>
 */
public class MySQL57Utf8Dialect extends org.hibernate.dialect.MySQL57Dialect {
    // MySQL57InnodbDialect is deprecated! It tells us to set hibernate.dialect.storage_engine = innodb
    // but we can't set that and hibernate.dialect in Grails/GORM due to hierarchical property maps.
    // Fortunately, InnoDB is the default in MySQL57Dialect (from MySQL55Dialect).

    @Override
    public String getTableTypeString() {
        return super.getTableTypeString() + " DEFAULT CHARSET=utf8";
        }

}
