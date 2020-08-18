rabbitmq-tutorials (https://www.rabbitmq.com/getstarted.html)
==================

### Start RabbitMQ

1. Start RabbitMQ with Docker Compose
```bash
$ docker-compose up -d
```

2. Access the management console

URL: http://localhost:15672/
Credentials: guest/guest


### (Optional) Install RabbitMQ with HomeBrew

```bash
$ brew install rabbitmq
```

A good benefit here is that you have `rabbitmqadmin`, `rabbitmqctl`, `rabbitmq-plugins`, etc (See more details here: https://www.rabbitmq.com/cli.html)

```bash
$ rabbitmqadmin help subcommands

$ rabbitmqadmin list queues
$ rabbitmqadmin list exchanges

```

### Access RabbitMQ with Java client library (amqp-client)




### Access RabbitMQ using [Spring AMQP Framework](https://spring.io/projects/spring-amqp)