FROM rabbitmq:3.7-management
ADD rabbitmq.config /etc/rabbitmq/
ADD definitions.json /etc/rabbitmq/

RUN chown rabbitmq:rabbitmq /etc/rabbitmq/definitions.json /etc/rabbitmq/rabbitmq.config

#RUN rabbitmq-plugins enable --offline rabbitmq_mqtt rabbitmq_federation_management rabbitmq_shovel_management rabbitmq_stomp rabbitmq_shovel