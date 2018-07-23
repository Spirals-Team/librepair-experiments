/*
 * SonarQube PHP Plugin
 * Copyright (C) 2010-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.php.checks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.sonar.check.Rule;
import org.sonar.php.checks.utils.CheckUtils;
import org.sonar.plugins.php.api.symbols.Symbol;
import org.sonar.plugins.php.api.tree.CompilationUnitTree;
import org.sonar.plugins.php.api.tree.SeparatedList;
import org.sonar.plugins.php.api.tree.Tree.Kind;
import org.sonar.plugins.php.api.tree.expression.ArrayInitializerTree;
import org.sonar.plugins.php.api.tree.expression.ArrayPairTree;
import org.sonar.plugins.php.api.tree.expression.AssignmentExpressionTree;
import org.sonar.plugins.php.api.tree.expression.BinaryExpressionTree;
import org.sonar.plugins.php.api.tree.expression.ExpressionTree;
import org.sonar.plugins.php.api.tree.expression.FunctionCallTree;
import org.sonar.plugins.php.api.tree.expression.LiteralTree;
import org.sonar.plugins.php.api.visitors.PHPVisitorCheck;

import static org.sonar.php.checks.utils.CheckUtils.trimQuotes;

@Rule(key = EmptyDatabasePasswordCheck.KEY)
public class EmptyDatabasePasswordCheck  extends PHPVisitorCheck {

  public static final String KEY = "S2115";

  private static final String MESSAGE = "Add password protection to this database.";

  private Map<Symbol, List<ExpressionTree>> assignedValuesBySymbol = new HashMap<>();
  private List<FunctionCallTree> functionCalls = new ArrayList<>();

  @Override
  public void visitCompilationUnit(CompilationUnitTree tree) {
    assignedValuesBySymbol.clear();
    functionCalls.clear();
    super.visitCompilationUnit(tree);
    checkFunctionCalls();
  }

  @Override
  public void visitAssignmentExpression(AssignmentExpressionTree assignment) {
    ExpressionTree variable = assignment.variable();
    Symbol symbol = context().symbolTable().getSymbol(variable);
    if (symbol != null) {
      if (!assignedValuesBySymbol.containsKey(symbol)) {
        assignedValuesBySymbol.put(symbol, new ArrayList<>());
      }
      assignedValuesBySymbol.get(symbol).add(assignment.value());
    }
    super.visitAssignmentExpression(assignment);
  }

  @Override
  public void visitFunctionCall(FunctionCallTree tree) {
    functionCalls.add(tree);
    super.visitFunctionCall(tree);
  }

  private void checkFunctionCalls() {
    for (FunctionCallTree functionCall : functionCalls) {
      String functionName = CheckUtils.getFunctionName(functionCall);
      if ("mysqli".equals(functionName) || "mysqli_connect".equals(functionName) || "PDO".equals(functionName)) {
        checkPasswordArgument(functionCall, 2);
      } else if ("oci_connect".equals(functionName)) {
        checkPasswordArgument(functionCall, 1);
      } else if ("sqlsrv_connect".equals(functionName)) {
        checkSqlServer(functionCall);
      } else if ("pg_connect".equals(functionName)) {
        checkPostgresql(functionCall);
      }
    }
  }

  private void checkPasswordArgument(FunctionCallTree functionCall, int argumentIndex) {
    SeparatedList<ExpressionTree> arguments = functionCall.arguments();
    if (arguments.size() > argumentIndex) {
      ExpressionTree passwordArgument = arguments.get(argumentIndex);
      if (hasEmptyValue(passwordArgument)) {
        context().newIssue(this, passwordArgument, MESSAGE);
      }
    }
  }

  private static boolean isEmptyLiteral(ExpressionTree expression) {
    if (expression.is(Kind.REGULAR_STRING_LITERAL)) {
      LiteralTree literal = (LiteralTree) expression;
      return literal.value().length() == 2;
    }
    return false;
  }

  private boolean hasEmptyValue(ExpressionTree expression) {
    if (isEmptyLiteral(expression)) {
      return true;
    } else if (expression.is(Kind.VARIABLE_IDENTIFIER)) {
      Symbol symbol = context().symbolTable().getSymbol(expression);
      List<ExpressionTree> assignedValues = assignedValuesBySymbol.get(symbol);
      return assignedValues != null && assignedValues.stream().allMatch(EmptyDatabasePasswordCheck::isEmptyLiteral);
    }
    return false;
  }

  private void checkSqlServer(FunctionCallTree functionCall) {
    SeparatedList<ExpressionTree> arguments = functionCall.arguments();
    int argumentIndex = 1;
    if (arguments.size() > argumentIndex) {
      ExpressionTree connectionInfo = arguments.get(argumentIndex);
      ExpressionTree password = sqlServerPassword(connectionInfo);
      if (password != null && hasEmptyValue(password)) {
        context().newIssue(this, password, MESSAGE);
      }
    }
  }

  private ExpressionTree sqlServerPassword(ExpressionTree connectionInfo) {
    if (connectionInfo.is(Kind.ARRAY_INITIALIZER_FUNCTION, Kind.ARRAY_INITIALIZER_BRACKET)) {
      for (ArrayPairTree arrayPairTree : ((ArrayInitializerTree) connectionInfo).arrayPairs()) {
        ExpressionTree key = arrayPairTree.key();
        if (key != null && key.is(Kind.REGULAR_STRING_LITERAL) && "PWD".equals(trimQuotes((LiteralTree) key))) {
          return arrayPairTree.value();
        }
      }
      return null;
    }
    Symbol symbol = context().symbolTable().getSymbol(connectionInfo);
    List<ExpressionTree> assignedValues = assignedValuesBySymbol.get(symbol);
    if (assignedValues == null || assignedValues.size() != 1) {
      return null;
    }
    return sqlServerPassword(assignedValues.get(0));
  }

  private void checkPostgresql(FunctionCallTree functionCall) {
    SeparatedList<ExpressionTree> arguments = functionCall.arguments();
    if (arguments.isEmpty()) {
      return;
    }
    ExpressionTree connectionString = arguments.get(0);
    Symbol symbol = context().symbolTable().getSymbol(connectionString);
    List<ExpressionTree> assignedValues = assignedValuesBySymbol.get(symbol);
    if (assignedValues != null && assignedValues.size() == 1) {
      connectionString = assignedValues.get(0);
    }
    checkPostgresqlConnectionString(connectionString);
  }

  private void checkPostgresqlConnectionString(ExpressionTree connectionString) {
    List<ExpressionTree> concatenationOperands = new ArrayList<>();
    if (connectionString.is(Kind.CONCATENATION)) {
      concatenationOperands(connectionString, concatenationOperands);
    } else {
      concatenationOperands.add(connectionString);
    }

    ExpressionTree connectionStringLastPart = concatenationOperands.get(concatenationOperands.size() - 1);
    Pattern noPasswordPattern = Pattern.compile(".*password\\s*=\\s*");
    Pattern emptyPasswordPattern = Pattern.compile(noPasswordPattern.pattern() + "''.*");

    if (concatenationOperands.stream().anyMatch(e -> isStringLiteralMatching(emptyPasswordPattern, e))
      || isStringLiteralMatching(noPasswordPattern, connectionStringLastPart)) {
      context().newIssue(this, connectionString, MESSAGE);
    }
  }

  private static boolean isStringLiteralMatching(Pattern pattern, ExpressionTree expressionTree) {
    if (expressionTree.is(Kind.REGULAR_STRING_LITERAL)) {
      return pattern.matcher(trimQuotes((LiteralTree) expressionTree)).matches();
    }
    return false;
  }

  private static void concatenationOperands(ExpressionTree expression, List<ExpressionTree> operands) {
    if (expression.is(Kind.CONCATENATION)) {
      BinaryExpressionTree binary = (BinaryExpressionTree) expression;
      concatenationOperands(binary.leftOperand(), operands);
      concatenationOperands(binary.rightOperand(), operands);
    } else {
      operands.add(expression);
    }
  }

}
