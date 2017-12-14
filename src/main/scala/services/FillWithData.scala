package services

import java.sql.Timestamp
import java.time.format.DateTimeFormatter

import models._

object FillWithData {

  val formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy")

  val users = List(
    User(1, "coghueserriletesik@icloud.com", "Инна", "Данатоная", "f", new Timestamp(-631065600)),
    User(2, "sosodachenheonne@list.me", "Роман", "Терленчан", "m", new Timestamp(885254400)),
    User(3, "vyhihhimrusedac@list.me", "Артём", "Пенленный", "m", new Timestamp(48124800)),
    User(4, "istoordadanaihdut@gmail.com", "Борислав", "Фетленвич", "m", new Timestamp(-1007596800))
      //LocalDateTime.ofInstant(Instant.ofEpochMilli(-1007596800), ZoneOffset.UTC))
  )

  /*
"email": "istoordadanaihdut@gmail.com"},
   */

  val locations = List(
    Location(1, "Поместье", "Белоруссия", "Белстан", 69),
    Location(2, "Серпантин", "Мальдивы", "Лиссадам", 51),
    Location(3, "Ручей", "Лаос", "Ньюква", 80),
    Location(4, "Дача", "Эстония", "Барсдорф", 55)
  )

  val visits = List(
    Visit(1, 1, 3, new Timestamp(1049447314), 3),
    Visit(2, 2, 4, new Timestamp(1307383431), 4),
    Visit(3, 4, 2, new Timestamp(1161828238), 1),
    Visit(4, 3, 1, new Timestamp(1099027376), 1)
  )

  def fillDatabase(): Unit = {
    users.map(userRepository.create(_))
    locations.map(locationRepository.create(_))
    visits.map(visitRepository.create(_))
  }

}
