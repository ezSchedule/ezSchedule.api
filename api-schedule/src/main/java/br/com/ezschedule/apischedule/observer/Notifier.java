package br.com.ezschedule.apischedule.observer;

import br.com.ezschedule.apischedule.model.ForumPost;
import br.com.ezschedule.apischedule.model.Tenant;

import java.util.List;

public interface Notifier {
    public void update(ForumPost f, Tenant tenant);
}
