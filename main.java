class TimeUtils {
    // Проверка корректности времени (9:00-21:00)
    public static boolean isValidTime(String startTime) {
        //преобразование символа в число
        int hour = (startTime.charAt(0) - '0') * 10 + (startTime.charAt(1) - '0');
        // Проверка, что время начала задачи в пределах рабочего дня
        return hour >= 9 && hour < 21;
    }

    // Преобразование времени в минуты
    public static int timeToMinutes(String time) {
        int hours = (time.charAt(0) - '0') * 10 + (time.charAt(1) - '0');  // Извлекаем часы
        int minutes = (time.charAt(3) - '0') * 10 + (time.charAt(4) - '0'); // Извлекаем минуты

        return hours * 60 + minutes; // Переводим время в минуты
    }

    // Преобразование минут обратно в формат времени "hh:mm"
    public static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        // Перевод минут обратно в формат времени
        //d используется для форматирования целых чисел
        return String.format("%02d:%02d", hours, mins);
    }
}
class Task {
    private String name; // Название задачи
    private String startTime; // Время начала задачи
    private int duration; // Продолжительность задачи в минутах

    // Конструктор для инициализации задачи
    public Task(String name, String startTime, int duration) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    // Метод для вычисления времени окончания задачи
    public String getEndTime() {
        int totalMinutes = TimeUtils.timeToMinutes(startTime) + duration;
        return TimeUtils.minutesToTime(totalMinutes); // Возвращает время окончания задачи в формате "hh:mm"
    }

    @Override
    public String toString() {
        // Форматированный вывод задачи с её временем начала, окончания и названием
        return String.format("%s - %s - %s", startTime, getEndTime(), name);
    }
}

class Calendar {
    // Массив для хранения задач
    private Task[] tasks = new Task[100];
    // Количество задач в календаре
    private int taskCount = 0;

    // Метод 1: Вывод списка задач
    public void displayTasks() {
        // Сортируем задачи по времени начала
        sortTasks();
        System.out.println("Список задач:");
        for (int i = 0; i < taskCount; i++) {
            // Выводим задачи с их индексом
            System.out.printf("%d - %s\n", i + 1, tasks[i]);
        }
    }

    // Метод 2: Добавление задачи
    public void addTask(String startTime, int duration, String name) {
        // Проверка, что время начала задачи корректно
        if (!TimeUtils.isValidTime(startTime)) {
            System.out.println("Некорректное время начала. Время должно быть в интервале с 9:00 до 21:00.");
            return;
        }
        // Создаём новую задачу
        Task newTask = new Task(name, startTime, duration);

        // Проверка на пересечение времени задач
        for (int i = 0; i < taskCount; i++) {
            if (isOverlapping(tasks[i], newTask)) {
                System.out.println("Задача с таким временем уже существует.");
                return;
            }
        }

        // Добавление задачи в календарь, если есть место
        if (taskCount < tasks.length) {
            tasks[taskCount] = newTask;
            taskCount++;
            System.out.println("Задача добавлена: " + newTask);
        } else {
            System.out.println("Нет места для добавления новой задачи.");
        }
    }

    // Метод 3: Поиск самой длинной задачи
    public void longestTask() {
        // Если задач нет
        if (taskCount == 0) {
            System.out.println("Нет задач.");
            return;
        }

        Task longest = tasks[0];
        for (int i = 1; i < taskCount; i++) {
            if (tasks[i].getDuration() > longest.getDuration()) {
                longest = tasks[i];
            }
        }
        System.out.println("Самая длинная задача: " + longest);
    }

    // Метод 4: Задачи на заданном промежутке времени
    public void tasksInTimeRange(String startRange, String endRange) {
        System.out.println("Задачи на промежутке времени:");
        for (int i = 0; i < taskCount; i++) {
            // Проверка, что задача пересекается с заданным временем
            if (tasks[i].getStartTime().compareTo(endRange) < 0 && tasks[i].getEndTime().compareTo(startRange) > 0) {
                System.out.println(tasks[i]);
            }
        }
    }

    // Метод 5: Задачи, завершённые до указанного времени
    public void tasksCompletedBefore(String endTime) {
        System.out.println("Задачи, завершенные до " + endTime + ":");
        for (int i = 0; i < taskCount; i++) {
            // Выводим задачи, которые закончились до указанного времени
            if (tasks[i].getEndTime().compareTo(endTime) <= 0) {
                System.out.println(tasks[i]);
            }
        }
    }

