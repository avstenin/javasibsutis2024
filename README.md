# Report
## Lab0 - Hello, world !
## Lab1 
Система доменных имен (DNS, англ. Domain Name System) позволяет
обращаться к хосту по имени домена, например mail.com – домен второго
уровня, www.mail.com – третьего. Каждый запрос от ПК пользователя с
указанием такого имени сначала обращается к системе DNS, основной и
резервный адрес которой указан на сетевом адаптере. Часто запросы к DNS
могут быть продолжительны по времени прохождения пакетов по сети,
необходимо определить какой из 3-х заданных DNS адресов с клавиатуры
имеет наименьшее среднее время отклика.
Пример ввода:
Введите адрес сервера DNS1:
8.8.8.8
Введите адрес сервера DNS2:
8.8.4.4
Введите адрес сервера DNS3:
77.88.8.7
Ответ вывести в консоль с указанием среднего времени отклика для всех
корректно указанных DNS в порядке убывания.
*Для получения данных о доступности можно используем команду PING.
## Lab2
1. Создайте массив данных 3хn, где первый элемент строки в каждом столбце
является IP адресом DNS-сервера, а i+1+n строка содержит среднее время
отклика от ПК пользователя до сервера. *Используя ветвление после
коммита реализуйте каждый новый функционал в своей ветке, а затем
выполните слияние.
2. Количество DNS-серверов, их адреса (и соответственно размерность
массива) задается пользователем с клавиатуры.
3. Реализуйте хранение результатов работы программы в виде файлов. При
просмотре статистики запросов программа рекурсивно считывает все файлы
из каталога запуска или указанной пользователем директории.