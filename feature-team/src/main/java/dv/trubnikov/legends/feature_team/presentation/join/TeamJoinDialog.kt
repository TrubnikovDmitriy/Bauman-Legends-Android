package dv.trubnikov.legends.feature_team.presentation.join

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dv.trubnikov.legends.feature_team.R
import dv.trubnikov.legends.feature_team.databinding.TeamJoinDialogBinding

@AndroidEntryPoint
class TeamJoinDialog : DialogFragment() {

    private val viewModel: TeamJoinDialogViewModel by viewModels()
    private val args: TeamJoinDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.team_join_dialog, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bind = TeamJoinDialogBinding.bind(view)
        setUpClickListeners(bind)
        observeData(bind)

        return view
    }

    private fun observeData(bind: TeamJoinDialogBinding) {
        viewModel.getErrors().observe(viewLifecycleOwner) { errorMessage ->
            bind.inviteCode.error = errorMessage
        }
        viewModel.getTeamData().observe(viewLifecycleOwner) { team ->
            val action = TeamJoinDialogDirections.actionTeamJoinDialogToTeamProfileFragment(team)
            findNavController().navigate(action)
        }
    }

    private fun setUpClickListeners(bind: TeamJoinDialogBinding) {
        bind.cancelButton.setOnClickListener {
            dismiss()
        }
        bind.createButton.setOnClickListener {
            bind.inviteCode.error = null
            val inviteCode = bind.inviteCode.editText?.text?.toString() ?: return@setOnClickListener
            viewModel.joinToTeam(args.teamId, inviteCode)
        }
    }
}