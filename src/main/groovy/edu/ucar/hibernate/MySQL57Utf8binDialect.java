package edu.ucar.hibernate;

/**
 * A Hibernate/GORM dialect for MySQL 5.7, additionally specifying the UTF8 charset
 * and a utf8_bin collation.
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
public class MySQL57Utf8binDialect extends MySQL57Utf8Dialect {

    @Override
    public String getTableTypeString() {
        return super.getTableTypeString() + " COLLATE=utf8_bin";
        }

}
