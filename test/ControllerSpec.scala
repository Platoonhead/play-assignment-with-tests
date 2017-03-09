import controllers.{AdminPanelController, HomeController, LoginController}
import models.Accounts
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, contentType, status, _}
import services.AppCacheProvider


class ControllerSpec extends PlaySpec with OneAppPerTest with MockitoSugar {


  "HomeController" should {

    "render the index page" in {

      val homeObj = new HomeController
      val home = homeObj.index().apply(FakeRequest())
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Get me an Account!")
    }

  }

  "LoginController" should {

    "showProfile" in {
      val customCache = mock[AppCacheProvider]
      val showObj = new LoginController(customCache)
      val user: Accounts = Accounts("aa", "aa", "aa", "aa", "aa", "aa", "", "", 11, "", true, true)
      customCache.insert("aa", user)
      val userDetails: Option[Accounts] = customCache.retrieve("aa")
      when(userDetails) thenReturn Some(user)
      val home = showObj.showProfile("aa").apply(FakeRequest())
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Get me an Account!")
    }

    "logout" in {
      val customCache = mock[AppCacheProvider]
      val showObj = new LoginController(customCache)
      val home = showObj.logout.apply(FakeRequest())
      status(home) equals 303
    }

  }

  "AdminPanelController.scala" should {

    "showPanel" in {
      val customCache = mock[AppCacheProvider]
      val showObj = new AdminPanelController(customCache)
      val home = showObj.showPanel().apply(FakeRequest())
      status(home) equals 303
    }


    "showMaintanancePanel" in {
      val customCache = mock[AppCacheProvider]
      val showObj = new AdminPanelController(customCache)
      val home = showObj.showMaintanancePanel().apply(FakeRequest())
      status(home) equals 200
    }

    "suspend" in {
      val customCache = mock[AppCacheProvider]
      val showObj = new AdminPanelController(customCache)
      val user: Accounts = Accounts("aa", "aa", "aa", "aa", "aa", "aa", "", "", 11, "", true, true)
      customCache.insert("aa", user)
      val userDetails: Option[Accounts] = customCache.retrieve("aa")
      when(userDetails) thenReturn Some(user)
      val home = showObj.suspend("aa").apply(FakeRequest())
      status(home) equals 303
    }


    "resume" in {
      val customCache = mock[AppCacheProvider]
      val showObj = new AdminPanelController(customCache)
      val user: Accounts = Accounts("aa", "aa", "aa", "aa", "aa", "aa", "", "", 11, "", true, true)
      customCache.insert("aa", user)
      val userDetails: Option[Accounts] = customCache.retrieve("aa")
      when(userDetails) thenReturn Some(user)
      val home = showObj.resume("aa").apply(FakeRequest())
      status(home) equals 303
    }


  }


}





