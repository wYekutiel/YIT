package com.yekutiel.kutiweissyit.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yekutiel.kutiweissyit.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}