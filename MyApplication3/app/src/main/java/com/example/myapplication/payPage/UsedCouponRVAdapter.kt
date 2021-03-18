package com.example.myapplication.payPage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class UsedCouponRVAdapter(var context: Context, val couponData:CouponData, val payPage: PayPage): RecyclerView.Adapter<UsedCouponRVAdapter.Holder>() {

    //var usedCouponNumAL:ArrayList<Int> = payPage.usedCouponNumAL
    //var


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.paypage_usedcouponline, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        //usedCouponNumAL = payPage.usedCouponNumAL
        Log.d("확인 RV어댑터 목록 갯수", payPage.usedCouponNumAL.size.toString())
        return payPage.usedCouponNumAL.size
    }

    override fun onBindViewHolder(holder:Holder, position: Int) {
        holder.bind(position)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView!!){
        public fun bind(pos:Int){
            //Log.d("확인 UsedCouponRVAdapter.holder.bind", pos.toString()+" "+payPage.usedCouponNumAL[pos].toString()+" "+couponData.couponList.size)
            var couponExplanationTV = itemView.findViewById<TextView>(R.id.UsedCouponExplanation)
            couponExplanationTV.setText(couponData.couponList[payPage.usedCouponNumAL[pos]].explanation)


            var usedCouponDiscountTV:TextView = itemView.findViewById(R.id.usedCouponDiscountTV)
            usedCouponDiscountTV.setText(couponData.couponList[payPage.usedCouponNumAL[pos]].discountPrice.toString() + "원")
            //usedCouponDiscountTV.setText("")            //Log.d("확인 UsedCouponRVAdapter.holder.bind", "끝")

        }
    }
}