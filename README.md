If you want more than one brooker then just do: 
version: '3.8'

services:
  kafka1:
    image: bitnami/kafka:3.7
    container_name: kafka1
    ports:
      - "9092:9092"
    environment:
      - KAFKA_ENABLE_KRAFT=yes
      - KAFKA_CFG_NODE_ID=1
      - KAFKA_KRAFT_CLUSTER_ID=kraft-cluster-id-123
      - KAFKA_CFG_PROCESS_ROLES=broker,controller
      - KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka1:9092
      - KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=1@kafka1:9093
      - ALLOW_PLAINTEXT_LISTENER=yes

  kafka2:
    image: bitnami/kafka:3.7
    container_name: kafka2
    ports:
      - "9094:9092"
    environment:
      - KAFKA_ENABLE_KRAFT=yes
      - KAFKA_CFG_NODE_ID=2
      - KAFKA_KRAFT_CLUSTER_ID=kraft-cluster-id-123
      - KAFKA_CFG_PROCESS_ROLES=broker
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka2:9092
      - KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=1@kafka1:9093
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - kafka1

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    ports:
      - "8081:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka1:9092,kafka2:9092
    depends_on:
      - kafka1
      - kafka2
