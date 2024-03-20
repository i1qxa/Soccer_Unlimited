package com.soerrrunlimi.unlimifootb.scsfoa.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.soerrrunlimi.unlimifootb.scsfoa.R
import com.soerrrunlimi.unlimifootb.scsfoa.data.LEVEL
import com.soerrrunlimi.unlimifootb.scsfoa.data.PREFS_NAME
import com.soerrrunlimi.unlimifootb.scsfoa.databinding.ActivityAwardsBinding

class AwardsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAwardsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupBtnBackClickListener()
        checkLevel()
    }

    private fun checkLevel(){
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val level = prefs.getInt(LEVEL, 1)
        if (level == 2){
            binding.cupSecond.setImageResource(R.drawable.award_cup_complete)
        }
        if (level == 3){
            binding.cupSecond.setImageResource(R.drawable.award_cup_complete)
            binding.cupThird.setImageResource(R.drawable.award_cup_complete)
        }
    }

    private fun setupBtnBackClickListener(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}