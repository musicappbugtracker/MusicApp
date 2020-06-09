package app.musicapp.music.glide.audiocover;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

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

public class AudioFileCoverLoader implements StreamModelLoader<AudioFileCover> {

    @Override
    public DataFetcher<InputStream> getResourceFetcher(AudioFileCover model, int width, int height) {
        return new AudioFileCoverFetcher(model);
    }

    public static class Factory implements ModelLoaderFactory<AudioFileCover, InputStream> {
        @Override
        public ModelLoader<AudioFileCover, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new AudioFileCoverLoader();
        }

        @Override
        public void teardown() {
        }
    }
}
