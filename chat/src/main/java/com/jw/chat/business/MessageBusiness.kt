package com.jw.chat.business

import com.jw.chat.db.ChatDataBase
import com.jw.chat.db.bean.Message

/**
 * 作者 : jinwangx
 * 创建时间 : 2018/6/13
 * 描述 : 业务类(聊天消息)
 */
object MessageBusiness {
    /**
     * 新增消息
     * @param message Message
     * @return Int 1为成功
     */
    fun insert(message: Message) {
        ChatDataBase.getInstance().messageDao().insert(message)
        ConversationBusiness.insert(message)
    }

    /**
     * 更新消息
     * @param message Message
     * @return Int 1为成功
     */
    fun update(message: Message) = ChatDataBase.getInstance().messageDao().update(message)

    /**
     * 更新当前账号某一会话所有消息的阅读状态
     * @param owner String
     * @param account String
     * @param read Boolean 0为未读，1为已读
     * @return Int 1为成功
     */
    fun update(owner: String, account: String, read: Boolean) = ChatDataBase.getInstance().messageDao().update(owner, account, read)

    /**
     * 得到当前账号某一会话消息Cursor
     * @param owner String
     * @param account String
     * @return Cursor
     */
    fun query(owner: String, account: String) = ChatDataBase.getInstance().messageDao().query(owner, account)

    /**
     * 得到当前账号某一会话未读消息的条数
     * @param owner String
     * @param account String
     * @return Int 未读消息的条数
     */
    fun getUnreadCountByAccount(owner: String, account: String) = ChatDataBase.getInstance().messageDao().getUnreadCountByAccount(owner, account)

    /**
     * 得到当前账号所有未读消息的条数
     * @param owner String
     * @return Int 未读消息的条数
     */
    fun getUnreadCountByOwner(owner: String) = ChatDataBase.getInstance().messageDao().getUnreadCountByOwner(owner)
}