package com.example.myapplication.payPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bookMenu.DataMenuToPay
import com.example.myapplication.bookMenu.MenuListRVAdapter
import com.example.myapplication.bookMenu.MenuTableRVAdapter
import com.example.myapplication.bookTable.TableData
import com.example.myapplication.bookTime.BookData
import com.example.myapplication.payComplete.PayCompletePage

class PayPage: AppCompatActivity() {
    lateinit var dataMenuToPay:DataMenuToPay
    var price = 0
    var couponData = CouponData(1234)
    var checkBoxAL = ArrayList<CheckBox>()
    var usedCouponNumAL = ArrayList<Int>()
    private var discountPrice = 0
    private var totalPrice = 0

    lateinit var usedCouponRVAdapter: UsedCouponRVAdapter

    lateinit var totalPriceTV:TextView 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("확인 PayPage", "1")
        setContentView(R.layout.paypage)
        //Log.d("확인 PayPage", "2")

        var intent = getIntent()
        if (intent != null) {
            dataMenuToPay = intent.getSerializableExtra("dataMenuToPay") as DataMenuToPay
            price = intent.getIntExtra("price", 0)
            //Log.d("확인 PayPage", "price 확인"+price.toString())

        }
        else{
            Log.d("확인 PayPage", "intent 데이터 가져오기 오류")
        }
        totalPrice = price
        var payPageSikdangName:TextView = findViewById(R.id.payPageSikdangName)
        payPageSikdangName.setText(dataMenuToPay.sikdangName)

        var reqEditText:EditText = findViewById(R.id.reqEditText)
        reqEditText.setSingleLine(false)


        var payPageOriginalPrice:TextView = findViewById(R.id.payPageOriginalPrice)
        payPageOriginalPrice.setText(price.toString()+"원")




        //쿠폰 관련 부분
        var couponTV = findViewById<TextView>(R.id.couponTV)
        couponTV.setOnClickListener {
            showDialog()
        }

        var usedCouponRV: RecyclerView = findViewById(R.id.usedCouponRV)
        usedCouponRVAdapter = UsedCouponRVAdapter(this,  couponData, this)
        usedCouponRV.adapter = usedCouponRVAdapter

        var usedCouponLM = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)//인원 버튼
        usedCouponRV.layoutManager=usedCouponLM
        usedCouponRV.setHasFixedSize(true)

        totalPriceTV = findViewById(R.id.totalPriceTV)
        totalPriceTV.setText("총 가격 : "+totalPrice.toString()+"원")






        //결제방식 선택 체크박스
        checkBoxAL.add(findViewById(R.id.kakaoPayCB))
        checkBoxAL.add(findViewById(R.id.secondPayCB))

        //while 문으로 돌리면 마지감 i값이 들어가서 문제 생김 : 일일이 코드로 넣어줘야함
        //어차피 페이 종류 많이 안넣을테니 일일이 넣어도 상관 없음
        checkBoxAL[0].setOnClickListener {
            var j = 0
            while(j<checkBoxAL.size){
                if (0!=j){
                    checkBoxAL[j].isChecked = false
                }
                j++
            }
        }
        checkBoxAL[1].setOnClickListener {
            var j = 0
            while(j<checkBoxAL.size){
                if (1!=j){
                    checkBoxAL[j].isChecked = false
                }
                j++
            }
        }


        var payButton: Button = findViewById(R.id.payButton)
        payButton.setOnClickListener {
            var checkOne = -1
            var i = 0
            while(i<checkBoxAL.size){
                if (checkBoxAL[i].isChecked == true){
                    checkOne = i
                }
                i++
            }
            if (checkOne == -1){
                val myToast = Toast.makeText(this, "결제방식을 선택해주십시오.", Toast.LENGTH_SHORT).show()
            }
            else if(checkOne == 0){
                val myToast = Toast.makeText(this, "카카오페이", Toast.LENGTH_SHORT).show()
                var kakaoPay = KakaoPay()
                var isSuccess = kakaoPay.isPaySuccess()
                if (isSuccess == true){
                    //결제 성공
                    //결제 완료 정보 서버로 넘겨줘야한다
                    //Log.d("확인 PayPage PayCompletePage 호출", "1")
                    val intent= Intent(this, PayCompletePage::class.java)
                    //Log.d("확인 PayPage PayCompletePage 호출", "2")
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.putExtra("price", price)
                    //intent.putExtra("dataMenuToPay", dataMenuToPay)
                    startActivity(intent)

                }else{
                    //결제 실패
                }
            }else if(checkOne == 1){
                val myToast = Toast.makeText(this, "어쩌고페이", Toast.LENGTH_SHORT).show()
            }else{
                val myToast = Toast.makeText(this, "결제방식선택오류", Toast.LENGTH_SHORT).show()
            }

        }



    }
    /*

    public fun getUserId():Int{
        return 987321
    }*/

    public fun showDialog(){
        var customDialog = PayPageCouponDialog(this, couponData, dataMenuToPay.sikdangId, price, this)
        customDialog.show()
    }

    public fun setDiscountPrice(disPrice:Int){//다이얼로그 꺼졌을때 호출된다.
        discountPrice = disPrice
        setTotalPrice()
    }

    private fun setTotalPrice(){
        totalPrice = price - discountPrice
    }

    public fun setUsedCouponRV(usedCouponNumAL_:ArrayList<Int>){
        usedCouponNumAL = usedCouponNumAL_
        Log.d("확인 다이얼로그 닫고 쿠폰 갯수", usedCouponNumAL.size.toString())
        usedCouponRVAdapter.notifyDataSetChanged()
        totalPriceTV.setText("총 가격 : "+totalPrice.toString()+"원")
    }


}