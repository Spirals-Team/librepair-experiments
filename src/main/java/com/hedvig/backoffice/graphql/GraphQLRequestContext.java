package com.hedvig.backoffice.graphql;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import graphql.servlet.GraphQLContext;

public class GraphQLRequestContext extends GraphQLContext {
    private final Principal userPrincipal;
    public GraphQLRequestContext(HttpServletRequest request, Principal userPrincipal) {
        super(request);
        this.userPrincipal = userPrincipal;
    }

	public Principal getUserPrincipal() {
		return userPrincipal;
	}
}
