package br.com.ezschedule.apischedule.observer;

import br.com.ezschedule.apischedule.model.UserNotification;
import br.com.ezschedule.apischedule.model.Tenant;
import org.springframework.stereotype.Service;

@Service
public interface Notifier {
    public void update(UserNotification f, Tenant tenant);
}
