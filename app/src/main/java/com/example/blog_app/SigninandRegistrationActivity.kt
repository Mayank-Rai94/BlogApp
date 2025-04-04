package com.example.blog_app

import Register.WelcomeActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blog_app.Model.UserData
import com.example.blog_app.databinding.ActivitySigninandRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SigninandRegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var binding : ActivitySigninandRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninandRegistrationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Initialize firebase Authentication
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()



        //For Visibility of fields
        val action = intent.getStringExtra("action")
        if(action == "login"){
            binding.loginEmail.visibility = View.VISIBLE
            binding.loginPassword.visibility = View.VISIBLE
            binding.loginBtn.visibility = View.VISIBLE

            binding.registerName.visibility = View.GONE
            binding.registerEmail.visibility= View.GONE
            binding.registerPswd.visibility = View.GONE
            binding.cardView.visibility = View.GONE

            binding.registerBtn.isEnabled = false
            binding.registerBtn.alpha = 0.5f

            binding.registerNewHere.isEnabled = false
            binding.registerNewHere.alpha = 0.5f

            binding.loginBtn.setOnClickListener {

                val loginEmail = binding.loginEmail.text.toString()
                val loginPassword = binding.loginPassword.text.toString()

                if(loginEmail.isEmpty() || loginPassword.isEmpty())
                {
                    Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
                }else{
                    auth.signInWithEmailAndPassword(loginEmail,loginPassword)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Login Successfull 😊", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                            }else{
                                Toast.makeText(this, "Login failed please enter correct details", Toast.LENGTH_SHORT).show()
                            }

                        }
                }
            }

        }else if(action == "register"){
            binding.loginBtn.isEnabled = false
            binding.loginBtn.alpha = 0.5f

            binding.registerBtn.setOnClickListener {
                //Get data from edit text field
                val registerName = binding.registerName.text.toString()
                val registerEmail = binding.registerEmail.text.toString()
                val registerPassword = binding.registerPswd.text.toString()

                if(registerName.isEmpty() || registerEmail.isEmpty() || registerPassword.isEmpty())
                {
                    Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
                }else{
                    auth.createUserWithEmailAndPassword(registerEmail,registerPassword)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                val user = auth.currentUser
                                auth.signOut()
                                user?.let {
                                    //Save user data in firebase real time database
                                    val userReference = database.getReference("users")
                                    val userId = user.uid
                                    val userData = UserData(registerName,registerEmail)
                                    userReference.child(userId).setValue(userData)

                                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                                    startActivity(Intent(this,WelcomeActivity::class.java))
                                    finish()

                                }
                            }else{

                                Toast.makeText(this, "Registration Faild", Toast.LENGTH_SHORT).show()

                            }
                        }
                }
            }
        }
    }
}