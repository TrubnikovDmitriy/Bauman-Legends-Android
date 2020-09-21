package dv.trubnikov.legends.feature_login.presentation.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dv.trubnikov.legends.feature_login.R
import dv.trubnikov.legends.feature_login.databinding.FragmentRegistrationBinding
import dv.trubnikov.legends.utils.android.hideKeyboard
import dv.trubnikov.legends.utils.domain.appstate.AppState
import dv.trubnikov.legends.utils.domain.appstate.AppStateCenter
import dv.trubnikov.legends.utils.domain.appstate.AppStateResolver
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    @Inject lateinit var stateCenter: AppStateCenter
    @Inject lateinit var stateResolver: AppStateResolver
    private val viewModel: RegistrationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateCenter.getEvents().observe(this) { event -> when {
            event has AppState.NEED_NETWORK -> stateResolver.navigationForState(AppState.NEED_NETWORK)
        }}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        observeData(binding)
        setUpClickListeners(binding)

        return binding.root
    }

    private fun setUpClickListeners(binding: FragmentRegistrationBinding) {
        binding.root.hideKeyboard()
        binding.buttonSignUp.setOnClickListener {
            viewModel.signUp(
                login = binding.login.text.toString(),
                password = binding.password.text.toString(),
                firstName = binding.firstName.text.toString(),
                lastName = binding.lastName.text.toString(),
                group = binding.studyGroup.text.toString(),
                vkUri = binding.vkRef.text.toString()
            )
        }
    }

    private fun observeData(binding: FragmentRegistrationBinding) {
        viewModel.isFinished().observe(viewLifecycleOwner) { isFinished ->
            if (isFinished) {
                findNavController().popBackStack(R.id.login_nav_graph, true)
            }
        }

        viewModel.getErrors().observe(viewLifecycleOwner) { errorText ->
            if (errorText == null) {
                binding.errorText.visibility = View.GONE
            } else {
                binding.errorText.text = errorText
                binding.errorText.visibility = View.VISIBLE
            }
        }
    }
}