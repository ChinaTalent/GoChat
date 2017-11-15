package com.jw.gochat.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jw.gochat.ChatApplication;
import com.jw.gochat.R;
import com.jw.gochat.base.BaseActivity;
import com.jw.gochat.bean.Account;
import com.jw.gochat.db.AccountDao;
import com.jw.gochat.service.ChatCoreService;
import com.jw.gochat.utils.CacheUtils;
import com.jw.gochat.utils.ThemeUtils;
import com.jw.gochat.view.DialogLogout;
import com.jw.gochat.view.NormalTopBar;

import Lib.GoChatURL;
import butterknife.BindView;

/**
 * 创建时间：
 * 更新时间 2017/10/29 2017/10/29
 * 版本：
 * 作者：Mr.jin
 * 描述：设置页面，退出账号页面
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener,NormalTopBar.BackListener{

    @BindView(R.id.nt_setting)
    NormalTopBar ntSetting;
    @BindView(R.id.rl_set_ip)
    RelativeLayout rl;
    @BindView(R.id.tv_set_ip)
    TextView tvIp;

    @Override
    public void bindView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initView() {
        super.initView();
        tvIp.setText(GoChatURL.BASE_QQ_HOST);
        rl.setOnClickListener(this);
        ntSetting.setBackListener(this);
    }

    public void exit(View view){
        final DialogLogout dialogLogout=new DialogLogout(this);
        dialogLogout.show();
        dialogLogout.setClickLogoutListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = ChatApplication.getAccount();
                account.setCurrent(false);
                AccountDao dao=new AccountDao(SettingActivity.this);
                dao.updateAccount(account);
                ((ChatApplication)getApplication()).closeApplication();
                dialogLogout.dismiss();
                stopService(new Intent(SettingActivity.this, ChatCoreService.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_set_ip:
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                final AlertDialog dialog = builder.create();
                View view=View.inflate(this,R.layout.dialog_ip,null);
                final EditText etIp = (EditText) view.findViewById(R.id.et_ip_new);
                Button btnIpOk = (Button) view.findViewById(R.id.btn_ip_ok);
                Button btnIpCancel = (Button) view.findViewById(R.id.btn_ip_cancel);
                btnIpOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ipHost=etIp.getText().toString();
                        if(!TextUtils.isEmpty(ipHost)) {
                            dialog.dismiss();
                            CacheUtils.setCache("BASE_QQ_HOST", etIp.getText().toString(), SettingActivity.this);
                            tvIp.setText(GoChatURL.BASE_QQ_HOST);
                            ThemeUtils.show(SettingActivity.this,"应用将在2s后关闭,请重新启动以完成ip初始化");
                            new android.os.Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((ChatApplication)getApplication()).closeApplication();
                                }
                            },2000);
                        }
                        else
                            etIp.setError("ip地址不能为空");
                    }
                });
                btnIpCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setView(view);
                dialog.show();
                break;
        }
    }

    @Override
    public void back() {
        finish();
    }
}
