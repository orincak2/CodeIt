package com.rel.codeit


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class   MainActivity : AppCompatActivity() {

    var butContinue: ImageView? = null
    var butNewFile: ImageView? = null
    var butOpenFile: ImageView? = null
    var sharedPreferences: SharedPreferences? = null
    var closeSavedFilesWindow: ImageView? = null
    var savedFilesLayouts: LinearLayout? = null
    var savedFilesLayoutsVisibility: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        InitViews()

        FillSavedFilles()

        InitButtonsLisenerst()
    }

    fun InitViews(){
        butContinue = findViewById(R.id.butContinueMain)
        butNewFile = findViewById(R.id.butNewFileMain)
        butOpenFile = findViewById(R.id.butOpenFileMain)
        closeSavedFilesWindow = findViewById(R.id.CloseSaved)
        savedFilesLayouts = findViewById(R.id.scrollLayoutMain)
        savedFilesLayoutsVisibility = findViewById(R.id.SavedFiles)
    }

    fun FillSavedFilles(){
        sharedPreferences = this.getSharedPreferences("Code", Context.MODE_PRIVATE)
        val value = sharedPreferences!!.getInt("SavedCount", 0)

        repeat(value) { index ->
            val linearLayout = LinearLayout(this)
            val layoutParamss = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            var paddingInDp = 20
            var scale = resources.displayMetrics.density
            var paddingInPx = (paddingInDp * scale + 0.5f).toInt()
            layoutParamss.setMargins(paddingInPx, 0, paddingInPx, 0)
            linearLayout.layoutParams = layoutParamss

            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.setBackgroundResource(R.drawable.spodok)

            val textView = TextView(this)
            textView.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            val nazov = sharedPreferences!!.getString((index+1).toString()+"nazov", "Unknown")
            textView.text = nazov.toString()
            linearLayout.addView(textView)

            val button = Button(this)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = "Open"
            button.setBackgroundResource(R.drawable.button_border)
            paddingInDp = 5
            paddingInPx = (paddingInDp * scale + 0.5f).toInt()
            layoutParams.setMargins(0, paddingInPx, 0, paddingInPx)
            button.layoutParams = layoutParams
            button.setOnClickListener {
                val intent = Intent(this, IDEActivity::class.java)
                val editor = sharedPreferences!!.edit()
                editor.putInt("mode", index+1)
                editor.apply()
                startActivity(intent)
            }
            linearLayout.addView(button)
            savedFilesLayouts!!.addView(linearLayout)
        }
    }

    fun InitButtonsLisenerst(){
        butContinue!!.setOnClickListener{
            val editor = sharedPreferences!!.edit()
            editor.putInt("mode", 0)
            editor.apply()
            val intent = Intent(this, IDEActivity::class.java)
            startActivity(intent)
        }

        butNewFile!!.setOnClickListener{
            val editor = sharedPreferences!!.edit()
            editor.putInt("mode", 9999)
            editor.apply()
            val intent = Intent(this, IDEActivity::class.java)
            startActivity(intent)
        }


        butOpenFile!!.setOnClickListener{
            savedFilesLayoutsVisibility!!.setVisibility(View.VISIBLE)
        }

        closeSavedFilesWindow!!.setOnClickListener{
            savedFilesLayoutsVisibility!!.setVisibility(View.INVISIBLE)
        }
    }
}































