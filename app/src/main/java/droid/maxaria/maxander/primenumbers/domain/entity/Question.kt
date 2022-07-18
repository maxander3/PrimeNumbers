package droid.maxaria.maxander.primenumbers.domain.entity

class Question (
    val sum:Int,
    val visibleNumber:Int,
    val options:List<Int>
){
    val rightAnswer get()= sum-visibleNumber
}