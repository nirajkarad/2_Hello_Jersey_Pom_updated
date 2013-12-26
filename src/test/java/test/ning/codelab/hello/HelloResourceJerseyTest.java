package test.ning.codelab.hello;

import java.util.Arrays;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;

import ning.codelab.hello.HelloResource;
import ning.codelab.hello.HelloResource.HelloMessage;
import ning.codelab.hello.HelloServerModule;

import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
* HelloResource TestNG module.
*/
public class HelloResourceJerseyTest
{
    /** see MyConfig class */
    private static final String XN_HELLO_MESSAGE_PROPERTY_KEY = "xn.hello.message";
    
    private HttpHeaders http;
   
    @BeforeMethod
    public void setUp()
    {
        System.clearProperty(XN_HELLO_MESSAGE_PROPERTY_KEY);
        
    }

    private HelloResource useGuiceToInstantiateTheHelloResource()
    {
        Injector injector = Guice.createInjector(new HelloServerModule());
        HelloResource theHello = injector.getInstance(HelloResource.class);

        return theHello;
    }

    @Test
    public void testgreeting()
    {
            HelloResource theHello = useGuiceToInstantiateTheHelloResource();
            http= EasyMock.createMock(HttpHeaders.class);
            EasyMock.expect(http.getAcceptableLanguages()).andReturn(Arrays.asList(Locale.ENGLISH, Locale.US));
            EasyMock.replay(http);
        assert "[en, en_US]".equals(theHello.getGreeting(http));
    }
    
    @Test
    public void testHelloXml()
    {
        HelloResource theHello = useGuiceToInstantiateTheHelloResource();
        assert "GlamIndia".equals(theHello.getXMLOfCustomer().getName());
    }
    
    @Test
    public void testHelloDefaultPlaintext()
    {
        HelloResource theHello = useGuiceToInstantiateTheHelloResource();
        assert "hello, world".equals(theHello.getPlainText());
    }

    @Test
    public void testHelloDefaultJson()
    {
        HelloResource theHello = useGuiceToInstantiateTheHelloResource();

        HelloMessage helloResult = theHello.getJson();
        assert "hello, world".equals(helloResult.getMessage());
    }
}