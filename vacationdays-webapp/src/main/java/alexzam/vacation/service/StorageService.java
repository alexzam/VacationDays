package alexzam.vacation.service;

import alexzam.vacation.model.User;
import alexzam.vacation.model.UserImpl;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StorageService {

    @Autowired
    private DatastoreService datastoreService;

    public void saveUser(User user) {
        Entity eUser = user.toEntity();

        datastoreService.put(eUser);
    }

    public User loadUser(String id) {
        Query query = new Query("User")
                .setFilter(new Query.FilterPredicate("googleId", Query.FilterOperator.EQUAL, id));

        PreparedQuery preparedQuery = datastoreService.prepare(query);
        Entity entity = preparedQuery.asSingleEntity();
        return entity == null ? null : new UserImpl(entity);
    }
}
