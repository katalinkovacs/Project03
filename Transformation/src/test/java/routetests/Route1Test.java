package routetests;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import routes.Route1;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;


//CamelTestSupport will give extra camel specific functionality like createCamelContext
public class Route1Test extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder(){
        return new Route1();
    }


    //Set up before running test
    @Override
    public void setUp() throws Exception{
        super.setUp();
        // need the definition of the route to add test magic
        RouteDefinition route1Definition = context.getRouteDefinition("route1");

        //override existing route with this
        route1Definition.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                // test magic: replacing "from" with direct:in so we can directly send
                // message to it
                replaceFromWith("direct:in");
                // intercepting the message before it is sent to "to" endpoint
                // then we skip that endpoint and direct it to a mock endpoint
                // which we will use in test later to get the final message
                interceptSendToEndpoint("amq:data.out")
                        .skipSendToOriginalEndpoint().to("mock:out");
            }
        });
    }

    @Test
    public void route1_whenValidInput_thenValidOutput() throws Exception{
        // reading in xml request and expected with some
        String request = FileUtils.readFileToString(ResourceUtils.getFile("classpath:input/employee2.json"));
        String expected = FileUtils.readFileToString(ResourceUtils.getFile("classpath:expected/expectedResultRoute1.xml"));

        // using template which is given by CamelTestSupport
        // template is like a client which sends a request to the given endpoint
        template.sendBody("direct:in", request);

        // here we get the exchange from mock endpoint which we used to replace the original one
        Exchange ex = getMockEndpoint("mock:out").getExchanges().get(0);
        // get the message body as string which is our xml output
        String result = ex.getIn().getBody(String.class);

        // the following is just a third party xml tool to help compare 2 xml files
        //https://technicalmumbojumbo.wordpress.com/2010/01/31/xml-comparison-tutorial-using-xmlunit/
        Diff diff = new Diff(expected, result);

        List<Difference> differences = new DetailedDiff(diff).getAllDifferences();

        for (Object object : differences) {
            Difference difference = (Difference)object;
            System.out.println("***********************");
            System.out.println(difference);
            System.out.println("***********************");
        }

        // we asserting that the differences are 0
        assertThat(differences.size(), is(0));

    }
}


