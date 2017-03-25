package realmrecyclerbug.com.realmrecyclerbug;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import realmrecyclerbug.com.realmrecyclerbug.realm.Item;

/**
 * Created by syco on 3/25/2017.
 */

public class ItemAdapter extends RealmRecyclerViewAdapter<Item, ItemAdapter.ItemViewHolder> {
    Context context;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.delete)
        Button delete;
        @BindView(R.id.item_root_layout)
        LinearLayout rootLayout;


        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        public void bindItem(int position, Item item) {
            rootLayout.setBackgroundColor(item.color);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(getAdapterPosition());
                }
            });
        }
    }

    public ItemAdapter(Context context, OrderedRealmCollection<Item> data) {
        super(data, true);
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindItem(position, getData().get(position));
    }

    private void deleteItem(final int position){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                getData().remove(position);
            }
        });
    }
}
