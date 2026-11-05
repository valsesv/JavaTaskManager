package com.vseslav.taskmanager.kafka;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.event.TaskEvent;
import com.vseslav.taskmanager.event.TaskEventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskEventProducer {

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

    @Value("${app.kafka.topic.tasks}")
    private String topic;

    public void send(TaskEventType type, TaskDTO task) {
        TaskEvent event = new TaskEvent(
                type,
                task.id(),
                task.name(),
                task.status() != null ? task.status().name() : null,
                task.userId()
        );
        kafkaTemplate.send(topic, String.valueOf(task.id()), event);
        log.info("Kafka event sent: {}", event);
    }

    public void sendDeleted(Long taskId) {
        TaskEvent event = new TaskEvent(TaskEventType.DELETED, taskId, null, null, null);
        kafkaTemplate.send(topic, String.valueOf(taskId), event);
        log.info("Kafka event sent: {}", event);
    }
}
