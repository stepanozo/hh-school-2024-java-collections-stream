package tasks;

import common.Person;
import common.PersonService;

import java.util.*;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  //Асимптотика работы O(N)
  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);

    Map<Integer, Person> personMap = new HashMap<>();

    for(Person p: persons)
      personMap.put(p.id(), p);

    List<Person> sortedPersonList = new ArrayList<>();

    for(Integer id : personIds)
      sortedPersonList.add(personMap.get(id));

    return sortedPersonList;
  }
}
