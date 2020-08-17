package sim.coder.theory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , Adapter.OnItemLisetner {

    private lateinit var mInterstitialAd: InterstitialAd



    override fun OnClick(item: BooksData, position: Int) {
        val start = Intent(this, BookPage::class.java)
        start.putExtra("pdfFileName", item.Title)
        startActivity(start)    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Banner ads

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)




        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-5329195808649014/8244552144"
        mInterstitialAd.loadAd(
            AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build())

        mInterstitialAd.adListener= object : AdListener(){

            override fun onAdLoaded() {
                mInterstitialAd.show()
                super.onAdLoaded()
            }
        }






        var recyclerView:RecyclerView=findViewById(R.id.recyclePdf)

        recyclerView.layoutManager= LinearLayoutManager(this,LinearLayout.VERTICAL,false)


        var book=ArrayList<BooksData>()

        book.add(BooksData("نظرية الفستق الجزء الأول"))
        book.add(BooksData("نظرية الفستق الجزء الثاني"))


        recyclerView.adapter=Adapter(book,this)




    }

}


