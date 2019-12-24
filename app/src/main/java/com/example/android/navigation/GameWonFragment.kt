/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding
import java.io.File
import java.util.*


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu, menu)
    }

    // Creating our Share Intent
    private fun getShareIntent() {
       /* val args = GameWonFragmentArgs.fromBundle(arguments!!)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        return shareIntent*/
        Handler().postDelayed({
            requireActivity().window.decorView.let { view ->
                val bitmap =
                        Bitmap.createBitmap(view.width * 2, view.height * 2, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap).apply { scale(2f, 2f) }
                view.draw(canvas)
                val file = File(requireContext().filesDir.absolutePath, "Screenshot${Date()}.png")
                val fileOutput = file.outputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutput)
                fileOutput.close()
                bitmap.recycle()
                val intent = ShareCompat.IntentBuilder.from(requireActivity())
                        .addStream(
                                FileProvider.getUriForFile(requireActivity(), "com.example.android.navigation.fileprovider", file)
                        )
                        .setType("image/*")
                        .createChooserIntent()
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                startActivity(intent)
            }
        }, 2000)
    }


    // Starting an Activity with our new Intent
   /* private fun shareSuccess() {
        startActivity(getShareIntent())
    }
*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.share -> getShareIntent()
        }
        return super.onOptionsItemSelected(item)

    }


}
