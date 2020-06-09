package app.musicapp.music.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import app.musicapp.music.helper.StackBlur;
import app.musicapp.music.util.ImageUtil;
import app.musicapp.music.BuildConfig;

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

public class BlurTransformation extends BitmapTransformation {
    public static final float DEFAULT_BLUR_RADIUS = 5f;

    private Context context;
    private float blurRadius;
    private int sampling;

    private BlurTransformation(Builder builder) {
        super(builder.context);
        init(builder);
    }

    private BlurTransformation(Builder builder, BitmapPool bitmapPool) {
        super(bitmapPool);
        init(builder);
    }

    private void init(Builder builder) {
        this.context = builder.context;
        this.blurRadius = builder.blurRadius;
        this.sampling = builder.sampling;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int sampling;
        if (this.sampling == 0) {
            sampling = ImageUtil.calculateInSampleSize(toTransform.getWidth(), toTransform.getHeight(), 100);
        } else {
            sampling = this.sampling;
        }

        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;

        Bitmap out = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        if (out == null) {
            out = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(out);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(toTransform, 0, 0, paint);

        if (Build.VERSION.SDK_INT >= 17) {
            try {
                final RenderScript rs = RenderScript.create(context.getApplicationContext());
                final Allocation input = Allocation.createFromBitmap(rs, out, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
                final Allocation output = Allocation.createTyped(rs, input.getType());
                final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

                script.setRadius(blurRadius);
                script.setInput(input);
                script.forEach(output);

                output.copyTo(out);

                rs.destroy();

                return out;

            } catch (RSRuntimeException e) {
                // on some devices RenderScript.create() throws: android.support.v8.renderscript.RSRuntimeException: Error loading libRSSupport library
                if (BuildConfig.DEBUG) e.printStackTrace();
            }
        }

        return StackBlur.blur(out, blurRadius);
    }

    @Override
    public String getId() {
        return "BlurTransformation(radius=" + blurRadius + ", sampling=" + sampling + ")";
    }

    public static class Builder {
        private Context context;
        private BitmapPool bitmapPool;
        private float blurRadius = DEFAULT_BLUR_RADIUS;
        private int sampling;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        /**
         * @param blurRadius The radius to use. Must be between 0 and 25. Default is 5.
         * @return the same Builder
         */
        public Builder blurRadius(@FloatRange(from = 0.0f, to = 25.0f) float blurRadius) {
            this.blurRadius = blurRadius;
            return this;
        }

        /**
         * @param sampling The inSampleSize to use. Must be a power of 2, or 1 for no down sampling or 0 for auto detect sampling. Default is 0.
         * @return the same Builder
         */
        public Builder sampling(int sampling) {
            this.sampling = sampling;
            return this;
        }

        /**
         * @param bitmapPool The BitmapPool to use.
         * @return the same Builder
         */
        public Builder bitmapPool(BitmapPool bitmapPool) {
            this.bitmapPool = bitmapPool;
            return this;
        }

        public BlurTransformation build() {
            if (bitmapPool != null) {
                return new BlurTransformation(this, bitmapPool);
            }
            return new BlurTransformation(this);
        }
    }
}
