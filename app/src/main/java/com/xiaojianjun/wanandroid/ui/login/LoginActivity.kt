package com.xiaojianjun.wanandroid.ui.login

import android.view.inputmethod.EditorInfo.IME_ACTION_GO
import androidx.lifecycle.Observer
import com.xiaojianjun.wanandroid.R
import com.xiaojianjun.wanandroid.ui.base.BaseVmActivity
import com.xiaojianjun.wanandroid.ui.register.RegisterActivity
import com.xiaojianjun.wanandroid.util.core.ActivityManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVmActivity<LoginViewModel>() {

    override fun layoutRes() = R.layout.activity_login

    override fun viewModelClass() = LoginViewModel::class.java

    override fun initView() {
        ivClose.setOnClickListener {
            ActivityManager.finish(LoginActivity::class.java)
        }
        tvGoRegister.setOnClickListener {
            ActivityManager.start(RegisterActivity::class.java)
        }
        tietPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == IME_ACTION_GO) {
                btnLogin.performClick()
                true
            } else {
                false
            }
        }
        btnLogin.setOnClickListener {
            tilAccount.error = ""
            tilPassword.error = ""
            val account = tietAccount.text.toString()
            val password = tietPassword.text.toString()
            when {
                account.isEmpty() -> tilAccount.error = getString(R.string.account_can_not_be_empty)
                password.isEmpty() -> tilPassword.error =
                    getString(R.string.password_can_not_be_empty)
                else -> mViewModel.login(account, password)
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(this@LoginActivity, Observer {
                if (it) showProgressDialog(R.string.logging_in) else hideProgressDialog()
            })
            loginResult.observe(this@LoginActivity, Observer {
                if (it) {
                    ActivityManager.finish(LoginActivity::class.java)
                }
            })
        }
    }
}
