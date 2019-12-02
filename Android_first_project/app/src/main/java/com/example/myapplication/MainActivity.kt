package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var textView1: TextView
    //var dbHandler = DBHelper(this, null)
    //var users = mutableListOf<User>(User("aa","bb", null),User("cc","dd", null),User("aa","bb", null))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // R - klasa statyczna przenosi identyfikatory
        // z pliku r(resource) bierze kategorie layout
        setContentView(R.layout.activity_main)
        hideKeyboard()
        var dbHandler = DBHelper(this, null)
        //txtView1.text = savedInstanceState?.getString(STRING_KEY)?:getString(R.string.txtView_wyswietl)
        updateUI()
        btnSave.setOnClickListener {
            Toast.makeText(this, "Zapisano", Toast.LENGTH_SHORT).show()
            var newUser = User(editTxtName.text.toString(), editTxtLastName.text.toString(), null)
            //users.add(newUser)
            dbHandler.addUser(newUser)


            updateUI()
        }
    }
    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)

        //for()
        //outState.putString(STRING_KEY, txtView1.text.toString())
    }
    companion object{
        var STRING_KEY = "user_key"
        fun getSelectedUser(id: Int){

        }
    }
    fun updateUI(){
        var dbHandler = DBHelper(this, null)
        users_recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false) //co to robi?
        val adapter = UserListAdapter(dbHandler.getAllUsers(), this)
        users_recycler_view.adapter = adapter
        hideKeyboard()
    }
    fun AppCompatActivity.hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}
class User(firstName: String, lastName: String, id: Int?){
    var id = id
    var firstName = firstName
    var lastName = lastName
    override fun toString(): String {
        return "$firstName $lastName"
    }
}
