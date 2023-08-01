package com.electronx.electroqr

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.electronx.electroqr.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linkReceiveTextView = binding.linkViewReceive
        // Validate that the developer has set the app code.
        //  validateAppCode()

        // [START get_deep_link]
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

                // [START_EXCLUDE]
                // Display deep link in the UI
                if (deepLink != null) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Found deep link!",
                        Snackbar.LENGTH_LONG,
                    ).show()

                    linkReceiveTextView.text = deepLink.toString()
                } else {
                    Log.d(TAG, "getDynamicLink: no link found")
                }
                // [END_EXCLUDE]
            }
            .addOnFailureListener(this) { e -> Log.w(TAG, "getDynamicLink:onFailure", e) }
        // [END get_deep_link]
    }
    // [END on_create]


    //расшарить диплинк
    /*private fun shareDeepLink(deepLink: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Firebase Deep Link")
        intent.putExtra(Intent.EXTRA_TEXT, deepLink)

        startActivity(intent)
    }
*/
    companion object {
        private const val TAG = "MainActivity"
    }
}