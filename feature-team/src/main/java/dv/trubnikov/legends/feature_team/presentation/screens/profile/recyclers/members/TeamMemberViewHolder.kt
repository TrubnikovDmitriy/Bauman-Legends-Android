package dv.trubnikov.legends.feature_team.presentation.screens.profile.recyclers.members

import androidx.recyclerview.widget.RecyclerView
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.core_models.user.UserRole
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.databinding.VhMemberInfoBinding
import dv.trubnikov.legends.utils.android.hide
import dv.trubnikov.legends.utils.android.show

class TeamMemberViewHolder(private val bind: VhMemberInfoBinding) : RecyclerView.ViewHolder(bind.root) {
    fun bindData(user: UserData) {
        bind.primaryText.text = itemView.context.getString(R.string.member_full_name, user.firstName, user.lastName)
        bind.secondaryText.text = itemView.context.getString(R.string.member_login_name, user.login, user.studyGroup)
        if (user.role == UserRole.CAPTAIN) {
            bind.captainIcon.show()
        } else {
            bind.captainIcon.hide()
        }
    }
}
