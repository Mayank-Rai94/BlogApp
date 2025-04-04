package com.example.blog_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blog_app.Model.BlogItemModel
import com.example.blog_app.Model.UserData
import com.example.blog_app.databinding.ActivityAddArticleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date

class AddArticle : AppCompatActivity() {
    private lateinit var  binding : ActivityAddArticleBinding
    private val databaseReference = FirebaseDatabase.getInstance().getReference("blogs")
    private val userReference = FirebaseDatabase.getInstance().getReference("users")
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.addBlogButton.setOnClickListener{
            val title : String = binding.blogTitle.editText?.text.toString().trim()
            val des : String = binding.blogdescription.editText?.text.toString().trim()
            if(title.isEmpty() || des.isEmpty())
            {
                Toast.makeText(this, "Please all the fields", Toast.LENGTH_SHORT).show()
            }

            // Get Current User
            val user : FirebaseUser?=auth.currentUser
            if(user != null)
            {
                val userId = user.uid
                val userName = user.displayName?:"Anonymous"

                //fetch user name from database

                userReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userData = snapshot.getValue(UserData::class.java)
                        if(userData !=null){
                            val userNameFromDb : String = userData.name

                            val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())


                            //create blog item variable

                            val blogItem = BlogItemModel(
                                title,
                                userNameFromDb,
                                currentDate,
                                des,
                                likeCount = 0
                            )

                            //Generate a unique key for blog post

                            val key = databaseReference.push().key
                            if(key!=null){
                                val blogReference = databaseReference.child(key)
                                blogReference.setValue(blogItem).addOnCompleteListener{
                                    if(it.isSuccessful){
                                        finish()
                                    }else{
                                        Toast.makeText(this@AddArticle, "Failed to add blog", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
    }
}