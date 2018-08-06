package com.hedvig.backoffice.graphql;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;

import org.springframework.stereotype.Component;

import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLContextBuilder;

@Component
public class GraphQLRequestContextBuilder implements GraphQLContextBuilder {

	@Override
	public GraphQLContext build(HttpServletRequest httpServletRequest) {
		return new GraphQLRequestContext(httpServletRequest, httpServletRequest.getUserPrincipal());
	}

	@Override
	public GraphQLContext build(HandshakeRequest handshakeRequest) {
		return null;
	}

	@Override
	public GraphQLContext build() {
		return null;
	}
}