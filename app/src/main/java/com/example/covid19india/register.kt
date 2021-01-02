package com.example.covid19india

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {


    private var auth: FirebaseAuth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        regn_progress_bar.visibility= View.INVISIBLE


        //registration process
        regn_btn.setOnClickListener(){
            val e:String=email_txt_regn.text.toString()
            val p:String=password_txt_regn.toString()
            if(e.isEmpty()){
                email_txt_regn.error="Required!"
                email_txt_regn.requestFocus()
            }
            if(p.isEmpty()){
                password_txt_regn.error="Required!"
                password_txt_regn.requestFocus()
            }
            if(!(e.isEmpty()||p.isEmpty())){
                regn_progress_bar.visibility=View.VISIBLE
                email_txt_regn.isClickable=false
                password_txt_regn.isClickable=false
                regn_btn.isClickable=false
                auth.createUserWithEmailAndPassword(e, p)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(baseContext,"Please verify your email.",Toast.LENGTH_LONG).show()
                                    } else {
                                        Toast.makeText(baseContext, task.exception?.message,Toast.LENGTH_SHORT).show()
                                    }
                                    var i:Intent=Intent(this,login::class.java)
                                    startActivity(i)
                                }
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext,task.exception?.message,Toast.LENGTH_SHORT).show()
                            regn_progress_bar.visibility=View.INVISIBLE
                            email_txt_regn.isClickable=true
                            password_txt_regn.isClickable=true
                            regn_btn.isClickable=true
                        }

                        // ...
                    }
            }
        }

    }
}




