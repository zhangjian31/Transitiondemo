package plug.zj.com.transitiondemo.list;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import plug.zj.com.transitiondemo.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MessageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        setTitle("Content");

        TextView msgTextView = (TextView) findViewById(R.id.msg);
        msgTextView.setText(getIntent().getStringExtra("msg"));

        executeTransition2();
    }

    public void executeTransition() {
        ListItemTransition transition = new ListItemTransition();
        transition.setPositionDuration(300);
        transition.setSizeDuration(300);
        transition.setPositionInterpolator(new FastOutLinearInInterpolator());
        transition.setSizeInterpolator(new FastOutSlowInInterpolator());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transition.addTarget("message");
            getWindow().setSharedElementEnterTransition(transition);
        }
    }

    public void executeTransition2() {
        postponeEnterTransition();

        final View decorView = getWindow().getDecorView();
        getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                decorView.getViewTreeObserver().removeOnPreDrawListener(this);
                supportStartPostponedEnterTransition();
                return true;
            }
        });

        ListItemTransition transition = new ListItemTransition();
        transition.setPositionDuration(300);
        transition.setSizeDuration(300);
        transition.setPositionInterpolator(new FastOutLinearInInterpolator());
        transition.setSizeInterpolator(new FastOutSlowInInterpolator());
        transition.addTarget("message");

        getWindow().setSharedElementEnterTransition(transition);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
