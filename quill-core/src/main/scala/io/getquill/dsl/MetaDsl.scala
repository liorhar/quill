package io.getquill.dsl

import scala.language.experimental.macros

trait MetaDslLowPriorityImplicits {
  this: MetaDsl =>

  implicit def materializeQueryMeta[T]: QueryMeta[T] = macro MetaDslMacro.materializeQueryMeta[T]
  implicit def materializeUpdateMeta[T]: UpdateMeta[T] = macro MetaDslMacro.materializeUpdateMeta[T]
  implicit def materializeInsertMeta[T]: InsertMeta[T] = macro MetaDslMacro.materializeInsertMeta[T]
  implicit def materializeEntityMeta[T]: EntityMeta[T] = macro MetaDslMacro.materializeEntityMeta[T]
}

trait MetaDsl extends MetaDslLowPriorityImplicits {
  this: CoreDsl =>

  trait Embedded

  trait EntityMeta[T] {
    val entity: Quoted[EntityQuery[T]]
  }

  trait QueryMeta[T] {
    val expand: Quoted[Query[T] => Query[_]]
    val extract: ResultRow => T
  }

  trait UpdateMeta[T] {
    val expand: Quoted[(EntityQuery[T], T) => Update[T]]
  }

  trait InsertMeta[T] {
    val expand: Quoted[(EntityQuery[T], T) => Insert[T]]
  }
}
