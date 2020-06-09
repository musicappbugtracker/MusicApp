package app.musicapp.music.misc;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

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

public abstract class WeakContextAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private WeakReference<Context> contextWeakReference;

    public WeakContextAsyncTask(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    @Nullable
    protected Context getContext() {
        return contextWeakReference.get();
    }
}
