package com.example.palindromeapp.ui.secondscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.palindromeapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // take user name from first fragment
        val userName = arguments?.let { SecondFragmentArgs.fromBundle(it).name }

        // username from thrird fragment
        parentFragmentManager.setFragmentResultListener(
            "userSelection",
            viewLifecycleOwner
        ) { _, bundle ->
            val selectedUserName = bundle.getString("selectedUserName")
            binding.tvChooseUser.text = selectedUserName
        }

        // set the user name to the text view
        binding.tvUserName.text = userName

        // go to thrid fragment
        binding.btnChooseUser.setOnClickListener {
            findNavController().navigate(
                SecondFragmentDirections.actionSecondFragmentToThirdFragment(
                    userName ?: ""
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}