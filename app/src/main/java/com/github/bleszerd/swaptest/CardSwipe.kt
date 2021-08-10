package com.github.bleszerd.swaptest

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.view.doOnLayout
import com.github.bleszerd.swaptest.databinding.CardSwipeLayoutBinding

/**
SwapTest
10/08/2021 - 09:48
Created by bleszerd.
@author alive2k@programmer.net
 */
class CardSwipe @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding = CardSwipeLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var cardClickListener: OnClickListener
    lateinit var deleteButtonListener: OnClickListener

    init {
        setLayout(attrs)
        setListeners()
    }

    //Prepare component on mount
    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.CardSwipe
            )

            //Handle xml attributes here (before recycle) if you want

            attributes.recycle()
        }
    }

    //performClick is only called in one situation
    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        var startX = 0f
        var absoluteDragX = 0f
        var isDragged = false

        binding.deleteButton.doOnLayout {
            if (::deleteButtonListener.isInitialized)
                binding.deleteButton.setOnClickListener(deleteButtonListener)
        }

        binding.swipeCard.setOnTouchListener { view, event ->
            if (::cardClickListener.isInitialized)
                view.setOnClickListener(cardClickListener)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                }
                MotionEvent.ACTION_UP -> {
                    println(absoluteDragX)

                    if (absoluteDragX <= 30 && absoluteDragX >= -30)
                        view.performClick()

                    if (absoluteDragX <= -60 && !isDragged) {
                        view.animate()
                            .x(-275f)
                            .setDuration(150)
                            .setInterpolator(AccelerateDecelerateInterpolator())
                            .start()

                        isDragged = true
                    } else if (absoluteDragX > 40) {
                        view.animate()
                            .x(0f)
                            .setDuration(500)
                            .start()

                        isDragged = false
                    }

                    //Reset default values
                    startX = 0f
                    absoluteDragX = 0f
                }
                MotionEvent.ACTION_MOVE -> {
                    absoluteDragX = event.x - startX
                }
            }

            //This consumes the click event, it cannot be passed on
            true
        }
    }
}