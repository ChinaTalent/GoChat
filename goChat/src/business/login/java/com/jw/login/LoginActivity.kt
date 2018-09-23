package com.jw.login

import android.content.Intent
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.jw.business.db.model.AccountInfo
import com.jw.gochat.GoChatApplication
import com.jw.gochat.R
import com.jw.gochat.databinding.ActivityLoginBinding
import com.jw.login.fragment.FillInfoFra
import com.jw.login.fragment.LoginFra
import com.jw.login.fragment.RegisterFra
import com.sencent.mm.GoChatBindingActivity

/**
 * 创建时间：
 * 更新时间 2017/11/2 21:46
 * 版本：
 * 作者：Mr.jin
 * 描述：
 * 默认进入登陆页面
 * 如果存在本地账户，但是没有完成详细信息，则进入资料填写页面
 */

class LoginActivity : GoChatBindingActivity<ActivityLoginBinding>() {
    private var fm: FragmentManager? = null
    private var signInFra: LoginFra? = null
    private var signUpFra: RegisterFra? = null
    private var fillInfoFra: FillInfoFra? = null
    private var me: AccountInfo? = null

    override fun getLayoutId() = R.layout.activity_login

    override fun doConfig(arguments: Intent) {
        me = GoChatApplication.getAccountInfo()
        fm = supportFragmentManager
        if (me != null && me?.name == null)
            goFillInfoFra()
        else
            goLoginFra()
    }

    override fun onReceiveDataFromFragment(tag: String, name: String, data: Any?) {
        super.onReceiveDataFromFragment(tag, name, data)
        when (name) {
            "register" -> goFillInfoFra()
        }
    }

    //进入登陆页面
    fun goLoginFra() {
        if (signInFra == null) {
            signInFra = LoginFra()
        }
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.add(R.id.fl_login, signInFra, TAG_SIGN_IN)
        fragmentTransaction.addToBackStack(TAG_SIGN_IN)
        fragmentTransaction.commit()
    }

    //进入注册页面
    fun goRegisterFra() {
        if (signUpFra == null) {
            signUpFra = RegisterFra()
        }
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        fragmentTransaction.replace(R.id.fl_login, signUpFra, TAG_SIGN_UP)
        fragmentTransaction.addToBackStack(TAG_SIGN_UP)
        fragmentTransaction.commit()
    }

    //进入填写信息页面
    fun goFillInfoFra() {
        if (fillInfoFra == null) {
            fillInfoFra = FillInfoFra()
        }
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        fragmentTransaction.replace(R.id.fl_login, fillInfoFra, TAG_FILL_INFO)
        fragmentTransaction.addToBackStack(TAG_FILL_INFO)
        fragmentTransaction.commit()
    }

    companion object {
        private const val TAG_SIGN_UP = "sign_up"
        private const val TAG_SIGN_IN = "sign_IN"
        private const val TAG_FILL_INFO = "fill_info"
    }
}