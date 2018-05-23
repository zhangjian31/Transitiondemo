package plug.zj.com.transitiondemo;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangjian on 2018/5/23.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChangeColorTransition extends Transition {
    private static final String PROPNAME_BACKGROUND = "custom_color";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues.view.getBackground() instanceof ColorDrawable) {
            captureValues(transitionValues);
        }
    }


    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues.view.getBackground() instanceof ColorDrawable) {
            captureValues(transitionValues);
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (null == startValues || null == endValues) {
            return null;
        }

        final View view = endValues.view;
        int startColor = (Integer) startValues.values.get(PROPNAME_BACKGROUND);
        int endColor = (Integer) endValues.values.get(PROPNAME_BACKGROUND);
        if (startColor != endColor) {
            ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Object value = animation.getAnimatedValue();
                    if (null != value) {
                        view.setBackgroundColor((Integer) value);
                    }
                }
            });

            return animator;
        }
        return null;
    }

    @Override
    public String[] getTransitionProperties() {
        return new String[]{
                PROPNAME_BACKGROUND
        };
    }


    private void captureValues(TransitionValues transitionValues) {
        ColorDrawable c = (ColorDrawable) transitionValues.view.getBackground();
        transitionValues.values.put(PROPNAME_BACKGROUND, c.getColor());
    }
}
