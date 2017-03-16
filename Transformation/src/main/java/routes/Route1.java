package routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import processors.Processor1;

public class Route1 extends RouteBuilder{

    Processor1 processor1Object = new Processor1();

    public void configure() throws Exception {

        JacksonDataFormat myEmployee2JsonFormat = new JacksonDataFormat(employee2.Employee2.class);

        //marshall method needs a java object called myEmployee1JaxbDataFormat
        JaxbDataFormat myEmployee1JaxbDataFormat = new JaxbDataFormat("employee1");

        //from("file:C:/Kati/Marlo/GitRepo/FileReads/Project01/From")
        //from("file:Transformation/src/main/resources/data/inbox/inbox1?noop=true")
        from("amq:route1.data.in")
                .id("route1")
                .unmarshal(myEmployee2JsonFormat)
                .bean(processor1Object, "processor1Method")   // object + method
                .marshal(myEmployee1JaxbDataFormat)
                //.to("file:Transformation/src/main/resources/data/outbox");
                .to("amq:route1.data.out");
    }
}