package controllers

import javax.inject._

import models.{Accounts, LoginData, Operations}
import play.api.Logger
import play.api.cache.CacheApi
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import services.MD5

@Singleton
class SignupController @Inject() (cache: CacheApi) extends Controller {

 val RegForm:Form[Accounts] = Form{

   mapping(
      "fname" -> nonEmptyText,
      "mname" -> text,
      "lname" -> nonEmptyText,
      "uname" -> nonEmptyText,
      "psw" -> nonEmptyText,
      "repsw" -> nonEmptyText,
      "mobile" -> nonEmptyText,
      "gender" ->nonEmptyText,
      "age" -> number(min = 18, max = 75),
      "hobby" -> nonEmptyText,
      "accountType" ->boolean,
      "isBlocked"-> boolean
   )(Accounts.apply)(Accounts.unapply)

 }

  def processForm = Action{ implicit request =>
    RegForm.bindFromRequest.fold(
      formErrors => {
        Redirect(routes.HomeController.index()).flashing("masterMsg"->"something Went Wrong,Try Later")
      },
      LoginData => {

        val CacheUser = cache.get[models.Accounts](LoginData.uname)
        if(CacheUser==None){
          if(LoginData.pswd==LoginData.repswd){
            if(LoginData.mobile.length==10){
              val encryptedUser = LoginData.copy(pswd = MD5.hash(LoginData.pswd) )
              cache.set(LoginData.uname,encryptedUser)
              Operations.addUser(LoginData.uname)
              Redirect(routes.LoginController.showProfile(LoginData.uname)).withSession("currentUser"->LoginData.uname).flashing("msg"->"Registration Successful")
            }
            else{
              Redirect(routes.HomeController.index()).flashing("mobile"->"Invalid Mobile Number")
            }

          }
          else{

            Redirect(routes.HomeController.index()).flashing("passMismatch"->"Pasword dosent Match")
          }

        }
        else{

          Redirect(routes.HomeController.index()).flashing("alreadyExist"->"Username Already Taken")
        }

      }
    )

  }


}
