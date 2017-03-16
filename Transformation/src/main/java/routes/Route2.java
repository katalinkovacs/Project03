package routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import processors.Processor2;


public class Route2 extends RouteBuilder {

    Processor2 processor2Object = new Processor2();

    public void configure() throws Exception {

        //marshall method needs a java object called jaxbDataFormat1
        JaxbDataFormat myEmployee1jaxbDataFormat = new JaxbDataFormat("employee1");

        JacksonDataFormat myEmployee2JsonFormat = new JacksonDataFormat(employee2.Employee2.class);

        //from("file:Transformation/src/main/resources/data/inbox/inbox2?noop=true")
        from("amq:route2.data.in")
                .id("route2")
                //unmarshalling is the process to read in xml/json/etc and convert to java object using jaxb generated template
                .unmarshal(myEmployee1jaxbDataFormat)
                .bean(processor2Object, "processor2Method")   // object + method
                //marshalling is the process to convert java to xml/json/etc
                .marshal(myEmployee2JsonFormat)
                //.to("file:Transformation/src/main/resources/data/outbox");
                .to("amq:route2.data.out");
    }
}