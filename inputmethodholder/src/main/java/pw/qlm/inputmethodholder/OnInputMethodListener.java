package pw.qlm.inputmethodholder;

/**
 * Created by qiulinmin on 2017/3/6.
 */
public interface OnInputMethodListener {

    void onShow(boolean result);

    /**
     * 主动调用hideSoftInputFromWindow会走到该方法，
     * 通过系统按钮关闭软键盘的时候未调用，
     * 因为键盘在一个独立的进程中，我们hook的只是本地进程的一个binder代理
     */
    void onHide(boolean result);

}
