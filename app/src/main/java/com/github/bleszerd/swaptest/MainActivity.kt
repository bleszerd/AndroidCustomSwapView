package com.github.bleszerd.swaptest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.bleszerd.swaptest.databinding.ActivityMainBinding

/**
SwapTest
10/08/2021 - 09:45
Created by bleszerd.
@author alive2k@programmer.net
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Set click on card over delete button
        binding.cardSwipe.cardClickListener = View.OnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        //Set delete button click
        binding.cardSwipe.deleteButtonListener = View.OnClickListener {
            Toast.makeText(this, "Deleting...", Toast.LENGTH_SHORT).show()
        }

        setContentView(binding.root)
    }
}