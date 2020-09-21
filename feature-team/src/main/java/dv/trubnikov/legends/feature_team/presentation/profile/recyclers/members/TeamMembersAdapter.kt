package dv.trubnikov.legends.feature_team.presentation.profile.recyclers.members

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dv.trubnikov.legends.core_models.user.UserData
import dv.trubnikov.legends.feature_team.databinding.VhMemberInfoBinding
import dv.trubnikov.legends.utils.android.SillyDiffUtilsCallback
import dv.trubnikov.legends.utils.android.layoutInflater

class TeamMembersAdapter : RecyclerView.Adapter<TeamMemberViewHolder>() {

    private var data: List<UserData> = emptyList()

    fun setMembers(newData: List<UserData>) {
        val diffResult = DiffUtil.calculateDiff(SillyDiffUtilsCallback(data, newData))
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMemberViewHolder {
        val bind = VhMemberInfoBinding.inflate(parent.context.layoutInflater(), parent, false)
        return TeamMemberViewHolder(bind)
    }

    override fun onBindViewHolder(holder: TeamMemberViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int = data.size
}
