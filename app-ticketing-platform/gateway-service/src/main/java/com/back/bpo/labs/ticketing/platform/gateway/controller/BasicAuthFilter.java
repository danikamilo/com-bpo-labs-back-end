package com.back.bpo.labs.ticketing.platform.gateway.controller;


import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.StringTokenizer;

/**
 * @author Daniel Camilo
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class BasicAuthFilter implements ContainerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";

    private static final String USERNAME = "bpo";
    private static final String PASSWORD = "labs.123";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(BASIC_PREFIX)) {
            abort(requestContext);
            return;
        }

        String base64Credentials = authHeader.substring(BASIC_PREFIX.length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

        final StringTokenizer tokenizer = new StringTokenizer(credentials, ":");
        final String user = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        final String pass = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        if (!USERNAME.equals(user) || !PASSWORD.equals(pass)) {
            abort(requestContext);
        }
    }

    private void abort(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                jakarta.ws.rs.core.Response.status(jakarta.ws.rs.core.Response.Status.UNAUTHORIZED)
                        .header("WWW-Authenticate", "Basic realm=\"Restricted Area\"")
                        .entity("Access denied for this resource").build()
        );
    }
}