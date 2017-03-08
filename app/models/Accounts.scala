package models

import play.api.libs.json.{Json, OFormat}

case class Accounts(
                      fname:String,
                      mname:String,
                      lname:String,
                      uname:String,
                      pswd:String,
                      repswd:String,
                      mobile:String,
                      gender:String,
                      age:Int,
                      hobby:String,
                      isAdmin :Boolean,
                      isBlocked : Boolean
                   )

case class LoginData(
                      username:String,
                      password:String
                    )

/**
  * comanion object to send info back to client as Json
  */

object Accounts {

  implicit val accountJson: OFormat[Accounts] = Json.format[Accounts]

}
object LoginData {

  implicit val loginDataJson: OFormat[LoginData] = Json.format[LoginData]

}



