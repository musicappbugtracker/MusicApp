package app.musicapp.music.helper;

import android.content.Context;
import android.view.ViewGroup;

import app.musicapp.music.R;

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

public class HorizontalAdapterHelper {
    public static final int LAYOUT_RES = R.layout.item_grid_card_horizontal;

    public static final int TYPE_FIRST = 1;
    public static final int TYPE_MIDDLE = 2;
    public static final int TYPE_LAST = 3;

    public static void applyMarginToLayoutParams(Context context, ViewGroup.MarginLayoutParams layoutParams, int viewType) {
        int listMargin = context.getResources().getDimensionPixelSize(R.dimen.default_item_margin);
        if (viewType == TYPE_FIRST) {
            layoutParams.leftMargin = listMargin;
        } else if (viewType == TYPE_LAST) {
            layoutParams.rightMargin = listMargin;
        }
    }

    public static int getItemViewtype(int position, int itemCount) {
        if (position == 0) {
            return TYPE_FIRST;
        } else if (position == itemCount - 1) {
            return TYPE_LAST;
        } else return TYPE_MIDDLE;
    }
}
