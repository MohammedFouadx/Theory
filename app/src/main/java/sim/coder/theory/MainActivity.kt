package sim.coder.theory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*




const val GAME_LENGTH_MILLISECONDS = 3000L
const val AD_UNIT_ID = "ca-app-pub-5329195808649014/1688074937"

class MainActivity : AppCompatActivity() , Adapter.OnItemLisetner {

    private lateinit var mInterstitialAd: InterstitialAd
    private var mCountDownTimer: CountDownTimer? = null
    private var mGameIsInProgress = false
    private var mTimerMilliseconds = 5000L


    override fun OnClick(item: BooksData, position: Int) {
        val start = Intent(this, BookPage::class.java)
        start.putExtra("pdfFileName", item.Title)
        startActivity(start)    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) {}


        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("ABCDEF012345"))
                .build()
        )


        // Create the InterstitialAd and set it up.
        mInterstitialAd = InterstitialAd(this).apply {
            adUnitId = AD_UNIT_ID
            adListener = (
                    object : AdListener() {
                        override fun onAdLoaded() {
                            showInterstitial()
                            //Toast.makeText(this@MainActivity, "onAdLoaded()", Toast.LENGTH_SHORT).show()
                        }

                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            val error = "domain: ${loadAdError.domain}, code: ${loadAdError.code}, " +
                                    "message: ${loadAdError.message}"
                            Toast.makeText(
                                this@MainActivity,
                                "onAdFailedToLoad() with error $error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onAdClosed() {
                           startGame()
                            //showInterstitial()
                        }
                    }
                    )
        }

        // Create the "retry" button, which triggers an interstitial between game plays.
//        retry_button.visibility = View.INVISIBLE
//        retry_button.setOnClickListener { showInterstitial()
       //}

        // Kick off the first play of the "game."
        startGame()

        var recyclerView:RecyclerView=findViewById(R.id.recyclePdf)

        recyclerView.layoutManager= LinearLayoutManager(this,LinearLayout.VERTICAL,false)


        var book=ArrayList<BooksData>()

        book.add(BooksData("نظرية الفستق الجزء الأول"))
        book.add(BooksData("نظرية الفستق الجزء الثاني"))


        recyclerView.adapter=Adapter(book,this)


    }

    // Create the game timer, which counts down to the end of the level
    // and shows the "retry" button.
    private fun createTimer(milliseconds: Long) {
        mCountDownTimer?.cancel()

        mCountDownTimer = object : CountDownTimer(milliseconds, 50) {
            override fun onTick(millisUntilFinished: Long) {
                mTimerMilliseconds = millisUntilFinished
                //timer.text = "seconds remaining: ${ millisUntilFinished / 1000 + 1 }"
            }

            override fun onFinish() {
                mGameIsInProgress = false
//                timer.text = "done!"
//                retry_button.visibility = View.VISIBLE
            }
        }
    }

    // Show the ad if it's ready. Otherwise toast and restart the game.
    private fun showInterstitial() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            //Toast.makeText(this, "Ad wasn't loaded.", Toast.LENGTH_SHORT).show()
            startGame()
        }
    }

    // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
    private fun startGame() {
        if (!mInterstitialAd.isLoading && !mInterstitialAd.isLoaded) {
            // Create an ad request.
            val adRequest = AdRequest.Builder().build()

            mInterstitialAd.loadAd(adRequest)
        }

        //retry_button.visibility = View.INVISIBLE
        resumeGame(GAME_LENGTH_MILLISECONDS)
    }

    private fun resumeGame(milliseconds: Long) {
        // Create a new timer for the correct length and start it.
        mGameIsInProgress = true
        mTimerMilliseconds = milliseconds
        createTimer(milliseconds)
        mCountDownTimer?.start()
    }

    // Resume the game if it's in progress.
    public override fun onResume() {
        super.onResume()

        if (mGameIsInProgress) {
            resumeGame(mTimerMilliseconds)
        }
    }

    // Cancel the timer if the game is paused.
    public override fun onPause() {
        mCountDownTimer?.cancel()
        super.onPause()
    }
}
