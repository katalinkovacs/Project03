package routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import processors.Processor3;


public class Route3 extends RouteBuilder{

    Processor3 processor3Object = new Processor3();

    public void configure() throws Exception {

        JacksonDataFormat myEmployee2JsonFormat = new JacksonDataFormat(employee2.Employee2.class);
        JacksonDataFormat myEmployee3JsonFormat = new JacksonDataFormat(employee3.Employee3.class);

        //from("file:Transformation/src/main/resources/data/inbox/inbox3?noop=true")
        from("amq:route3.data.in")
                .id("route3")
                .unmarshal(myEmployee2JsonFormat)
                .bean(processor3Object, "processor3Method")   // object + method
                .marshal(myEmployee3JsonFormat)
                //.to("file:Transformation/src/main/resources/data/outbox");
                .to("amq:route3.data.out");
    }
}
