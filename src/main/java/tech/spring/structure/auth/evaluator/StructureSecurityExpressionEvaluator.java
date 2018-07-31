package tech.spring.structure.auth.evaluator;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

@Component
public class StructureSecurityExpressionEvaluator {

    private static final FilterChain EMPTY_CHAIN = (request, response) -> {
        throw new UnsupportedOperationException();
    };

    @Autowired
    private SecurityExpressionHandler<FilterInvocation> securityExpressionHandler;

    public boolean evaluate(String securityExpression, HttpServletRequest request, HttpServletResponse response) {
        Expression expression = securityExpressionHandler.getExpressionParser().parseExpression(securityExpression);
        EvaluationContext evaluationContext = createEvaluationContext(securityExpressionHandler, request, response);
        return ExpressionUtils.evaluateAsBoolean(expression, evaluationContext);
    }

    private EvaluationContext createEvaluationContext(SecurityExpressionHandler<FilterInvocation> handler, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FilterInvocation filterInvocation = new FilterInvocation(request, response, EMPTY_CHAIN);
        return handler.createEvaluationContext(authentication, filterInvocation);
    }

}
