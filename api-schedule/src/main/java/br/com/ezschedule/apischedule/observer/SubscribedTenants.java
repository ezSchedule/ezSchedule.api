package br.com.ezschedule.apischedule.observer;


import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Tenant;

import java.util.ArrayList;
import java.util.List;


public class SubscribedTenants {

    List<Notifier> notifierList;

    public SubscribedTenants() {
        this.notifierList = new ArrayList<>();
    }

    public void subscribe(Notifier n) {
        if (n != null) {
            notifierList.add(n);
        }
    }

    public void unsubscribe(Notifier n) {
        notifierList.remove(n);
    }

    public void notifySubscribers(List<Tenant> t,ForumPost f) {
        for(Notifier n : notifierList){
            for (Tenant tenant : t ){
                n.update(f,tenant);
            }
        }
    }


}
