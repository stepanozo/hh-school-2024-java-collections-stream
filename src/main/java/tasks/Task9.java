package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {  //Проверка пустого списка была удалена, т.к. не нужна. Удаление первой персоны переписано как skip(1)
    return persons.stream().skip(1).map(Person::firstName).toList(); //collect(Collectors.toList()) можно заменить на toList()
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    return getNames(persons).stream().collect(Collectors.toSet()); //distinct был удалён, поскольку здесь уже set, дубликаты уже удаляются
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) { //Переписал через оператор ?, стало компактнее
    return (person.firstName() != null ? person.firstName() : "") +
            (person.secondName() != null ? person.secondName() : "") +
            (person.middleName() != null ? person.middleName() : "");
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>();
      persons.forEach(person -> map.put(person.id(), convertPersonToString(person))); //Заменил цикл на foreach
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream().anyMatch(persons2::contains); //Заменил на anyMatch
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count(); //Заменил foreach на count
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString()); //они равны, потому что toString их сортирует обратно?
  }
}
