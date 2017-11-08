package com.example.jmay.asla;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.ContentUris;

/**
 * Created by jmay on 10/9/2017.
 */

public class FilePath {
    @TargetApi(Build.VERSION_CODES.M)
    public static String getPath(final Context context, final Uri uri){
        final boolean kitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if(kitKat && DocumentsContract.isDocumentUri(context, uri)){
            if(isExternalStorageDocument(uri)){
                final String documentID = DocumentsContract.getDocumentId(uri);
                final String[] split = documentID.split(":");
                final String type = split[0];
                if("primary".equalsIgnoreCase(type)){
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }else if(isDownloadsDocument(uri)){
                final String documentID = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentID));
                return getDataColumn(context, contentUri, null, null);

            }else if(isMediaDocument(uri)){
                final String documentID = DocumentsContract.getDocumentId(uri);
                final String[] split = documentID.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if("image".equalsIgnoreCase(type)){
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }else if("video".equalsIgnoreCase(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }else if("audio".equalsIgnoreCase(type)){
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }

        }else if("content".equalsIgnoreCase(uri.getScheme())){
            if(isGooglePhotoUri(uri)){
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);

        }else if("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri){
        return "com.android.externalstorage.documents".equalsIgnoreCase(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri){
        return "com.android.providers.downloads.documents".equalsIgnoreCase(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri){
        return "com.android.providers.media.documents".equalsIgnoreCase(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs)
    {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try{
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if(cursor != null && cursor.moveToFirst()){
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }

        }finally {
            if(cursor != null){
                cursor.close();
            }
            return null;
        }
    }
    public static boolean isGooglePhotoUri(Uri uri){
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}