    // Метод 6: Удаление задачи по номеру
    public void removeTask(int taskNumber) {
        // Проверка, что задача существует
        if (taskNumber >= 1 && taskNumber <= taskCount) {
            // Задача для удаления
            Task taskToRemove = tasks[taskNumber - 1];
            // Сдвигаем все задачи, начиная с удаляемой, на одну позицию влево
            for (int i = taskNumber - 1; i < taskCount - 1; i++) {
                tasks[i] = tasks[i + 1];
            }
            taskCount--; // Уменьшаем количество задач
            System.out.println("Задача удалена: " + taskToRemove);
        } else {
            System.out.println("Задача с таким номером не существует.");
        }
    }

    // Метод 7: Удаление задач, начинающихся на заданном промежутке
    public void removeTasksStartingInRange(String startRange, String endRange) {
        for (int i = 0; i < taskCount; i++) {
            // Удаляем задачи, которые начинаются в пределах указанного промежутка
            if (tasks[i].getStartTime().compareTo(startRange) >= 0 && tasks[i].getStartTime().compareTo(endRange) < 0) {
                System.out.println("Задача удалена: " + tasks[i]);
                removeTask(i + 1); // Удаляем задачу
                i--; // Смещаем индекс на одну позицию назад
            }
        }
    }

    // Метод 8: Удаление задач, пересекающих промежуток
    public void removeTasksOverlapping(String startRange, String endRange) {
        // Переводим время в минуты
        int startRangeMinutes = TimeUtils.timeToMinutes(startRange);
        int endRangeMinutes = TimeUtils.timeToMinutes(endRange);

        for (int i = 0; i < taskCount; i++) {
            // Переводим время начала задачи в минуты
            int taskStartMinutes = TimeUtils.timeToMinutes(tasks[i].getStartTime());
            // Переводим время конца задачи в минуты
            int taskEndMinutes = TimeUtils.timeToMinutes(tasks[i].getEndTime());

            if (taskStartMinutes < endRangeMinutes && taskEndMinutes > startRangeMinutes) {
                // Если задача пересекает промежуток(То есть задача пересекает интервал,
                // если хотя бы частично она попадает в диапазон от startRange до endRange),
                // обрезаем её время
                if (taskStartMinutes < startRangeMinutes) {
                    taskStartMinutes = startRangeMinutes;
                }

                if (taskEndMinutes > endRangeMinutes) {
                    taskEndMinutes = endRangeMinutes;
                }

                // Переводим минуты обратно в правильный формат
                String newStartTime = TimeUtils.minutesToTime(taskStartMinutes);
                String newEndTime = TimeUtils.minutesToTime(taskEndMinutes);
                //создаем новую задачу с новыми значениями времени начала и окончания.
                //(taskEndMinutes - taskStartMinutes) - это вычисление продолжительности задачи в минутах после того,
                // как её время было "обрезано" (если оно пересекало заданный интервал).
                Task newTask = new Task(tasks[i].getName(), newStartTime, taskEndMinutes - taskStartMinutes);

                // Обновляем задачу
                tasks[i] = newTask;
                System.out.println("Задача обрезана: " + tasks[i]);
            }
        }
    }

