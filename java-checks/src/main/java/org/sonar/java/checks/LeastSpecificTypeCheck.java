/*
 * SonarQube Java
 * Copyright (C) 2012-2017 SonarSource SA
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
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.java.resolve.ClassJavaType;
import org.sonar.java.resolve.JavaSymbol;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Rule(key = "S3242")
public class LeastSpecificTypeCheck extends IssuableSubscriptionVisitor {

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return Collections.singletonList(Tree.Kind.METHOD);
  }

  @Override
  public void visitNode(Tree tree) {
    if (!hasSemantic()) {
      return;
    }

    MethodTree methodTree = (MethodTree) tree;
    Symbol.MethodSymbol methodSymbol = methodTree.symbol();
    if (!methodSymbol.isPublic() || isImplementationOrOverride(methodSymbol)) {
      return;
    }

    List<Symbol> parameters = methodTree.parameters().stream()
      .map(VariableTree::symbol)
      .collect(Collectors.toList());

    for (Symbol param: parameters) {
      handleParameter(param);
    }
  }

  private void handleParameter(Symbol parameter) {
    List<IdentifierTree> usages = parameter.usages();
    List<Symbol> usageSymbols = new ArrayList<>();
    for (IdentifierTree usage: usages) {
      Tree parent = usage.parent();
      while (parent != null && !parent.is(Tree.Kind.ARGUMENTS, Tree.Kind.METHOD_INVOCATION, Tree.Kind.ASSIGNMENT, Tree.Kind.EXPRESSION_STATEMENT)) {
        parent = parent.parent();
      }
      if (parent == null || parent.is(Tree.Kind.EXPRESSION_STATEMENT)) {
        return;
      }
      if (parent.is(Tree.Kind.METHOD_INVOCATION)) {
        Symbol methodInvocation = ((MethodInvocationTree) parent).symbol();
        usageSymbols.add(methodInvocation);
      }
    }
    if (!usageSymbols.isEmpty()) {
      JavaSymbol.TypeJavaSymbol parameterType = (JavaSymbol.TypeJavaSymbol) parameter.type().symbol();
      Symbol.TypeSymbol leastSpecificType = findLeastSpecificType(usageSymbols, parameterType);
      if (parameter.type().symbol() != leastSpecificType && !leastSpecificType.type().is("java.lang.Object")) {
        String suggestedType = leastSpecificType.type().fullyQualifiedName().replace('$', '.');
        reportIssue(parameter.declaration(), String.format("Use '%s' here; it is a more general type than '%s'.", suggestedType, parameter.type()));
      }
    }
  }

  private static boolean isImplementationOrOverride(Symbol.MethodSymbol method) {
    if (method.isStatic()) {
      return false;
    }
    Symbol owner = method.owner();
    for (ClassJavaType type : ((JavaSymbol.TypeJavaSymbol) owner).superTypes()) {
      Collection<Symbol> symbols = type.symbol().lookupSymbols(method.name());
      if (symbols.stream()
        .filter(Symbol::isMethodSymbol)
        .anyMatch(s -> sameMethod(method, (Symbol.MethodSymbol) s))) {
        return true;
      }
    }
    return false;
  }

  private static Symbol.TypeSymbol findLeastSpecificType(List<Symbol> usedSymbols, Symbol.TypeSymbol candidate) {
    List<Type> interfaces = candidate.interfaces();
    for (Type iface: interfaces) {
      if (containsAll(usedSymbols, iface.symbol())) {
        return findLeastSpecificType(usedSymbols, iface.symbol());
      }
    }
    Type superClass = candidate.superClass();
    if (superClass != null && containsAll(usedSymbols, superClass.symbol())) {
      return findLeastSpecificType(usedSymbols, superClass.symbol());
    }
    return candidate;
  }


  private static boolean containsAll(List<Symbol> usedSymbols, Symbol.TypeSymbol candidate) {
//    System.out.println("Testing " + candidate.type().fullyQualifiedName());
    Collection<Symbol> memberSymbols = allMembers(candidate);
    for (Symbol usedSymbol: usedSymbols) {
      if (memberSymbols.stream().noneMatch(m -> sameSymbol(usedSymbol, m))) {
//        System.out.println("Missing " + usedSymbol.name());
        return false;
      }
    }
    return true;
  }

  private static Collection<Symbol> allMembers(Symbol.TypeSymbol candidate) {
    ArrayList<Symbol> symbols = new ArrayList<>(candidate.memberSymbols());
    List<Symbol> inherited = ((JavaSymbol.TypeJavaSymbol) candidate).superTypes().stream()
      .flatMap(type -> type.symbol().memberSymbols().stream())
      .collect(Collectors.toList());
    symbols.addAll(inherited);
    return symbols;
  }

  private static boolean sameSymbol(Symbol s1, Symbol s2) {
    return s1.isMethodSymbol() && s2.isMethodSymbol() && sameMethod(((JavaSymbol.MethodJavaSymbol) s1), ((JavaSymbol.MethodJavaSymbol) s2));
  }

  private static boolean sameMethod(Symbol.MethodSymbol s1, Symbol.MethodSymbol s2) {
    if (s1.name().equals(s2.name()) && s1.returnType().equals(s2.returnType())) {
      List<Symbol.TypeSymbol> parameters1 = s1.parameterTypes().stream().map(Type::symbol).collect(Collectors.toList());
      List<Symbol.TypeSymbol> parameters2 = s2.parameterTypes().stream().map(Type::symbol).collect(Collectors.toList());
      return parameters1.equals(parameters2);
    }
    return false;
  }
}
