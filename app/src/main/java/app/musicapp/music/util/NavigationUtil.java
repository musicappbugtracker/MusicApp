package app.musicapp.music.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import app.musicapp.music.helper.MusicPlayerRemote;
import app.musicapp.music.model.Genre;
import app.musicapp.music.model.Playlist;
import app.musicapp.music.ui.activities.AlbumDetailActivity;
import app.musicapp.music.ui.activities.ArtistDetailActivity;
import app.musicapp.music.ui.activities.GenreDetailActivity;
import app.musicapp.music.ui.activities.PlaylistDetailActivity;
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

public class NavigationUtil {

    public static void goToArtist(@NonNull final Activity activity, final int artistId, @Nullable Pair... sharedElements) {
        final Intent intent = new Intent(activity, ArtistDetailActivity.class);
        intent.putExtra(ArtistDetailActivity.EXTRA_ARTIST_ID, artistId);

        //noinspection unchecked
        if (sharedElements != null && sharedElements.length > 0) {
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    public static void goToAlbum(@NonNull final Activity activity, final int albumId, @Nullable Pair... sharedElements) {
        final Intent intent = new Intent(activity, AlbumDetailActivity.class);
        intent.putExtra(AlbumDetailActivity.EXTRA_ALBUM_ID, albumId);

        //noinspection unchecked
        if (sharedElements != null && sharedElements.length > 0) {
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    public static void goToGenre(@NonNull final Activity activity, final Genre genre, @Nullable Pair... sharedElements) {
        final Intent intent = new Intent(activity, GenreDetailActivity.class);
        intent.putExtra(GenreDetailActivity.EXTRA_GENRE, genre);

        activity.startActivity(intent);
    }

    public static void goToPlaylist(@NonNull final Activity activity, final Playlist playlist, @Nullable Pair... sharedElements) {
        final Intent intent = new Intent(activity, PlaylistDetailActivity.class);
        intent.putExtra(PlaylistDetailActivity.EXTRA_PLAYLIST, playlist);

        activity.startActivity(intent);
    }

    public static void openEqualizer(@NonNull final Activity activity) {
        final int sessionId = MusicPlayerRemote.getAudioSessionId();
        if (sessionId == AudioEffect.ERROR_BAD_VALUE) {
            Toast.makeText(activity, activity.getResources().getString(R.string.no_audio_ID), Toast.LENGTH_LONG).show();
        } else {
            try {
                final Intent effects = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
                effects.putExtra(AudioEffect.EXTRA_AUDIO_SESSION, sessionId);
                effects.putExtra(AudioEffect.EXTRA_CONTENT_TYPE, AudioEffect.CONTENT_TYPE_MUSIC);
                activity.startActivityForResult(effects, 0);
            } catch (@NonNull final ActivityNotFoundException notFound) {
                Toast.makeText(activity, activity.getResources().getString(R.string.no_equalizer), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
