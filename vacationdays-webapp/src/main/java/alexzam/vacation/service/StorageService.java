package alexzam.vacation.service;

import alexzam.vacation.model.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StorageService {

    @Autowired
    private DatastoreService datastoreService;

    public void saveUser(User user) {
        Entity eUser = new Entity("User");

        eUser.setUnindexedProperty("lastDate", user.getLastKnownDate().toDate());
        eUser.setUnindexedProperty("lastValue", user.getLastKnownValue());

        datastoreService.put(eUser);
    }
}
