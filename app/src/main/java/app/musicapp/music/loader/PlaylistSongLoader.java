package app.musicapp.music.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import app.musicapp.music.model.PlaylistSong;

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

public class PlaylistSongLoader {

    @NonNull
    public static List<PlaylistSong> getPlaylistSongList(@NonNull final Context context, final int playlistId) {
        List<PlaylistSong> songs = new ArrayList<>();
        Cursor cursor = makePlaylistSongCursor(context, playlistId);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                songs.add(getPlaylistSongFromCursorImpl(cursor, playlistId));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return songs;
    }

    @NonNull
    private static PlaylistSong getPlaylistSongFromCursorImpl(@NonNull Cursor cursor, int playlistId) {
        final int id = cursor.getInt(0);
        final String title = cursor.getString(1);
        final int trackNumber = cursor.getInt(2);
        final int year = cursor.getInt(3);
        final long duration = cursor.getLong(4);
        final String data = cursor.getString(5);
        final int dateModified = cursor.getInt(6);
        final int albumId = cursor.getInt(7);
        final String albumName = cursor.getString(8);
        final int artistId = cursor.getInt(9);
        final String artistName = cursor.getString(10);
        final int idInPlaylist = cursor.getInt(11);

        return new PlaylistSong(id, title, trackNumber, year, duration, data, dateModified, albumId, albumName, artistId, artistName, playlistId, idInPlaylist);
    }

    public static Cursor makePlaylistSongCursor(@NonNull final Context context, final int playlistId) {
        try {
            return context.getContentResolver().query(
                    MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId),
                    new String[]{
                            MediaStore.Audio.Playlists.Members.AUDIO_ID,// 0
                            AudioColumns.TITLE,// 1
                            AudioColumns.TRACK,// 2
                            AudioColumns.YEAR,// 3
                            AudioColumns.DURATION,// 4
                            AudioColumns.DATA,// 5
                            AudioColumns.DATE_MODIFIED,// 6
                            AudioColumns.ALBUM_ID,// 7
                            AudioColumns.ALBUM,// 8
                            AudioColumns.ARTIST_ID,// 9
                            AudioColumns.ARTIST,// 10
                            MediaStore.Audio.Playlists.Members._ID // 11
                    }, SongLoader.BASE_SELECTION, null,
                    MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER);
        } catch (SecurityException e) {
            return null;
        }
    }
}