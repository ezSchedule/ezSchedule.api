package br.com.ezschedule.apischedule.observer;

import br.com.ezschedule.apischedule.email.SendMail;
import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailNotifier implements Notifier {

    @Autowired
   private SendMail sendMail;

    @Override
    public void update(ForumPost f, Tenant tenant) {
        sendMail.send(tenant.getEmail(), f.getTypeMessage(),f.getTextContent());
    }
}
