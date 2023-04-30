package ru.stankin.graduation.exception

enum class BusinessErrorInfo(override val messageFormat: String) : ErrorInfo {

    TASK_TEMPLATE_CHECK_NOT_FOUND("Шаблон контрольной проверки не найден"),
    TASK_TEMPLATE_CHECKS_EMPTY("Для шаблона задания не указаны проверки"),

    TASK_TEMPLATE_NOT_FOUND("Шаблон задания с id %s не найден"),
    TASK_TEMPLATE_ACTIVATE_IMPOSSIBLE("Невозможно активировать шаблон задания"),

    TASK_SCHEDULER_NOT_FOUND("Планировщик задания не найден"),
    TASK_SCHEDULER_TRIGGER_DATE_IS_BEFORE_NOW("Дата срабатывания триггера не может быть в прошлом"),
    TASK_SCHEDULER_EXPIRE_DELAY_INCORRECT("Промежуток истечения срока действия задания не задано или меньше 1ms"),
    TASK_SCHEDULER_CRON_NOT_FOUND("Значение периодичности не задано"),

    USER_BY_ID_NOT_FOUND("Пользователь с id %s не найден"),
    USER_BY_USERNAME_NOT_FOUND("Пользователь %s не найден"),

    TASK_NOT_FOUND("Задание не найдено"),

    TASK_CHECK_NOT_FOUND("Контрольная проверка не найдена"),
    TASK_CHECK_COMMENT_NOT_FOUND("Для контрольной проверки необходимо задать комментарий"),
    TASK_CHECK_CONTROL_VALUE_NOT_FOUND("Для контрольной проверки необходимо задать контрольное значение"),
    TASK_CHECK_FILES_NOT_FOUND("Для контрольной проверки необходимо прикрепить файлы"),

    FILE_NOT_FOUND("Файл не найден"),

    GROUP_NOT_FOUND("Группа не найдена"),

    REQUIRED_ATTRIBUTE_NOT_SET("Обязательный аттрибут '%s' не задан"),

    USER_DISABLED("Пользователь недоступен"),
    USER_BAD_CREDENTIALS("Неверный логин или пароль"),
}