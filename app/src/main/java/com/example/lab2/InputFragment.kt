package com.example.lab2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class InputFragment : Fragment() {

    interface OnInputConfirmedListener {
        fun onInputConfirmed(resultText: String)
    }

    private var listener: OnInputConfirmedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnInputConfirmedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnInputConfirmedListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val inputField = view.findViewById<EditText>(R.id.etQuestion)
        val typeGroup = view.findViewById<RadioGroup>(R.id.rgType)
        val levelGroup = view.findViewById<RadioGroup>(R.id.rgDifficulty)
        val okButton = view.findViewById<Button>(R.id.btnOk)

        okButton.setOnClickListener {
            val inputText = inputField.text.toString().trim()
            val selectedTypeId = typeGroup.checkedRadioButtonId
            val selectedLevelId = levelGroup.checkedRadioButtonId

            if (inputText.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a question", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedTypeId == -1) {
                Toast.makeText(requireContext(), "Please select a question type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedLevelId == -1) {
                Toast.makeText(requireContext(), "Please select a difficulty level", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val type = view.findViewById<RadioButton>(selectedTypeId)?.text
            val level = view.findViewById<RadioButton>(selectedLevelId)?.text

            val result = "Question: $inputText\nType: $type\nDifficulty: $level"

            listener?.onInputConfirmed(result)
        }

        return view
    }
}
