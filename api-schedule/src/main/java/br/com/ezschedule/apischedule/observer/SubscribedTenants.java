package br.com.ezschedule.apischedule.observer;


import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Tenant;
import br.com.ezschedule.apischedule.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscribedTenants {

    @Autowired
    EmailNotifier emailNotifier;


    @Autowired
    TenantRepository tenantRepository;


    public void subscribe(Tenant t) {
        tenantRepository.subscribeOrUnsubTenant(1,t.getIdUser());
    }

    public void unsubscribe(Tenant t) {
        tenantRepository.subscribeOrUnsubTenant(0,t.getIdUser());
    }

    public void notifySubscribers(List<Tenant> tenantList,ForumPost f) {
        for(Tenant t: tenantList){
            emailNotifier.update(f,t);
        }
    }


}
