package com.snapp.phonebook.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("githubRepository.topic");
    }

    @Bean
    public Queue autoDeleteQueue() {
        return new Queue("githubRepository queue");
    }

    @Bean
    public Binding binding(TopicExchange topic, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue)
                .to(topic)
                .with("githubRepository.*.*");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


}
