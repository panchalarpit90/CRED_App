package com.example.cred.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cred.R
import com.example.cred.databinding.FragmentHomeBinding
import com.example.cred.model.PlanItem
import com.example.cred.utils.OnPlanItemSelectedListener
import com.example.cred.utils.PriceAdapter
import me.tankery.lib.circularseekbar.CircularSeekBar
import java.text.NumberFormat
import java.util.Locale
import java.util.Stack

class HomeFragment : Fragment(), OnPlanItemSelectedListener {
    private lateinit var adapter: PriceAdapter
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var binding: FragmentHomeBinding
    private val cardStack = Stack<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        cardStack.push(0)

        binding.emiBtn.setOnClickListener {
            showSecondCard()
        }
        binding.bankBtn.setOnClickListener {
            showThirdCard()
        }

        binding.firstCard.setOnClickListener {
            showFirstCard()
        }
        binding.firstDownButton.setOnClickListener {
            showFirstCard()

        }
        binding.secondCard.setOnClickListener {
            showSecondCard()
        }
        binding.secDownButton.setOnClickListener {
            showSecondCard()

        }
        binding.cutBtn.setOnClickListener {
            showFirstCard()

        }
        binding.helpBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Help Button is Clicked", Toast.LENGTH_SHORT).show()
        }
        binding.kycBtn.setOnClickListener {
            Toast.makeText(requireContext(), "KYC Button is Clicked", Toast.LENGTH_SHORT).show()
        }
        binding.createBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Create Account Button is Clicked", Toast.LENGTH_SHORT)
                .show()

        }
        binding.changeBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Change Account Button is Clicked", Toast.LENGTH_SHORT)
                .show()

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    handleBackPress()
                }
            })
        binding.seekBar.max = 487891 - 500F
        binding.seekBar.progress = 150000 - 500F
        updateScoreText(binding.seekBar.progress.toInt())
        binding.seekBar.setOnSeekBarChangeListener(object :
            CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                updateScoreText(progress.toInt())
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {}
            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {}
        })
        viewModel.properties.observe(viewLifecycleOwner) { properties ->
            properties?.let { props ->
                val items = props.items.get(1).open_state.body.items
                if (items != null) {
                    setupPriceRecyclerView(items)
                }
            }
        }
        return binding.root
    }

    private fun setupPriceRecyclerView(prices: List<PlanItem>) {
        adapter = PriceAdapter(requireContext(), this)
        binding.priceRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@HomeFragment.adapter
        }
        adapter.submitList(prices)
    }

    private fun updateScoreText(progress: Int) {
        val formattedScore = formatCurrency(progress + 500)
        binding.scoreCollapseText.text = formattedScore
        binding.scoreText.text = formattedScore
    }

    private fun formatCurrency(value: Int): String {
        val locale = Locale("en", "IN")
        val currencyFormat = NumberFormat.getCurrencyInstance(locale)
        currencyFormat.minimumFractionDigits = 0
        currencyFormat.maximumFractionDigits = 0
        return currencyFormat.format(value).replace("₹", "₹").trim()
    }

    private fun showFirstCard() {
        binding.firstCard.findViewById<View>(R.id.expanded_content)?.visibility = View.VISIBLE
        binding.firstCard.findViewById<View>(R.id.collapsed_content)?.visibility = View.GONE
        binding.secondCard.visibility = View.GONE
        binding.thirdCard.visibility = View.GONE
        updateStack(0)
    }

    private fun showSecondCard() {
        binding.firstCard.findViewById<View>(R.id.expanded_content)?.visibility = View.GONE
        binding.firstCard.findViewById<View>(R.id.collapsed_content)?.visibility = View.VISIBLE
        binding.secondCard.visibility = View.VISIBLE
        binding.secondCard.findViewById<View>(R.id.expanded_second_content)?.visibility =
            View.VISIBLE
        binding.secondCard.findViewById<View>(R.id.collapsed_second_content)?.visibility = View.GONE
        binding.thirdCard.visibility = View.GONE
        updateStack(1)
    }

    private fun showThirdCard() {
        binding.secondCard.findViewById<View>(R.id.expanded_second_content)?.visibility = View.GONE
        binding.secondCard.findViewById<View>(R.id.collapsed_second_content)?.visibility =
            View.VISIBLE
        binding.thirdCard.visibility = View.VISIBLE
        binding.thirdCard.findViewById<View>(R.id.expanded_third_content)?.visibility = View.VISIBLE
        updateStack(2)
    }

    private fun updateStack(cardNumber: Int) {
        if (cardStack.contains(cardNumber)) {
            while (cardStack.peek() != cardNumber) {
                cardStack.pop()
            }
        } else {
            cardStack.push(cardNumber)
        }
    }

    private fun handleBackPress() {
        if (cardStack.size == 1) {
            requireActivity().finish()
        } else {
            cardStack.pop()
            when (cardStack.peek()) {
                0 -> showFirstCard()
                1 -> showSecondCard()
                2 -> showThirdCard()
            }
        }
    }

    override fun onItemSelected(emi: String, duration: String) {
        binding.amountText.text = emi
        binding.durationText.text = duration

    }
}
