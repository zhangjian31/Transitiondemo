# Transitiondemo
自定义Transition
1、继承Transition
2、重写方法
    //收集动画的开始信息
    public void captureStartValues(TransitionValues transitionValues) {

    }
    //收集动画的结束信息
    public void captureEndValues(TransitionValues transitionValues) {

    }
    //自定义动画
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {

    }

3、TransitionValues只有两个成员变量view和values
   view：要在哪个视图上收集信息
   values：存放收集到的信息

4、这一系列的动画其实在我们跳转后的界面上完成的

