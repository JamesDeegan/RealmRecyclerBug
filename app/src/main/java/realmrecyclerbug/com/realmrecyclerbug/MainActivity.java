package realmrecyclerbug.com.realmrecyclerbug;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import realmrecyclerbug.com.realmrecyclerbug.realm.Item;
import realmrecyclerbug.com.realmrecyclerbug.realm.ItemParent;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.getItemAnimator().setChangeDuration(0);

        mRealm = Realm.getDefaultInstance();

        ItemAdapter adapter = new ItemAdapter(getApplicationContext(), getItemParent().items);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.add_items_button)
    public void addItems() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ItemParent parent = getItemParent();

                for (int i = 0; i < 10; i++) {
                    Item item = new Item();
                    item.color = getNewColor();
                    parent.items.add(item);
                }
            }
        });
    }

    private ItemParent getItemParent() {
        ItemParent parent = mRealm.where(ItemParent.class).findFirst();
        if (parent == null) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createObject(ItemParent.class);
                }
            });
            parent = mRealm.where(ItemParent.class).findFirst();
        }
        return parent;
    }

    private int getNewColor() {
        Random rnd = new Random();
        return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}
