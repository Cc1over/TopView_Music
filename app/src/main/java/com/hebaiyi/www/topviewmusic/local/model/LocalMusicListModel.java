package com.hebaiyi.www.topviewmusic.local.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import com.hebaiyi.www.topviewmusic.bean.LocalMusic;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LocalMusicListModel {

    public void loadLocalMusicList(final Context context, final LocalMusicListCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = context.getContentResolver();
                Cursor cursor = contentResolver
                        .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                null, null, null,
                                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                List<LocalMusic> localList = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        LocalMusic music = new LocalMusic();
                        long id = cursor.getLong(cursor
                                .getColumnIndex(MediaStore.Audio.Media._ID));
                        String title = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.TITLE));
                        String artist = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.ARTIST));
                        long duration = cursor.getLong(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DURATION));
                        long size = cursor.getLong(cursor
                                .getColumnIndex(MediaStore.Audio.Media.SIZE));
                        String url = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DATA));
                        String album = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                        int albumId = cursor.getInt(cursor
                                .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                        int isMusic = cursor.getInt(cursor
                                .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                        String albumPic = getAlbumArt(albumId, context);
                        if (isMusic != 0 && duration / (500 * 60) >= 1) {
                            music.setId(id);
                            music.setTitle(title);
                            music.setArtist(artist);
                            music.setDuration(duration);
                            music.setSize(size);
                            music.setUrl(url);
                            music.setAlbum(album);
                            music.setAlbumId(albumId);
                            music.setAlbumPic(albumPic);
                            localList.add(music);
                        }
                        cursor.moveToNext();
                    }
                    callback.onSuccess(localList);
                    cursor.close();
                }
            }
        }).start();
    }

    private String getAlbumArt(int albumId,Context context) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Integer.toString(albumId)),
                projection, null, null, null);
        String albumArt = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            albumArt = cur.getString(0);
        }
        cur.close();
        return albumArt;
    }



    public interface LocalMusicListCallback {

        void onFail(Exception e);

        void onSuccess(List<LocalMusic> localList);

    }


}
