package dv.trubnikov.legends.core_ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.makeMeasureSpec
import androidx.annotation.ColorInt
import androidx.core.graphics.withTranslation
import kotlin.math.max
import kotlin.math.min

class SansaraProgressBar @JvmOverloads constructor(
    context: Context,
    attrSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrSet, defStyleAttr, defStyleRes) {

    companion object {
        private const val FPS = 60L

        private const val CIRCLE_COUNT_DEFAULT = 16
        private const val CIRCLE_SPEED_DEFAULT = 0.4f
        private const val CIRCLE_SWEEP_DEFAULT = 180f
        private const val CIRCLE_WIDTH_DEFAULT = 0.95f

        data class Attrs(
            val circleCount: Int,
            val circleSweep: Float,
            val circleWidthPercent: Float,
            val circleSpeedDegree: Float,
            val circleDirection: Boolean,
            @ColorInt val circleColor: Int
        )

        private fun obtainAttributes(ta: TypedArray, context: Context): Attrs {
            val width = ta.getFloat(R.styleable.SansaraProgressBar_circleWidth, CIRCLE_WIDTH_DEFAULT)
            val speed = ta.getFloat(R.styleable.SansaraProgressBar_circleSpeed, CIRCLE_SPEED_DEFAULT)
            val sweep = ta.getFloat(R.styleable.SansaraProgressBar_circleSweep, CIRCLE_SWEEP_DEFAULT)
            val count = ta.getInteger(R.styleable.SansaraProgressBar_circleCount, CIRCLE_COUNT_DEFAULT)
            val color = ta.getColor(R.styleable.SansaraProgressBar_circleColor, context.getColor(R.color.colorAccent))
            val dir = ta.getInt(R.styleable.SansaraProgressBar_circleDirection, 0) != 0
            return Attrs(
                circleColor = color,
                circleCount = count,
                circleDirection = dir,
                circleSpeedDegree = speed,
                circleWidthPercent = max(min(width, 1.0f), 0.0f),
                circleSweep = max(min(sweep, 360f), 0f)
            )
        }
    }

    private lateinit var tick: Runnable
    private var angle: Float = 0f

    private val paint: Paint
    val attrs: Attrs

    init {
        val typedArray = context.obtainStyledAttributes(
            attrSet, R.styleable.SansaraProgressBar, defStyleAttr, defStyleRes)
        attrs = obtainAttributes(typedArray, context)
        typedArray.recycle()

        paint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
        }

        tick = Runnable {
            angle += attrs.circleSpeedDegree
            invalidate()
            handler?.postDelayed(tick, 1_000 / FPS)
        }

        if (isInEditMode) {
            angle = 15f
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        handler?.post(tick)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler?.removeCallbacks(tick)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Making the progress square
        val minSide = min(measuredWidth, measuredHeight)
        val measureSpec = makeMeasureSpec(minSide, MeasureSpec.EXACTLY)
        super.onMeasure(measureSpec, measureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = attrs.circleColor
        paint.strokeWidth = measuredWidth / 2f / attrs.circleCount * attrs.circleWidthPercent
        repeat(attrs.circleCount + 1) {
            drawArc(canvas, it)
        }
    }

    private fun drawArc(canvas: Canvas, index: Int) {
        val halfSide = measuredWidth.toFloat() / 2
        val halfWidth = paint.strokeWidth / 2
        val radius = index * halfSide / attrs.circleCount

        val angle = if (attrs.circleDirection) {
            if (index % 2 == 0) angle else -angle
        } else {
            angle
        }

        canvas.withTranslation(halfSide - radius, halfSide - radius) {
            drawArc(
                halfWidth, halfWidth, 2 * radius - halfWidth, 2 * radius - halfWidth,
                angle * index, attrs.circleSweep, false, paint
            )
        }
    }
}
