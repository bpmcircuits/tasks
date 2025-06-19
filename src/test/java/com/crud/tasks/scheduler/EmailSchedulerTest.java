package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmailSchedulerTest {

    @Autowired
    private EmailScheduler emailScheduler;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private AdminConfig adminConfig;

    @MockBean
    private SimpleEmailService simpleEmailService;

    @Test
    void shouldSendInformationEmailWithSingularTaskMessage() {
        // Given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("admin@example.com");

        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);

        // When
        emailScheduler.sendInformationEmail();

        // Then
        verify(simpleEmailService, times(1)).send(mailCaptor.capture());

        Mail capturedMail = mailCaptor.getValue();
        assertThat(capturedMail.getMailTo()).isEqualTo("admin@example.com");
        assertThat(capturedMail.getSubject()).isEqualTo("Tasks: Once a day email");
        assertThat(capturedMail.getMessage()).isEqualTo("Currently in database you got: 1 task");
    }

    @Test
    void shouldSendInformationEmailWithPluralTaskMessage() {
        // Given
        when(taskRepository.count()).thenReturn(5L);
        when(adminConfig.getAdminMail()).thenReturn("admin@example.com");

        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);

        // When
        emailScheduler.sendInformationEmail();

        // Then
        verify(simpleEmailService, times(1)).send(mailCaptor.capture());

        Mail capturedMail = mailCaptor.getValue();
        assertThat(capturedMail.getMailTo()).isEqualTo("admin@example.com");
        assertThat(capturedMail.getSubject()).isEqualTo("Tasks: Once a day email");
        assertThat(capturedMail.getMessage()).isEqualTo("Currently in database you got: 5 tasks");
    }
}
