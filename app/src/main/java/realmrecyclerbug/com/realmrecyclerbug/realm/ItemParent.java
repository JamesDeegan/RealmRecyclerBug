package realmrecyclerbug.com.realmrecyclerbug.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by syco on 3/25/2017.
 */

public class ItemParent extends RealmObject {
    public RealmList<Item> items = new RealmList<>();
}
