
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
