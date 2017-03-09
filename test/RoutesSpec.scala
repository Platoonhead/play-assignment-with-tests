import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class RoutesSpec extends PlaySpec with OneAppPerTest {


  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Get me an Account!")
    }

  }

  "LoginController" should {

    "sucessfull redirect to logout" in {
      val home = route(app, FakeRequest(GET, "/logout")).get
      status(home) equals 303
    }


    "sucessfull redirect to profilepage" in {
      val home = route(app, FakeRequest(GET, "/profilepage")).get
      status(home) equals 303
    }

  }

  "AdminPanelController" should {

    "sucessfull redirect to profile" in {
      val home = route(app, FakeRequest(GET, "/profile")).get
      status(home) equals 303
    }

    "sucessfull redirect to profilevisit" in {
      val home = route(app, FakeRequest(GET, "/profilevisit")).get
      status(home) equals 200
    }

    "sucessfull redirect to adminpanel" in {
      val home = route(app, FakeRequest(GET, "/resumed")).get
      status(home) equals 303
    }

    "sucessfull redirect to adminpanel on suspend(refresh)" in {
      val home = route(app, FakeRequest(GET, "/suspend")).get
      status(home) equals 303
    }

  }


  "SignUpController" should {

    "sucessfull redirect to profilepage" in {
      val home = route(app, FakeRequest(POST, "/addPeople")).get
      status(home) equals 303
    }

  }

}
