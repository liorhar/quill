package test

import io.getquill.MirrorContext
import io.getquill.MirrorIdiom
import io.getquill.SnakeCase

object Test extends App {
  val ctx = new MirrorContext[MirrorIdiom, SnakeCase]
  import ctx._
  
  case class Person(name: String, age: Int)
  
  materializeEntityMeta[Person]
  println(run(query[Person].delete))
}
