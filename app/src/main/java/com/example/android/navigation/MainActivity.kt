/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding
import android.view.WindowManager
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navController=this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this,navController)
        val snackbar = Snackbar.make(findViewById(R.id.myNavHostFragment), R.string.about_text, Snackbar.LENGTH_INDEFINITE)
        val snackbarView = snackbar.view
        val tv = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        tv.maxLines = 3
        tv.textAlignment=View.TEXT_ALIGNMENT_INHERIT
        snackbar.setAction("Dismiss",View.OnClickListener {  })
        snackbar.show()
    }
    /*private fun getScreenShot(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }*/


    override fun onSupportNavigateUp(): Boolean {
        val navController=this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

    var doubleBackToExitOnce:Boolean = false

    override fun onBackPressed() {
        if(doubleBackToExitOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitOnce=true
        Toast.makeText(this,"Press again to exit application",Toast.LENGTH_SHORT).show()

    }



}
