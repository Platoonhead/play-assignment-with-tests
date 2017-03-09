import models.Accounts
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.mvc.Flash
import play.api.test.Helpers.{contentAsString, _}
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class ViewsSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  "Rending landing / index page================================" in new App {
    val a = mock[Flash]
    val html = views.html.index()(a)
    contentAsString(html) must include("Get me an Account!")
  }

  "Rending profile page================================" in new App {
    val fakeFlash = mock[Flash]
    val html = views.html.profile(

      List(Accounts("aa", "aa", "aa", "aa", "aa", "aa", "", "", 11, "", true, true)), FakeRequest(GET, "/profile")

    )(fakeFlash)
    contentAsString(html) must include("aa")
  }


  "Rending admin panel page================================" in new App {
    val fakeFlash = mock[Flash]
    val html = views.html.adminpanel(List(Accounts("aa", "aa", "aa", "aa", "aa", "aa", "", "", 11, "", true, true)))

    contentAsString(html) must include("aa")
  }


}
