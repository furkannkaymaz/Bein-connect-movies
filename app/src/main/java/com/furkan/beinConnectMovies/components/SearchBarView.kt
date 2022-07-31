package com.furkan.beinConnectMovies.components

import android.content.Context

import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import com.furkan.beinConnectMovies.base.BaseLinearLayout
import com.furkan.beinConnectMovies.databinding.MainViewSearchBarBinding


class SearchBarView(context: Context, attrs: AttributeSet? = null) :
    BaseLinearLayout<MainViewSearchBarBinding>(context, attrs) {


    override fun createView(inflater: LayoutInflater): MainViewSearchBarBinding {
        return MainViewSearchBarBinding.inflate(inflater, this, true)
    }

    override fun viewCreated(attrs: AttributeSet?) {

    }

    fun getEditText() : EditText{
        return binding.editTextSearch
    }

}