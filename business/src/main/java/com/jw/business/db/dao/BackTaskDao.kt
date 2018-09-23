package com.jw.business.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import android.database.Cursor
import com.jw.business.db.model.BackTask

/**
 * 作者 : jinwangx
 * 创建时间 : 2018/6/13
 * 描述 : 数据库操作(后台任务)
 */
@Dao
interface BackTaskDao {

    @Query("select * from back_task where owner=:owner")
    fun getTaskByOwner(owner: String): List<BackTask>?

    @Query("select * from back_task where owner=:owner and state=:state")
    fun getTaskByState(owner: String, state: Int): Cursor?

    @Insert
    fun insert(task: BackTask): Long

    @Update
    fun update(task: BackTask): Int

    @Query("update back_task set state=:state where _id=:id")
    fun updateTaskStateById(id: Long, state: Int): Int

}