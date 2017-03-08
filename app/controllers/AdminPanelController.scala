package controllers

import javax.inject.Inject

import models.{Accounts, LoginData, Operations}
import play.api.cache.CacheApi
import play.api.mvc.{Action, Controller}

class AdminPanelController @Inject() (cache: CacheApi) extends Controller {

  def showPanel()= Action { implicit  request =>
    request.session.get("currentUser").map { user =>

      Redirect(routes.LoginController.showProfile(user)).flashing("msg"->"Welcome Back")

    }.getOrElse {
      Unauthorized("Oops, you are not connected")
    }

  }

  def showMaintanancePanel()= Action { implicit  request =>

    val allusers =  Operations.getAllUsers
    val users = for {
       i <- allusers
    }yield  cache.get[models.Accounts](i)
    Ok(views.html.adminpanel(users.flatten.toList))

  }

  def suspend(username:String)= Action { implicit  request =>
     val user = cache.get[models.Accounts](username)
    user match {
      case Some(x) => {
                        val BlockedUser = x.copy(isBlocked = true)
                        cache.remove(username)
                        cache.set(username,BlockedUser)
      }
    }
    Redirect(routes.AdminPanelController.showMaintanancePanel())

  }

    def resume(username:String)= Action { implicit  request =>
    val user = cache.get[models.Accounts](username)
    user match {
      case Some(x) => {
        val resumedUser = x.copy(isBlocked = false)
        cache.remove(username)
        cache.set(username,resumedUser)
      }
    }
    Redirect(routes.AdminPanelController.showMaintanancePanel())

  }


}
