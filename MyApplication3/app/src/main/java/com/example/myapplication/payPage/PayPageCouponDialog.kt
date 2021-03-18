package com.example.myapplication.payPage

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bookTable.TableFloorFragment
//쿠폰 정보 띄우는 다이얼로그
class PayPageCouponDialog(context: Context, val couponData:CouponData, val sikdangId:Int, val originaPrice:Int, var payPage:PayPage): Dialog(context) {
    public var couponMatchList = ArrayList<Boolean>()//각 쿠폰이 이 식당에 사용되는 쿠폰인지 확인, 어댑터에서 확인한다
    public var couponMatchNumList = ArrayList<Int>()//매치되는 쿠폰이 각각 몇번째인지 저장하는 ArrayList
    public var couponCBAL = ArrayList<CheckBox>() // 리스트에 나오는 체크박스의 ArrayList
    private var discountPrice = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paypage_coupondialog)
        val payPageCouponDialog = this

        initCouponMatchList()


        var couponListRV:RecyclerView = findViewById(R.id.couponListRV)
        var couponListRVAdapter = CouponListRVAdapter(context, couponData, payPageCouponDialog)
        couponListRV.adapter = couponListRVAdapter

        var couponListLM = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)//인원 버튼
        couponListRV.layoutManager=couponListLM
        couponListRV.setHasFixedSize(true)

        var completeButton: Button = findViewById(R.id.couponSelectButton)
        completeButton.setOnClickListener {
            calDiscount()
            payPage.setDiscountPrice(discountPrice)
            this.dismiss()

        }



    }

    public fun initCouponMatchList(){
        var i = 0
        //Log.d("확인 다이얼로그 쿠폰 개수 확인", couponData.couponList.size.toString())
        while (i<couponData.couponList.size){
            //Log.d("확인 다이얼로그 쿠폰 할당", i.toString()+"/"+couponData.couponList.size+" "+couponData.couponList[i].sikdangId)
            if (couponData.couponList[i].sikdangId == sikdangId){
                couponMatchList.add(true)
                couponMatchNumList.add(i)
            }
            else{
                couponMatchList.add(false)
            }
            i++
        }
    }

    //CouponListRVAdapter 클래스에서 사용
    //쿠폰 하나 체크할때마다 쿠폰리스트 돌며 체크되어있는 쿠폰의 중복정보 파악하여 중복 불가시 false 리턴
    //쿠폰 사용에 아무 문제 없으면 true 리턴
    public fun checkCouponList(pos:Int):Boolean{
        if (originaPrice < couponData.couponList[couponMatchNumList[pos]].minPrice){
            val myToast = Toast.makeText(context, "쿠폰 사용가능 최소금액 미충족 ", Toast.LENGTH_SHORT).show()
            return false
        }
        if (couponData.couponList[couponMatchNumList[pos]].overlap == 1){//1끼리만 중복 가능한 쿠폰
            var i = 0
            while (i < couponCBAL.size){
                if (i != pos){//이번에 체크한 체크박스는 검사 안한다
                    if (couponCBAL[i].isChecked){//체크박스 켜진 경우
                        if (couponData.couponList[couponMatchNumList[i]].overlap == 2){//중복 불가한 쿠폰 켜져있는 경우
                            val myToast = Toast.makeText(context, "쿠폰 중복 사용 불가", Toast.LENGTH_SHORT).show()
                            return false
                        }
                    }
                }
                i++
            }
            return true
        }
        if (couponData.couponList[couponMatchNumList[pos]].overlap == 2){//하나만 사용 가능한 쿠폰인 경우
            var i = 0
            while (i < couponCBAL.size){
                if (i != pos){//이번에 체크한 체크박스는 검사 안한다
                    if (couponCBAL[i].isChecked){//체크박스 켜진 경우
                        if (couponData.couponList[couponMatchNumList[i]].overlap == 1 || couponData.couponList[couponMatchNumList[i]].overlap == 2){//쿠폰 중복 여부가 1이나 2인 경우
                            val myToast = Toast.makeText(context, "쿠폰 중복 사용 불가", Toast.LENGTH_SHORT).show()
                            return false
                        }
                    }
                }
                i++
            }
            return true
        }
        if (couponData.couponList[couponMatchNumList[pos]].overlap == 3){//어떤 쿠폰이든 중복 가능할 경우
            return true
        }


        return false
    }



    //선택완료 버튼 눌렸을 때 호출
    //총 할인 가격 계산
    private fun calDiscount(){
        var i = 0
        var discount = 0
        while (i<couponCBAL.size){
            if (couponCBAL[i].isChecked){//체크박스 체크되어있는경우
                discount += couponData.couponList[couponMatchNumList[i]].discountPrice
            }
            i++
        }
        discountPrice = discount
    }





}