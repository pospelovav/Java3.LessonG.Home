/*
Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов с аннотациями @Test.
Для этого у него должен быть статический метод start(), которому в качестве параметра передается или объект типа Class, или имя класса.
Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой имеется, далее запущены методы с аннотациями @Test,
а по завершению всех тестов – метод с аннотацией @AfterSuite. К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10),
в соответствии с которыми будет выбираться порядок их выполнения, если приоритет одинаковый, то порядок не имеет значения.
Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре,
иначе необходимо бросить RuntimeException при запуске «тестирования».
Это домашнее задание никак не связано с темой тестирования через JUnit и использованием этой библиотеки, то есть проект пишется с нуля.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class MyTestClass {
    public static void start(Class c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Method[] methods = c.getDeclaredMethods();
        ArrayList<Method> listMethods = new ArrayList();
        Method methodBefore = null;
        Method methodAfter = null;
        Object objForTest = c.newInstance();

        for (Method o : methods) {
            if (o.isAnnotationPresent(Test.class)){
                listMethods.add(o);
            }

            if (o.isAnnotationPresent(BeforeSuite.class)){
                if (methodBefore == null){
                    methodBefore = o;
                } else {
                    throw new RuntimeException("Is many BeforeSuite methods");
                }
            }

            if (o.isAnnotationPresent(AfterSuite.class)){
                if (methodAfter == null){
                    methodAfter = o;
                } else {
                    throw new RuntimeException("Is many AfterSuite methods");
                }
            }
        }
        listMethods.sort(new Comparator<Method>() {             //сортировка методов по приоритету
            @Override
            public int compare(Method methodOne, Method methodTwo) {
                return methodTwo.getAnnotation(Test.class).priority() - methodOne.getAnnotation(Test.class).priority();
            }
        });

        listMethods.add(0, methodBefore);   //добавление Before метода в начало списка
        listMethods.add(methodAfter);              //добавление After метода в конец списка
        for (int i = 0; i < listMethods.size(); i++) {
            listMethods.get(i).invoke(objForTest, null);
            if (listMethods.get(i).isAnnotationPresent(Test.class)){
                System.out.println(", with priority: " + listMethods.get(i).getAnnotation(Test.class).priority());
            }
        }
    }
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class c = KitMethodsTest.class;
        start(c);
    }
}
