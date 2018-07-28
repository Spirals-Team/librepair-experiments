package com.dslplatform.json

import java.lang.reflect.{GenericArrayType, ParameterizedType, Type => JavaType}

import scala.collection.concurrent.TrieMap
import scala.reflect.runtime.universe._
import scala.runtime.Nothing$
import scala.util.{Try, Success}

private[json] object TypeAnalysis {

  private val genericsCache = new TrieMap[String, GenericType]
  private val typeTagCache = new java.util.concurrent.ConcurrentHashMap[Type, JavaType]
  typeTagCache.put(typeOf[Nothing], classOf[Nothing$])
  typeTagCache.put(typeOf[Any], classOf[AnyRef])
  typeTagCache.put(typeOf[Option[Nothing]], classOf[Option[AnyRef]])
  typeTagCache.put(typeOf[None.type], classOf[Option[AnyRef]])

  private class GenericType(
    val name: String,
    val raw: JavaType,
    val arguments: Array[JavaType]) extends ParameterizedType {
    private val argObjects = arguments.map(_.asInstanceOf[AnyRef])

    override def hashCode: Int = {
      java.util.Arrays.hashCode(argObjects) ^ raw.hashCode
    }

    override def equals(other: Any): Boolean = {
      other match {
        case pt: ParameterizedType =>
          raw == pt.getRawType && java.util.Arrays.equals(argObjects, pt.getActualTypeArguments.map(_.asInstanceOf[AnyRef]))
        case _ =>
          false
      }
    }

    def getActualTypeArguments: Array[JavaType] = arguments

    def getRawType: JavaType = raw

    def getOwnerType: JavaType = null

    override def toString: String = name
  }

  private def makeGenericType(container: Class[_], arguments: List[JavaType]): ParameterizedType = {
    val sb = new StringBuilder
    sb.append(container.getTypeName)
    sb.append("<")
    sb.append(arguments.head.getTypeName)
    arguments.tail.foreach { arg =>
      sb.append(", ")
      sb.append(arg.getTypeName)
    }
    sb.append(">")
    val name = sb.toString
    genericsCache.getOrElseUpdate(
      name, {
        new GenericType(name, container, arguments.toArray)
      })
  }

  private class GenArrType(genType: JavaType) extends GenericArrayType {
    lazy private val typeName = genType.getTypeName + "[]"

    override def getGenericComponentType: JavaType = genType

    override def getTypeName: String = typeName

    override def toString: String = typeName
  }

  def convertType(tpe: Type): JavaType = {
    val found = typeTagCache.get(tpe)
    if (found != null) found
    else {
      findUnknownType(tpe, scala.reflect.runtime.currentMirror) match {
        case Some(jt) =>
          typeTagCache.put(tpe, jt)
          jt
        case _ =>
          sys.error(s"Unable to convert $tpe to Java representation")
      }
    }
  }

  private def findUnknownType(tpe: Type, mirror: Mirror): Option[JavaType] = {
    tpe.dealias match {
      case TypeRef(_, sym, args) if args.isEmpty =>
        Try(mirror.runtimeClass(sym.asClass)).toOption
      case TypeRef(_, sym, args) if sym.fullName == "scala.Array" && args.lengthCompare(1) == 0 =>
        Try(convertType(args.head)) match {
          case Success(typeArg) => Some(new GenArrType(typeArg))
          case _ => None
        }
      case TypeRef(_, sym, args) =>
        Try(mirror.runtimeClass(sym.asClass)) match {
          case Success(symClass) =>
            val typeArgs = args.flatMap(it => Try(convertType(it)).toOption)
            if (typeArgs.lengthCompare(args.size) == 0) {
              Some(makeGenericType(symClass, typeArgs))
            } else None
          case _ => None
        }
      case _ =>
        None
    }
  }
}