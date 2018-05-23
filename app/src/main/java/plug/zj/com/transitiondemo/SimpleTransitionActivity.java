package plug.zj.com.transitiondemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by zhangjian on 2018/5/23.
 * 定义视图树的状态scene1,scene2关键帧
 * ChangeBounds 处理View本身大小、位置改变
 * Transition 过度效果抽象
 * 内置效果：slide幻灯片  Fade淡入淡出
 */

public class SimpleTransitionActivity extends AppCompatActivity {
    private ViewGroup rootView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_transition);
        rootView = (ViewGroup) findViewById(R.id.rootView);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                transition5();
            }
        }, 1000);

    }

    private void transition1() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Scene scene = Scene.getSceneForLayout(rootView, R.layout.scene2, SimpleTransitionActivity.this);
            TransitionManager.go(scene, new ChangeBounds());
        }
    }

    private void transition2() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Scene scene = new Scene(rootView, LayoutInflater.from(this).inflate(R.layout.scene2, null));
            TransitionManager.go(scene, new ChangeBounds());
        }
    }

    private void transition3() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Scene scene = Scene.getSceneForLayout(rootView, R.layout.scene3, SimpleTransitionActivity.this);
            ChangeBounds changeBounds = new ChangeBounds();
//            changeBounds.addTarget(R.id.image_a);
            changeBounds.setDuration(2000);
            TransitionManager.go(scene, changeBounds);
        }
    }


    private void transition4() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(2000);

            //系统保存当前视图树的状态场景
            TransitionManager.beginDelayedTransition(rootView,changeBounds);
            //修改视图树
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rootView.findViewById(R.id.image_a).getLayoutParams();
            params.width=600;
            params.height=600;
            //再次绘制时系统自动对比之前保存状态，执行动画
            rootView.findViewById(R.id.image_a).setLayoutParams(params);


            ChangeBounds changeBounds2 = new ChangeBounds();
            changeBounds2.setDuration(2000);
            //系统保存当前视图树的状态场景
            TransitionManager.beginDelayedTransition(rootView,changeBounds2);
            //修改视图树
            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) rootView.findViewById(R.id.image_b).getLayoutParams();
            params2.width=100;
            params2.height=100;
            //再次绘制时系统自动对比之前保存状态，执行动画
            rootView.findViewById(R.id.image_b).setLayoutParams(params2);
        }
    }


    private void transition5() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Scene scene = new Scene(rootView, LayoutInflater.from(this).inflate(R.layout.scene2, null));
            ChangeColorTransition changeColorTransition = new ChangeColorTransition();
            changeColorTransition.addTarget(R.id.rl_scene);
            changeColorTransition.setDuration(5000);
            TransitionManager.go(scene, changeColorTransition);
        }
    }
}
