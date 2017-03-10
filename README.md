InputMethodHolder
====

> A keyboard listener for Android which by hooking the InputMethodManager.
  Of course you can also hook the other system services similarly,
  If you want, create a class, make it a subclass of `Hook`, and using `ServiceManagerHook` to hook ServiceManager, learn more from `InputMethodManagerHook`.
  If you like this project, ,welcome to fork or star!

Steps：
---------

1.Import InputMethodHodler as a library  
2.Call the initialization method, the method will hook InputMethodManager, recommended to call at `attachBaseContext`:
```java
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        InputMethodHolder.init(base);
        super.attachBaseContext(base);
    }
}
```
3.Register the listener,and unregister when unused：
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
4.Don't forget to release when exiting(avoid memory leaks)：
```java
InputMethodHolder.release();
```

**Please read Sample for getting specific use, and if have any problems please submit ISSUE.**

Defect
-----
`onShow` works well in most situations, but `onHide` can only give callbacks by calling `hideSoftInputFromWindows` manually.
The reason is that system keyboard is hold by another process,
and the procedure for using the keyboard by an application  is a local process remote call through `InputMethodManager`,
the hook is just that InputMethodManager of the application's process.

```java
public interface OnInputMethodListener {
    void onShow(boolean result);
    void onHide(boolean result);
}
```

Sample
----------

![](http://ojlty2hua.qnssl.com/image-1488865989092-c2hvd2lucHV0LnBuZw==.png?imageView2/3/w/400/h/400/q/60|watermark/2/text/cWxtLnB3/font/5a6L5L2T/fontsize/500/fill/I0VGRUZFRg==/dissolve/100/gravity/SouthEast/dx/10/dy/10)  

![](http://ojlty2hua.qnssl.com/image-1488866117210-aGlkZWlucHV0LnBuZw==.png?imageView2/3/w/400/h/400/q/60|watermark/2/text/cWxtLnB3/font/5a6L5L2T/fontsize/500/fill/I0VGRUZFRg==/dissolve/100/gravity/SouthEast/dx/10/dy/10)

Contact
----
pqponet@gmail.com

License
--------
    Copy right 2017. Linmin qiu
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.