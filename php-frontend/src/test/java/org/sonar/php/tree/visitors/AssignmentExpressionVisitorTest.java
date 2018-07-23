package org.sonar.php.tree.visitors;

import org.junit.Test;
import org.sonar.php.PHPTreeModelTest;
import org.sonar.php.parser.PHPLexicalGrammar;
import org.sonar.php.tree.symbols.SymbolTableImpl;
import org.sonar.plugins.php.api.symbols.SymbolTable;
import org.sonar.plugins.php.api.tree.CompilationUnitTree;
import org.sonar.plugins.php.api.tree.expression.IdentifierTree;
import org.sonar.plugins.php.api.tree.expression.LiteralTree;

import static org.fest.assertions.Assertions.assertThat;

public class AssignmentExpressionVisitorTest extends PHPTreeModelTest {

  @Test
  public void getAssignmentValue() throws Exception {
    CompilationUnitTree tree = parse("<?php function foo() { $a = 1; }", PHPLexicalGrammar.COMPILATION_UNIT);
    SymbolTable symbolTable = SymbolTableImpl.create(tree);

    AssignmentExpressionVisitor assignmentExpressionVisitor = new AssignmentExpressionVisitor(symbolTable);
    tree.accept(assignmentExpressionVisitor);
    IdentifierTree var = ((SymbolTableImpl) symbolTable).getSymbols("$a").get(0).declaration();

    assertThat(assignmentExpressionVisitor.getAssignmentValue(var).get()).isInstanceOf(LiteralTree.class);

  }

  @Test
  public void getAssignmentValue_global() throws Exception {
    CompilationUnitTree tree = parse("<?php $a = 1;", PHPLexicalGrammar.COMPILATION_UNIT);
    SymbolTable symbolTable = SymbolTableImpl.create(tree);

    AssignmentExpressionVisitor assignmentExpressionVisitor = new AssignmentExpressionVisitor(symbolTable);
    tree.accept(assignmentExpressionVisitor);
    IdentifierTree var = ((SymbolTableImpl) symbolTable).getSymbols("$a").get(0).declaration();

    assertThat(assignmentExpressionVisitor.getAssignmentValue(var).get()).isInstanceOf(LiteralTree.class);

  }

  @Test
  public void getAssignmentValue_multiple() throws Exception {
    CompilationUnitTree tree = parse("<?php $a = 1;\n$a = 2;", PHPLexicalGrammar.COMPILATION_UNIT);
    SymbolTable symbolTable = SymbolTableImpl.create(tree);

    AssignmentExpressionVisitor assignmentExpressionVisitor = new AssignmentExpressionVisitor(symbolTable);
    tree.accept(assignmentExpressionVisitor);
    IdentifierTree var = ((SymbolTableImpl) symbolTable).getSymbols("$a").get(0).declaration();

    assertThat(assignmentExpressionVisitor.getAssignmentValue(var).isPresent()).isFalse();

  }


}