package org.sonar.php.tree.visitors;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.sonar.plugins.php.api.symbols.Symbol;
import org.sonar.plugins.php.api.symbols.SymbolTable;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.expression.AssignmentExpressionTree;
import org.sonar.plugins.php.api.tree.expression.ExpressionTree;
import org.sonar.plugins.php.api.visitors.PHPVisitorCheck;

public class AssignmentExpressionVisitor extends PHPVisitorCheck {

  private Map<Symbol, List<ExpressionTree>> assignedValuesBySymbol = new HashMap<>();
  private SymbolTable symbolTable;

  public AssignmentExpressionVisitor(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  @Override
  public void visitAssignmentExpression(AssignmentExpressionTree assignment) {
    ExpressionTree variable = assignment.variable();
    Symbol symbol = symbolTable.getSymbol(variable);
    if (symbol != null) {
      if (!assignedValuesBySymbol.containsKey(symbol)) {
        assignedValuesBySymbol.put(symbol, new ArrayList<>());
      }
      assignedValuesBySymbol.get(symbol).add(assignment.value());
    }
    super.visitAssignmentExpression(assignment);
  }

  public Optional<ExpressionTree> getAssignmentValue(Tree expression) {
    Symbol symbol = symbolTable.getSymbol(expression);
    List<ExpressionTree> values = assignedValuesBySymbol.get(symbol);
    return values != null && values.size() == 1 ? Optional.of(values.get(0)) : Optional.empty();
  }

}