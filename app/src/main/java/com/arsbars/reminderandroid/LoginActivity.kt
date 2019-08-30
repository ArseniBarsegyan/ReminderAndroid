package com.arsbars.reminderandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import com.arsbars.reminderandroid.view.fragments.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val rootLayout = findViewById<ConstraintLayout>(R.id.loginActivityRootLayout)
        if (rootLayout.childCount == 0) {
            fragmentTransaction.replace(R.id.loginActivityRootLayout,
                    LoginFragment.newInstance())
        }
        fragmentTransaction.commit()
    }
}
