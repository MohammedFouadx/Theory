package sim.coder.theory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() , Adapter.OnItemLisetner {
    override fun OnClick(item: BooksData, position: Int) {
        val start = Intent(applicationContext, BookPage::class.java)
        start.putExtra("pdfFileName", item.Title)
        startActivity(start)    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)








        var recyclerView:RecyclerView=findViewById(R.id.recyclePdf)

        recyclerView.layoutManager= LinearLayoutManager(this,LinearLayout.VERTICAL,false)


        var book=ArrayList<BooksData>()

        book.add(BooksData("نظرية الفستق الجزء الأول"))
        book.add(BooksData("نظرية الفستق الجزء الثاني"))


        recyclerView.adapter=Adapter(book,this)




    }



}


