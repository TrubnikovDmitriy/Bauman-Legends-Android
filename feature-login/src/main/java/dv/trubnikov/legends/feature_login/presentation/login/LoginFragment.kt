package dv.trubnikov.legends.feature_login.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dv.trubnikov.legends.feature_login.R
import dv.trubnikov.legends.feature_login.databinding.FragmentLoginBinding
import dv.trubnikov.legends.utils.android.hideKeyboard

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        observeData(binding)
        setUpClickListeners(binding)
        dispatchBackPressed()

        return binding.root
    }

    private fun setUpClickListeners(binding: FragmentLoginBinding) {
        binding.buttonSignIn.setOnClickListener {
            binding.root.hideKeyboard()
            val username = binding.login.text.toString()
            val password = binding.password.text.toString()
            viewModel.login(username, password)
        }

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_from_login_to_registration)
        }
    }

    private fun observeData(binding: FragmentLoginBinding) {
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

    private fun dispatchBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = requireActivity().finish()
            }
        )
    }
}
