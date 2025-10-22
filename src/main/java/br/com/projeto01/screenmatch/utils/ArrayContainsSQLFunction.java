package br.com.projeto01.screenmatch.utils;

import java.util.List;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.SqlTypes;

public class ArrayContainsSQLFunction extends StandardSQLFunction {

  private static final BasicTypeReference<Boolean> RETURN_TYPE = new BasicTypeReference<>("boolean",
      Boolean.class, SqlTypes.BOOLEAN);

  public ArrayContainsSQLFunction(final String funcName) {
    super(funcName, true, RETURN_TYPE);
  }

  public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> arguments,
      SqlAstTranslator<?> translator) {

    if (arguments.size() != 2) {
      throw new IllegalArgumentException("Function error");
    }

    sqlAppender.append("(");
    arguments.get(0).accept(translator);
    sqlAppender.append(" @> ARRAY[");
    arguments.get(1).accept(translator);
    sqlAppender.append("]");
  }
}
