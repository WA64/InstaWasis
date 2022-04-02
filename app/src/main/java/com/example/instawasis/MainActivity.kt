package com.example.instawasis

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.instawasis.fragments.ComposeFragment
import com.example.instawasis.fragments.FeedFragment
import com.example.instawasis.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File

//Let user create a post by taking a photo with their camera
class MainActivity : AppCompatActivity() {

    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager



        findViewById<Button>(R.id.bLogout).setOnClickListener {

            ParseUser.logOut()
            val currentUser = ParseUser.getCurrentUser() // this will now be null
            val intent = Intent(this ,LoginActivity::class.java)
            startActivity(intent)
            finish()

        }



        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->

            var fragmentToShow: Fragment? = null
            when (item.itemId){

                R.id.action_home -> {
                    fragmentToShow = FeedFragment();
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment();
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
            }
            if (fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.bflContainer, fragmentToShow).commit()
            }
            true
        }
        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home

      //  queryPosts()
    }











    companion object{
        const val TAG = "MainActivity"
    }


}
