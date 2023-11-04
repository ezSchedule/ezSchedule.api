package br.com.ezschedule.apischedule.service;

import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.model.UserNotification;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.observer.RowObject;
import br.com.ezschedule.apischedule.observer.SubscribedTenants;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    TenantRepository tenantRepository;
    @Autowired
    SubscribedTenants subscribedTenants;

    @Autowired
    private SendMail sendMail;

    private RowObject<Tenant> forumPostFila = new RowObject<>(100);



    public ResponseEntity<Void> send(UserNotification userNotification) {
        //descomente se deseje enviar emails para todos usuários inscritos
//        sendMails(notification);
        try {
            String topic = "conversations";

            Notification notification = Notification.builder()
                    .setBody(userNotification.getTextContent())
                    .setTitle("EzSchedule")
                    .build();

            Message message = Message.builder()
                    .setNotification(notification)
                    .setTopic(topic)
                    .build();

            FirebaseMessaging.getInstance().send(message);
            return ResponseEntity.status(201).build();

        } catch (FirebaseMessagingException exception) {
            ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(404).build();
    }


    public void sendMails(UserNotification p) {
        List<Tenant> tenantList = tenantRepository.findSubscribedTenants(p.getCondominium().getId());

        for (Tenant t : tenantList) {
            forumPostFila.insert(t);
        }

        for (int i = 0; forumPostFila.getLength() > 0; i++) {
            sendEmailsOnADelayBasis(forumPostFila.poll(), p);
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendEmailsOnADelayBasis(Tenant t, UserNotification f) {
        subscribedTenants.notifySubscribers(t, f);
    }
}
