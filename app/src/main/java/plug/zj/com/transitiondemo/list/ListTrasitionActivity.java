package plug.zj.com.transitiondemo.list;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import plug.zj.com.transitiondemo.R;
import plug.zj.com.transitiondemo.TransitionHelper;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ListTrasitionActivity extends AppCompatActivity {
    private ListView mListView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                startActivity2(view, mAdapter.getItem(position));
            }
        });
    }


    public void startActivity(View view, String content) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("msg", content);

        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, view, view.getTransitionName());
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }

    public void startActivity2(View view, String content) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("msg", content);

        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, TransitionHelper.createSafeTransitionParticipants(this, Pair.create(view, view.getTransitionName())));
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }

}
