package edu.ucar.mysql;

import java.time.LocalDateTime;
import java.sql.Timestamp;

/**
 * Collection of static/constant default values for use in GORM domains
 */
class Defaults {

  // mysql integer types
  // http://dev.mysql.com/doc/refman/5.6/en/integer-types.html
  // min unsigned is always 0

  // CAREFUL: Java primitive types are all SIGNED,
  //   so the unsigned maximums need to use the next bigger primitive

  /**
   * (signed) tinyint(1)
   */
  final static byte minTinyint = -128;

  /**
   * (signed) tinyint(1)
   */
  final static byte maxTinyint = 127;

  /**
   * unsigned tinyint(1)
   */
  final static short maxUnsignedTinyint = 255;

  /**
   * (signed) smallint(6)
   */
  final static short minSmallint = -32768;

  /**
   * (signed) smallint(6)
   */
  final static short maxSmallint = 32767;

  /**
   * unsigned smallint(5)
   */
  final static int maxUnsignedSmallint = 65535;

  /**
   * (signed) mediumint(9)
   */
  final static long minMediumint = -8388608;

  /**
   * (signed)  mediumint(9)
   */
  final static long maxMediumint = 8388607;

  /**
   * (signed)  mediumint(8)
   */
  final static long maxUnsignedMediumint = 16777215;

  /**
   * (signed) int(11)
   */
  final static long minInt = -2147483648;

  /**
   * (signed)  int(11)
   */
  final static long maxInt = 2147483647;

  /**
   * unsigned  int(10)
   */
  final static long maxUnsignedInt = 4294967295;

  /**
   * (signed) bigint(20)
   */
  final static BigInteger minBigint = new BigInteger('-9223372036854775808');

  /**
   * (signed) bigint(20)
   */
  final static BigInteger maxBigint = new BigInteger('9223372036854775807');

  /**
   * unsigned bigint(20)
   */
  final static BigInteger minUnsignedBigint = BigInteger.ZERO;

  /**
   * unsigned bigint(20)
   */
  final static BigInteger maxUnsignedBigint = new BigInteger('18446744073709551615');

  /**
   * MySQL minimum is 1s; epoch [0,1s) is invalid.
   * This value provides a valid default instead of having to deal with
   * the MySQL special zero value '0000-00-00 00:00:00'.
   */
  final static Timestamp minTimestamp = new Timestamp(1000);

  /**
   * sigh, stuck on 32-bits
   *
   * @seealso https://bugs.mysql.com/bug.php?id=12654
   */
  final static Timestamp maxTimestamp = new Timestamp(2147483647000);

  /**
   * hardcoded string versions for GORM, since GORM stuff happens before BootStrap, so the default timezone is probably wrong
   * quoted versions since GORM mappings don't quote what needs quotes :(
   */
  final static String minTimestampQuotedString = "'1970-01-01 00:00:01'";
  final static String maxTimestampQuotedString = "'2038-01-19 03:14:07'";

  /**
   * strings that may be useful with Hibernate parametized values (prefer to use the Timestamp values above)
   */
  final static String minTimestampString = '1970-01-01 00:00:01';
  final static String maxTimestampString = '2038-01-19 03:14:07';
  final static String minTimestampStringFsp = '1970-01-01 00:00:01.000000';

  /**
   * documented maximums but no way to actually get these into a column as mysql will round
   * <code>
   *   update table set column = '2038-01-19 03:14:07.999999'
   *   update table set column = timestamp('2038-01-19 03:14:07.999999')
   *   => "Incorrect datetime value"
   * </code>
   */
  final static BigDecimal maxMysqlTimestampDecimal = new BigDecimal('2147483647.999999');
  final static String maxTimestampStringFsp = '2038-01-19 03:14:07.999999';


  /**
   * approximate earliest reasonable date
   */
  final static LocalDateTime beginDate = LocalDateTime.of(1750,1,1,0,0,0);

  /**
   * latest reasonable date
   */
  final static LocalDateTime endDate = LocalDateTime.of(9999,12,31,23,59,59);

  /**
   * normal global values
   */
  final static BigDecimal minimumLatitude = new BigDecimal(-90);
  final static BigDecimal minimumLongitude = new BigDecimal(-180);
  final static BigDecimal maximumLatitude = new BigDecimal(90);
  final static BigDecimal maximumLongitude = new BigDecimal(180);

}
