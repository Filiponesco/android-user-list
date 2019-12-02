package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.user_row.view.*

class UserListAdapter (var users: MutableList<User>, val context: Context): RecyclerView.Adapter<UserListAdapter.ViewHolder>(){

    //jest wykonywana, gdy nie ma jeszcze odpowiedniej ilości obiektów ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        //view.setOnClickListener{view.txtView_id.setTextColor(Color.RED)}
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return users.size
    }
    //wywołuje się zawsze (Recykling starych obiektów)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var user = users[position]
        //ustawia wartosc textBox'ow w user_row.xml
        holder.bind(user)
        //ustawienie nasłuchiwanie kliknięcia przycisków
        holder.click(context)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //zwraca id textBox'ów w user_row.xml wraz z jego zawartością
        private val userIDText = itemView.findViewById<TextView?>(R.id.txtView_id)
        private val userNameText = itemView.findViewById<TextView?>(R.id.txtView_firstName)
        private val userSurnameText = itemView.findViewById<TextView?>(R.id.txtView_lastName)
        private val btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
        private val btnDelete = itemView.findViewById<Button>(R.id.btnDelete)
        fun bind(user: User){
            userIDText?.text = user.id.toString()
            userNameText?.text = user.firstName
            userSurnameText?.text = user.lastName
        }
        fun click(context: Context){
            btnEdit.setOnClickListener{
                val intent = Intent(context, OneUserActivity::class.java).apply {
                    putExtra("id", userIDText?.text)
                }
                context.startActivity(intent)
            }
            btnDelete.setOnClickListener{
                val user = User(
                    userNameText?.text.toString(), userSurnameText?.text.toString(), Integer.parseInt(userIDText?.text.toString()))
                val db = DBHelper(context, null)
                db.delete(user)
                //po nacisnieciu delete recycler view musi się zaktualizować
                val mainActivity = context as MainActivity
                mainActivity.updateUI()
                //val adapter = UserListAdapter(db.getAllUsers(), context)
                //adapter.notifyDataSetChanged()
            }
        }
    }
}
