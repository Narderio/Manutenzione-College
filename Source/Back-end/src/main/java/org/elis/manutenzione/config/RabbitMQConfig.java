package org.elis.manutenzione.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * Crea una coda RabbitMQ denominata "messaggistica".
     * <p>
     * La coda è:
     * <ul>
     *     <li><b>durevole</b>: persiste anche se il broker RabbitMQ viene riavviato.</li>
     *     <li><b>auto-cancellata</b>: viene eliminata automaticamente quando l'ultimo consumatore si disconnette.</li>
     *     <li><b>esclusiva</b>: può essere consumata solo da un'applicazione alla volta.</li>
     * </ul>
     *
     * @return La coda RabbitMQ configurata.
     */
    @Bean
    protected Queue secondQueue() {
        return QueueBuilder.durable("messaggistica")
                .autoDelete()
                .build();
    }

    /**
     * Crea un exchange di tipo "topic" denominato "TopicTestExchange".
     * <p>
     * L'exchange è:
     * <ul>
     *     <li><b>durevole</b>: persiste anche se il broker RabbitMQ viene riavviato.</li>
     *     <li><b>auto-cancellato</b>: viene eliminato automaticamente quando l'ultimo binding viene rimosso.</li>
     *     <li><b>interno</b>: non è possibile pubblicare messaggi direttamente su questo exchange.</li>
     * </ul>
     *
     * @return L'exchange RabbitMQ configurato.
     */
    @Bean
    protected Exchange exampleExchange() {
        return ExchangeBuilder.topicExchange("TopicTestExchange")
                .autoDelete()
                //.internal()
                .durable(true)
                .build();
    }

    @Bean
    Binding binding(){
        return BindingBuilder
                .bind(secondQueue())
                .to(exampleExchange())
                .with("TopicTestExchange")
                .noargs();
    }

}
