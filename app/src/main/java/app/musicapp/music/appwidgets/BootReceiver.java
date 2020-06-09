package app.musicapp.music.appwidgets;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import app.musicapp.music.service.MusicService;

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

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        final AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

        // Start music service if there are any existing widgets
        if (widgetManager.getAppWidgetIds(new ComponentName(context, AppWidgetBig.class)).length > 0 ||
                widgetManager.getAppWidgetIds(new ComponentName(context, AppWidgetClassic.class)).length > 0 ||
                widgetManager.getAppWidgetIds(new ComponentName(context, AppWidgetSmall.class)).length > 0 ||
                widgetManager.getAppWidgetIds(new ComponentName(context, AppWidgetCard.class)).length > 0) {
            final Intent serviceIntent = new Intent(context, MusicService.class);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) { // not allowed on Oreo
                context.startService(serviceIntent);
            }
        }
    }
}
