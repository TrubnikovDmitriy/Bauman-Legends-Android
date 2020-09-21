package dv.trubnikov.legends.feature_team.presentation.search

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dv.trubnikov.legends.core_models.team.TeamData
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.databinding.VhSearchTeamItemBinding
import dv.trubnikov.legends.utils.android.SillyDiffUtilsCallback
import dv.trubnikov.legends.utils.android.layoutInflater

class TeamSearchAdapter(
    private val teamClickListener: (Long) -> Unit
) : RecyclerView.Adapter<TeamSearchViewHolder>() {

    private var data: List<TeamData> = emptyList()

    fun setData(newData: List<TeamData>) {
        val diffResult = DiffUtil.calculateDiff(SillyDiffUtilsCallback(this.data, newData))
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamSearchViewHolder {
        val view = parent.context.layoutInflater().inflate(R.layout.vh_search_team_item, parent, false)
        val bind = VhSearchTeamItemBinding.bind(view)
        val holder = TeamSearchViewHolder(bind)

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                teamClickListener(data[position].teamId)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: TeamSearchViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class TeamSearchViewHolder(
    private val bind: VhSearchTeamItemBinding
) : RecyclerView.ViewHolder(bind.root) {

    fun bindData(team: TeamData) {
        bind.teamAvatar.setTeamName(team.teamId.toString())
        bind.teamName.text = team.teamName
        bind.teamSize.text = bind.root.resources.getQuantityString(R.plurals.team_size_format, team.size, team.size)
        bind.teamScore.text = bind.root.resources.getQuantityString(R.plurals.team_score_format, team.score, team.score)
    }
}
