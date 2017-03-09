package services

import javax.inject.Inject

import models.Accounts
import play.api.cache.CacheApi

trait AppCache {

   def insert(key:String,value:Accounts):Boolean
   def retrieve(key:String):Option[Accounts]
   def remove(key:String):Boolean

}
class  AppCacheProvider @Inject()(cache: CacheApi) extends AppCache{

  override def insert(key: String, value: Accounts) = {

    cache.set(key,value)
    true
  }

  override def retrieve(key: String) = {

    cache.get(key)
  }

  override def remove(key:String)  = {
    cache.remove(key)
    true
  }

}
