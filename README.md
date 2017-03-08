InputMethodHolder
====

> 通过 Hook InputMethodManager 的方式监听系统软键盘的状态, 当然不仅仅是监听软键盘，类似的还可以hook其他系统服务。顺手点个Star吧！

使用方式：
---------

1.导入InputMethodHodler作为依赖库。
2.调用初始化方法, 该方法会 hook InputMethodManager，所以建议越早调用越好:
```java
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        InputMethodHolder.init(base);
        super.attachBaseContext(base);
    }
}
```
3.需要监听的地方注册监听器,并且不要忘记反注册：
```java
onInputMethodListener = new OnInputMethodListener() {
    @Override
	public void onShow(boolean result) {
	    Toast.makeText(MainActivity.this, "Show input method! " + result, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onHide(boolean result) {
	    Toast.makeText(MainActivity.this, "Hide input method! " + result, Toast.LENGTH_SHORT).show();
	}
};
InputMethodHolder.registerListener(onInputMethodListener);
```
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    InputMethodHolder.unregisterListener(onInputMethodListener);
}
```
4.应用退出时释放资源：
```java
InputMethodHolder.release();
```

**具体使用方法请看Sample，未做充分测试，在定制ROM中可能存在兼容性问题，欢迎提ISSUE**

缺陷：
-----

```java
public interface OnInputMethodListener {
    void onShow(boolean result);
    /**
     * 仅支持主动调用hideSoftInputFromWindow，会回调onHide()
     * 通过系统按钮关闭软键盘的时候不会回调，
     * 因为键盘在一个独立的进程中，我们hook的只是本地进程的一个binder代理
     */
    void onHide(boolean result);
}
```

Sample演示
----------

![](http://ojlty2hua.qnssl.com/image-1488865989092-c2hvd2lucHV0LnBuZw==.png?imageView2/3/w/400/h/400/q/60|watermark/2/text/cWxtLnB3/font/5a6L5L2T/fontsize/500/fill/I0VGRUZFRg==/dissolve/100/gravity/SouthEast/dx/10/dy/10)  

![](http://ojlty2hua.qnssl.com/image-1488866117210-aGlkZWlucHV0LnBuZw==.png?imageView2/3/w/400/h/400/q/60|watermark/2/text/cWxtLnB3/font/5a6L5L2T/fontsize/500/fill/I0VGRUZFRg==/dissolve/100/gravity/SouthEast/dx/10/dy/10)