package ning.codelab.hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;

/**
 * Initially, a small class that implements a "message service." For
 * simplification, the service delegates to a message configured by the
 * configuration object. In the future, more complex logic may be enabled.
 * 
 * Now, we extend the HelloResource class to be a Jersey endpoint.
 * This is a jax-rs resource class. The @Path annotation says it
 * matches / (in the sense of http://localhost:9999/ )
 *
 * Different methods are annotated to handle different methods (only GET here),
 * and different Accept header media type thingamabobs.
 *
 * See http://jersey.dev.java.net/ for Jersey documentation
 */
@Path("/")
public class HelloResource
{
    private final MyConfig config;

    @Inject
    public HelloResource(MyConfig config)
    {
        this.config = config;
    }

    @GET
    @Path("plaintext")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPlainText() {
        return this.getMessage();
    }
    
    
    @GET
    @Path("xml/customer")
    @Produces(MediaType.APPLICATION_XML)
    public Customer getXMLOfCustomer()
    {
    	Customer c= new Customer();
    	c.setName("GlamIndia");
    	return c;
    }
    
    
    
    @GET
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloMessage getJson() {
        /**
         * an inner class is used here to put an envelope around the
         * message for JSON formatting, if we just returned the message the
         * response would be:
         * 
         * "hello, world"
         *
         * with the object envelope it becomes:
         *
         * {"message":"hello, world"}
         *
         * which may or may not matter, but we want something like that often
         * enough that an example is reasonable to provide.
         */
        return new HelloMessage() {
            public String getMessage() {
                return HelloResource.this.getMessage();
            }
        };
    }
    
    /**
     * The original "message service" implementation.
     */
    public String getMessage()
    {
        return config.getMessage();
    }
    
    /**
     * An inner class "response object" so that we can test more easily.
     */
    public abstract static class HelloMessage {
        public abstract String getMessage();
    }
}
