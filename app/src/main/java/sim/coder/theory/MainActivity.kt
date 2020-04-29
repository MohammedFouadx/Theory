package sim.coder.theory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() , Adapter.OnItemLisetner {
    override fun OnClick(item: BooksData, position: Int) {
        val start = Intent(applicationContext, BookPage::class.java)
        start.putExtra("pdfFileName", item.Title)
        startActivity(start)    }

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
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


