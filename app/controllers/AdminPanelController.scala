package controllers

import javax.inject.Inject

import models.{Accounts, Operations}
import play.api.mvc.{Action, Controller}
import services.AppCacheProvider

class AdminPanelController @Inject()(cache: AppCacheProvider) extends Controller {

  def showPanel() = Action { implicit request =>
    request.session.get("currentUser").map { user =>

      Redirect(routes.LoginController.showProfile(user)).flashing("msg" -> "Welcome Back")

    }.getOrElse {
      Unauthorized("Oops, you are not connected")
    }

  }

  def showMaintanancePanel() = Action { implicit request =>

    val allusers = Operations.getAllUsers
    val users = for {
      i <- allusers
    } yield cache.retrieve(i)
    Ok(views.html.adminpanel(users.flatten.toList))
  }

  def suspend(username: String) = Action { implicit request =>
    val user: Option[Accounts] = cache.retrieve(username)
    user match {
      case Some(x) => {
        val BlockedUser = x.copy(isBlocked = true)
        cache.remove(username)
        cache.insert(username, BlockedUser)
      }
      case _ => false
    }
    Redirect(routes.AdminPanelController.showMaintanancePanel())

  }

  def resume(username: String) = Action { implicit request =>
    val user: Option[Accounts] = cache.retrieve(username)
    user match {
      case Some(x) => {
        val resumedUser = x.copy(isBlocked = false)
        cache.remove(username)
        cache.insert(username, resumedUser)
      }
      case _ => false
    }
    Redirect(routes.AdminPanelController.showMaintanancePanel())

  }


}
