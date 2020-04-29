package sim.coder.theory

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_book_page.*

class BookPage : AppCompatActivity() {


    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_page)


        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-5329195808649014/8244552144"
        mInterstitialAd.loadAd(
            AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build())

        

        val pref= getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()
        var no =pref.getInt("pageno",0)
        var noo =pref.getInt("pagenoo",0)



        val getItem = intent.getStringExtra("pdfFileName")

        if (getItem == "نظرية الفستق الجزء الأول") {
            PdfViewer.fromAsset("theory1.pdf")
                .onPageChange { page, pageCount ->
                    editor.putInt("pageno",page)
                    editor.commit()
                }
                .defaultPage(no)
                .load()
        } else if(mInterstitialAd.isLoaded)
            mInterstitialAd.show()


        if (getItem == "نظرية الفستق الجزء الثاني") {
            PdfViewer.fromAsset("theory2.pdf")
                .onPageChange { page, pageCount ->
                    editor.putInt("pagenoo",page)
                    editor.commit()
                }
                .defaultPage(noo)
                .load()
        }
    }

}


