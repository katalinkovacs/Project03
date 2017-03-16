package startapps;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import routes.Route2;

import javax.jms.ConnectionFactory;

/**
 * Created by Katika on 16/03/2017.
 */
public class StartApp2 {


        public static void main(String ... args) throws Exception{

            //CREATE CAMELCONTEXT
            CamelContext context = new DefaultCamelContext();

            ConnectionFactory connectionFactoryAMQ =
                    new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");

            // adding a new JMS component to camel context called "amq"
            context.addComponent("amq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactoryAMQ));

            //ADD ROUTES TO CONTEXT
            context.addRoutes(new Route2());

            context.start();
            Thread.sleep(10000);
            context.stop();
        }

    }
