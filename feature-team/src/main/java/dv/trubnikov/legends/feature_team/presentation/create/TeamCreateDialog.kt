package dv.trubnikov.legends.feature_team.presentation.create

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.databinding.TeamCreateDialogBinding

@AndroidEntryPoint
class TeamCreateDialog : DialogFragment() {

    private val viewModel: TeamCreateDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.team_create_dialog, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bind = TeamCreateDialogBinding.bind(view)
        setUpClickListeners(bind)
        observeData(bind)

        return view
    }

    private fun observeData(bind: TeamCreateDialogBinding) {
        viewModel.getErrors().observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                bind.teamName.error = errorMessage
            }
        }
        viewModel.getTeamData().observe(viewLifecycleOwner) { team ->
            if (team != null) {
                val action = TeamCreateDialogDirections.actionTeamCreateDialogToTeamProfileFragment(team)
                findNavController().navigate(action)
            }
        }
    }

    private fun setUpClickListeners(bind: TeamCreateDialogBinding) {
        bind.cancelButton.setOnClickListener {
            dismiss()
        }
        bind.createButton.setOnClickListener {
            bind.teamName.error = null
            viewModel.createTeam(bind.teamName.editText?.text?.toString() ?: return@setOnClickListener)
        }
    }
}