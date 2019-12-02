package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_one_user.*

class OneUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_user)
        val userId: Int = intent.getStringExtra("id").toInt()
        val db = DBHelper(this, null)
        val user = db.getUser(userId)
        editText.setText(user?.firstName)
        editText2.setText(user?.lastName)

        btnUpdate.setOnClickListener{
            val newUser = User(editText.text.toString(), editText2.text.toString(), userId)
            db.update(newUser)
            //val intent = Intent(this,MainActivity::class.java)
            //startActivity(intent)
            finish()
        }
    }
}
