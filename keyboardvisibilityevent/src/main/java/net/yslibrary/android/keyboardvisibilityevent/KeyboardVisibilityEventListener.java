package net.yslibrary.android.keyboardvisibilityevent;

/**
 * Created by yshrsmz on 15/03/17.
 */
public interface KeyboardVisibilityEventListener {

    void onKeyboardOpened(int keyboardHeight);

    void onKeyboardClosed();
}
