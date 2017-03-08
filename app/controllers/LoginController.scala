package controllers

import javax.inject._

import models.{Accounts, LoginData}
import play.api.cache.CacheApi
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import services.MD5

@Singleton
class LoginController @Inject() (cache: CacheApi) extends Controller {

  val loginForm:Form[LoginData] = Form{
   mapping(
     "uname" -> nonEmptyText,
     "psw" -> nonEmptyText
   )(LoginData.apply)(LoginData.unapply)

  }

def showProfile(username:String)= Action { implicit  request =>

  val CacheUser: Option[Accounts] = cache.get[models.Accounts](username)
  Ok(views.html.profile(CacheUser.toList, request))
}

  def processForm = Action{ implicit request =>
    loginForm.bindFromRequest.fold(
      formErrors => {
        Redirect(routes.HomeController.index()).flashing("msg"->"Incorrect username or password")
      },
      LoginData => {
        val CacheUser: Option[Accounts] = cache.get[models.Accounts](LoginData.username)
         val result =  CacheUser.map(x=>

              if(x.uname == LoginData.username && x.pswd ==MD5.hash(LoginData.password)) true
              else false
         )

         if(result.contains(true)){

           CacheUser match {
              case Some(x) if(x.isBlocked==false) => Redirect(routes.LoginController.showProfile(LoginData.username)).withSession("currentUser"->LoginData.username).flashing("msg"->"Login Successful")
              case _ => Redirect(routes.HomeController.index()).flashing("denied"->"Permission Suspended,contact Admin")
           }

         }
         else
           Redirect(routes.HomeController.index()).flashing("msg"->"Incorrect username or password")

      }
    )


  }

  def logout = Action{ implicit request=>

    Redirect(routes.HomeController.index()).withNewSession
  }



}
