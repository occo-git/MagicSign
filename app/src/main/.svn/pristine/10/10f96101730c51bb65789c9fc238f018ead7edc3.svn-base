package com.softigress.magicsigns._system;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.SparseArray;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._system.Utils.Utils;
import org.xmlpull.v1.XmlPullParser;

public class BitmapManager {

    //region static
    public static int[] gameBitmapIds = new int[]{
            R.drawable.btn_close,
            R.drawable.orden,
            R.drawable.earth_green
    };

    public static Paint getBitmapPaint() {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //p.setDither(true);
        p.setFilterBitmap(true);
        return p;
    }

    public static Bitmap GetBitmap(int id, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inScaled = false;
        BitmapFactory.decodeResource(Utils.resources, id, options);
        int w = options.outWidth;
        int h = options.outHeight;

        int scale;
        if (w > h)
            scale = Math.round((float) h / (float) height);
        else
            scale = Math.round((float) w / (float) width);

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        options.inScaled = false;
        //options.inPreferQualityOverSpeed = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(Utils.resources, id, options);
    }

    public static Bitmap GetBitmap(int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        //options.inPreferQualityOverSpeed = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(Utils.resources, id, options);
    }

    public static Bitmap copy(Bitmap b) {
        return b.copy(Bitmap.Config.ARGB_8888, true);
    }

    public static void clearBitmaps(SparseArray<Bitmap> t) {
        if (t != null) {
            for (int i = 0; i < t.size(); i++) {
                Bitmap b = t.valueAt(i);
                if (b != null)
                    b.recycle();
            }
            t.clear();
            t = null;
        }
    }
    //endregion

    //region bitmaps
    public void parseBitmaps(int itemsBitmapId, int xmlId) {
        Bitmap itemsBitmap = GetBitmap(itemsBitmapId);
        if (itemsBitmap != null) {
            try {
                XmlPullParser parser = Utils.resources.getXml(xmlId);
                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("item")) {
                        // индекс аттрибута по алфавиту
                        String name = parser.getAttributeValue(1);
                        int x = Integer.parseInt(parser.getAttributeValue(3));
                        int y = Integer.parseInt(parser.getAttributeValue(4));
                        int w = Integer.parseInt(parser.getAttributeValue(2));
                        int h = Integer.parseInt(parser.getAttributeValue(0));
                        Bitmap b = Bitmap.createBitmap(itemsBitmap, x, y, w, h);
                        if (b != null) {
                            int id = Utils.getStringResId(name);
                            if (bitmapDictionary.indexOfKey(id) < 0)
                                bitmapDictionary.put(id, b);
                        }
                    }
                    parser.next();
                }
            } catch (Throwable t) {
                //Utils.CrashReport("BitmapUtils.parseBitmaps", t);
            }
            itemsBitmap.recycle();
        }
    }

    public void loadGameBitmaps() { loadBitmaps(gameBitmapIds); }

    private SparseArray<Bitmap> bitmapDictionary;
    public void loadBitmaps(int[] bitmapIds) {
        if (bitmapDictionary == null)
            bitmapDictionary = new SparseArray<>();
        // удаляем неиспользуемые
        for (int i = 0; i < bitmapDictionary.size(); i++) {
            int key = bitmapDictionary.keyAt(i);
            if (!Utils.inArray(key, bitmapIds)) {
                Bitmap b = bitmapDictionary.valueAt(i);
                if (b != null)
                    b.recycle();
                bitmapDictionary.removeAt(i);
            }
        }
        // добавляем отсутствующие
        for (int bitmapId : bitmapIds)
            addBitmap(bitmapId);
    }

    private void addBitmap(int id) {
        if (bitmapDictionary.indexOfKey(id) < 0)
            bitmapDictionary.put(id, GetBitmap(id));
    }

    public Bitmap getLoadedBitmap(int id) {
        if (bitmapDictionary.indexOfKey(id) >= 0)
            return bitmapDictionary.get(id);
        else
            return null;
    }

    public void recycle() {
        clearBitmaps(bitmapDictionary);
    }
    //endregion
}
