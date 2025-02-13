package com.example.palindromeapp.ui.firstscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.palindromeapp.databinding.FragmentFirstBinding
import java.util.Locale

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkBtn.setOnClickListener {
            val sentence = binding.palindromeEditText.text.toString()

            if (sentence.isBlank()) {
                AlertDialog.Builder(requireContext())
                    .setMessage("Please enter a sentence")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                val isPalindrome = isPalindrome(sentence)
                val result = if (isPalindrome) "is a palindrome" else "is not a palindrome"
                AlertDialog.Builder(requireContext())
                    .setMessage("\"$sentence\" $result")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Button Next
        binding.nextBtn.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            if (name.isNotEmpty()) {
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(name)
                findNavController().navigate(action)
            } else {
                AlertDialog.Builder(requireContext())
                    .setMessage("Please enter your name")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isPalindrome(sentence: String): Boolean {
        val cleanSentence =
            sentence.replace("[^A-Za-z0-9]".toRegex(), "").lowercase(Locale.getDefault())
        return cleanSentence == cleanSentence.reversed()
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

}