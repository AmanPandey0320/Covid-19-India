package com.example.covid19india

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    var mAuth:FirebaseAuth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_progress_bar.visibility= View.INVISIBLE
        regn_view_btn.setOnClickListener(){
            var i:Intent= Intent(this,register::class.java)
            startActivity(i)
            finish()
        }
        reset_btn.setOnClickListener(){
            var e:String=email_txt.text.toString();
            if(e.isEmpty()){
                email_txt.error="Required"
                email_txt.requestFocus()
            } else{
                login_progress_bar.visibility=View.VISIBLE
                email_txt.isClickable=false
                password_txt.isClickable=false
                regn_view_btn.isClickable=false
                reset_btn.isClickable=false
                login_btn.isClickable=false
                mAuth.sendPasswordResetEmail(e).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(baseContext,"Please check your inbox.",Toast.LENGTH_SHORT).show()
                        login_progress_bar.visibility=View.INVISIBLE
                        email_txt.isClickable=true
                        password_txt.isClickable=true
                        regn_view_btn.isClickable=true
                        reset_btn.isClickable=true
                        login_btn.isClickable=true
                    }else{
                        Toast.makeText(baseContext,task.exception.toString(),Toast.LENGTH_SHORT).show()
                        login_progress_bar.visibility=View.INVISIBLE
                        email_txt.isClickable=true
                        password_txt.isClickable=true
                        regn_view_btn.isClickable=true
                        reset_btn.isClickable=true
                        login_btn.isClickable=true
                    }
                }
            }
        }
        login_btn.setOnClickListener(){
            var e:String= email_txt.text.toString()
            var p:String= password_txt.text.toString()
            if(e.isEmpty()){
                email_txt.error="Required!"
                email_txt.requestFocus()
            }
            if(p.isEmpty()){
                password_txt.error="Required"
                password_txt.requestFocus()
            }
            if(!(p.isEmpty()||e.isEmpty())){
                login_progress_bar.visibility=View.VISIBLE
                email_txt.isClickable=false
                password_txt.isClickable=false
                regn_view_btn.isClickable=false
                reset_btn.isClickable=false
                login_btn.isClickable=false
                mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener { task ->
                    if(!task.isSuccessful){
                        Toast.makeText(baseContext,task.exception.toString(),Toast.LENGTH_SHORT).show();
                        login_progress_bar.visibility=View.INVISIBLE
                        email_txt.isClickable=true
                        password_txt.isClickable=true
                        regn_view_btn.isClickable=true
                        reset_btn.isClickable=true
                        login_btn.isClickable=true
                    } else{
                        if(mAuth.currentUser?.isEmailVerified!!){
                            var i: Intent=Intent(this,tracker::class.java)
                            startActivity(i)
                            finish()
                        }else{
                            Toast.makeText(baseContext,"Please verify your e-mail.",Toast.LENGTH_SHORT).show()
                            login_progress_bar.visibility=View.VISIBLE
                            email_txt.isClickable=false
                            password_txt.isClickable=false
                            regn_view_btn.isClickable=false
                            reset_btn.isClickable=false
                            login_btn.isClickable=false
                        }
                    }
                }
            }
        }
    }
}