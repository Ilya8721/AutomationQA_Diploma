# План автоматизации тестирования возможности покупки тура 

## Перечень автоматизируемых сценариев:

### Сценарии перехода с главной страницы сайта (http://localhost:8080/) к форме ввода данных карты:

**Сценарий №1. Через кнопку "Купить":**
1. Нажать кнопку "Купить";
##### Ожидаемый результат: открылась форма ввода данных карты.

**Сценарий №2. Через кнопку "Купить в кредит":**
1. Нажать кнопку "Купить в кредит";
##### Ожидаемый результат: открылась форма ввода данных карты.


### Сценарии заполнения и отправки формы ввода данных карты:

##### Позитивные сценарии заполнения формы:

**Сценарий №1. Использование одобренной карты:**
1. Заполнить поле "Номер карты" номером одобренной карты:
    - цифры;
    - 16 цифр;
2. Заполнить поле "Месяц":
    - цифры;
    - 2 цифры;
    - от 01 до 12;
3. Заполнить поле "Год":
    - цифры;
    - последние 2 цифры года;
    - не просроченная и не более 5 лет с текущей даты;
4. Заполнить поле "Владелец":
    - латинские буквы;
    - не более 64 символов;
5. Заполнить поле "CVC/CVV":
    - цифры;
    - 3 цифры;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется PopUp с сообщением "Успешно. Операция одобрена банком."


##### Негативные сценарии заполнения формы:

**Сценарий №1. Использование не одобренной карты:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется PopUp с сообщением "Ошибка! Банк отказал в проведении операции."

**Сценарий №2. "Номер карты" буквами:**
1. Заполнить поле "Номер карты" буквами:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Номер карты" "Неверный формат"

**Сценарий №3. "Номер карты" спецсимволами:**
1. Заполнить поле "Номер карты" спецсимволами:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Номер карты" "Неверный формат"

**Сценарий №4. "Номер карты" заполнено не полностью:**
1. Заполнить поле "Номер карты" 15 цифрами;
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Номер карты" "Неверный формат"

**Сценарий №5. "Номер карты" заполнено излишне:**
1. Заполнить поле "Номер карты" 17 цифрами;
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется PopUp с сообщением "Ошибка! Банк отказал в проведении операции."

**Сценарий №6. "Месяц" буквами:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" буквами:
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Месяц" "Неверный формат"

**Сценарий №7. "Месяц" спецсимволами:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" спецсимволами:
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Месяц" "Неверный формат"

**Сценарий №8. "Месяц" заполнен не полностью:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" одной цифрой:
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Месяц" "Неверный формат"

**Сценарий №9. "Месяц" заполнен излишне:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" цифрами 111:
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется PopUp с сообщением "Ошибка! Банк отказал в проведении операции."

**Сценарий №10. "Месяц" больше 12, но меньше 100:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" цифрой 13;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Месяц" "Неверно указан срок действия карты"

**Сценарий №11. "Год" буквами:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" буквами;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Год" "Неверный формат"

**Сценарий №12. "Год" спецсимволами:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" спецсимволами;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Год" "Неверный формат"

**Сценарий №13. "Год" значением в прошлом:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" значением в прошлом;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Год" "Истёк срок действия карты"

**Сценарий №14. "Год" значением более 5 лет в будущем:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" значением более 5 лет в будущем;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Год" "Неверно указан срок действия карты"

**Сценарий №15. "Год" заполнено не полностью:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" одной цифрой;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Год" "Неверный формат"

**Сценарий №16. "Год" заполнено излишне:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" заполнен полным значением года срока действия;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется PopUp с сообщением "Ошибка! Банк отказал в проведении операции."

**Сценарий №17. "Владелец" кириллицей:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" кириллицей;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Владелец" "Неверный формат"

**Сценарий №18. "Владелец" цифрами:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" цифрами;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Владелец" "Неверный формат"

**Сценарий №19. "Владелец" спецсимволами:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" спецсимволами;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Владелец" "Неверный формат"

**Сценарий №20. "Владелец" заполнено излишне:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" 65 латинскими буквами;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Владелец" "Неверный формат"

**Сценарий №21. "CVC/CVV" буквами:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" буквами;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "CVC/CVV" "Неверный формат"

**Сценарий №22. "CVC/CVV" спецсимволами:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" спецсимволами;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "CVC/CVV" "Неверный формат"

**Сценарий №23. "CVC/CVV" заполнено не полностью:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" 1 цифрой;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "CVC/CVV" "Неверный формат"

