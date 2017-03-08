package models
import services.BackendService

import scala.collection.mutable.ListBuffer

object Operations {

   def addUser(user:String) = {BackendService.listOfAccounts+=user ; true}

   def getAllUsers:ListBuffer[String]= BackendService.listOfAccounts

}
