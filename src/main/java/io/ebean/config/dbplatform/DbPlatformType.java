package io.ebean.config.dbplatform;

/**
 * Represents a DB type with name, length, precision, and scale.
 * <p>
 * The length is for VARCHAR types and precision/scale for DECIMAL types.
 * </p>
 */
public class DbPlatformType implements ExtraDbTypes {

  /**
   * The data type name (VARCHAR, INTEGER ...)
   */
  private final String name;

  /**
   * The default length or precision.
   */
  private final int defaultLength;

  /**
   * The default scale (decimal).
   */
  private final int defaultScale;

  /**
   * Set to true if the type should never have a length or scale.
   */
  private final boolean canHaveLength;

  /**
   * Parse a type definition into a DbPlatformType.
   * <p>
   * e.g. "decimal(18,6)"
   * e.g. "text"
   * </p>
   */
  public static DbPlatformType parse(String columnDefinition) {
    return DbPlatformTypeParser.parse(columnDefinition);
  }

  /**
   * Construct with no length or scale.
   */
  public DbPlatformType(String name) {
    this(name, 0, 0);
  }

  /**
   * Construct with a given length.
   */
  public DbPlatformType(String name, int defaultLength) {
    this(name, defaultLength, 0);
  }

  /**
   * Construct for Decimal with precision and scale.
   */
  public DbPlatformType(String name, int defaultPrecision, int defaultScale) {
    this.name = name;
    this.defaultLength = defaultPrecision;
    this.defaultScale = defaultScale;
    this.canHaveLength = true;
  }

  /**
   * Use with canHaveLength=false for types that should never have a length.
   *
   * @param name          the type name
   * @param canHaveLength set this to false for type that should never have a length
   */
  public DbPlatformType(String name, boolean canHaveLength) {
    this.name = name;
    this.defaultLength = 0;
    this.defaultScale = 0;
    this.canHaveLength = canHaveLength;
  }

  /**
   * Return the type name.
   */
  public String getName() {
    return name;
  }

  /**
   * Return the default length/precision.
   */
  public int getDefaultLength() {
    return defaultLength;
  }

  /**
   * Return the default scale.
   */
  public int getDefaultScale() {
    return defaultScale;
  }

  /**
   * Return the type for a specific property that incorporates the name, length,
   * precision and scale.
   * <p>
   * The deployLength and deployScale are for the property we are rendering the
   * DB type for.
   * </p>
   *
   * @param deployLength the length or precision defined by deployment on a specific
   *                     property.
   * @param deployScale  the scale defined by deployment on a specific property.
   */
  public String renderType(int deployLength, int deployScale) {
    return renderType(deployLength, deployScale, true);
  }

  /**
   * Render the type defining strict mode.
   * <p>
   * If strict mode if OFF then this will render with a scale value even if
   * that is not strictly supported. The reason for supporting this is to enable
   * use to use types like jsonb(200) as a "logical" type that maps to JSONB for
   * Postgres and VARCHAR(200) for other databases.
   * </p>
   */
  public String renderType(int deployLength, int deployScale, boolean strict) {

    StringBuilder sb = new StringBuilder();
    sb.append(name);

    if (canHaveLength || !strict) {
      // see if there is a precision/scale to add (or not)
      int len = deployLength != 0 ? deployLength : defaultLength;
      if (len == Integer.MAX_VALUE) {
        sb.append("(max)"); // TODO: this is sqlserver specific
      } else if (len > 0) {
        sb.append("(");
        sb.append(len);
        int scale = deployScale != 0 ? deployScale : defaultScale;
        if (scale > 0) {
          sb.append(",");
          sb.append(scale);
        }
        sb.append(")");
      }
    }

    return sb.toString();
  }

  /**
   * Create a copy of the type with a new default length.
   */
  public DbPlatformType withLength(int defaultLength) {
    return new DbPlatformType(name, defaultLength);
  }
}
