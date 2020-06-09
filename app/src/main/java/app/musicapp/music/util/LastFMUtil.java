package app.musicapp.music.util;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.musicapp.music.lastfm.rest.model.LastFmAlbum.Album;
import app.musicapp.music.lastfm.rest.model.LastFmArtist.Artist;

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

public class LastFMUtil {
    public static String getLargestArtistImageUrl(List<Artist.Image> images) {
        Map<ImageSize, String> imageUrls = new HashMap<>();
        for (Artist.Image image : images) {
            ImageSize size = null;
            final String attribute = image.getSize();
            if (attribute == null) {
                size = ImageSize.UNKNOWN;
            } else {
                try {
                    size = ImageSize.valueOf(attribute.toUpperCase(Locale.ENGLISH));
                } catch (final IllegalArgumentException e) {
                    // if they suddenly again introduce a new image size
                }
            }
            if (size != null) {
                imageUrls.put(size, image.getText());
            }
        }
        return getLargestImageUrl(imageUrls);
    }

    public static String getLargestAlbumImageUrl(List<Album.Image> images) {
        Map<ImageSize, String> imageUrls = new HashMap<>();
        for (Album.Image image : images) {
            ImageSize size = null;
            final String attribute = image.getSize();
            if (attribute == null) {
                size = ImageSize.UNKNOWN;
            } else {
                try {
                    size = ImageSize.valueOf(attribute.toUpperCase(Locale.ENGLISH));
                } catch (final IllegalArgumentException e) {
                    // if they suddenly again introduce a new image size
                }
            }
            if (size != null) {
                imageUrls.put(size, image.getText());
            }
        }
        return getLargestImageUrl(imageUrls);
    }

    private static String getLargestImageUrl(Map<ImageSize, String> imageUrls) {
        if (imageUrls.containsKey(ImageSize.MEGA)) {
            return imageUrls.get(ImageSize.MEGA);
        }
        if (imageUrls.containsKey(ImageSize.EXTRALARGE)) {
            return imageUrls.get(ImageSize.EXTRALARGE);
        }
        if (imageUrls.containsKey(ImageSize.LARGE)) {
            return imageUrls.get(ImageSize.LARGE);
        }
        if (imageUrls.containsKey(ImageSize.MEDIUM)) {
            return imageUrls.get(ImageSize.MEDIUM);
        }
        if (imageUrls.containsKey(ImageSize.SMALL)) {
            return imageUrls.get(ImageSize.SMALL);
        }
        if (imageUrls.containsKey(ImageSize.UNKNOWN)) {
            return imageUrls.get(ImageSize.UNKNOWN);
        }
        return null;
    }

    public enum ImageSize {
        SMALL, MEDIUM, LARGE, EXTRALARGE, MEGA, UNKNOWN
    }
}
