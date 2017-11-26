package au.com.rest.test.services;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@Provider
public class BasicFilter implements ContainerRequestFilter, ContainerResponseFilter {


    private final String HEALTH_CHECK = "/health/check";
    private final String LOGIN = "/login/check";
    private final String TOKEN = "token";

    /**
     * After a request this is the first method the will be called, before than any service
     *
     * The best place to check tokens, cancel request, check URLs and header
     *
     * @param containerRequestContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        final UriInfo info = containerRequestContext.getUriInfo();
        final String urlPath = info.getPath();
        containerRequestContext.getHeaders().add("Request", "Adding a value in the request");
        containerRequestContext.getHeaders().add("URL called", urlPath);
        final String token = containerRequestContext.getHeaders().getFirst(TOKEN);
        if (token != null) {
            // validate the token, if fail validation set abort response
        } else {
            final Response response = Response.status(Response.Status.FORBIDDEN).build();
            containerRequestContext.abortWith(response);
        }
    }


    /**
     * Response after the service has run, perfect place to add the just created token
     *
     * @param containerRequestContext
     * @param containerResponseContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        final MultivaluedMap<String, String> requestHeaderAsMap = containerRequestContext.getHeaders();
        if (requestHeaderAsMap.getFirst(TOKEN) != null) {
            for (String key : requestHeaderAsMap.keySet()) {
                containerResponseContext.getHeaders().add(key, requestHeaderAsMap.get(key));
            }
            containerResponseContext.getHeaders().add("TestResponse", "Adding some value to the response");
        } else {
            containerResponseContext.setStatus(Response.Status.FORBIDDEN.getStatusCode());
        }
    }
}
