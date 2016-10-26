package org.robolectric.shadows;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Window;
import android.widget.ProgressBar;
import com.android.internal.policy.PhoneWindow;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.util.ReflectionHelpers;
import org.robolectric.util.ReflectionHelpers.ClassParameter;

import static org.robolectric.internal.Shadow.directlyOn;

/**
 * Shadow for {@link android.view.Window}.
 */
@SuppressWarnings({"UnusedDeclaration"})
@Implements(Window.class)
public class ShadowWindow {
  private @RealObject Window realWindow;

  private int flags;
  private int softInputMode;

  public static Window create(Context context) throws Exception {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return new PhoneWindow(context);
    } else {
      Class<? extends Window> phoneWindowClass =
          (Class<? extends Window>) Window.class.getClassLoader().loadClass("com.android.internal.policy.impl.PhoneWindow");
      return ReflectionHelpers.callConstructor(phoneWindowClass, ClassParameter.from(Context.class, context));
    }
  }

  @Implementation
  public void setFlags(int flags, int mask) {
    this.flags = (this.flags & ~mask) | (flags & mask);
    directlyOn(realWindow, Window.class, "setFlags", ClassParameter.from(int.class, flags), ClassParameter.from(int.class, mask));
  }

  @Implementation
  public void setSoftInputMode(int softInputMode) {
    this.softInputMode = softInputMode;
    directlyOn(realWindow, Window.class, "setSoftInputMode", ClassParameter.from(int.class, softInputMode));
  }

  public boolean getFlag(int flag) {
    return (flags & flag) == flag;
  }

  public CharSequence getTitle() {
    return "";
  }

  public Drawable getBackgroundDrawable() {
    return null;
  }

  public int getSoftInputMode() {
    return softInputMode;
  }

  public ProgressBar getProgressBar() {
    return null;
  }

  public ProgressBar getIndeterminateProgressBar() {
    return null;
  }
}