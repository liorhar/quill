package io.getquill.dsl

import scala.language.experimental.macros
import scala.reflect.ClassTag

trait MetaDslLowPriorityImplicits {
  this: MetaDsl =>

  implicit def materializeQueryMeta[T]: QueryMeta[T] = macro MetaDslMacro.materializeQueryMeta[T]
  implicit def materializeUpdateMeta[T]: UpdateMeta[T] = macro MetaDslMacro.materializeUpdateMeta[T]
  implicit def materializeInsertMeta[T]: InsertMeta[T] = macro MetaDslMacro.materializeInsertMeta[T]
  implicit def materializeDeleteMeta[T]: DeleteMeta[T] = macro MetaDslMacro.materializeDeleteMeta[T]
}

trait MetaDsl extends MetaDslLowPriorityImplicits {
  this: CoreDsl =>

  trait Embedded
  
  abstract class Meta[T: ClassTag] {
    val classTag = implicitly[ClassTag[T]]
  }

  abstract class QueryMeta[T: ClassTag] extends Meta[T] {
    val expand: Quoted[Query[T] => Query[_]]
    val extract: ResultRow => T
  }

  abstract class UpdateMeta[T: ClassTag] extends Meta[T]  {
    val expand: Quoted[(EntityQuery[T], T) => Update[T]]
  }

  abstract class InsertMeta[T: ClassTag] extends Meta[T]  {
    val expand: Quoted[(EntityQuery[T], T) => Insert[T]]
  }

  abstract class DeleteMeta[T: ClassTag] extends Meta[T] 
}
