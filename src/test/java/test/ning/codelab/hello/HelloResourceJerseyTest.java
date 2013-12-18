package test.ning.codelab.hello;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ning.codelab.hello.HelloResource;
import ning.codelab.hello.HelloServerModule;
import ning.codelab.hello.HelloResource.HelloMessage;

/**
 * HelloResource TestNG module.
 */
public class HelloResourceJerseyTest
{
    /** see MyConfig class */
    private static final String XN_HELLO_MESSAGE_PROPERTY_KEY = "xn.hello.message";

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
