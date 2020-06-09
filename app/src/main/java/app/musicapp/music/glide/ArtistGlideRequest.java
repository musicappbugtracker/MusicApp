package app.musicapp.music.glide;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import app.musicapp.music.App;
import app.musicapp.music.glide.artistimage.AlbumCover;
import app.musicapp.music.glide.artistimage.ArtistImage;
import app.musicapp.music.glide.palette.BitmapPaletteTranscoder;
import app.musicapp.music.glide.palette.BitmapPaletteWrapper;
import app.musicapp.music.model.Album;
import app.musicapp.music.model.Artist;
import app.musicapp.music.model.Song;
import app.musicapp.music.util.ArtistSignatureUtil;
import app.musicapp.music.util.CustomArtistImageUtil;
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

public class ArtistGlideRequest {

    public static final int DEFAULT_ANIMATION = android.R.anim.fade_in;
    private static final DiskCacheStrategy DEFAULT_DISK_CACHE_STRATEGY = DiskCacheStrategy.ALL;
    private static final int DEFAULT_ERROR_IMAGE = R.drawable.default_artist_image;

    public static DrawableTypeRequest createBaseRequest(RequestManager requestManager, Artist artist, boolean noCustomImage) {
        boolean hasCustomImage = CustomArtistImageUtil.getInstance(App.getInstance()).hasCustomArtistImage(artist);
        if (noCustomImage || !hasCustomImage) {
            final List<AlbumCover> songs = new ArrayList<>();
            for (final Album album : artist.albums) {
                final Song song = album.safeGetFirstSong();
                songs.add(new AlbumCover(album.getYear(), song.data));
            }
            return requestManager.load(new ArtistImage(artist.getName(), songs));
        } else {
            return requestManager.load(CustomArtistImageUtil.getFile(artist));
        }
    }

    private static Key createSignature(Artist artist) {
        return ArtistSignatureUtil.getInstance(App.getInstance()).getArtistSignature(artist.getName());
    }

    public static class Builder {
        final RequestManager requestManager;
        final Artist artist;
        boolean noCustomImage;

        private Builder(@NonNull RequestManager requestManager, Artist artist) {
            this.requestManager = requestManager;
            this.artist = artist;
        }

        public static Builder from(@NonNull RequestManager requestManager, Artist artist) {
            return new Builder(requestManager, artist);
        }

        public PaletteBuilder generatePalette(Context context) {
            return new PaletteBuilder(this, context);
        }

        public BitmapBuilder asBitmap() {
            return new BitmapBuilder(this);
        }

        public Builder noCustomImage(boolean noCustomImage) {
            this.noCustomImage = noCustomImage;
            return this;
        }

        public DrawableRequestBuilder<GlideDrawable> build() {
            //noinspection unchecked
            return createBaseRequest(requestManager, artist, noCustomImage)
                    .diskCacheStrategy(DEFAULT_DISK_CACHE_STRATEGY)
                    .error(DEFAULT_ERROR_IMAGE)
                    .animate(DEFAULT_ANIMATION)
                    .priority(Priority.LOW)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .signature(createSignature(artist));
        }
    }

    public static class BitmapBuilder {
        private final Builder builder;

        public BitmapBuilder(Builder builder) {
            this.builder = builder;
        }

        public BitmapRequestBuilder<?, Bitmap> build() {
            //noinspection unchecked
            return createBaseRequest(builder.requestManager, builder.artist, builder.noCustomImage)
                    .asBitmap()
                    .diskCacheStrategy(DEFAULT_DISK_CACHE_STRATEGY)
                    .error(DEFAULT_ERROR_IMAGE)
                    .animate(DEFAULT_ANIMATION)
                    .priority(Priority.LOW)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .signature(createSignature(builder.artist));
        }
    }

    public static class PaletteBuilder {
        final Context context;
        private final Builder builder;

        public PaletteBuilder(Builder builder, Context context) {
            this.builder = builder;
            this.context = context;
        }

        public BitmapRequestBuilder<?, BitmapPaletteWrapper> build() {
            //noinspection unchecked
            return createBaseRequest(builder.requestManager, builder.artist, builder.noCustomImage)
                    .asBitmap()
                    .transcode(new BitmapPaletteTranscoder(context), BitmapPaletteWrapper.class)
                    .diskCacheStrategy(DEFAULT_DISK_CACHE_STRATEGY)
                    .error(DEFAULT_ERROR_IMAGE)
                    .animate(DEFAULT_ANIMATION)
                    .priority(Priority.LOW)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .signature(createSignature(builder.artist));
        }
    }
}
