import models.Accounts
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play._
import play.api.cache.CacheApi
import services.{AppCache, AppCacheProvider}


class ServicesSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  "UserService" should {
    "insert///////////////////////////////////////////////////////////////" in {
      val cache = mock[CacheApi]
      val customCache = mock[AppCache]
      val user = Accounts("aa", "aa", "aa", "aa", "aa", "aa", "", "", 11, "", true, true)
      val service = new AppCacheProvider(cache)
      customCache.insert("aa", user)
      when(cache.get[Accounts]("aa")) thenReturn Some(user)

    }

    "remove///////////////////////////////////////////////////////////////" in {
      val cache = mock[CacheApi]
      val customCache = mock[AppCache]
      val user = Accounts("aa", "aa", "aa", "aa", "aa", "aa", "", "", 11, "", true, true)
      customCache.insert("aa", user)
      customCache.remove("aa")
      when(cache.get[Accounts]("aa")) thenReturn None


    }

    "retrive///////////////////////////////////////////////////////////////" in {
      val cache = mock[CacheApi]
      val customCache = mock[AppCache]
      val user = Accounts("aa", "aa", "aa", "aa", "aa", "aa", "", "", 11, "", true, true)
      cache.set("aa", user)
      when(customCache.retrieve("aa")) thenReturn Some(user)
    }
  }


}
