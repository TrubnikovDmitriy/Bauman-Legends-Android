package dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money

import android.content.ClipData
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.databinding.VhTeamInfoBinding
import dv.trubnikov.legends.feature_team.databinding.VhTeamMoneyBinding
import dv.trubnikov.legends.feature_team.presentation.profile.recyclers.money.TeamMoneyViewHolderType.*
import dv.trubnikov.legends.utils.android.clipboardManager
import dv.trubnikov.legends.utils.android.layoutInflater


enum class TeamMoneyViewHolderType {
    MONEY,
    CLICKABLE,
    INFORMATION
}

sealed class TeamMoneyViewHolder(itemView: View) : ViewHolder(itemView) {

    companion object {
        fun createViewHolder(
            container: ViewGroup,
            type: TeamMoneyViewHolderType
        ): TeamMoneyViewHolder {
            return when(type) {
                MONEY -> MoneyViewHolder(container)
                CLICKABLE -> CodeViewHolder(container)
                INFORMATION -> InfoViewHolder(container)
            }
        }
    }

    abstract fun bindTeamData(teamData: TeamMoneyRecyclerData)
    abstract fun setToggleClickListener(action: () -> Unit)
}

private class InfoViewHolder(
    private val bind: VhTeamInfoBinding
) : TeamMoneyViewHolder(bind.root) {

    constructor(root: ViewGroup) : this(VhTeamInfoBinding.bind(
        root.context.layoutInflater().inflate(R.layout.vh_team_info, root, false)
    ))

    override fun bindTeamData(teamData: TeamMoneyRecyclerData) {
        bind.root.text = itemView.context.getString(teamData.type.valueDescription)
    }

    override fun setToggleClickListener(action: () -> Unit) {
        bind.root.setOnClickListener {
            bind.root.setOnClickListener(null)
            action()
        }
    }
}

private open class MoneyViewHolder(
    protected val bind: VhTeamMoneyBinding
) : TeamMoneyViewHolder(bind.root) {

    constructor(root: ViewGroup) : this(VhTeamMoneyBinding.bind(
        root.context.layoutInflater().inflate(R.layout.vh_team_money, root, false)
    ))

    override fun bindTeamData(teamData: TeamMoneyRecyclerData) {
        bind.secondaryText.text = teamData.value
        bind.primaryText.setText(teamData.type.valueName)
        bind.icon.setImageResource(teamData.type.valueIcon)
    }

    final override fun setToggleClickListener(action: () -> Unit) {
        bind.info.setOnClickListener {
            bind.info.setOnClickListener(null)
            action()
        }
    }
}

private class CodeViewHolder(
    container: ViewGroup
) : MoneyViewHolder(container) {

    override fun bindTeamData(teamData: TeamMoneyRecyclerData) {
        super.bindTeamData(teamData)
        setUpClickListener(bind.root.context, teamData.value)
    }

    private fun setUpClickListener(context: Context, inviteCode: String?) {
        TypedValue().also { outValue ->
            context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
            itemView.foreground = ContextCompat.getDrawable(context, outValue.resourceId)
        }

        itemView.setOnClickListener {
            val clipData = ClipData.newPlainText("invite code", inviteCode)
            it.context.clipboardManager().setPrimaryClip(clipData)
            Toast.makeText(it.context, R.string.invite_code_copied, Toast.LENGTH_LONG).show()
        }
    }
}
