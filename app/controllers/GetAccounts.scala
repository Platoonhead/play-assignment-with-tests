package controllers

import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.BackendService.listOfAccounts

class GetAccounts extends Controller {


   def getUsers = Action{

     Ok(Json.toJson(listOfAccounts))

   }

}
