package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.databinding.FragmentPostContentBinding

class PostContentFragment : Fragment() {

    private val args by navArgs<PostContentFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPostContentBinding.inflate(layoutInflater, container, false).also { binding ->

        binding.edit.requestFocus()
        binding.edit.setText(args.initialContent)


        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: FragmentPostContentBinding) {
        val text = binding.edit.text.toString()
        if (!text.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KEY, text)
            setFragmentResult(REQUEST_KEY, resultBundle)
        }

        findNavController().navigateUp()
    }

    companion object {
        const val RESULT_KEY = "postNewContent"
        const val REQUEST_KEY = "requestKey"
    }
}
