public class KitMethodsTest {
    @BeforeSuite
    public void init() {
        System.out.println("Init");
    }
//    @BeforeSuite
//    public void init2() {
//        System.out.println("Init");
//    }


    @Test(priority = 4)
    public void testOne() {
        System.out.print("TestOne");
    }

    @Test(priority = 7)
    public void testTwo() {
        System.out.print("TestTwo");
    }

    @Test(priority = 2)
    public void testThree() {
        System.out.print("TestThree");
    }

    @Test(priority = 4)
    public void testFour() {
        System.out.print("TestFour");
    }

    @Test(priority = 9)
    public void testFive() {
        System.out.print("TestFive");
    }


    @AfterSuite
    public void shutdown() {
        System.out.println("Shutdown");
    }
//    @AfterSuite
//    public void shutdown2() {
//        System.out.println("Shutdown2");
//    }
}
