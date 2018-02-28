package com.nekopiano.scala.cats

import cats.data.State

object StateMonadSpec extends App {

  // just w/ List
  {
    type Stack = List[Int]

    def pop(s0: Stack): (Stack, Int) =
      s0 match {
        case x :: xs => (xs, x)
        case Nil => sys.error("stack is empty")
      }

    def push(s0: Stack, a: Int): (Stack, Unit) = (a :: s0, ())


    // perform

    def stackManip(s0: Stack): (Stack, Int) = {
      val (s1, _) = push(s0, 3)
      val (s2, a) = pop(s1)
      pop(s2)
    }

    val result = stackManip(List(5, 8, 2, 1))
    println(result)

  }

  // state monad w/ Cats
  {
    type Stack = List[Int]

    import cats._, cats.data._, cats.implicits._

    val pop = State[Stack, Int] {
      case x :: xs => (xs, x)
      case Nil => sys.error("stack is empty")
    }

    def push(a: Int) = State[Stack, Unit] {
      case xs => (a :: xs, ())
    }


    // perform


    def stackManip: State[Stack, Int] = for {
      _ <- push(3)
      a <- pop
      b <- pop
    } yield (b)

    val result = stackManip.run(List(5, 8, 2, 1)).value
    println("result=" + result)

  }


  // get put as helper funcs
  {
    type Stack = List[Int]
    import cats._, cats.data._, cats.implicits._

    def stackyStack: State[Stack, Unit] = for {
      stackNow <- State.get[Stack]
      r <- if (stackNow === List(1, 2, 3)) State.set[Stack](List(8, 3, 1))
      else State.set[Stack](List(9, 2, 1))
    } yield r

    val result2 = stackyStack.run(List(1, 2, 3)).value
    println("result2=" + result2)


    // pop and push w/ get and set

    val pop: State[Stack, Int] = for {
      s <- State.get[Stack]
      (x :: xs) = s
      _ <- State.set[Stack](xs)
    } yield x


    def push(x: Int): State[Stack, Unit] = for {
      xs <- State.get[Stack]
      r <- State.set(x :: xs)
    } yield r

    // perform

    def stackManip: State[Stack, Int] = for {
      _ <- push(3)
      a <- pop
      b <- pop
    } yield (b)

    val result3 = stackManip.run(List(5, 8, 2, 1)).value
    println("result3=" + result3)


  }

  {
    import cats._, cats.data._, cats.implicits._

    // http://eed3si9n.com/herding-cats/Monad.html
    // http://eed3si9n.com/herding-cats/ja/Monad.html
    def counter: State[Int, Unit] = for {
      init <- State.get[Int]
      next <- State.set(init + 1)
      r <- State.get[Int]
    } yield r

    val result4 = counter.run(2).value
    println("result4=" + result4)
    val result4b = counter.run(result4._1).value
    println("result4b=" + result4b)

  }


  // https://stackoverflow.com/questions/7177134/functional-pattern-for-double-fold
  {
//    case class Counter(next: Int = 0, str2int: Map[String,Int] = Map()) {
//
//      //      def apply( str: String ): (Counter, Int) = (str2int get str) fold (
////        (this, _),
////        (new Counter(next+1, str2int + (str -> next)), next)
////      )
//
//      def apply( str: String ): ((Int) => String) => String = (str2int get str) fold("a")
//    }
//
//    type CounterState[A] = State[Counter, A]
//
//    def count(s: String): CounterState[Int] = state(_(s))
//
//    def toInt(strs: Seq[String]): CounterState[Seq[Int]] =
//      strs.traverse[CounterState, Int](count)

  }

  // free monad
  // http://eed3si9n.com/herding-cats/ja/stackless-scala-with-free-monads.html
  {
    import cats._, cats.data._, cats.implicits._, cats.free.{Free, Trampoline}

    import Trampoline._

    // Entering paste mode (ctrl-D to finish)
    def even[A](ns: List[A]): Trampoline[Boolean] =
      ns match {
        case Nil => done(true)
        case x :: xs => suspend(odd(xs))
      }

    def odd[A](ns: List[A]): Trampoline[Boolean] =
      ns match {
        case Nil => done(false)
        case x :: xs => suspend(even(xs))
      }

    // Exiting paste mode, now interpreting.
    val result5 = even(List(1, 2, 3)).run
    println("result5=" + result5)
    val result5b = even((0 to 3000).toList).run
    println("result5b=" + result5b)
    val result5c = even((0 to 3001).toList).run
    println("result5c=" + result5c)
  }

}
