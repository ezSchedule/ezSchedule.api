package br.com.ezschedule.apischedule.observer;

import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.model.UserNotification;
import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailNotifier implements Notifier {

    @Autowired
    private SendMail sendMail;

    @Override
    public void update(UserNotification f, Tenant tenant) {
        sendMail.send(tenant.getEmail(), f.getTypeMessage(), f.getTextContent());
    }
}
