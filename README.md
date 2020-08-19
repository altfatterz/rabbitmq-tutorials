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

### Access RabbitMQ with [Java client library](https://www.rabbitmq.com/java-client.html) (amqp-client)


### Access RabbitMQ using [Spring AMQP Framework](https://spring.io/projects/spring-amqp)


### RabbitMQ on Kubernetes

1. Have a Kubernetes cluster running. The easiest is to enable Kubernetes in Docker Desktop.

```bash
$ kubectl config current-context
docker-desktop
```

2. Install [Helm](https://helm.sh/)

```bash
$ brew install helm
$ helm version
version.BuildInfo{Version:"v3.3.0", GitCommit:"8a4aeec08d67a7b84472007529e8097ec3742105", GitTreeState:"dirty", GoVersion:"go1.14.6"}
```

3. Install RabbitMQ using Helm using the `bitnami/rabbitmq` chart

```bash
$ helm install rabbitmq bitnami/rabbitmq --set auth.password=s3cr3t
```

4. Access RabbitMQ

RabbitMQ can be accessed within the cluster on port 5672 at rabbitmq.default.svc

To access for outside the cluster, perform the following steps:

To access the RabbitMQ AMQP port:

```bash
echo "URL : amqp://127.0.0.1:5672/"
kubectl port-forward --namespace default svc/rabbitmq 5672:5672
```

To access the RabbitMQ management interface:

```bash
echo "URL : http://127.0.0.1:15672/"
kubectl port-forward --namespace default svc/rabbitmq 15672:15672 
```

Resources:

1. https://github.com/bitnami/charts/tree/master/bitnami/rabbitmq
2. https://phoenixnap.com/kb/install-and-configure-rabbitmq-on-kubernetes
3. Issues:
   - https://github.com/bitnami/charts/issues/3095
   - https://github.com/bitnami/charts/pull/3155