package dv.trubnikov.legends.core_ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import de.hdodenhof.circleimageview.CircleImageView

class TeamAvatarView @JvmOverloads constructor(
    context: Context,
    attrSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CircleImageView(context, attrSet, defStyleAttr) {

    private val textRect = Rect()
    private val textPaint = Paint()
    private var teamName: String? = null

    init {
        context.obtainStyledAttributes(attrSet, R.styleable.TeamAvatarView).apply {
            teamName = getString(R.styleable.TeamAvatarView_teamName)
            textPaint.color = getColor(R.styleable.TeamAvatarView_textColor, Color.WHITE)
            textPaint.textSize = getDimension(R.styleable.TeamAvatarView_textSize, -1f)
            textPaint.typeface = ResourcesCompat.getFont(context, R.font.legends_font)
            textPaint.isAntiAlias = true
        }.recycle()

        if (isInEditMode) teamName = "42"
    }

    fun setTeamName(name: String?) {
        teamName = name
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val teamName = teamName ?: return
        textPaint.getTextBounds(teamName, 0, teamName.length, textRect)
        val x = measuredWidth / 2f - textRect.width() / 2f - textRect.left
        val y = measuredHeight / 2f + textRect.height() / 2f

        canvas.drawText(teamName, x, y, textPaint)
    }
}
