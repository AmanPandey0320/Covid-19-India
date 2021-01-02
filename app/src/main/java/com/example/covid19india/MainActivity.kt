package com.example.covid19india

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.os.postDelayed
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var mAuth:FirebaseAuth=FirebaseAuth.getInstance()
    var handler:Handler= Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       handler.postDelayed({
           if(mAuth.currentUser==null){
               var i:Intent=Intent(this,login::class.java)
               startActivity(i)
               finish()
           }else{
               if(mAuth.currentUser!!.isEmailVerified){
                   var i:Intent= Intent(this,tracker::class.java)
                   startActivity(i)
                   finish()
               }else{
                   Toast.makeText(baseContext,"Please verify your email!",Toast.LENGTH_SHORT).show()
               }
           }
       },3000)
    }


}