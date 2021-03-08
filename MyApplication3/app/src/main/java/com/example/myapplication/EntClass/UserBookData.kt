package com.example.myapplication.EntClass

//데이터 베이스 접근 필요
//현재 이 유저가 이미 식당을 예약 한 경우 그 예약정보를 불러온다
class UserBookData(var userId:Int) {
    private var isBooked = true//이 유저가 이미 예약 했는가
    private var sikdangName:String=""//예약 했다면 그 식당의 이름
    private var bookTime:String = ""//예약 했다면 예약한 시간

    init{
        setData()
    }
    //여기서 예약 정보 집어넣는다
    private fun setData(){
        isBooked = false
        sikdangName = "임시 식당 이름"
        bookTime = "1730"
    }


    public fun getIsBooked():Boolean{
        return isBooked
    }
    public fun getSikdangName():String{
        return sikdangName
    }
    public fun getBookTime():String{
        return bookTime
    }
}