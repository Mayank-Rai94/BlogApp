package com.example.blog_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blog_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binging : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binging.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binging.floatingAddArticleButton.setOnClickListener {
            startActivity(Intent(this,AddArticle::class.java))
        }
    }
}