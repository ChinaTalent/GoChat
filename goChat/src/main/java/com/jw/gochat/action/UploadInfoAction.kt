package com.jw.gochat.action

import android.content.Context
import android.content.Intent
import com.jw.business.db.dao.AppDatabase
import com.jw.business.model.bean.Account
import com.jw.business.model.bean.BackTask
import com.jw.business.model.bean.Contact
import com.jw.business.model.bean.NetTask
import com.jw.chat.GoChatURL
import com.jw.chat.db.bean.Message
import com.jw.chat.db.dao.MessageDao
import com.jw.gochat.service.BackgroundService
import com.jw.gochat.utils.BackTaskFactory
import com.jw.gochat.utils.CommonUtil
import com.jw.library.utils.FileUtils
import java.io.File


/**
 * 创建时间：2017/11/2 20:14
 * 更新时间 2017/11/2 20:14
 * 版本：
 * 作者：Mr.jin
 * 描述：
 */

class UploadInfoAction {

    val action: String
        get() = "uploadInfo"

    fun doAction(context: Context, me: Account,
                 name: String, iconFile: File) {
        Thread {
            run {
                val friendDao = AppDatabase.getInstance(context).friendDao()
                // 初始化通讯录
                val friend = Contact()
                friend.owner = me.account
                friend.account = "GoChat"
                friend.alpha = "G"
                friend.area = ""
                // /data/Android/com.jw.qq/friendIcon/
                val file = File(CommonUtil.getFriendIconDir(context), friend.account!!)
                val path = file.absolutePath
                //存储默认头像到本地头像文件夹
                val inPath = CommonUtil.getIconDir(context) + "/default_icon_user.png"
                FileUtils.copy(inPath, path)
                friend.icon = path
                friend.name = "小旺"
                friend.nickName = ""
                friend.sort = 1000

                friendDao.addFriend(friend)
                val messageDao = MessageDao(context)
                val message = Message()
                message.account = "GoChat"
                message.content = "欢迎使用GoChat，有你更精彩"
                message.createTime = System.currentTimeMillis()
                //未接收
                message.direction = 1
                message.owner = me.account
                message.isRead = false
                messageDao.addMessage(message)
            }
        }.start()
        me.name = name
        me.icon = GoChatURL.BASE_HTTP + "/repo/icon/" + me.account + ".png"
        val dao = AppDatabase.getInstance(context).accountDao()
        dao.update(me)
        doAddTask(context, me, iconFile, "addName")
        doAddTask(context, me, iconFile, "addIcon")

    }

    /**
     * 添加后台任务
     * @param context
     * @param me
     * @param iconFile
     * @param type
     */
    fun doAddTask(context: Context, me: Account, iconFile: File, type: String) {
        var path: String? = null
        var netTask: NetTask? = null
        // 存储到后台任务中
        val fileName = me.account + "_" + System.currentTimeMillis()
        // /data/Android/com.jw.qq/task/
        path = File(CommonUtil.getTaskDir(context), fileName).absolutePath
        if (type == "addName") {
            netTask = BackTaskFactory.userNameChangeTask(me.name!!)
        }
        if (type == "addIcon") {
            netTask = BackTaskFactory.userIconChangeTask(me.account!!, iconFile.absolutePath)
        }
        //将后台任务添加进数据库
        val task = BackTask()
        task.owner = me.account
        task.path = path
        task.state = 0
        AppDatabase.getInstance(context).backTaskDao().addTask(task)

        try {
            // 把网络任务属性序列化入path中
            FileUtils.write<NetTask>(netTask!!, path)

            // 开启后台服务
            context.startService(
                    Intent(context, BackgroundService::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
