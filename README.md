**Пердставленное приложение запрашивает у пользователя следующие данные в произвольном порядке, разделённые пробелом:**

`Фамилия Имя Отчество датарождения номертелефона пол`

Форматы данных:

*фамилия, имя, отчество* - строки

*дата_рождения* - строка формата dd.mm.yyyy

*номер_телефона* - целое беззнаковое число без форматирования

*пол* - символ латиницей f или m.

- Приложение валидирует количество введённых данных. Если количество не совпадает с требуемым, возвращается код ошибки, который обрабатывается с последующим выводом сообщения для пользователя о том, что он ввел меньше или больше данных, чем требуется.

- Приложение пытается распарсить полученные значения и выделяет из них требуемые параметры. Если форматы данных не совпадают, бросается исключение, соответствующее типу проблемы. При этом пользователю выводится сообщение с информацией, какие именно данные введены не верно.

- Если всё введено и обработано верно, создаётся файл с названием, равным фамилии, в него в одну строку записываются полученные данные в  виде строки:
`<Фамилия><Имя><Отчество><датарождения><номертелефона><пол>`
Однофамильцы записываются в один и тот же файл, в отдельные строки.

- При возникновении проблемы с чтением-записью в файл, возникшее исключение корректно обрабатывается с выводом для пользователя стектрейса ошибки.