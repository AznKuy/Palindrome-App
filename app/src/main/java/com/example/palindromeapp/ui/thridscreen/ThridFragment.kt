package com.example.palindromeapp.ui.thridscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.palindromeapp.R
import com.example.palindromeapp.databinding.FragmentThridBinding

class ThridFragment : Fragment() {

    private var _binding: FragmentThridBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeUsers()
        userViewModel.fetchUsers()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter { user ->
            val selectedUserName = "${user.firstName} ${user.lastName}".trim()
            val resultBundle = Bundle().apply { putString("selectedUserName", selectedUserName) }
            parentFragmentManager.setFragmentResult("userSelection", resultBundle)
            findNavController().popBackStack()
        }

        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (userViewModel.isLoading.value != true && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                ) {
                    userViewModel.loadMoreUsers()
                }

            }
        })
    }

    private fun observeUsers() {
        binding.swipeRefresh.setOnRefreshListener {
            userViewModel.fetchUsers(isRefreshing = true)
        }

        userViewModel.users.observe(viewLifecycleOwner) { users ->
            userAdapter.setUsers(users)
            binding.swipeRefresh.isRefreshing = false

            if (users.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvUsers.visibility = View.VISIBLE
            }
        }


        userViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            isLoading = loading
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            if (!isLoading) binding.swipeRefresh.isRefreshing = false
        }

        userViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                binding.swipeRefresh.isRefreshing = false
                // Opsional: tampilkan pesan error
                Toast.makeText(requireContext(), "Error loading data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}