**Сценарий №24. "CVC/CVV" заполнено излишне:**
1. Заполнить поле "Номер карты" номером не одобренной карты:
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" 4 цифрой;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется PopUp с сообщением "Ошибка! Банк отказал в проведении операции."


##### Пустые поля в анкете:
**Сценарий №1. "Номер карты" не заполнено:**
1. Поле "Номер карты" не заполнено;
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Номер карты" "Неверный формат"

**Сценарий №2. "Месяц" не заполнено:**
1. Поле "Номер карты" валидными данными;
2. Заполнить поле "Месяц" не заполнено;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Месяц" "Неверный формат"

**Сценарий №3. "Год" не заполнено:**
1. Поле "Номер карты" валидными данными;
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" не заполнено;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Год" "Неверный формат"

**Сценарий №4. "Владелец" не заполнено:**
1. Поле "Номер карты" валидными данными;
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" не заполнено;
5. Заполнить поле "CVC/CVV" валидными данными;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "Владелец" "Поле обязательно для заполнения"

**Сценарий №5. "CVC/CVV" не заполнено:**
1. Поле "Номер карты" валидными данными;
2. Заполнить поле "Месяц" валидными данными;
3. Заполнить поле "Год" валидными данными;
4. Заполнить поле "Владелец" валидными данными;
5. Заполнить поле "CVC/CVV" не заполнено;
6. Кликнуть по кнопке "Продолжить".
##### Ожидаемый результат: Появляется ошибка поля "CVC/CVV" "Неверный формат"


## Перечень используемых инструментов с обоснованием выбора:
- _IntelliJ IDEA 2022.3.1 (Community Edition)_ - среда разработки, мощная и удобная, с поддержкой Java и всех последних технологий и фреймворков. Версия Community Edition бесплатная.
- _Java 11_ - язык для написания автотестов, имеет набор готового ПО для разработки и запуска приложений.
- _Gradle_ - сборка проекта, управление подключёнными зависимостями, генерации отчётов о тестировании. Более простой в использовании благодаря тому, что билд скрипты проще чем в Maven.
- _JUnit 5_ - запуска тестов. Предоставляет много новых функций и возможностей по сравнению с JUnit4 и TestNG.
- _Docker_ — это программное обеспечение, для разработки, доставки и запуска контейнерных приложений. Он позволяет создавать контейнеры, автоматизировать их запуск и развертывание, управляет жизненным циклом. С помощью Docker можно запускать множество контейнеров на одной хост-машине.
- _Selenide_ фреймворк, который позволяющая быстро и просто его использовать при написании тестов.
- _Lombok_ - основанная на аннотациях библиотека Java, позволяющая сократить шаблонный код, уменьшает трудозатраты и время на разработку и обеспечивает некоторую дополнительную функциональность.
- _Faker_- библиотека для генерации тестовых данных.
- _Rest Assured_ - библиотека, позволяет пользователям обрабатывать HTTP-запросы, которые можно записать в достаточно читабельном формате.
- _Allure_ - фреймворк для создания отчетов о тестировании, более подробных чем в Gradle.
- _Git и GitHub_ для ведения репозитория по проектам. Git очень распространенная система контроля версий, поэтому достаточно хорошо взаимодействует с различными ОС. GitHub специализированный веб-сервис с удобным интерфейсом, интегрирован с Git.
- _AppVeyor_ - Continuous Integration веб-сервис, предназначенный для сборки и тестирования программного обеспечения расположенного на GitHub, использующий виртуальные машины Microsoft Windows и Ubuntu. Бесплатный и удобный сервис, который может осуществлять сборку как под управлением Linux, так и под Windows, а если необходимо то под несколькими сразу.

## Перечень и описание возможных рисков при автоматизации:
- Изменение интерфейса
- Изменение бизнес логики приложения
- Большое количество дефектов в функциональности
- Проблемы окружения
- Проблемы с тестовыми данными
- Инфраструктурные проблемы

## Интервальная оценка с учётом рисков в часах
- Ориентировочное время реализации: 10 дней.

## План сдачи работ: когда будут готовы автотесты, результаты их прогона;
- План автоматизации: 15.06.2023;
- Автотест: 21.06.2023;
- Подготовка отчётных документов по итогам автоматизированного тестирования: 22.06.2023;
- Подготовка отчётных документов по итогам автоматизации: 23.06.2023;

## Отчёт по автоматизации:
- Оформляется в виде файла с именем Summary.md и заливается в репозиторий вашего проекта.