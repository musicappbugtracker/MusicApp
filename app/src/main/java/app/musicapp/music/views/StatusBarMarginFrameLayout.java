package app.musicapp.music.views;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;

/*
 * @author Karim Abou Zeid (kabouzeid)
 *
 * This file is part of Music App.

    Music App is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Music App is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Music App.  If not, see <https://www.gnu.org/licenses/>.
 */

public class StatusBarMarginFrameLayout extends FrameLayout {

    public StatusBarMarginFrameLayout(Context context) {
        super(context);
    }

    public StatusBarMarginFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBarMarginFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            lp.topMargin = insets.getSystemWindowInsetTop();
            setLayoutParams(lp);
        }
        return super.onApplyWindowInsets(insets);
    }
}