    // Метод 9: Удаление самых коротких задач
    public void removeShortestTasks() {
        if (taskCount == 0) { // Если нет задач
            System.out.println("Нет задач для удаления.");
            return;
        }
        // Находим минимальную продолжительность
        int minDuration = tasks[0].getDuration();
        for (int i = 1; i < taskCount; i++) {
            if (tasks[i].getDuration() < minDuration) {
                minDuration = tasks[i].getDuration();
            }

        }

        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getDuration() == minDuration) {
                System.out.println("Самая короткая задача удалена: " + tasks[i]);

                // Сдвигаем все задачи после удаленной на одну позицию влево
                for (int j = i; j < taskCount - 1; j++) {
                    tasks[j] = tasks[j + 1]; // Перемещаем задачу на предыдущую позицию
                }

                taskCount--; // Уменьшаем количество задач
                i--; // Смещаем индекс на одну позицию назад для проверки следующей задачи
            }
        }

    }

    // Метод 10: Добавление задачи в первый доступный временной интервал
    public void addTaskInFirstAvailableSlot(int duration, String name) {
        String availableStart = "09:00"; // Начало рабочего времени

        // Если задач нет, добавляем с 09:00
        if (taskCount == 0) {
            if (isAvailableTimeSlot(availableStart, "21:00", duration)) {
                addTask(availableStart, duration, name); // Добавляем задачу
                return;
            }
        }

        // Ищем первый доступный временной интервал
        for (int i = 0; i < taskCount; i++) {
            Task currentTask = tasks[i];
            String currentTaskEndTime = currentTask.getEndTime();

           // проверяется, начинается ли текущая задача после того времени, которое обозначили как availableStart.
            if (currentTask.getStartTime().compareTo(availableStart) > 0) {
                // проверяется, есть ли достаточно времени
                // для добавления новой задачи между availableStart и временем начала текущей задачи.
                if (isAvailableTimeSlot(availableStart, currentTask.getStartTime(), duration)) {
                    addTask(availableStart, duration, name);
                    return;
                }
            }
            //Добавление новой задачи
            availableStart = currentTaskEndTime;
        }

        // Проверка последнего интервала
        // проверяет, можно ли разместить задачу в оставшемся времени между текущим
        // доступным временем (availableStart) и временем закрытия рабочего дня (21:00)
        if (isAvailableTimeSlot(availableStart, "21:00", duration)) {
            addTask(availableStart, duration, name); // Добавляем задачу
        } else {
            System.out.println("Не удалось найти доступный временной интервал для задачи.");
        }
    }

    // Дополнительные вспомогательные методы для проверки и обработки времени

    // Проверка на пересечение времени двух задач
    private boolean isOverlapping(Task task1, Task task2) {
        String endTime1 = task1.getEndTime();
        String endTime2 = task2.getEndTime();
        // Проверка на пересечение задач
        return !(task2.getStartTime().compareTo(endTime1) >= 0 || task1.getStartTime().compareTo(endTime2) >= 0);
    }

    // Метод для проверки доступности временного интервала
    private boolean isAvailableTimeSlot(String startTime, String endTime, int duration) {
        int startTotalMinutes = TimeUtils.timeToMinutes(startTime);
        int endTotalMinutes = TimeUtils.timeToMinutes(endTime);

        // Проверка, что промежуток времени достаточен для размещения задачи
        return (endTotalMinutes - startTotalMinutes) >= duration;
    }

    // Сортировка задач по времени начала
    private void sortTasks() {
        for (int i = 0; i < taskCount - 1; i++) {
            for (int j = 0; j < taskCount - i - 1; j++) {
                // Сортировка задач по времени начала
                if (tasks[j].getStartTime().compareTo(tasks[j + 1].getStartTime()) > 0) {
                    Task temp = tasks[j];
                    tasks[j] = tasks[j + 1];
                    tasks[j + 1] = temp;
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Calendar calendar = new Calendar(); // Создаем объект календаря

        // Тест добавления задачи
        calendar.addTask("09:00", 30, "Task 1");
        calendar.addTask("09:45", 30, "Task 2");
        calendar.addTask("10:30", 45, "Task 3");

        // Проверка отображения всех задач
        calendar.displayTasks();

        // Тест нахождения самой длинной задачи
        calendar.longestTask();

        // Тест задач в определенном промежутке времени
        calendar.tasksInTimeRange("09:30", "10:00");

        // Тест задач, завершенных до указанного времени
        calendar.tasksCompletedBefore("10:00");

        // Тест удаления задачи по номеру
        calendar.removeTask(1); // Удаляем задачу с индексом 1
        calendar.displayTasks();

        // Тест удаления задач, начинающихся в определенном промежутке
        calendar.removeTasksStartingInRange("09:00", "10:00");
        calendar.displayTasks();

        // Тест удаления задач, которые пересекаются с промежутком времени
        calendar.removeTasksOverlapping("09:15", "10:00");
        calendar.displayTasks();

        // Тест удаления самых коротких задач
        calendar.removeShortestTasks();
        calendar.displayTasks();

        // Тест добавления задачи в первый доступный временной интервал
        calendar.addTaskInFirstAvailableSlot(30, "Task 4");
        calendar.displayTasks();
    }
}
