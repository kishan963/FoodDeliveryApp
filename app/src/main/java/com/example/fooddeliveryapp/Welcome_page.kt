package com.example.fooddeliveryapp

import Activity.Login_page
import Fragment.*
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.fooddeliveryapp.R
import com.google.android.material.navigation.NavigationView
import kotlin.system.exitProcess

lateinit var sharedPreferences: SharedPreferences
lateinit var drawerLayout: DrawerLayout
@SuppressLint("StaticFieldLeak")
lateinit var text: TextView
lateinit var coordinatorLayout: CoordinatorLayout
@SuppressLint("StaticFieldLeak")
lateinit var toolbar: androidx.appcompat.widget.Toolbar
@SuppressLint("StaticFieldLeak")
lateinit var frameLayout: FrameLayout
lateinit var navigationView: NavigationView
var previousMenuItem : MenuItem? = null

class Welcome_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)
        sharedPreferences = getSharedPreferences("MyPreferences" , Context.MODE_PRIVATE )
        val convertView = LayoutInflater.from(this).inflate(R.layout.drawer_header, null)
        drawerLayout=findViewById(R.id.drawable_layout)
        text = convertView.findViewById(R.id.textView_drawer)
        text.setText(sharedPreferences.getString("name","foodPe")).toString()

        coordinatorLayout=findViewById(R.id.coordinator_layout)
        toolbar=findViewById(R.id.toolbar_layout)
        frameLayout=findViewById(R.id.frame_layout)
        navigationView=findViewById(R.id.navigation_layout)

        navigationView.addHeaderView(convertView)
        setUpToolbar()

        val actionBarDrawerToggle= ActionBarDrawerToggle(this@Welcome_page, drawerLayout,R.string.open_drawer,R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        opendashboard()
        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null)
                previousMenuItem?.isChecked=false

            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId)
            {
                R.id.dashboard ->{

                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        DashBoardFragement() ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "All Restaurant"
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        ProfileFragement() ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "My Profile"
                }
                R.id.favourites ->{
                    supportActionBar?.title = "Favourite Restaurant"
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        FavouriteFragement() ).commit()
                    drawerLayout.closeDrawers()

                }
                R.id.faq ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        Faqs() ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "FAQs"
                }
                R.id.orderHistory ->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        OrderHistory() ).commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "My Previous Order"
                }
                R.id.logOut ->{

                    sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
                    drawerLayout.closeDrawers()
                    startActivity(Intent(this, Login_page::class.java))
                    finish()
                }
            }

            return@setNavigationItemSelectedListener true
        }

    }

    fun  setUpToolbar(){
           setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

      }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home)
            drawerLayout.openDrawer(GravityCompat.START)

        return super.onOptionsItemSelected(item)
    }
    fun opendashboard()
    {
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, DashBoardFragement() ).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "All Restaurant"

        navigationView.setCheckedItem(R.id.dashboard)

    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame_layout)

        when(frag)
        {
            !is DashBoardFragement -> opendashboard()
            else  ->  super.onBackPressed()
        }


    }

}