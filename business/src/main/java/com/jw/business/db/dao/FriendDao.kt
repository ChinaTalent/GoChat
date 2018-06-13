package com.jw.business.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import android.database.Cursor
import com.jw.business.model.bean.Friend

@Dao
interface FriendDao {

    @Insert
    fun insert(friend: Friend):Long

    @Update
    fun update(friend: Friend):Int

    @Query("select * from friend where owner=:owner")
    fun getFriendAll(owner: String):Cursor?

    @Query("select * from friend where owner=:owner and account=:account")
    fun getFriendByAccount(owner: String, account: String):Friend

    @Query("update friend set icon =:iconPath where owner=:owner and account=:account")
    fun updateFriend(owner:String,account: String, iconPath: String):Int

}