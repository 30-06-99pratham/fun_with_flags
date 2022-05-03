package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList : ArrayList<Question>? = null
    private var mSelectedOptionPosition : Int =0
    private var mUserName: String? = null
    private var mCorrectAns : Int = 0




    private var progressBar : ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestion : TextView? = null
    private var ivImage: ImageView? = null

    private var tv_option1 : TextView? = null
    private var tv_option2 : TextView? = null
    private var tv_option3 : TextView? = null
    private var tv_option4 : TextView? = null
    private var submit_btn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        mUserName = intent.getStringExtra(Constants.USER_NAME)

        tvQuestion = findViewById(R.id.tvQuestion)      //Question
        ivImage = findViewById(R.id.tv_image)           //Image
        progressBar = findViewById(R.id.progress_bar)   //Progressbar
        tvProgress = findViewById(R.id.tvProgress)      //Progress
        tv_option1 = findViewById(R.id.tv_option1)      //option1
        tv_option2 = findViewById(R.id.tv_option2)      //option2
        tv_option3 = findViewById(R.id.tv_option3)      //option3
        tv_option4 = findViewById(R.id.tv_option4)      //option4
        submit_btn = findViewById(R.id.tv_submit_btn)   //Submit Button
        mQuestionsList = Constants.getQuestions()
        setQuestion()
        tv_option1?.setOnClickListener(this)
        tv_option2?.setOnClickListener(this)
        tv_option3?.setOnClickListener(this)
        tv_option4?.setOnClickListener(this)
        submit_btn?.setOnClickListener(this)


    }

    private fun setQuestion() {

        val question: Question = mQuestionsList!![mCurrentPosition-1]
        defaultOptionsView()
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max} "
        tvQuestion?.text = question.questions
        ivImage?.setImageResource(question.image)
        tv_option1?.text = question.option1
        tv_option2?.text = question.option2
        tv_option3?.text = question.option3
        tv_option4?.text = question.option4

        if(mCurrentPosition == mQuestionsList!!.size){
            submit_btn?.text = "FINISH"
        }
        else{
            submit_btn?.text = "SUBMIT"
        }

    }
    private fun defaultOptionsView(){

        val options = ArrayList<TextView>()
        tv_option1?.let { options.add(0,it) }
        tv_option2?.let{options.add(1,it)}
        tv_option3?.let{options.add(2,it)}
        tv_option4?.let{options.add(3,it)}

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionsView(tv:TextView,selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor(("#363A43")))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,R.drawable.selected_option_border_bg
        )

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option1->{
                tv_option1?.let{
                    selectedOptionsView(it,1)
                }
            }
            R.id.tv_option2->{
                tv_option2?.let{
                    selectedOptionsView(it,2)
                }
            }
            R.id.tv_option3->{
                tv_option3?.let{
                    selectedOptionsView(it,3)
                }
            }
            R.id.tv_option4->{
                tv_option4?.let{
                    selectedOptionsView(it,4)
                }
            }
            R.id.tv_submit_btn->{
                if(mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }

                    else -> {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, mUserName)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAns)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        startActivity(intent)
                        finish()


                    }
                }
                }
                else{
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if(question!!.correctAns != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                    }
                    else{
                        mCorrectAns++
                    }
                    answerView(question.correctAns,R.drawable.correct_option_border_bg)
                    if(mCurrentPosition == mQuestionsList!!.size){
                        submit_btn?.text = "FINISH"
                    }
                    else{
                        submit_btn?.text = "NEXT QUESTION"
                    }
                    mSelectedOptionPosition=0

                }
            }

        }
    }
    private fun answerView(ans : Int,drawableView:Int){
        when(ans){
            1->{
                tv_option1?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )

            }
            2->{
                tv_option2?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3->{
                tv_option3?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4->{
                tv_option4?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }

}