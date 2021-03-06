package com.jw.business.db.dao

import android.arch.persistence.room.*
import com.jw.business.db.model.AccountInfo

/**
 * 作者 : jinwangx
 * 创建时间 : 2018/6/13
 * 描述 : 数据库操作(账号信息)
 */
@Dao
interface AccountInfoDao {

    @Query("select * from account")
    fun getAccountInfoAll(): List<AccountInfo>?

    @Query("select * from account where current=1")
    fun getCurrentAccountInfo(): AccountInfo?

    @Query("select * from account where account=:account")
    fun getAccountInfoByAccount(account: String): AccountInfo?

    @Query("update account set icon=:iconPath where account=:account")
    fun updateIconByAccount(account: String, iconPath: String): Int

    @Insert
    fun insert(accountInfo: AccountInfo): Long

    @Update
    fun update(accountInfo: AccountInfo): Int

    @Delete
    fun delete(accountInfo: AccountInfo): Int

}