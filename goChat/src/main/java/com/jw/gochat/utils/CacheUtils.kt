package com.jw.gochat.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.jw.library.utils.FileUtils
import com.jw.library.utils.PrefUtils

/**
 * 缓存工具类
 *
 * @author Kevin
 */
object CacheUtils {
    var PREF_NAME = "config"

    /**
     * 设置缓存目录的名字，默认为"config"
     * @param name
     */
    fun setCacheDirectoryName(name: String) {
        PrefUtils.setCacheDirectoryName(name)
    }

    /**
     * 设置缓存
     * @param key url
     * @param value json
     * @param ctx
     */
    fun setCache(key: String, value: String, ctx: Context) {
        PrefUtils.setString(ctx, key, value)
    }

    fun setCache(key: String, value: Boolean, ctx: Context) {
        PrefUtils.setBoolean(ctx, key, value)
    }

    fun setCache(key: String, value: Int, ctx: Context) {
        PrefUtils.setInt(ctx, key, value)
    }

    fun setCache(key: String, value: Float, ctx: Context) {
        PrefUtils.setFloat(ctx, key, value)
    }

    fun setCache(key: String, value: Long, ctx: Context) {
        PrefUtils.setLong(ctx, key, value)
    }


    /**
     * 获取缓存
     * @param key
     * @param ctx
     * @return
     */
    fun getCacheString(key: String, defaultString: String, ctx: Context): String? {
        return PrefUtils.getString(ctx, key, defaultString)
    }

    fun getCacheInt(key: String, defaultInt: Int, ctx: Context): Int {
        return PrefUtils.getInt(ctx, key, defaultInt)
    }

    /**
     * 默认为false
     * @param key
     * @param ctx
     * @return
     */
    fun getCacheBoolean(key: String, defaultBoolean: Boolean, ctx: Context): Boolean {
        return PrefUtils.getBoolean(ctx, key, defaultBoolean)
    }

    fun getCacheFloat(key: String, ctx: Context): Float {
        return PrefUtils.getFloat(ctx, key, 0f)
    }

    fun getCacheLong(key: String, ctx: Context): Long {
        return PrefUtils.getLong(ctx, key, 0)
    }

    /**
     * 清除缓存
     * @param key
     * @param ctx
     */
    fun clearCache(key: String, ctx: Context) {
        PrefUtils.remove(ctx, key)
    }

    fun clearCacheAll(ctx: Context) {
        PrefUtils.clear(ctx)
    }

    fun CacheContains(key: String, ctx: Context): Boolean {
        return PrefUtils.contains(ctx, key)
    }

    fun clearImageCache(context: Context) {
        Glide.get(context).clearDiskCache()
        //Glide.get(context).clearMemory();
    }

    fun clear(context: Context) {
        clearCacheAll(context)
        clearImageCache(context)
    }

    fun getCacheSize(context: Context, jsonCachePath: String, imageCachePath: String): Double {
        var jsonCachePath = jsonCachePath
        jsonCachePath = context.filesDir.parent + "/shared_prefs/" + PREF_NAME + ".xml"
        return getImageCacheSize(imageCachePath) + getJsonCacheSize(jsonCachePath)
    }

    fun getJsonCacheSize(jsonCachePath: String): Double {
        return FileUtils.getFileOrFilesSize(jsonCachePath, 3)
    }

    fun getImageCacheSize(imageCachePath: String): Double {
        return FileUtils.getFileOrFilesSize(imageCachePath, 3)
    }

}
