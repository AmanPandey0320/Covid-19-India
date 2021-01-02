package com.example.covid19india

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tracker.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class tracker : AppCompatActivity() {

    lateinit var stateAdapter: StateAdapter

    var mAuth:FirebaseAuth=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)

        list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header,list,false))

        //logout process

        logout_btn.setOnClickListener(){
            mAuth.signOut()
            var i : Intent = Intent(this,login::class.java)
            startActivity(i)
            finish()
        }
        // go to tips



        fetchResults()

    }


    private fun fetchResults() {
        GlobalScope.launch {
            val responce = withContext(Dispatchers.IO){Client.api.execute()}
             if(responce.isSuccessful){
                 val data =Gson().fromJson(responce.body?.string(),Response::class.java)
                 launch (Dispatchers.Main){
                     bindCombinedData(data.statewise[0])
                     bindStateWiseData(data.statewise.subList(1,data.statewise.size))
                 }
             }
        }

    }

    private fun bindStateWiseData(subList: List<StatewiseItem>) {
        stateAdapter= StateAdapter(subList)
        list.adapter=stateAdapter
    }

    private fun bindCombinedData(data: StatewiseItem) {
        val lastUpdatedtime = data.lastupdatedtime
        val simpleDateFormat =SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        last_update.text = "Last Updated ${getTimeAgo(simpleDateFormat.parse(lastUpdatedtime))}"
        confirmed_tv.text=data.confirmed
        active_tv.text=data.active
        deceased_tv.text=data.deaths
        recovered_tv.text=data.recovered

    }
    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }

}
