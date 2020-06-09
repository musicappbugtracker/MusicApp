package app.musicapp.music.helper;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import app.musicapp.music.loader.SongLoader;
import app.musicapp.music.model.Song;

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

public class SearchQueryHelper {
    private static final String TITLE_SELECTION = "lower(" + MediaStore.Audio.AudioColumns.TITLE + ") = ?";
    private static final String ALBUM_SELECTION = "lower(" + MediaStore.Audio.AudioColumns.ALBUM + ") = ?";
    private static final String ARTIST_SELECTION = "lower(" + MediaStore.Audio.AudioColumns.ARTIST + ") = ?";
    private static final String AND = " AND ";

    @NonNull
    public static List<Song> getSongs(@NonNull final Context context, @NonNull final Bundle extras) {
        final String query = extras.getString(SearchManager.QUERY, null);
        final String artistName = extras.getString(MediaStore.EXTRA_MEDIA_ARTIST, null);
        final String albumName = extras.getString(MediaStore.EXTRA_MEDIA_ALBUM, null);
        final String titleName = extras.getString(MediaStore.EXTRA_MEDIA_TITLE, null);

        List<Song> songs = new ArrayList<>();

        if (artistName != null && albumName != null && titleName != null) {
            songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, ARTIST_SELECTION + AND + ALBUM_SELECTION + AND + TITLE_SELECTION, new String[]{artistName.toLowerCase().trim(), albumName.toLowerCase().trim(), titleName.toLowerCase().trim()}));
        }
        if (!songs.isEmpty()) {
            return songs;
        }

        if (artistName != null && titleName != null) {
            songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, ARTIST_SELECTION + AND + TITLE_SELECTION, new String[]{artistName.toLowerCase().trim(), titleName.toLowerCase().trim()}));
        }
        if (!songs.isEmpty()) {
            return songs;
        }

        if (albumName != null && titleName != null) {
            songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, ALBUM_SELECTION + AND + TITLE_SELECTION, new String[]{albumName.toLowerCase().trim(), titleName.toLowerCase().trim()}));
        }
        if (!songs.isEmpty()) {
            return songs;
        }

        if (artistName != null) {
            songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, ARTIST_SELECTION, new String[]{artistName.toLowerCase().trim()}));
        }
        if (!songs.isEmpty()) {
            return songs;
        }

        if (albumName != null) {
            songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, ALBUM_SELECTION, new String[]{albumName.toLowerCase().trim()}));
        }
        if (!songs.isEmpty()) {
            return songs;
        }

        if (titleName != null) {
            songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, TITLE_SELECTION, new String[]{titleName.toLowerCase().trim()}));
        }
        if (!songs.isEmpty()) {
            return songs;
        }


        songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, ARTIST_SELECTION, new String[]{query.toLowerCase().trim()}));
        if (!songs.isEmpty()) {
            return songs;
        }

        songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, ALBUM_SELECTION, new String[]{query.toLowerCase().trim()}));
        if (!songs.isEmpty()) {
            return songs;
        }

        songs = SongLoader.getSongs(SongLoader.makeSongCursor(context, TITLE_SELECTION, new String[]{query.toLowerCase().trim()}));
        if (!songs.isEmpty()) {
            return songs;
        }

        return SongLoader.getSongs(context, query);
    }
}
