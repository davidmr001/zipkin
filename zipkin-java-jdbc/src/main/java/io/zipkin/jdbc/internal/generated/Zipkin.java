/**
 * This class is generated by jOOQ
 */
package io.zipkin.jdbc.internal.generated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import io.zipkin.jdbc.internal.generated.tables.ZipkinAnnotations;
import io.zipkin.jdbc.internal.generated.tables.ZipkinSpans;

/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org",
    "jOOQ version:3.7.0" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Zipkin extends SchemaImpl {

  private static final long serialVersionUID = -889462586;

  /**
   * The reference instance of <code>zipkin</code>
   */
  public static final Zipkin ZIPKIN = new Zipkin();

  /**
   * No further instances allowed
   */
  private Zipkin() {
    super("zipkin");
  }

  @Override
  public final List<Table<?>> getTables() {
    List result = new ArrayList();
    result.addAll(getTables0());
    return result;
  }

  private final List<Table<?>> getTables0() {
    return Arrays.<Table<?>>asList(ZipkinAnnotations.ZIPKIN_ANNOTATIONS,
        ZipkinSpans.ZIPKIN_SPANS);
  }
}