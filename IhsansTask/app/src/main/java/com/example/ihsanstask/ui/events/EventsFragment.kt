package com.example.ihsanstask.ui.events

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ihsanstask.R
import com.example.ihsanstask.bases.BaseFragment
import com.example.ihsanstask.databinding.FragmentEventsBinding
import com.example.ihsanstask.utils.addDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>(), EventsAdapter.OnClickListener {

    private val viewModel: EventsViewModel by activityViewModels()

    private val eventsAdapter: EventsAdapter by lazy { EventsAdapter(this) }

    private val checkedEvents: MutableSet<Int> by lazy { mutableSetOf() }

    override fun getLayoutId(): Int = R.layout.fragment_events

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        collectEvents()
        binding.handleClicks()

    }

    private fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner

        binding.eventsRecycler.apply {
            adapter = eventsAdapter
            setEmptyView(binding.eventsEmpty)
            addDecoration(resources.getDimension(com.intuit.sdp.R.dimen._8sdp).toInt(), true)
        }
    }

    private fun collectEvents() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.eventsFlow.collect {
                eventsAdapter.submitList(it)
            }
        }
    }

    private fun FragmentEventsBinding.handleClicks() {
        back.setOnClickListener {
            findNavController().popBackStack()
        }

        delete.setOnClickListener {
            viewModel.deleteEvents(checkedEvents)
        }
    }

    override fun editEvent(id: Int) {
        findNavController().navigate(
            EventsFragmentDirections.actionEventsFragmentToAddEventFragment(
                "",
                "",
                id
            )
        )
    }

    override fun selectEvent(id: Int) {
        checkedEvents.add(id)
    }

    override fun removeEvent(id: Int) {
        checkedEvents.remove(id)
    }
}
