package com.example.quizapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)




        val tvName : TextView = findViewById(R.id.tv_name)
        val tvScore : TextView = findViewById(R.id.tv_score)
        val tvBtnFinish :TextView = findViewById(R.id.btn_finish)

        tvName.text  = intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        val correctAns = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)

        tvScore.text = "Your Score is $correctAns/$totalQuestions"

        tvBtnFinish.setOnClickListener{

            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }





    }
}