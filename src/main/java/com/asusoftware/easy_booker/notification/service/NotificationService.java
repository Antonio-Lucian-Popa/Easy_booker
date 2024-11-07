package com.asusoftware.easy_booker.notification.service;

import com.asusoftware.easy_booker.appointment.model.Appointment;
import com.asusoftware.easy_booker.appointment.repository.AppointmentRepository;
import com.asusoftware.easy_booker.notification.model.Notification;
import com.asusoftware.easy_booker.notification.model.dto.NotificationMessageDto;
import com.asusoftware.easy_booker.notification.model.dto.NotificationRequestDto;
import com.asusoftware.easy_booker.notification.model.dto.NotificationResponseDto;
import com.asusoftware.easy_booker.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private final NotificationRepository notificationRepository;

    @Autowired
    private final AppointmentRepository appointmentRepository;

    @Autowired
    private final  WebSocketNotificationService webSocketNotificationService; // Serviciul de WebSocket

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, AppointmentRepository appointmentRepository, WebSocketNotificationService webSocketNotificationService) {
        this.notificationRepository = notificationRepository;
        this.appointmentRepository = appointmentRepository;
        this.webSocketNotificationService = webSocketNotificationService;
    }

    public List<NotificationResponseDto> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public NotificationResponseDto getNotificationById(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return convertToResponseDTO(notification);
    }

    @Transactional
    public NotificationResponseDto createNotification(NotificationRequestDto NotificationRequestDto) {
        Appointment appointment = appointmentRepository.findById(NotificationRequestDto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Notification notification = new Notification();
        notification.setAppointment(appointment);
        notification.setNotificationType(NotificationRequestDto.getNotificationType());
        notification.setSentAt(LocalDateTime.now());
        notification.setStatus("pending");

        Notification savedNotification = notificationRepository.save(notification);

        // Trimite notificare în timp real prin WebSocket
        NotificationMessageDto message = new NotificationMessageDto();
        message.setMessage("New notification created with status: " + notification.getStatus());
        message.setType(notification.getNotificationType());
        webSocketNotificationService.sendNotification(message);

        return convertToResponseDTO(savedNotification);
    }

    @Transactional
    public NotificationResponseDto updateNotificationStatus(UUID id, String status) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setStatus(status);
        Notification updatedNotification = notificationRepository.save(notification);

        // Trimite notificare în timp real prin WebSocket
        NotificationMessageDto message = new NotificationMessageDto();
        message.setMessage("Notification status updated to: " + status);
        message.setType(notification.getNotificationType());
        webSocketNotificationService.sendNotification(message);

        return convertToResponseDTO(updatedNotification);
    }

    @Transactional
    public void deleteNotification(UUID id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found");
        }
        notificationRepository.deleteById(id);

        // Trimite notificare de ștergere prin WebSocket
        NotificationMessageDto message = new NotificationMessageDto();
        message.setMessage("Notification deleted with ID: " + id);
        message.setType("delete");
        webSocketNotificationService.sendNotification(message);
    }

    private NotificationResponseDto convertToResponseDTO(Notification notification) {
        NotificationResponseDto dto = new NotificationResponseDto();
        dto.setId(notification.getId());
        dto.setAppointmentId(notification.getAppointment().getId());
        dto.setNotificationType(notification.getNotificationType());
        dto.setSentAt(notification.getSentAt());
        dto.setStatus(notification.getStatus());
        return dto;
    }
}
