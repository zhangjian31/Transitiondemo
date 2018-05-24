package plug.zj.com.transitiondemo.list;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhangjian on 2018/5/24.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ListItemTransition extends Transition {
    private static final String TOP = "top";
    private static final String HEIGHT = "height";


    private long mPositionDuration;
    private long mSizeDuration;

    private TimeInterpolator mPositionInterpolator;
    private TimeInterpolator mSizeInterpolator;

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Rect rect = new Rect();
        view.getHitRect(rect);

        transitionValues.values.put(TOP, rect.top);
        transitionValues.values.put(HEIGHT, rect.height());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(TOP, 0);
        transitionValues.values.put(HEIGHT, transitionValues.view.getHeight());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final View endView = endValues.view;
        int startTop = (int) startValues.values.get(TOP);
        int startHeight = (int) startValues.values.get(HEIGHT);
        int endTop = (int) endValues.values.get(TOP);
        int endHeight = (int) endValues.values.get(HEIGHT);

        ViewCompat.setTranslationY(endView, startTop);
        endView.getLayoutParams().height = startHeight;
        endView.requestLayout();

        ValueAnimator positionAnimator = ValueAnimator.ofInt(startTop, endTop);
        if (mPositionDuration > 0) { positionAnimator.setDuration(mPositionDuration);}
        positionAnimator.setInterpolator(mPositionInterpolator);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                ViewCompat.setTranslationY(endView, current);
            }
        });


        ValueAnimator sizeAnimator = ValueAnimator.ofInt(startHeight, endHeight);
        if (mSizeDuration > 0) { sizeAnimator.setDuration(mSizeDuration);}
        sizeAnimator.setInterpolator(mSizeInterpolator);
        sizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                endView.getLayoutParams().height = current;
                endView.requestLayout();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(sizeAnimator).after(positionAnimator);
        return animatorSet;
    }

    @Override
    public String[] getTransitionProperties() {
        return new String[]{
                TOP, HEIGHT
        };
    }

    public void setPositionDuration(long duration) {
        mPositionDuration = duration;
    }

    public void setSizeDuration(long duration) {
        mSizeDuration = duration;
    }

    public void setPositionInterpolator(TimeInterpolator interpolator) {
        mPositionInterpolator = interpolator;
    }

    public void setSizeInterpolator(TimeInterpolator interpolator) {
        mSizeInterpolator = interpolator;
    }
}
