package br.com.ezschedule.apischedule.observer;


import br.com.ezschedule.apischedule.model.UserNotification;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribedTenants {

    @Autowired
    EmailNotifier emailNotifier;


    @Autowired
    TenantRepository tenantRepository;


    public void subscribe(Tenant t) {
        tenantRepository.subscribeOrUnsubTenant(1,t.getId());
    }

    public void unsubscribe(Tenant t) {
        tenantRepository.subscribeOrUnsubTenant(0,t.getId());
    }

    public void notifySubscribers(Tenant t, UserNotification f) {
            emailNotifier.update(f,t);

    }


}
